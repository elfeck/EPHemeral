package de.greenOwlProduction.ephemeral.math;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public class GOPVec3f implements GOPVecf {

	protected float x, y, z;

	public GOPVec3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public GOPVec3f() {
		x = 0;
		y = 0;
		z = 0;
	}

	@Override
	public float getN(int index) {
		switch (index) {
			case 0:
				return x;
			case 1:
				return y;
			case 2:
				return z;
			default:
				return Float.NaN;
		}
	}

	@Override
	public void setN(int index, float value) {
		switch (index) {
			case 0:
				x = value;
				break;
			case 1:
				y = value;
				break;
			case 2:
				z = value;
				break;
		}
	}

	@Override
	public float[] toArray() {
		return new float[] { x, y, z };
	}

	public GOPVec3f addVec3f(GOPVec3f vec) {
		x += vec.x;
		y += vec.y;
		z += vec.z;
		return this;
	}

	public GOPVec3f addVec3f(float vx, float vy, float vz) {
		x += vx;
		y += vy;
		z += vz;
		return this;
	}

	public GOPVec3f subVec3f(GOPVec3f vec) {
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		return this;
	}

	public GOPVec3f subVec3f(float vx, float vy, float vz) {
		x -= vx;
		y -= vy;
		z -= vz;
		return this;
	}

	public GOPVec3f mulVec3f(GOPVec3f vec) {
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		return this;
	}

	public GOPVec3f mulVec3f(float vx, float vy, float vz) {
		x *= vx;
		y *= vy;
		z *= vz;
		return this;
	}

	public GOPVec3f mulScalar(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		return this;
	}

	public float dot(GOPVec3f vec) {
		return x * vec.x + y * vec.y + z * vec.z;
	}

	public float dot(float vx, float vy, float vz) {
		return x * vx + y * vy + z * vz;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z);
	}

	public GOPVec3f normalize() {
		float oldLength = length();
		x /= oldLength;
		y /= oldLength;
		z /= oldLength;
		return this;
	}

	public GOPVec3f negate() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}

	public GOPVec3f toLength(float newLength) {
		return this.normalize().mulScalar(newLength);
	}

	public float angle(GOPVec3f vec) {
		return (float) Math.acos(dot(vec) / (length() * vec.length()));
	}

	public float angle(float vx, float vy, float vz) {
		return (float) Math.acos(dot(vx, vy, vz) / length() * length(vx, vy, vz));
	}

	public float distance(GOPVec3f vec) {
		return length(x - vec.x, y - vec.y, z - vec.z);
	}

	public float distance(float vx, float vy, float vz) {
		return length(x - vx, y - vy, z - vz);
	}

	public float distance(GOPVec3f vec1, GOPVec3f vec2) {
		return length((vec1.x - x) - ((vec1.x - x) * (vec2.x / vec2.length())) * (vec2.x / vec2.length()),
				(vec1.y - y) - ((vec1.y - y) * (vec2.y / vec2.length())) * (vec2.y / vec2.length()),
				(vec1.z - z) - ((vec1.z - z) * (vec2.z / vec2.length())) * (vec2.z / vec2.length()));
	}

	public float distance(float a1, float a2, float a3, float b1, float b2, float b3) {
		return length((a1 - x) - ((a1 - x) * (b1 / length(b1, b2, b3))) * (b1 / length(b1, b2, b3)),
				(a2 - y) - ((a2 - y) * (b2 / length(b1, b2, b3))) * (b2 / length(b1, b2, b3)),
				(a3 - z) - ((a3 - z) * (b3 / length(b1, b2, b3))) * (b3 / length(b1, b2, b3)));
	}

	public GOPVec2f stripZ() {
		return new GOPVec2f(x, y);
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

	public GOPVec3f setX(float x) {
		this.x = x;
		return this;
	}

	public GOPVec3f setY(float y) {
		this.y = y;
		return this;
	}

	public GOPVec3f setZ(float z) {
		this.z = z;
		return this;
	}

	public GOPVec3f setXYZ(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public GOPVec3f copy() {
		return new GOPVec3f(x, y, z);
	}

	public String toString() {
		return "(" + x + "  " + y + "  " + z + ")";
	}

	public static GOPVec3f addVec3f(GOPVec3f v1, GOPVec3f v2) {
		return new GOPVec3f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
	}

	public static GOPVec3f subVec3f(GOPVec3f v1, GOPVec3f v2) {
		return new GOPVec3f(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
	}

	public static GOPVec3f mulVec3f(GOPVec3f v1, GOPVec3f v2) {
		return new GOPVec3f(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
	}

	public static GOPVec3f mulScalar(GOPVec3f vec, float scalar) {
		return new GOPVec3f(vec.x * scalar, vec.y * scalar, vec.z * scalar);
	}

	public static GOPVec3f normalize(GOPVec3f vec) {
		return new GOPVec3f(vec.x, vec.y, vec.z).normalize();
	}

	public static float length(float vx, float vy, float vz) {
		return (float) Math.sqrt(vx * vx + vy * vy + vz * vz);
	}

}
