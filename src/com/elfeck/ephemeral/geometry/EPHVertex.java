package com.elfeck.ephemeral.geometry;

import java.util.List;

import com.elfeck.ephemeral.math.EPHVecf;


/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public class EPHVertex {

	private EPHVecf[] data;
	private int index;

	public EPHVertex(int index, EPHVecf[] data) {
		this.data = data;
		this.index = index;
	}

	protected void fetchFloatData(List<Float> vertexValues) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].getDimension(); j++) {
				vertexValues.add(data[i].getN(j));
			}
		}
	}

	protected void fetchIndexData(List<Integer> indices) {
		indices.add(index);
	}

	protected int getIndex() {
		return index;
	}

}
