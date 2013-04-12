/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EPHVertexArrayObject {

	protected static EPHShaderProgramPool shaderProgramPool;
	private static int uniformKey = 0;

	private boolean dead;
	private int mode, size, usage, handle;
	private int[] viewPortRect, scissorRect;
	private List<EPHVaoEntry> entries;
	private EPHVertexBufferObject vbo;
	private EPHIndexBufferObject ibo;

	public EPHVertexArrayObject(int mode, int usage, int[] viewPortRect, int[] scissorRect, List<EPHVertexAttribute> vertexAttributes) {
		dead = false;
		this.mode = mode;
		this.size = 0;
		this.usage = usage;
		this.viewPortRect = viewPortRect;
		this.scissorRect = scissorRect;
		handle = -1;
		entries = new ArrayList<EPHVaoEntry>();
		vbo = new EPHVertexBufferObject(vertexAttributes);
		ibo = new EPHIndexBufferObject();
	}

	public EPHVertexArrayObject(int[] viewPortRect, int[] scissorRect, List<EPHVertexAttribute> vertexAttributes) {
		this(EPHRenderUtils.TYPE_TRIANGLES, EPHRenderUtils.MODE_STATIC_DRAW, viewPortRect, scissorRect, vertexAttributes);
	}

	public EPHVertexArrayObject(int mode, List<EPHVertexAttribute> vertexAttributes) {
		this(mode, EPHRenderUtils.MODE_STATIC_DRAW, EPHRenderContext.getWindowDimensions(), EPHRenderContext.getWindowDimensions(), vertexAttributes);
	}

	public EPHVertexArrayObject(List<EPHVertexAttribute> vertexAttributes) {
		this(EPHRenderUtils.TYPE_TRIANGLES, vertexAttributes);
	}

	private void glInit() {
		if (handle < 0) handle = glGenVertexArrays();
		glUpdateVbo();
		glUpdateIbo();
		glBindVertexArray(handle);
		vbo.glBind();
		ibo.glBind();
		glBindVertexArray(0);
	}

	private void glUpdateVbo() {
		vbo.glInit(usage);
	}

	private void glUpdateIbo() {
		ibo.glInit(usage);
	}

	private Map<Integer, String> vertexAttributesToMap(List<EPHVertexAttribute> vertexAttributes) {
		Map<Integer, String> names = new HashMap<Integer, String>();
		for (EPHVertexAttribute va : vertexAttributes) {
			names.put(va.index, va.name);
		}
		return names;
	}

	private boolean hasVisibleEntries() {
		boolean hasVisibleEntries = false;
		for (EPHVaoEntry entry : entries) {
			if (entry.isVisible()) {
				hasVisibleEntries = true;
				break;
			}
		}
		return hasVisibleEntries;
	}

	private synchronized void checkGlInit() {
		if (!EPHRenderContext.isInitialized()) {
			synchronized (EPHRenderContext.glInitMonitor) {
				try {
					EPHRenderContext.glInitMonitor.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	protected synchronized EPHVaoEntryDataSet addData(EPHVaoEntry entry, List<Float> newVertexValues, List<Integer> newIndices) {
		checkGlInit();
		if (entry.iboLowerBound < 0) {
			entry.iboLowerBound = ibo.getCurrentIndex();
			entry.iboUpperBound = ibo.getCurrentIndex() - 1;
			entry.vboLowerBound = vbo.getCurrentIndex();
			entry.vboUpperBound = vbo.getCurrentIndex() - 1;
		}
		vbo.addData(newVertexValues, entry.vboUpperBound + 1);
		ibo.addData(newIndices, entry.iboUpperBound + 1, (entry.vboUpperBound + 1) / vbo.getStride(), newVertexValues.size() / vbo.getStride());
		for (EPHVaoEntry other : entries) {
			if (other.vboLowerBound > entry.vboLowerBound) {
				other.vboLowerBound += newVertexValues.size();
				other.vboUpperBound += newVertexValues.size();
			}
			if (other.iboLowerBound > entry.iboLowerBound) {
				other.iboLowerBound += newIndices.size();
				other.iboUpperBound += newIndices.size();
			}
		}
		EPHVaoEntryDataSet dataSet = new EPHVaoEntryDataSet(entry.vboUpperBound - entry.vboLowerBound + 1, entry.vboUpperBound - entry.vboLowerBound
				+ newVertexValues.size(), entry.iboUpperBound - entry.iboLowerBound + 1, entry.iboUpperBound - entry.iboLowerBound + newIndices.size());
		entry.vboUpperBound += newVertexValues.size();
		entry.iboUpperBound += newIndices.size();
		size += newIndices.size();
		return dataSet;
	}

	protected synchronized void removeData(EPHVaoEntry entry, int vboLower, int vboUpper, int iboLower, int iboUpper) {
		checkGlInit();
		int deletedVertexValues = vboUpper - vboLower + 1;
		int deletedIndices = iboUpper - iboLower + 1;
		vbo.removeData(vboLower, vboUpper);
		ibo.removeData(iboLower, iboUpper, deletedVertexValues / vbo.getStride());
		entry.vboUpperBound -= deletedVertexValues;
		entry.iboUpperBound -= deletedIndices;
		if (entry.iboUpperBound < 0) {
			entry.iboLowerBound = -1;
			entry.vboLowerBound = -1;
		}
		for (EPHVaoEntry other : entries) {
			if (other.vboLowerBound > entry.vboLowerBound) {
				other.vboLowerBound -= deletedVertexValues;
				other.vboUpperBound -= deletedVertexValues;
			}
			if (other.iboLowerBound > entry.iboLowerBound) {
				other.iboLowerBound -= deletedIndices;
				other.iboUpperBound -= deletedIndices;
			}
		}
		size -= deletedIndices;
	}

	protected synchronized void updateVboData(int vboLower, int vboUpper, List<Float> vertexValues) {
		checkGlInit();
		vbo.updateData(vboLower, vboUpper, vertexValues);
	}

	public synchronized void glRender() {
		if (handle < 0) glInit();
		if (!vbo.isUpdated()) glUpdateVbo();
		if (!ibo.isUpdated()) glUpdateIbo();
		if (size > 0 && hasVisibleEntries()) {
			glBindVertexArray(handle);
			glViewport(viewPortRect[0], viewPortRect[1], viewPortRect[2], viewPortRect[3]);
			glScissor(scissorRect[0], scissorRect[1], scissorRect[2], scissorRect[3]);
			EPHShaderProgram currentProgram = null;
			EPHVaoEntry current, next;
			boolean bindRequ = true;
			for (int i = 0; i < entries.size(); i++) {
				current = entries.get(i);
				if (!current.visible || current.iboLowerBound < 0) continue;
				next = i < entries.size() - 1 ? entries.get(i + 1) : null;
				currentProgram = shaderProgramPool.getShaderProgram(current.programKey);
				if (!currentProgram.isLinked()) currentProgram.glAttachAndLinkProgram(vertexAttributesToMap(vbo.getVertexAttributes()));
				if (bindRequ) currentProgram.glBind();
				currentProgram.glUseUniforms(current.uniformKey);
				glDrawElements(mode, current.iboUpperBound - current.iboLowerBound + 1, GL_UNSIGNED_INT, current.iboLowerBound * 4);
				bindRequ = next == null ? true : current.programKey.equals(next.programKey) ? false : true;
				if (bindRequ) currentProgram.glUnbind();
			}
			glBindVertexArray(0);
		}
	}

	public synchronized EPHVaoEntry addEntry(String programKey) {
		checkGlInit();
		EPHVaoEntry entry = new EPHVaoEntry(this);
		entry.programKey = programKey;
		entry.uniformKey = uniformKey++;
		entry.shaderUniforms = shaderProgramPool.getShaderProgram(programKey).getShaderUniforms();
		entries.add(entry);
		return entry;
	}

	public synchronized void removeEntry(EPHVaoEntry entry) {
		checkGlInit();
		removeData(entry, 0, entry.vboUpperBound - entry.vboLowerBound, 0, entry.iboUpperBound - entry.iboLowerBound);
		entries.remove(entry);
	}

	public void glDispose() {
		vbo.glDispose();
		ibo.glDispose();
		glDeleteBuffers(handle);
	}

	public synchronized void setViewportRect(int[] bounds) {
		viewPortRect = bounds;
	}

	public synchronized void setScissorRect(int[] bounds) {
		scissorRect = bounds;
	}

	public synchronized void resetViewPortAndScissor() {
		viewPortRect = EPHRenderContext.getWindowDimensions();
		scissorRect = EPHRenderContext.getWindowDimensions();
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	protected static void glInitShaderProgramPool(String parentPath) {
		shaderProgramPool = new EPHShaderProgramPool(parentPath);
		shaderProgramPool.glInit();
	}

	public static void glDisposeShaderPrograms() {
		shaderProgramPool.glDisposeShaderPrograms();
	}

}
