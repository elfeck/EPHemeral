package com.elfeck.ephemeral.geometry;

import java.util.List;


/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public class EPHTriangle {

	private EPHVertex[] vertices;

	public EPHTriangle(EPHVertex[] vertices) {
		this.vertices = vertices;
	}

	protected void fetchIndexData(List<Integer> indices) {
		for (int i = 0; i < vertices.length; i++) {
			vertices[i].fetchIndexData(indices);
		}
	}

	protected EPHVertex getVertex(int index) {
		return vertices[index];
	}

}
