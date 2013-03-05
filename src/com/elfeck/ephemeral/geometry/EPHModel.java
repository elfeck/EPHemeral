/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.geometry;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.glContext.EPHVertexArrayObject;
import com.elfeck.ephemeral.glContext.EPHVertexAttribute;


public class EPHModel {

	private EPHVertexArrayObject vao;
	private List<EPHVertexAttribute> attributes;

	private List<EPHDrawable> drawables;
	private EPHSurface surface;

	public EPHModel(EPHSurface surface) {
		this.surface = surface;
		vao = null;
		attributes = new ArrayList<EPHVertexAttribute>();
		drawables = new ArrayList<EPHDrawable>();
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

	public void addDrawable(EPHDrawable drawable) {
		drawables.add(drawable);
	}

	public void create() {
		vao = new EPHVertexArrayObject(attributes);
		for (EPHDrawable drawable : drawables) {
			drawable.addDataToVao(vao);
		}
	}

	public void setViewPort(int x, int y, int width, int height) {
		vao.setViewportRect(x, y, width, height);
	}

	public void setClippingPane(int x, int y, int width, int height) {
		vao.setScissorRect(x, y, width, height);
	}

	public void removeFromVao(EPHDrawable drawable) {
		drawable.removeDataFromVao(vao);
	}

	public void addToVao(EPHDrawable drawable) {
		drawable.addDataToVao(vao);
	}

	public void addToSurface() {
		surface.addVao(vao);
	}

}
