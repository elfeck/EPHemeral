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

import com.elfeck.ephemeral.glContext.uniform.EPHUniformObject;


public class EPHVertexArrayObject {

	private static EPHShaderProgramPool shaderPrograms;

	private boolean dead, updated;
	private int mode, size, usage, handle, currentKey;
	private List<EPHVaoEntry> entries;
	private EPHVertexBufferObject vbo;
	private EPHIndexBufferObject ibo;
	private EPHUniformBufferObject ubo;
	
	public EPHVertexArrayObject(int mode, int usage, List<EPHVertexAttribute> vertexAttributes, List<EPHUniformObject> uniforms) {
		dead = false;
		updated = false;
		this.mode = mode;
		this.size = 0;
		this.usage = usage;
		handle = -1;
		currentKey = 0;
		entries = new ArrayList<EPHVaoEntry>();
		vbo = new EPHVertexBufferObject(vertexAttributes);
		ibo = new EPHIndexBufferObject();
		ubo = new EPHUniformBufferObject(uniforms);
	}

	public EPHVertexArrayObject(List<EPHVertexAttribute> vertexAttributes, List<EPHUniformObject> uniforms) {
		this(EPHRenderUtils.TYPE_TRIANGLES, EPHRenderUtils.MODE_STATIC_DRAW, vertexAttributes, uniforms);
	}

	private void glInit() {
		if (handle < 0) handle = glGenVertexArrays();
		if(!shaderPrograms.isInitialized()) shaderPrograms.glInit();
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
			for(int i = 0; i < entries.size(); i++) {
				current = entries.get(i);
				next = i < entries.size() - 1 ? entries.get(i + 1) : null;
				currentProgram = shaderPrograms.getShaderProgram(current.programKey);
				if(!currentProgram.isLinked()) currentProgram.glAttachAndLinkProgram(vertexAttributesToMap(vbo.getVertexAttributes()));
				if(bindRequ) currentProgram.glBind();
				ubo.glUseUniforms(currentProgram.getProgramHandle(), current.uniformKey);
				glDrawElements(mode, current.iboUpperBound - current.iboLowerBound + 1, GL_UNSIGNED_INT, current.iboLowerBound * 4);
				bindRequ = next == null ? true : current.programKey.equals(next.programKey) ? false : true;
				if(bindRequ) currentProgram.glUnbind();
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
		EPHVaoEntry entry = new EPHVaoEntry(vbo.getCurrentIndex(), vbo.getCurrentIndex() + vertexValues.size() - 1,
				ibo.getCurrentIndex(), ibo.getCurrentIndex() + indices.size() - 1,
				currentKey++, programKey);
		ibo.addData(indices, vbo.addData(vertexValues));
		size += indices.size();
		entries.add(entry);
		updated = false;
		return entry;
	}
	
	public void removeData(EPHVaoEntry entry) {
		ibo.removeData(entry.iboLowerBound, entry.iboUpperBound, vbo.removeData(entry.vboLowerBound, entry.vboUpperBound));
		ubo.removeUniformEntry(entry.uniformKey);
		size -= entry.iboUpperBound - entry.iboLowerBound + 1;
		int deletedVertexValues = entry.vboUpperBound - entry.vboLowerBound + 1;
		int deletedIndices = entry.iboUpperBound - entry.iboLowerBound + 1;
		for(int i = entries.indexOf(entry) + 1; i < entries.size(); i++) {
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
	
	protected static void initShaderProgramPool() {
		shaderPrograms = new EPHShaderProgramPool();
	}
	
	public static void glDisposeShaderPrograms() {
		shaderPrograms.glDisposeShaderPrograms();
	}

}
