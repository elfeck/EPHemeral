/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

package com.elfeck.ephemeral.glContext;

import java.util.ArrayList;
import java.util.List;



public class EPHVaoBuilder {

	private List<EPHVertexAttribute> attributes;

	public EPHVaoBuilder() {
		attributes = new ArrayList<EPHVertexAttribute>();
	}

	private void adjustAttributes() {
		int stride = 0;
		int offset = 0;
		for (int i = 0; i < attributes.size(); i++) {
			stride += attributes.get(i).getSize();
		}
		stride *= 4;
		EPHVertexAttribute current;
		for (int i = 0; i < attributes.size(); i++) {
			current = attributes.get(i);
			current.setStride(stride);
			current.setOffset(offset * 4);
			offset += current.getSize();
		}
	}

	public void addAttribute(int size, String name) {
		attributes.add(new EPHVertexAttribute(attributes.size(), size, 0, 0, name));
		adjustAttributes();
	}

	public EPHVao create() {
		return new EPHVao(attributes);
	}

	public EPHVao create(int mode, int usage) {
		return new EPHVao(mode, usage, attributes);
	}

}
