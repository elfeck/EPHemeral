/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.math;

import java.util.List;

import com.elfeck.ephemeral.glContext.uniform.EPHUniformVecf;


public class EPHVec2f implements EPHVecf {

	protected float x, y;

	public EPHVec2f(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public EPHVec2f() {
		x = 0;
		y = 0;
	}

	@Override
	public float getN(int index) {
		if (index == 0) return x;
		if (index == 1) return y;
		return Float.NaN;

	}

	@Override
	public void setN(int index, float value) {
		if (index == 0) x = value;
		if (index == 1) y = value;
	}

	@Override
	public void addToN(int index, float value) {
		if (index == 0) x += value;
		if (index == 1) y += value;
	}

	@Override
	public void fetchData(List<Float> list) {
		list.add(x);
		list.add(y);
	}

	@Override
	public float[] toArray() {
		return new float[] { x, y };
	}

	@Override
	public EPHVec2f toVec2f() {
		return this;
	}

	@Override
	public int getDimension() {
		return 2;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EPHVec2f)) return false;
		EPHVec2f vec = (EPHVec2f) obj;
		return x == vec.x && y == vec.y;
	}

	@Override
	public EPHUniformVecf asUniformVecf() {
		return new EPHUniformVecf(this);
	}

	public EPHVec2f addVec2f(EPHVec2f vec) {
		x += vec.x;
		y += vec.y;
		return this;
	}

	public EPHVec2f addVec2f(float vx, float vy) {
		x += vx;
		y += vy;
		return this;
	}

	public EPHVec2f subVec2f(EPHVec2f vec) {
		x -= vec.x;
		y -= vec.y;
		return this;
	}

	public EPHVec2f subVec2f(float vx, float vy) {
		x -= vx;
		y -= vy;
		return this;
	}

	public EPHVec2f mulVec2f(EPHVec2f vec) {
		x *= vec.x;
		y *= vec.y;
		return this;
	}

	public EPHVec2f mulVec2f(float vx, float vy) {
		x *= vx;
		y *= vy;
		return this;
	}

	public EPHVec2f mulScalar(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	public float dot(EPHVec2f vec) {
		return x * vec.x + y * vec.y;
	}

	public float dot(float vx, float vy) {
		return x * vx + y * vy;
	}

	public float length() {
		return (float) Math.hypot(x, y);
	}

	public EPHVec2f normalize() {
		float oldLength = length();
		x /= oldLength;
		y /= oldLength;
		return this;
	}

	public EPHVec2f negate() {
		x = -x;
		y = -y;
		return this;
	}

	public EPHVec2f toLength(float newLength) {
		return this.normalize().mulScalar(newLength);
	}

	public float angle(EPHVec2f vec) {
		return (float) Math.acos(dot(vec) / (length() * vec.length()));
	}

	public float angle(float vx, float vy) {
		return (float) Math.acos(dot(vx, vy) / length() * length(vx, vy));
	}

	public float distance(EPHVec2f vec) {
		return length(x - vec.x, y - vec.y);
	}

	public float distance(float vx, float vy) {
		return length(x - vx, y - vy);
	}

	public EPHVec2f getPerpendicular() {
		return new EPHVec2f(-y, x);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public EPHVec2f setX(float x) {
		this.x = x;
		return this;
	}

	public EPHVec2f setY(float y) {
		this.y = y;
		return this;
	}

	public EPHVec2f setXY(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}

	public EPHVec2f copy() {
		return new EPHVec2f(x, y);
	}

	public String toString() {
		return "(" + x + "  " + y + ")";
	}

	public static EPHVec2f addVec2f(EPHVec2f v1, EPHVec2f v2) {
		return new EPHVec2f(v1.x + v2.x, v1.y + v2.y);
	}

	public static EPHVec2f subVec2f(EPHVec2f v1, EPHVec2f v2) {
		return new EPHVec2f(v1.x - v2.x, v1.y - v2.y);
	}

	public static EPHVec2f mulVec2f(EPHVec2f v1, EPHVec2f v2) {
		return new EPHVec2f(v1.x * v2.x, v1.y * v2.y);
	}

	public static EPHVec2f mulScalar(EPHVec2f vec, float scalar) {
		return new EPHVec2f(vec.x * scalar, vec.y * scalar);
	}

	public static EPHVec2f normalize(EPHVec2f vec) {
		return new EPHVec2f(vec.x, vec.y).normalize();
	}

	public static float length(float vx, float vy) {
		return (float) Math.hypot(vx, vy);
	}

}
