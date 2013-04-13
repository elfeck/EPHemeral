/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.test;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.glContext.EPHVaoBuilder;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.EPHVaoEntryDataSet;
import com.elfeck.ephemeral.glContext.EPHVao;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformMat4f;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec2f;
import com.elfeck.ephemeral.math.EPHVec4f;


public class EPHTestEntity implements EPHEntity {

	private EPHSurface surface;
	private EPHUniformMat4f mvpMatrix;
	private EPHUniformVec2f offset;
	private EPHVec4f color;
	private EPHVao vao;

	public EPHTestEntity(EPHSurface surface) {
		this.surface = surface;
		mvpMatrix = new EPHUniformMat4f(new float[][] { { 2.0f / EPHTest.WIDTH, 0, 0, 0 }, { 0, 2.0f / EPHTest.HEIGHT, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } });
		offset = new EPHUniformVec2f(0, 0);
		color = new EPHVec4f(0.8f, 0.4f, 0.1f, 1f);
		vao = null;
		init();
	}

	private void init() {
		EPHVaoBuilder builder = new EPHVaoBuilder();
		builder.addAttribute(4, "vertex_position");
		builder.addAttribute(4, "vertex_color");
		vao = builder.create();
		surface.addVao(vao);
	}

	@SuppressWarnings("unused")
	private EPHVaoEntry createEntry() {
		EPHVaoEntry entry = vao.addEntry("test");
		entry.registerUniformEntry("mvp_matrix", mvpMatrix);
		entry.registerUniformEntry("offset", offset);
		return entry;
	}

	@SuppressWarnings("unused")
	private EPHVaoEntryDataSet addQuad(float x, float y, EPHVaoEntry entry) {
		int size = 10;
		EPHTestQuad quad = new EPHTestQuad(x, y, size, size, 0.2f, color);
		List<Float> vertexValues = new ArrayList<Float>();
		List<Integer> indices = new ArrayList<Integer>();
		quad.fetchVertexData(vertexValues);
		EPHTestQuad.fetchIndices(0, indices);
		return entry.addData(vertexValues, indices);
	}

	@SuppressWarnings("unused")
	private void updateQuad(float x, float y, EPHVaoEntry entry, EPHVaoEntryDataSet dataSet) {
		int size = 10;
		EPHTestQuad quad = new EPHTestQuad(x, y, size, size, 0.2f, color);
		List<Float> vertexValues = new ArrayList<Float>();
		quad.fetchVertexData(vertexValues);
		entry.updateVboData(dataSet, vertexValues);
	}

	EPHVaoEntry first, second;
	EPHVaoEntryDataSet first1Set, first2Set, first3Set, second1Set;

	private void tick(int tick) {
		System.out.println("Tick: " + tick);
		switch (tick) {

		}
	}

	int tick = 0;
	int timePassed = 0;

	@Override
	public void doLogic(long delta) {
		if (timePassed > 500) {
			tick(tick++);
			timePassed = 0;
		} else {
			timePassed += delta / 1e6;
		}
	}

	@Override
	public boolean isDead() {
		return false;
	}

}
