/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

package com.elfeck.ephemeral.math.geom;

import java.util.Arrays;

import com.elfeck.ephemeral.math.EPHVec2f;


public class EPHRect2f {

	private float[] bounds;

	public EPHRect2f(float x, float y, float width, float height) {
		bounds = new float[] { x, y, width, height };
	}

	public EPHRect2f() {
		this(0, 0, 0, 0);
	}

	public boolean withinBounds(float x, float y) {
		return x >= bounds[0] && x < bounds[0] + bounds[2] && y >= bounds[1] && y < bounds[1] + bounds[3];
	}

	public boolean withinBounds(EPHVec2f position) {
		return withinBounds(position.getX(), position.getY());
	}

	public EPHVec2f getCenter() {
		return new EPHVec2f(bounds[0] + bounds[2] / 2.0f, bounds[1] + bounds[3] / 2.0f);
	}

	public void setCenter(float x, float y) {
		bounds[0] = x - bounds[2] / 2.0f;
		bounds[1] = y - bounds[3] / 2.0f;
	}

	public void setCenter(EPHVec2f position) {
		setCenter(position.getX(), position.getY());
	}

	public EPHVec2f[] getCornerPoints() {
		return new EPHVec2f[] {
								new EPHVec2f(bounds[0], bounds[1]),
								new EPHVec2f(bounds[0] + bounds[2], bounds[1]),
								new EPHVec2f(bounds[0], bounds[1] + bounds[3]),
								new EPHVec2f(bounds[0] + bounds[2], bounds[1] + bounds[3])
		};
	}

	public float[] asFloatArray() {
		return bounds;
	}

	public float[] toFloatArray() {
		return Arrays.copyOf(bounds, bounds.length);
	}

	public int[] toIntArray() {
		return new int[] { (int) bounds[0], (int) bounds[1], (int) bounds[2], (int) bounds[3] };
	}

	public float getX() {
		return bounds[0];
	}

	public void setX(float x) {
		bounds[0] = x;
	}

	public float getY() {
		return bounds[1];
	}

	public void setY(float y) {
		bounds[1] = y;
	}

	public float getWidth() {
		return bounds[2];
	}

	public void setWidth(float width) {
		bounds[2] = width;
	}

	public float getHeight() {
		return bounds[3];
	}

	public void setHeight(float height) {
		bounds[3] = height;
	}

	public void setRect(float[] rect) {
		bounds = rect;
	}

}
