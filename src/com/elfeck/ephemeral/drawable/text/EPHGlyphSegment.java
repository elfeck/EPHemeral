/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.drawable.text;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.math.EPHVec2f;


public class EPHGlyphSegment {

	private int windingRule, quadRes, cubicRes;
	private EPHVec2f currentVertex;
	private List<EPHVec2f> vertices;

	protected EPHGlyphSegment(int windingRule, float[] currentVertexData) {
		this.windingRule = windingRule;
		quadRes = 10;
		cubicRes = 10;
		currentVertex = new EPHVec2f(currentVertexData[0], currentVertexData[1]);
		vertices = new ArrayList<EPHVec2f>();
		vertices.add(currentVertex);
	}

	private float b(float t, float n, float m) {
		return (float) (c(n, m) * Math.pow(t, m) * Math.pow((1 - t), (n - m)));
	}

	private float c(float n, float m) {
		return smallFactorial(n) / (smallFactorial(m) * smallFactorial(n - m));
	}

	private float smallFactorial(float n) {
		float result = 1;
		for (int i = 1; i <= n; i++)
			result *= i;
		return result;
	}

	protected void addLineSegment(float[] data) {
		vertices.add(currentVertex = new EPHVec2f(data[0], data[1]));
	}

	protected void addQuadCurveSegment(float[] data) {
		EPHVec2f base, first, second;
		for (float t = 0; t <= 1; t += 1.0f / quadRes) {
			base = currentVertex.copy().mulScalar(b(t, 2, 0));
			first = new EPHVec2f(data[0], data[1]).mulScalar(b(t, 2, 1));
			second = new EPHVec2f(data[2], data[3]).mulScalar(b(t, 2, 2));
			vertices.add(currentVertex = base.addVec2f(first).addVec2f(second));
		}
	}

	protected void addCubicCurveSegment(float[] data) {
		EPHVec2f base, first, second, third;
		for (float t = 0; t <= 1; t += 1.0f / cubicRes) {
			base = currentVertex.copy().mulScalar(b(t, 3, 0));
			first = new EPHVec2f(data[0], data[1]).mulScalar(b(t, 3, 1));
			second = new EPHVec2f(data[2], data[3]).mulScalar(b(t, 3, 2));
			third = new EPHVec2f(data[4], data[5]).mulScalar(b(t, 3, 3));
			vertices.add(currentVertex = base.addVec2f(first).addVec2f(second).addVec2f(third));
		}
	}

	public EPHVec2f[] getShape() {
		return vertices.toArray(new EPHVec2f[vertices.size()]);
	}

}
