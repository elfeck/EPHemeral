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

<<<<<<< HEAD
	private int handle;
	private boolean updated;
	private IntBuffer indexBuffer;
=======
	private int handle, updateOffset;
	private boolean updated;
	private IntBuffer indexBuffer, updateBuffer;
>>>>>>> refs/heads/vao_problem
	private List<Integer> indices;

	protected EPHIndexBufferObject(List<Integer> indices) {
		handle = -1;
<<<<<<< HEAD
=======
		updateOffset = -1;
>>>>>>> refs/heads/vao_problem
		updated = false;
		indexBuffer = EPHRenderUtils.listToBufferi(indices);
		updateBuffer = null;
		this.indices = indices;
	}

	protected EPHIndexBufferObject() {
		handle = -1;
<<<<<<< HEAD
=======
		updateOffset = -1;
>>>>>>> refs/heads/vao_problem
		updated = false;
		indexBuffer = null;
		updateBuffer = null;
		indices = new ArrayList<Integer>();
	}

	protected void glInit(int usage) {
		if (handle < 0) handle = glGenBuffers();
<<<<<<< HEAD
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, usage);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
=======
		if (updateOffset < 0) {
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexBuffer, usage);
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			System.out.println("Init IBO");
		} else {
			System.out.println("Else :(");
			// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, handle);
			// glBufferSubData(GL_ELEMENT_ARRAY_BUFFER, updateOffset,
			// updateBuffer);
			// glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
			// updateBuffer = null;
		}
		updateOffset = -1;
>>>>>>> refs/heads/vao_problem
		updated = true;
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
<<<<<<< HEAD
=======
		updateOffset = -1;
>>>>>>> refs/heads/vao_problem
		updated = false;
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
<<<<<<< HEAD
=======
		updateOffset = -1;
		updated = false;
	}

	protected void updateData(int lowerBound, int upperBound, List<Integer> newIndices) {
		for (int i = lowerBound; i <= upperBound; i++) {
			indices.set(i, newIndices.get(i - lowerBound));
		}
		updateBuffer = EPHRenderUtils.listToBufferi(newIndices);
		updateOffset = lowerBound * 4;
>>>>>>> refs/heads/vao_problem
		updated = false;
	}

	protected int getCurrentIndex() {
		return indices.size();
	}

	protected boolean isUpdated() {
		return updated;
	}

}
