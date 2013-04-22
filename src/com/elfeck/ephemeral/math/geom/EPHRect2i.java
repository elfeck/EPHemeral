/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

package com.elfeck.ephemeral.math.geom;

import com.elfeck.ephemeral.math.EPHVec2f;

public class EPHRect2i {

	private int[] bounds;

	public EPHRect2i(int x, int y, int width, int height) {
		bounds = new int[] { x, y, width, height };
	}

	public EPHRect2i() {
		this(0, 0, 0, 0);
	}

	public boolean withinBounds(int x, int y) {
		return x >= bounds[0] && x < bounds[0] + bounds[2] && y >= bounds[1] && y < bounds[1] + bounds[3];
	}

	public EPHVec2f getCenter() {
		return new EPHVec2f(bounds[0] + bounds[2] / 2.0f, bounds[1] + bounds[3] / 2.0f);
	}

	public void setCenter(int x, int y) {
		bounds[0] = x - bounds[2] / 2;
		bounds[1] = y - bounds[3] / 2;
	}

	public EPHVec2f[] getCornerPoints() {
		return new EPHVec2f[] {
								new EPHVec2f(bounds[0], bounds[1]),
								new EPHVec2f(bounds[0] + bounds[2], bounds[1]),
								new EPHVec2f(bounds[0], bounds[1] + bounds[3]),
								new EPHVec2f(bounds[0] + bounds[2], bounds[1] + bounds[3])
		};
	}

	public float[] toFloatArray() {
		return new float[] { bounds[0], bounds[1], bounds[2], bounds[3] };
	}

	public int[] asIntArray() {
		return bounds;
	}

	public int[] toIntArray() {
		return new int[] { (int) bounds[0], (int) bounds[1], (int) bounds[2], (int) bounds[3] };
	}

	public int getX() {
		return bounds[0];
	}

	public void setX(int x) {
		bounds[0] = x;
	}

	public int getY() {
		return bounds[1];
	}

	public void setY(int y) {
		bounds[1] = y;
	}

	public int getWidth() {
		return bounds[2];
	}

	public void setWidth(int width) {
		bounds[2] = width;
	}

	public int getHeight() {
		return bounds[3];
	}

	public void setHeight(int height) {
		bounds[3] = height;
	}

	public void setRect(int[] rect) {
		bounds = rect;
	}

}
