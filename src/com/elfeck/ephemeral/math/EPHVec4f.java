/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.math;

import java.util.List;


public class EPHVec4f implements EPHVecf {

	protected float x, y, z, w;

	public EPHVec4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public EPHVec4f() {
		this(0, 0, 0, 0);
	}

	public EPHVec4f(EPHVec2f vec, float z, float w) {
		this(vec.x, vec.y, z, w);
	}

	@Override
	public float getN(int index) {
		if (index == 0) return x;
		if (index == 1) return y;
		if (index == 2) return z;
		if (index == 3) return w;
		return Float.NaN;

	}

	@Override
	public void setN(int index, float value) {
		if (index == 0) x = value;
		if (index == 1) y = value;
		if (index == 2) z = value;
		if (index == 3) w = value;
	}

	@Override
	public void addToN(int index, float value) {
		if (index == 0) x += value;
		if (index == 1) y += value;
		if (index == 2) z += value;
		if (index == 3) w += value;
	}

	@Override
	public void fetchData(List<Float> list) {
		list.add(x);
		list.add(y);
		list.add(z);
		list.add(w);
	}

	@Override
	public float[] toArray() {
		return new float[] { x, y, z, w };
	}

	@Override
	public EPHVec2f toVec2f() {
		return stripZW();
	}

	@Override
	public int getDimension() {
		return 4;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EPHVec4f)) return false;
		EPHVec4f vec = (EPHVec4f) obj;
		return x == vec.x && y == vec.y && z == vec.z && w == vec.w;
	}

	public EPHVec4f addVec4f(EPHVec4f vec) {
		x += vec.x;
		y += vec.y;
		z += vec.z;
		w += vec.w;
		return this;
	}

	public EPHVec4f addVec4f(float vx, float vy, float vz, float vw) {
		x += vx;
		y += vy;
		z += vz;
		w += vw;
		return this;
	}

	public EPHVec4f subVec4f(EPHVec4f vec) {
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		w -= vec.w;
		return this;
	}

	public EPHVec4f subVec4f(float vx, float vy, float vz, float vw) {
		x -= vx;
		y -= vy;
		z -= vz;
		w -= vw;
		return this;
	}

	public EPHVec4f mulVec4f(EPHVec4f vec) {
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		w *= vec.w;
		return this;
	}

	public EPHVec4f mulVec4f(float vx, float vy, float vz, float vw) {
		x *= vx;
		y *= vy;
		z *= vz;
		w *= vw;
		return this;
	}

	public EPHVec4f mulScalar(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		w *= scalar;
		return this;
	}

	public float dot(EPHVec4f vec) {
		return x * vec.x + y * vec.y + z * vec.z + w * vec.w;
	}

	public float dot(float vx, float vy, float vz, float vw) {
		return x * vx + y * vy + z * vz + w * vw;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public EPHVec4f normalize() {
		float oldLength = length();
		x /= oldLength;
		y /= oldLength;
		z /= oldLength;
		w /= oldLength;
		return this;
	}

	public EPHVec4f negate() {
		x = -x;
		y = -y;
		z = -z;
		w = -w;
		return this;
	}

	public EPHVec4f toLength(float newLength) {
		return this.normalize().mulScalar(newLength);
	}

	public float angle(EPHVec4f vec) {
		return (float) Math.acos(dot(vec) / (length() * vec.length()));
	}

	public float angle(float vx, float vy, float vz, float vw) {
		return (float) Math.acos(dot(vx, vy, vz, vw) / length() * length(vx, vy, vz, vw));
	}

	public float distance(EPHVec4f vec) {
		return length(x - vec.x, y - vec.y, z - vec.z, w - vec.w);
	}

	public float distance(float vx, float vy, float vz, float vw) {
		return length(x - vx, y - vy, z - vz, w - vw);
	}

	// public float distance(EPHVec4f vec1, EPHVec4f vec2) {
	// return length((vec1.x - x) - ((vec1.x - x) * (vec2.x / vec2.length())) *
	// (vec2.x / vec2.length()),
	// (vec1.y - y) - ((vec1.y - y) * (vec2.y / vec2.length())) * (vec2.y /
	// vec2.length()),
	// (vec1.z - z) - ((vec1.z - z) * (vec2.z / vec2.length())) * (vec2.z /
	// vec2.length()),
	// (vec1.w - w) - ((vec1.w - w) * (vec2.w / vec2.length())) * (vec2.w /
	// vec2.length()));
	// }
	//
	// public float distance(float a1, float a2, float a3, float a4, float b1,
	// float b2, float b3, float b4) {
	// return length((a1 - x) - ((a1 - x) * (b1 / length(b1, b2, b3, b4))) * (b1
	// / length(b1, b2, b3, b4)),
	// (a2 - y) - ((a2 - y) * (b2 / length(b1, b2, b3, b4))) * (b2 / length(b1,
	// b2, b3, b4)),
	// (a3 - z) - ((a3 - z) * (b3 / length(b1, b2, b3, b4))) * (b3 / length(b1,
	// b2, b3, b4)),
	// (a4 - w) - ((a4 - w) * (b4 / length(b1, b2, b3, b4))) * (b4 / length(b1,
	// b2, b3, b4)));
	// }

	public EPHVec3f stripW() {
		return new EPHVec3f(x, y, z);
	}

	public EPHVec2f stripZW() {
		return new EPHVec2f(x, y);
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getZ() {
		return z;
	}

	public float getW() {
		return w;
	}

	public EPHVec4f setX(float x) {
		this.x = x;
		return this;
	}

	public EPHVec4f setY(float y) {
		this.y = y;
		return this;
	}

	public EPHVec4f setZ(float z) {
		this.z = z;
		return this;
	}

	public EPHVec4f setW(float w) {
		this.w = w;
		return this;
	}

	public EPHVec4f setXYZW(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	public EPHVec4f copy() {
		return new EPHVec4f(x, y, z, w);
	}

	public String toString() {
		return "(" + x + "  " + y + "  " + z + "  " + w + ")";
	}

	public static EPHVec4f addVec4f(EPHVec4f v1, EPHVec4f v2) {
		return new EPHVec4f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z, v1.w + v2.w);
	}

	public static EPHVec4f subVec4f(EPHVec4f v1, EPHVec4f v2) {
		return new EPHVec4f(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z, v1.w - v2.w);
	}

	public static EPHVec4f mulVec4f(EPHVec4f v1, EPHVec4f v2) {
		return new EPHVec4f(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z, v1.w * v2.w);
	}

	public static EPHVec4f mulScalar(EPHVec4f vec, float scalar) {
		return new EPHVec4f(vec.x * scalar, vec.y * scalar, vec.z * scalar, vec.w * scalar);
	}

	public static EPHVec4f normalize(EPHVec4f vec) {
		return new EPHVec4f(vec.x, vec.y, vec.z, vec.w).normalize();
	}

	public static float length(float vx, float vy, float vz, float vw) {
		return (float) Math.sqrt(vx * vx + vy * vy + vz * vz + vw * vw);
	}

}
