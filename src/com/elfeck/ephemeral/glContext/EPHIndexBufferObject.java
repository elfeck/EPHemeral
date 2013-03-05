/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import static org.lwjgl.opengl.GL15.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;


public class EPHIndexBufferObject {

	private int handle;
	private IntBuffer indexBuffer;
	private List<Integer> indices;

	protected EPHIndexBufferObject(List<Integer> indices) {
		handle = -1;
		indexBuffer = EPHRenderUtils.listToBufferi(indices);
		this.indices = indices;
	}

	protected EPHIndexBufferObject() {
		handle = -1;
		indexBuffer = null;
		indices = new ArrayList<Integer>();
	}

	protected void glInit(int usage) {
		if (handle < 0) handle = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, usage);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	protected void glBind() {
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle);
	}

	protected void glDispose() {
		glDeleteBuffers(handle);
	}

	protected void addData(List<Integer> newIndices, int offset) {
		for (int i = 0; i < newIndices.size(); i++) {
			indices.add(newIndices.get(i) + offset);
		}
		indexBuffer = EPHRenderUtils.listToBufferi(indices);
	}

	protected void removeData(int lowerBound, int upperBound, int offset) {
		for (int i = upperBound; i >= lowerBound; i--) {
			indices.remove(i);
		}
		for (int i = lowerBound; i < indices.size(); i++) {
			indices.add(i, indices.get(i) - offset);
			indices.remove(i + 1);
		}
		indexBuffer = EPHRenderUtils.listToBufferi(indices);
	}

	protected int getCurrentIndex() {
		return indices.size();
	}

}
