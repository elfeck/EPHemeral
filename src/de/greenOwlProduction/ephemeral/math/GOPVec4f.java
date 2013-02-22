package de.greenOwlProduction.ephemeral.math;

public class GOPVec4f implements GOPVecf {

	protected float x, y, z, w;

	public GOPVec4f(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	public GOPVec4f() {
		x = 0;
		y = 0;
		z = 0;
		w = 0;
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
			case 3:
				return w;
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
			case 3:
				w = value;
				break;
		}
	}

	@Override
	public float[] toArray() {
		return new float[] { x, y, z, w };
	}

	public GOPVec4f addVec4f(GOPVec4f vec) {
		x += vec.x;
		y += vec.y;
		z += vec.z;
		w += vec.w;
		return this;
	}

	public GOPVec4f addVec4f(float vx, float vy, float vz, float vw) {
		x += vx;
		y += vy;
		z += vz;
		w += vw;
		return this;
	}

	public GOPVec4f subVec4f(GOPVec4f vec) {
		x -= vec.x;
		y -= vec.y;
		z -= vec.z;
		w -= vec.w;
		return this;
	}

	public GOPVec4f subVec4f(float vx, float vy, float vz, float vw) {
		x -= vx;
		y -= vy;
		z -= vz;
		w -= vw;
		return this;
	}

	public GOPVec4f mulVec4f(GOPVec4f vec) {
		x *= vec.x;
		y *= vec.y;
		z *= vec.z;
		w *= vec.w;
		return this;
	}

	public GOPVec4f mulVec4f(float vx, float vy, float vz, float vw) {
		x *= vx;
		y *= vy;
		z *= vz;
		w *= vw;
		return this;
	}

	public GOPVec4f mulScalar(float scalar) {
		x *= scalar;
		y *= scalar;
		z *= scalar;
		w *= scalar;
		return this;
	}

	public float dot(GOPVec4f vec) {
		return x * vec.x + y * vec.y + z * vec.z + w * vec.w;
	}

	public float dot(float vx, float vy, float vz, float vw) {
		return x * vx + y * vy + z * vz + w * vw;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y + z * z + w * w);
	}

	public GOPVec4f normalize() {
		float oldLength = length();
		x /= oldLength;
		y /= oldLength;
		z /= oldLength;
		w /= oldLength;
		return this;
	}

	public GOPVec4f negate() {
		x = -x;
		y = -y;
		z = -z;
		w = -w;
		return this;
	}

	public GOPVec4f toLength(float newLength) {
		return this.normalize().mulScalar(newLength);
	}

	public float angle(GOPVec4f vec) {
		return (float) Math.acos(dot(vec) / (length() * vec.length()));
	}

	public float angle(float vx, float vy, float vz, float vw) {
		return (float) Math.acos(dot(vx, vy, vz, vw) / length() * length(vx, vy, vz, vw));
	}

	public float distance(GOPVec4f vec) {
		return length(x - vec.x, y - vec.y, z - vec.z, w - vec.w);
	}

	public float distance(float vx, float vy, float vz, float vw) {
		return length(x - vx, y - vy, z - vz, w - vw);
	}

	public float distance(GOPVec4f vec1, GOPVec4f vec2) {
		return length((vec1.x - x) - ((vec1.x - x) * (vec2.x / vec2.length())) * (vec2.x / vec2.length()),
				(vec1.y - y) - ((vec1.y - y) * (vec2.y / vec2.length())) * (vec2.y / vec2.length()),
				(vec1.z - z) - ((vec1.z - z) * (vec2.z / vec2.length())) * (vec2.z / vec2.length()),
				(vec1.w - w) - ((vec1.w - w) * (vec2.w / vec2.length())) * (vec2.w / vec2.length()));
	}

	public float distance(float a1, float a2, float a3, float a4, float b1, float b2, float b3, float b4) {
		return length((a1 - x) - ((a1 - x) * (b1 / length(b1, b2, b3, b4))) * (b1 / length(b1, b2, b3, b4)),
				(a2 - y) - ((a2 - y) * (b2 / length(b1, b2, b3, b4))) * (b2 / length(b1, b2, b3, b4)),
				(a3 - z) - ((a3 - z) * (b3 / length(b1, b2, b3, b4))) * (b3 / length(b1, b2, b3, b4)),
				(a4 - w) - ((a4 - w) * (b4 / length(b1, b2, b3, b4))) * (b4 / length(b1, b2, b3, b4)));
	}

	public GOPVec3f stripW() {
		return new GOPVec3f(x, y, z);
	}

	public GOPVec2f stripZW() {
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

	public float getW() {
		return w;
	}

	public GOPVec4f setX(float x) {
		this.x = x;
		return this;
	}

	public GOPVec4f setY(float y) {
		this.y = y;
		return this;
	}

	public GOPVec4f setZ(float z) {
		this.z = z;
		return this;
	}

	public GOPVec4f setW(float w) {
		this.w = w;
		return this;
	}

	public GOPVec4f setXYZW(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
		return this;
	}

	public GOPVec4f copy() {
		return new GOPVec4f(x, y, z, w);
	}

	public String toString() {
		return "(" + x + "  " + y + "  " + z + "  " + w + ")";
	}

	public static GOPVec4f addVec4f(GOPVec4f v1, GOPVec4f v2) {
		return new GOPVec4f(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z, v1.w + v2.w);
	}

	public static GOPVec4f subVec4f(GOPVec4f v1, GOPVec4f v2) {
		return new GOPVec4f(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z, v1.w - v2.w);
	}

	public static GOPVec4f mulVec4f(GOPVec4f v1, GOPVec4f v2) {
		return new GOPVec4f(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z, v1.w * v2.w);
	}

	public static GOPVec4f mulScalar(GOPVec4f vec, float scalar) {
		return new GOPVec4f(vec.x * scalar, vec.y * scalar, vec.z * scalar, vec.w * scalar);
	}

	public static GOPVec4f normalize(GOPVec4f vec) {
		return new GOPVec4f(vec.x, vec.y, vec.z, vec.w).normalize();
	}

	public static float length(float vx, float vy, float vz, float vw) {
		return (float) Math.sqrt(vx * vx + vy * vy + vz * vz + vw * vw);
	}
}
