/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.test;

import java.util.List;

import com.elfeck.ephemeral.drawable.EPHDisplayable;
import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.ephemeral.math.EPHVec4f;


public class TestQuad implements EPHDisplayable {

	protected float layer;
	protected int width, height;
	protected EPHVec2f position;
	protected EPHVec4f color;

	public TestQuad(float x, float y, int width, int height, float layer, EPHVec4f color) {
		this.width = width;
		this.height = height;
		position = new EPHVec2f(x, y);
		this.layer = layer;
		this.color = color;
	}

	@Override
	public float getLayer() {
		return layer;
	}

	public void fetchVertexData(List<Float> vertexValues) {
		for (int i = 0; i < 4; i++) {
			if (i == 0) {
				vertexValues.add(position.getX());
				vertexValues.add(height + position.getY());
			}
			if (i == 1) {
				vertexValues.add(width + position.getX());
				vertexValues.add(height + position.getY());
			}
			if (i == 2) {
				vertexValues.add(width + position.getX());
				vertexValues.add(position.getY());
			}
			if (i == 3) {
				vertexValues.add(position.getX());
				vertexValues.add(position.getY());
			}
			vertexValues.add(layer);
			vertexValues.add(1f);
			color.fetchData(vertexValues);
			addAdditionalData(vertexValues);
		}
	}

	protected void addAdditionalData(List<Float> vertexValues) {

	}

	public static void fetchIndices(int offset, List<Integer> indices) {
		indices.add(0 + offset);
		indices.add(1 + offset);
		indices.add(2 + offset);
		indices.add(2 + offset);
		indices.add(3 + offset);
		indices.add(0 + offset);
	}
}
