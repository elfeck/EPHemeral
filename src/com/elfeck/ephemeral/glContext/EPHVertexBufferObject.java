package com.elfeck.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;


public class EPHVertexBufferObject {

	private int handle;
	private FloatBuffer vertexBuffer;
	private List<Float> vertexValues;
	private List<EPHVertexAttribute> vertexAttributes;

	protected EPHVertexBufferObject(List<Float> vertexValues, List<EPHVertexAttribute> vertexAttributes) {
		handle = -1;
		vertexBuffer = EPHRenderUtils.listToBufferf(vertexValues);
		this.vertexValues = vertexValues;
		this.vertexAttributes = vertexAttributes;
	}

	protected EPHVertexBufferObject(List<EPHVertexAttribute> vertexAttributes) {
		handle = -1;
		vertexBuffer = null;
		vertexValues = new ArrayList<Float>();
		this.vertexAttributes = vertexAttributes;
	}

	private int computeStride() {
		int stride = 0;
		for (EPHVertexAttribute va : vertexAttributes) {
			stride += va.size;
		}
		return stride;
	}

	protected void glInit(int usage) {
		if (handle < 0) handle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, handle);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, usage);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}

	protected void glBind() {
		glBindBuffer(GL_ARRAY_BUFFER, handle);
		for (EPHVertexAttribute currentAttrib : vertexAttributes) {
			glEnableVertexAttribArray(currentAttrib.index);
			glVertexAttribPointer(currentAttrib.index, currentAttrib.size, currentAttrib.type, currentAttrib.normalized, currentAttrib.stride, currentAttrib.offset);
		}
	}

	protected void glDispose() {
		glDeleteBuffers(handle);
	}

	protected int addData(List<Float> newVertexValues) {
		vertexValues.addAll(newVertexValues);
		vertexBuffer = EPHRenderUtils.listToBufferf(vertexValues);
		return (vertexValues.size() - newVertexValues.size()) / computeStride();
	}
	
	protected int removeData(int lowerBound, int upperBound) {
		for(int i = upperBound; i >= lowerBound; i--) {
			vertexValues.remove(i);
		}
		vertexBuffer = EPHRenderUtils.listToBufferf(vertexValues);
		return (upperBound - lowerBound + 1) / computeStride();
	}

	protected int getCurrentIndex() {
		return vertexValues.size();
	}
	
	protected List<EPHVertexAttribute> getVertexAttributes() {
		return vertexAttributes;
	}

}
