package com.elfeck.ephemeral.geometry;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public class EPHConvexPolygon extends EPHPolygon {

	public EPHConvexPolygon(String programKey, EPHVertex[] vertices) {
		super(programKey, vertices);
	}

	@Override
	protected void tessellate() {
		triangles = new EPHTriangle[vertices.length - 2];
		for (int i = 1; i < vertices.length - 1; i++) {
			triangles[i - 1] = new EPHTriangle(new EPHVertex[] { vertices[0], vertices[i], vertices[i + 1] });
		}
	}

}
