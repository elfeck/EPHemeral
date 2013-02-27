package com.elfeck.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public class EPHVertexAttribute {

	protected int index, size, type, stride, offset;
	protected boolean normalized;
	protected String name;

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

}
