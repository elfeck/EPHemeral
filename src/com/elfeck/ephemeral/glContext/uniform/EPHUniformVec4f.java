/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import com.elfeck.ephemeral.math.EPHVec4f;


public class EPHUniformVec4f extends EPHVec4f implements EPHUniformVecf {

	private boolean modified;

	public EPHUniformVec4f(float x, float y, float z, float w) {
		super(x, y, z, w);
		modified = true;
	}

	public EPHUniformVec4f() {
		this(0, 0, 0, 0);
	}

	@Override
	public void glUploadUniformContent(String name, int programHandle) {
		if (modified) glUniform4f(glGetUniformLocation(programHandle, name), x, y, z, w);
		modified = false;
	}

	@Override
	public void setN(int index, float value) {
		modified = true;
		super.setN(index, value);
	}

	@Override
	public void addToN(int index, float value) {
		modified = true;
		super.addToN(index, value);
	}

	@Override
	public EPHVec4f addVec4f(EPHVec4f vec) {
		modified = true;
		return super.addVec4f(vec);
	}

	@Override
	public EPHVec4f addVec4f(float vx, float vy, float vz, float vw) {
		modified = true;
		return super.addVec4f(vx, vy, vz, vw);
	}

	@Override
	public EPHVec4f subVec4f(EPHVec4f vec) {
		modified = true;
		return super.subVec4f(vec);
	}

	@Override
	public EPHVec4f subVec4f(float vx, float vy, float vz, float vw) {
		modified = true;
		return super.subVec4f(vx, vy, vz, vw);
	}

	@Override
	public EPHVec4f mulVec4f(EPHVec4f vec) {
		modified = true;
		return super.mulVec4f(vec);
	}

	@Override
	public EPHVec4f mulVec4f(float vx, float vy, float vz, float vw) {
		modified = true;
		return super.mulVec4f(vx, vy, vz, vw);
	}

	@Override
	public EPHVec4f mulScalar(float scalar) {
		modified = true;
		return super.mulScalar(scalar);
	}

	@Override
	public EPHVec4f normalize() {
		modified = true;
		return super.normalize();
	}

	@Override
	public EPHVec4f negate() {
		modified = true;
		return super.negate();
	}

	@Override
	public EPHVec4f toLength(float newLength) {
		modified = true;
		return super.toLength(newLength);
	}

	@Override
	public EPHVec4f setX(float x) {
		modified = true;
		return super.setX(x);
	}

	@Override
	public EPHVec4f setY(float y) {
		modified = true;
		return super.setY(y);
	}

	@Override
	public EPHVec4f setZ(float z) {
		modified = true;
		return super.setZ(z);
	}

	@Override
	public EPHVec4f setW(float w) {
		modified = true;
		return super.setW(w);
	}

	@Override
	public EPHVec4f setXYZW(float x, float y, float z, float w) {
		modified = true;
		return super.setXYZW(x, y, z, w);
	}

}
