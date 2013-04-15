/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import static org.lwjgl.opengl.GL15.*;

import java.util.ArrayList;
import java.util.List;


public class EPHIbo {

	private boolean bufferSizeChanged;
	private int handle;
	private List<Integer> indices;

	protected EPHIbo(List<Integer> indices) {
		handle = -1;
		bufferSizeChanged = true;
		this.indices = indices;
	}

	protected EPHIbo() {
		this(new ArrayList<Integer>());
	}

	protected void glInit(int usage) {
		if (handle < 0) handle = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, EPHRenderUtils.listToBufferi(indices), usage);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		bufferSizeChanged = false;
	}

	protected void glBind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle);
	}

	protected void glDispose() {
		glDeleteBuffers(handle);
	}

	protected void addData(List<Integer> newIndices, int jumpIn, int offset, int vertexCount) {
		for (int i = 0; i < newIndices.size(); i++) {
			indices.add(i + jumpIn, newIndices.get(i) + offset);
		}
		for (int i = jumpIn + newIndices.size(); i < indices.size(); i++) {
			indices.set(i, indices.get(i) + vertexCount);
		}
		bufferSizeChanged = true;
	}

	protected void removeData(int lowerBound, int upperBound, int offset) {
		for (int i = upperBound; i >= lowerBound; i--) {
			indices.remove(i);
		}
		for (int i = lowerBound; i < indices.size(); i++) {
			indices.set(i, indices.get(i) - offset);
		}
		bufferSizeChanged = true;
	}

	protected int getCurrentIndex() {
		return indices.size();
	}

	protected boolean isUpdated() {
		return !bufferSizeChanged;
	}

}
