package de.greenOwlProduction.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;


public class GOPVertexBufferObject {

	private int handle;
	private FloatBuffer vertexBuffer;
	private List<Float> vertexValues;
	private List<GOPVertexAttribute> vertexAttributes;

	protected GOPVertexBufferObject(List<Float> vertexValues, List<GOPVertexAttribute> vertexAttributes) {
		handle = -1;
		vertexBuffer = GOPRenderUtils.listToBufferf(vertexValues);
		this.vertexValues = vertexValues;
		this.vertexAttributes = vertexAttributes;
	}

	protected GOPVertexBufferObject(List<GOPVertexAttribute> vertexAttributes) {
		handle = -1;
		vertexBuffer = null;
		vertexValues = new ArrayList<Float>();
		this.vertexAttributes = vertexAttributes;
	}

	private int computeStride() {
		int stride = 0;
		for (GOPVertexAttribute va : vertexAttributes) {
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
		for (GOPVertexAttribute currentAttrib : vertexAttributes) {
			glEnableVertexAttribArray(currentAttrib.index);
			glVertexAttribPointer(currentAttrib.index, currentAttrib.size, currentAttrib.type, currentAttrib.normalized, currentAttrib.stride, currentAttrib.offset);
		}
	}

	protected void glDispose() {
		glDeleteBuffers(handle);
	}

	protected int addData(List<Float> newVertexValues) {
		vertexValues.addAll(newVertexValues);
		vertexBuffer = GOPRenderUtils.listToBufferf(vertexValues);
		return (vertexValues.size() - newVertexValues.size()) / computeStride();
	}

	protected int removeData(int[] reference) {
		for (int i = reference[1]; i >= reference[0]; i--) {
			vertexValues.remove(i);
		}
		vertexBuffer = GOPRenderUtils.listToBufferf(vertexValues);
		return (reference[1] - reference[0] + 1) / computeStride();
	}

	protected int getCurrentIndex() {
		return vertexValues.size();
	}

}
