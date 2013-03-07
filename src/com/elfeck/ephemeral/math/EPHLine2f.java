/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.math;

public class EPHLine2f {

	protected EPHVec2f base, direction;

	public EPHLine2f(EPHVec2f base, EPHVec2f direction) {
		this.base = base;
		this.direction = direction.normalize();
	}

	public float distanceToPoint(EPHVec2f point) {
		return new EPHVec2f((base.x - point.x) + ((base.x - point.x) * base.x) * base.x,
				(base.y - point.y) + ((base.y - point.y) * base.y) * base.y).length();
	}

	public float distanceToLine(EPHLine2f line) {
		if (direction.equals(line.direction)) return distanceToPoint(line.base);
		return -1;
	}

	public EPHVec2f getIntersectionPoint(EPHLine2f line) {
		if (distanceToLine(line) >= 0) return null;
		float lambda = ((line.base.y - base.y) * direction.x - (line.base.x - base.x) * direction.y) /
				(line.direction.x * direction.y - line.direction.y * direction.x);
		return line.direction.copy().mulScalar(lambda).addVec2f(line.base);
	}

	public float getAngleTo(EPHLine2f line) {
		return direction.angle(line.direction);
	}

}
