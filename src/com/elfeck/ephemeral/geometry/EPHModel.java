package com.elfeck.ephemeral.geometry;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.glContext.EPHVertexArrayObject;
import com.elfeck.ephemeral.glContext.EPHVertexAttribute;


public class EPHModel {

	private EPHVertexArrayObject vao;
	private List<EPHVertexAttribute> attributes;

	private List<EPHPolygon> polygons;
	private EPHSurface surface;

	public EPHModel(EPHSurface surface) {
		this.surface = surface;
		vao = null;
		attributes = new ArrayList<EPHVertexAttribute>();
		polygons = new ArrayList<EPHPolygon>();
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

	public void addPolygon(EPHPolygon polygon) {
		polygons.add(polygon);
	}

	public void create() {
		vao = new EPHVertexArrayObject(attributes);
		for (EPHPolygon polygon : polygons) {
			polygon.addDataToVao(vao);
		}
	}

	public void addToSurface() {
		surface.addVao(vao);
	}

}
