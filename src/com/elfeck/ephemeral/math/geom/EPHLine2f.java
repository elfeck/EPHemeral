/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.math.geom;

import com.elfeck.ephemeral.math.EPHVec2f;


public class EPHLine2f {

	protected EPHVec2f base, direction;

	public EPHLine2f(EPHVec2f base, EPHVec2f direction) {
		this.base = base;
		this.direction = direction.normalize();
	}

	public float distanceToPoint(EPHVec2f point) {
		return new EPHVec2f((base.getX() - point.getX()) + ((base.getX() - point.getX()) * base.getX()) * base.getX(),
				(base.getY() - point.getY()) + ((base.getY() - point.getY()) * base.getY()) * base.getY()).length();
	}

	public float distanceToLine(EPHLine2f line) {
		if (direction.equals(line.direction)) return distanceToPoint(line.base);
		return -1;
	}

	public EPHVec2f getIntersectionPoint(EPHLine2f line) {
		if (distanceToLine(line) >= 0) return null;
		float lambda = ((line.base.getY() - base.getY()) * direction.getX() - (line.base.getX() - base.getX()) * direction.getY()) /
				(line.direction.getX() * direction.getY() - line.direction.getY() * direction.getX());
		return line.direction.copy().mulScalar(lambda).addVec2f(line.base);
	}

	public float getAngleTo(EPHLine2f line) {
		return direction.angle(line.direction);
	}

}
