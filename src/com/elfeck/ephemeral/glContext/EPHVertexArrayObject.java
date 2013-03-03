package com.elfeck.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EPHVertexArrayObject {

	private static EPHShaderProgramPool shaderProgramPool;
	private static boolean shaderPoolInitialized = false;

	private boolean dead, updated;

	private int mode, size, usage, handle, uniformKey;
	private List<EPHVaoEntry> entries;
	private EPHVertexBufferObject vbo;
	private EPHIndexBufferObject ibo;

	public EPHVertexArrayObject(int mode, int usage, List<EPHVertexAttribute> vertexAttributes) {
		dead = false;
		updated = false;
		this.mode = mode;
		this.size = 0;
		this.usage = usage;
		handle = -1;
		uniformKey = 0;
		entries = new ArrayList<EPHVaoEntry>();
		vbo = new EPHVertexBufferObject(vertexAttributes);
		ibo = new EPHIndexBufferObject();
	}

	public EPHVertexArrayObject(List<EPHVertexAttribute> vertexAttributes) {
		this(EPHRenderUtils.TYPE_TRIANGLES, EPHRenderUtils.MODE_STATIC_DRAW, vertexAttributes);
	}

	private void glInit() {
		if (handle < 0) handle = glGenVertexArrays();
		vbo.glInit(usage);
		ibo.glInit(usage);

		glBindVertexArray(handle);
		vbo.glBind();
		ibo.glBind();
		glBindVertexArray(0);
		updated = true;
	}

	private Map<Integer, String> vertexAttributesToMap(List<EPHVertexAttribute> vertexAttributes) {
		Map<Integer, String> names = new HashMap<Integer, String>();
		for (EPHVertexAttribute va : vertexAttributes) {
			names.put(va.index, va.name);
		}
		return names;
	}

	public void glRender() {
		if (handle < 0 || !updated) glInit();
		if (size > 0) {
			glBindVertexArray(handle);
			EPHShaderProgram currentProgram = null;
			EPHVaoEntry current, next;
			boolean bindRequ = true;
			for (int i = 0; i < entries.size(); i++) {
				current = entries.get(i);
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

	public void glDispose() {
		vbo.glDispose();
		ibo.glDispose();
		glDeleteBuffers(handle);
	}

	public EPHVaoEntry addData(List<Float> vertexValues, List<Integer> indices, String programKey) {
		synchronized (EPHRenderContext.initMonitor) {
			if (!shaderPoolInitialized) {
				try {
					EPHRenderContext.initMonitor.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		EPHVaoEntry entry = new EPHVaoEntry(vbo.getCurrentIndex(), vbo.getCurrentIndex() + vertexValues.size() - 1,
				ibo.getCurrentIndex(), ibo.getCurrentIndex() + indices.size() - 1,
				uniformKey++, programKey, shaderProgramPool.getShaderProgram(programKey).getUniformTemplateBffer());
		ibo.addData(indices, vbo.addData(vertexValues));
		size += indices.size();
		entries.add(entry);
		updated = false;
		return entry;
	}

	public void removeData(EPHVaoEntry entry) {
		ibo.removeData(entry.iboLowerBound, entry.iboUpperBound, vbo.removeData(entry.vboLowerBound, entry.vboUpperBound));
		size -= entry.iboUpperBound - entry.iboLowerBound + 1;
		int deletedVertexValues = entry.vboUpperBound - entry.vboLowerBound + 1;
		int deletedIndices = entry.iboUpperBound - entry.iboLowerBound + 1;
		for (int i = entries.indexOf(entry) + 1; i < entries.size(); i++) {
			EPHVaoEntry currentEntry = entries.get(i);
			currentEntry.vboLowerBound -= deletedVertexValues;
			currentEntry.vboUpperBound -= deletedVertexValues;
			currentEntry.iboLowerBound -= deletedIndices;
			currentEntry.iboUpperBound -= deletedIndices;
		}
		entries.remove(entry);
		updated = false;
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
		shaderPoolInitialized = true;
	}

	public static void glDisposeShaderPrograms() {
		shaderProgramPool.glDisposeShaderPrograms();
	}

}
