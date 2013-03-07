/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.drawable;

import java.util.List;


public class EPHTessellationTriangle {

	private EPHVertex[] vertices;

	public EPHTessellationTriangle(EPHVertex[] vertices) {
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
