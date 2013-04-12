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

	private int handle, updateOffset;
	private boolean updated;
	private IntBuffer indexBuffer, updateBuffer;
	private List<Integer> indices;

	protected EPHIndexBufferObject(List<Integer> indices) {
		handle = -1;
		updateOffset = -1;
		updated = false;
		indexBuffer = EPHRenderUtils.listToBufferi(indices);
		updateBuffer = null;
		this.indices = indices;
	}

	protected EPHIndexBufferObject() {
		handle = -1;
		updateOffset = -1;
		updated = false;
		indexBuffer = null;
		updateBuffer = null;
		indices = new ArrayList<Integer>();
	}

	protected void glInit(int usage) {
		if (handle < 0) handle = glGenBuffers();
		if (indices.size() <= 0) return;
		if (updateOffset < 0) {
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, usage);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		} else {
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle);
			glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, updateOffset, updateBuffer);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			updateBuffer = null;
		}
		updateOffset = -1;
		updated = true;
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
		indexBuffer = EPHRenderUtils.listToBufferi(indices);
		updateOffset = -1;
		updated = false;
	}

	protected void removeData(int lowerBound, int upperBound, int offset) {
		for (int i = upperBound; i >= lowerBound; i--) {
			indices.remove(i);
		}
		for (int i = lowerBound; i < indices.size(); i++) {
			indices.set(i, indices.get(i) - offset);
		}
		indexBuffer = EPHRenderUtils.listToBufferi(indices);
		updateOffset = -1;
		updated = false;
	}

	/* protected void updateData(int lowerBound, int upperBound, List<Integer> newIndices) {
		// workaround to find the index offset
		// Problem will occur if the smallest possible index (within the area to replace) doesn't occur. Then the offset is to high.
		int offset = Integer.MAX_VALUE;
		for (int i = lowerBound; i <= upperBound; i++) {
			offset = Math.min(offset, indices.get(i));
		}
		for (int i = 0; i < newIndices.size(); i++) {
			newIndices.set(i, newIndices.get(i) + offset);
		}
		for (int i = lowerBound; i <= upperBound; i++) {
			indices.set(i, newIndices.get(i - lowerBound));
		}
		updateBuffer = EPHRenderUtils.listToBufferi(newIndices);
		updateOffset = lowerBound * 4;
		updated = false;
	} */

	protected int getCurrentIndex() {
		return indices.size();
	}

	protected boolean isUpdated() {
		return updated;
	}

}
