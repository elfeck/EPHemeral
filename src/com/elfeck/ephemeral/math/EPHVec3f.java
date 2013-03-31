/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.math;

import java.util.List;

import com.elfeck.ephemeral.glContext.uniform.EPHUniformVecf;


public class EPHVec3f implements EPHVecf {

	protected float x, y, z;

	public EPHVec3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public EPHVec3f() {
		x = 0;
		y = 0;
		z = 0;
	}

	@Override
	public float getN(int index) {
		if (index == 0) return x;
		if (index == 1) return y;
		if (index == 2) return z;
		return Float.NaN;

	}

	@Override
	public void setN(int index, float value) {
		if (index == 0) x = value;
		if (index == 1) y = value;
		if (index == 2) z = value;
	}

	@Override
	public void addToN(int index, float value) {
		if (index == 0) x += value;
		if (index == 1) y += value;
		if (index == 2) z += value;
	}

	@Override
	public void fetchData(List<Float> list) {
		list.add(x);
		list.add(y);
		list.add(z);
	}

	@Override
	public float[] toArray() {
		return new float[] { x, y, z };
	}

	@Override
	public EPHVec2f toVec2f() {
		return stripZ();
	}

	@Override
	public int getDimension() {
		return 3;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EPHVec3f)) return false;
		EPHVec3f vec = (EPHVec3f) obj;
		return x == vec.x && y == vec.y && z == vec.z;
	}

	@Override
	public EPHUniformVecf asUniformVecf() {
		return new EPHUniformVecf(this);
	}

	public EPHVec3f addVec3f(EPHVec3f vec) {
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}

	public EPHVec3f addVec3f(float vx, float vy, float vz) {
		x += vx;
		y += vy;
		z += vz;
		return this;
	}

	public EPHVec3f subVec3f(EPHVec3f vec) {
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		return this;
	}

	public EPHVec3f subVec3f(float vx, float vy, float vz) {
		x -= vx;
		y -= vy;
		z -= vz;
		return this;
	}

	public EPHVec3f mulVec3f(EPHVec3f vec) {
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}

	public EPHVec3f mulVec3f(float vx, float vy, float vz) {
		x *= vx;
		y *= vy;
		z *= vz;
		return this;
	}

	public EPHVec3f mulScalar(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	public float dot(EPHVec3f vec) {
		return x * vec.x + y * vec.y + z * vec.z;
	}

	public float dot(float vx, float vy, float vz) {
		return x * vx + y * vy + z * vz;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public EPHVec3f normalize() {
		float oldLength = length();
		x /= oldLength;
		y /= oldLength;
		z /= oldLength;
		return this;
	}

	public EPHVec3f negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	public EPHVec3f toLength(float newLength) {
		return this.normalize().mulScalar(newLength);
	}

	public float angle(EPHVec3f vec) {
		return (float) Math.acos(dot(vec) / (length() * vec.length()));
	}

	public float angle(float vx, float vy, float vz) {
		return (float) Math.acos(dot(vx, vy, vz) / length() * length(vx, vy, vz));
	}

	public float distance(EPHVec3f vec) {
		return length(x - vec.x, y - vec.y, z - vec.z);
	}

	public float distance(float vx, float vy, float vz) {
		return length(x - vx, y - vy, z - vz);
	}

	// public float distance(EPHVec3f vec1, EPHVec3f vec2) {
	// return length((vec1.x - x) - ((vec1.x - x) * (vec2.x / vec2.length())) *
	// (vec2.x / vec2.length()),
	// (vec1.y - y) - ((vec1.y - y) * (vec2.y / vec2.length())) * (vec2.y /
	// vec2.length()),
	// (vec1.z - z) - ((vec1.z - z) * (vec2.z / vec2.length())) * (vec2.z /
	// vec2.length()));
	// }
	//
	// public float distance(float a1, float a2, float a3, float b1, float b2,
	// float b3) {
	// return length((a1 - x) - ((a1 - x) * (b1 / length(b1, b2, b3))) * (b1 /
	// length(b1, b2, b3)),
	// (a2 - y) - ((a2 - y) * (b2 / length(b1, b2, b3))) * (b2 / length(b1, b2,
	// b3)),
	// (a3 - z) - ((a3 - z) * (b3 / length(b1, b2, b3))) * (b3 / length(b1, b2,
	// b3)));
	// }

	public EPHVec2f stripZ() {
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

	public EPHVec3f setX(float x) {
		this.x = x;
		return this;
	}

	public EPHVec3f setY(float y) {
		this.y = y;
		return this;
	}

	public EPHVec3f setZ(float z) {
		this.z = z;
		return this;
	}

	public EPHVec3f setXYZ(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public EPHVec3f copy() {
		return new EPHVec3f(x, y, z);
	}

	public String toString() {
		return "(" + x + "  " + y + "  " + z + ")";
	}

	public static EPHVec3f addVec3f(EPHVec3f v1, EPHVec3f v2) {
		return new EPHVec3f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}

	public static EPHVec3f subVec3f(EPHVec3f v1, EPHVec3f v2) {
		return new EPHVec3f(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
	}

	public static EPHVec3f mulVec3f(EPHVec3f v1, EPHVec3f v2) {
		return new EPHVec3f(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
	}

	public static EPHVec3f mulScalar(EPHVec3f vec, float scalar) {
		return new EPHVec3f(vec.x * scalar, vec.y * scalar, vec.z * scalar);
	}

	public static EPHVec3f normalize(EPHVec3f vec) {
		return new EPHVec3f(vec.x, vec.y, vec.z).normalize();
	}

	public static float length(float vx, float vy, float vz) {
		return (float) Math.sqrt(vx * vx + vy * vy + vz * vz);
	}

}
