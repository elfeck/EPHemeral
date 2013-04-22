/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.math.geom;

import com.elfeck.ephemeral.math.EPHVec2f;

public class EPHPolygon2f {

	protected EPHVec2f[] vertices;

	public EPHVec2f[] getVertices() {
		return vertices;
	}

	public EPHPolygon2f(EPHVec2f[] points) {
		this.vertices = points;
	}

	public EPHVec2f projectOntoAxis(EPHVec2f axis) {
		float min = vertices[0].dot(axis);
		float max = min;
		float dot;
		for (int i = 1; i < vertices.length; i++) {
			dot = vertices[i].dot(axis);
			if (dot < min) min = dot;
			if (dot > max) max = dot;
		}
		return new EPHVec2f(min, max);
	}

	public int getVertexCount() {
		return vertices.length;
	}

}
