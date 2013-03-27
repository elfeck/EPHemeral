/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.drawable;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.EPHVertexArrayObject;
import com.elfeck.ephemeral.glContext.EPHVertexAttribute;


public class EPHModel {

	private EPHVertexArrayObject vao;
	private List<EPHVertexAttribute> attributes;

	public EPHModel() {
		vao = null;
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

	public void addToSurface(EPHSurface surface) {
		surface.addVao(vao);
	}

	public void addAttribute(int size, String name) {
		attributes.add(new EPHVertexAttribute(attributes.size(), size, 0, 0, name));
		adjustAttributes();
	}

	public void create() {
		vao = new EPHVertexArrayObject(attributes);
	}

	public void setViewPort(int[] bounds) {
		vao.setViewportRect(bounds);
	}

	public void setScissor(int[] bounds) {
		vao.setScissorRect(bounds);
	}

	public EPHVaoEntry addToVao(List<Float> vertexValues, List<Integer> indices, String programKey) {
		return vao.addData(vertexValues, indices, programKey);
	}

	public EPHVaoEntry addToVao(List<Float> vertexValues, List<Integer> indices, EPHVaoEntry entry) {
		return vao.addData(vertexValues, indices, entry);
	}

	public void removeFromVao(EPHVaoEntry entry) {
		vao.removeData(entry);
	}

	public void updateToVbo(EPHVaoEntry entry, int subLower, int subUpper, List<Float> vertexValues) {
		vao.updateVbo(entry, subLower, subUpper, vertexValues);
	}

	public void updateToIbo(EPHVaoEntry entry, int subLower, int subUpper, List<Integer> indices) {
		vao.updateIbo(entry, subLower, subUpper, indices);
	}

}