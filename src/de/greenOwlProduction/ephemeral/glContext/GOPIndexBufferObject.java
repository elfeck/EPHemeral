package de.greenOwlProduction.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import static org.lwjgl.opengl.GL15.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;


public class GOPIndexBufferObject {

	private int handle;
	private IntBuffer indexBuffer;
	private List<Integer> indices;

	protected GOPIndexBufferObject(List<Integer> indices) {
		handle = -1;
		indexBuffer = GOPRenderUtils.listToBufferi(indices);
		this.indices = indices;
	}

	protected GOPIndexBufferObject() {
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
		indexBuffer = GOPRenderUtils.listToBufferi(indices);
	}

	protected void removeData(int[] reference, int offset) {
		for (int i = reference[1]; i >= reference[0]; i--) {
			indices.remove(i);
		}
		for (int i = reference[0]; i < indices.size(); i++) {
			indices.add(i, indices.get(i) - offset);
			indices.remove(i + 1);
		}
		indexBuffer = GOPRenderUtils.listToBufferi(indices);
	}

	protected int getCurrentIndex() {
		return indices.size();
	}

}
