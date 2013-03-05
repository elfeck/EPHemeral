/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

public class EPHVertexAttribute {

	private int stride, offset;
	protected final int index, size, type;
	protected final boolean normalized;
	protected final String name;

	public EPHVertexAttribute(int index, int size, int type, int stride, int offset, boolean normalized, String name) {
		this.index = index;
		this.size = size;
		this.type = type;
		this.stride = stride;
		this.offset = offset;
		this.normalized = normalized;
		this.name = name;
	}

	public EPHVertexAttribute(int index, int size, int stride, int offset, String name) {
		this(index, size, EPHRenderUtils.TYPE_FLOAT, stride, offset, false, name);
	}

	public int getStride() {
		return stride;
	}

	public int getOffset() {
		return offset;
	}

	public int getSize() {
		return size;
	}

	public void setStride(int stride) {
		this.stride = stride;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

}
