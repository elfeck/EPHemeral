/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;


public class EPHVertexBufferObject {

<<<<<<< HEAD
	private int handle;
	private boolean updated;
	private FloatBuffer vertexBuffer;
=======
	private int handle, updateOffset;
	private boolean updated;
	private FloatBuffer vertexBuffer, updateBuffer;
>>>>>>> refs/heads/vao_problem
	private List<Float> vertexValues;
	private List<EPHVertexAttribute> vertexAttributes;

	protected EPHVertexBufferObject(List<Float> vertexValues, List<EPHVertexAttribute> vertexAttributes) {
		handle = -1;
<<<<<<< HEAD
=======
		updateOffset = -1;
>>>>>>> refs/heads/vao_problem
		updated = false;
		vertexBuffer = EPHRenderUtils.listToBufferf(vertexValues);
		updateBuffer = null;
		this.vertexValues = vertexValues;
		this.vertexAttributes = vertexAttributes;
	}

	protected EPHVertexBufferObject(List<EPHVertexAttribute> vertexAttributes) {
		handle = -1;
<<<<<<< HEAD
=======
		updateOffset = -1;
>>>>>>> refs/heads/vao_problem
		updated = false;
		vertexBuffer = null;
		updateBuffer = null;
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
<<<<<<< HEAD
		glBindBuffer(GL_ARRAY_BUFFER, handle);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, usage);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
=======
		if (updateOffset < 0) {
			glBindBuffer(GL_ARRAY_BUFFER, handle);
			glBufferData(GL_ARRAY_BUFFER, vertexBuffer, usage);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			System.out.println("Init VBO");
		} else {
			System.out.println("Else :(");
			// glBindBuffer(GL_ARRAY_BUFFER, handle);
			// glBufferSubData(GL_ARRAY_BUFFER, updateOffset, updateBuffer);
			// glBindBuffer(GL_ARRAY_BUFFER, 0);
			// updateBuffer = null;
		}
		updateOffset = -1;
>>>>>>> refs/heads/vao_problem
		updated = true;
	}

	protected void glBind() {
		glBindBuffer(GL_ARRAY_BUFFER, handle);
		for (EPHVertexAttribute currentAttrib : vertexAttributes) {
			glEnableVertexAttribArray(currentAttrib.index);
			glVertexAttribPointer(currentAttrib.index, currentAttrib.size, currentAttrib.type, currentAttrib.normalized,
					currentAttrib.getStride(), currentAttrib.getOffset());
		}
	}

	protected void glDispose() {
		glDeleteBuffers(handle);
	}

	protected int addData(List<Float> newVertexValues) {
		vertexValues.addAll(newVertexValues);
		vertexBuffer = EPHRenderUtils.listToBufferf(vertexValues);
<<<<<<< HEAD
=======
		updateOffset = -1;
>>>>>>> refs/heads/vao_problem
		updated = false;
		return (vertexValues.size() - newVertexValues.size()) / computeStride();
	}

	protected int removeData(int lowerBound, int upperBound) {
		for (int i = upperBound; i >= lowerBound; i--) {
			vertexValues.remove(i);
		}
		vertexBuffer = EPHRenderUtils.listToBufferf(vertexValues);
<<<<<<< HEAD
=======
		updateOffset = -1;
>>>>>>> refs/heads/vao_problem
		updated = false;
		return (upperBound - lowerBound + 1) / computeStride();
	}

	protected void updateData(int lowerBound, int upperBound, List<Float> newVertexValues) {
		for (int i = lowerBound; i <= upperBound; i++) {
			vertexValues.set(i, newVertexValues.get(i - lowerBound));
		}
		updateBuffer = EPHRenderUtils.listToBufferf(newVertexValues);
		updateOffset = lowerBound * 4;
		updated = false;
	}

	protected int getCurrentIndex() {
		return vertexValues.size();
	}

	protected List<EPHVertexAttribute> getVertexAttributes() {
		return vertexAttributes;
	}

	protected boolean isUpdated() {
		return updated;
	}

}
