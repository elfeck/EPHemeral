/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.test;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.drawable.EPHModel;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.EPHVaoEntryDataSet;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformMat4f;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec2f;
import com.elfeck.ephemeral.math.EPHVec4f;


public class EPHTestEntity implements EPHEntity {

	private EPHSurface surface;
	private EPHUniformMat4f mvpMatrix;
	private EPHUniformVec2f offset;
	private EPHVec4f color;
	private EPHModel model;

	public EPHTestEntity(EPHSurface surface) {
		this.surface = surface;
		mvpMatrix = new EPHUniformMat4f(new float[][] { { 2.0f / EPHTest.WIDTH, 0, 0, 0 }, { 0, 2.0f / EPHTest.HEIGHT, 0, 0 }, { 0, 0, 1, 0 }, { 0, 0, 0, 1 } });
		offset = new EPHUniformVec2f(0, 0);
		color = new EPHVec4f(0.8f, 0.4f, 0.1f, 1f);
		model = new EPHModel();
		init();
	}

	private void init() {
		model.addAttribute(4, "vertex_position");
		model.addAttribute(4, "vertex_color");
		model.create();
		model.addToSurface(surface);
	}

	@SuppressWarnings("unused")
	private EPHVaoEntry createEntry() {
		EPHVaoEntry entry = model.addEntry("test");
		entry.registerUniformEntry("mvp_matrix", mvpMatrix);
		entry.registerUniformEntry("offset", offset);
		return entry;
	}

	@SuppressWarnings("unused")
	private EPHVaoEntryDataSet addQuad(float x, float y, EPHVaoEntry entry) {
		int size = 10;
		TestQuad quad = new TestQuad(x, y, size, size, 0.2f, color);
		List<Float> vertexValues = new ArrayList<Float>();
		List<Integer> indices = new ArrayList<Integer>();
		quad.fetchVertexData(vertexValues);
		TestQuad.fetchIndices(0, indices);
		return entry.addData(vertexValues, indices);
	}

	@SuppressWarnings("unused")
	private void updateQuad(float x, float y, EPHVaoEntry entry, EPHVaoEntryDataSet dataSet) {
		int size = 10;
		TestQuad quad = new TestQuad(x, y, size, size, 0.2f, color);
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
