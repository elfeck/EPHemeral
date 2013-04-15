/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.List;


public class EPHVbo {

	private boolean bufferSizeChanged;
	private int handle, stride, lowerUpdate, upperUpdate;
	private List<Float> vertexValues;
	private List<EPHVertexAttribute> vertexAttributes;

	protected EPHVbo(List<Float> vertexValues, List<EPHVertexAttribute> vertexAttributes) {
		this.vertexValues = vertexValues;
		this.vertexAttributes = vertexAttributes;
		bufferSizeChanged = true;
		lowerUpdate = -1;
		upperUpdate = -1;
		handle = -1;
		stride = computeStride();
	}

	protected EPHVbo(List<EPHVertexAttribute> vertexAttributes) {
		this(new ArrayList<Float>(), vertexAttributes);
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
		if (bufferSizeChanged) {
			glBindBuffer(GL_ARRAY_BUFFER, handle);
			glBufferData(GL_ARRAY_BUFFER, EPHRenderUtils.listToBufferf(vertexValues), usage);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
		} else {
			glBindBuffer(GL_ARRAY_BUFFER, handle);
			glBufferSubData(GL_ARRAY_BUFFER, lowerUpdate * 4, EPHRenderUtils.listToBufferf(vertexValues.subList(lowerUpdate, upperUpdate)));
			glBindBuffer(GL_ARRAY_BUFFER, 0);
		}
		bufferSizeChanged = false;
		lowerUpdate = -1;
		upperUpdate = -1;
	}

	protected void glBind() {
		glBindBuffer(GL_ARRAY_BUFFER, handle);
		for (EPHVertexAttribute currentAttrib : vertexAttributes) {
			glEnableVertexAttribArray(currentAttrib.index);
			glVertexAttribPointer(currentAttrib.index, currentAttrib.size, currentAttrib.type, currentAttrib.normalized, currentAttrib.getStride(),
					currentAttrib.getOffset());
		}
	}

	protected void glDispose() {
		glDeleteBuffers(handle);
	}

	protected void addData(List<Float> newVertexValues, int jumpIn) {
		vertexValues.addAll(jumpIn, newVertexValues);
		bufferSizeChanged = true;
	}

	protected void removeData(int lowerBound, int upperBound) {
		for (int i = upperBound; i >= lowerBound; i--) {
			vertexValues.remove(i);
		}
		bufferSizeChanged = true;
	}

	protected void updateData(int lowerBound, int upperBound, List<Float> newVertexValues) {
		for (int i = lowerBound; i <= upperBound; i++) {
			vertexValues.set(i, newVertexValues.get(i - lowerBound));
		}
		lowerUpdate = lowerUpdate < 0 ? lowerBound : Math.min(lowerUpdate, lowerBound);
		upperUpdate = Math.max(upperUpdate, upperBound + 1);
	}

	protected int getCurrentIndex() {
		return vertexValues.size();
	}

	protected List<EPHVertexAttribute> getVertexAttributes() {
		return vertexAttributes;
	}

	protected boolean isUpdated() {
		return lowerUpdate < 0 && upperUpdate < 0 && !bufferSizeChanged;
	}

	protected int getStride() {
		return stride;
	}

}
