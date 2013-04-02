/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import com.elfeck.ephemeral.math.EPHVec3f;


public class EPHUniformVec3f extends EPHVec3f implements EPHUniformVecf {

	private boolean modified;

	public EPHUniformVec3f(float x, float y, float z) {
		super(x, y, z);
		modified = true;
	}

	public EPHUniformVec3f() {
		this(0, 0, 0);
	}

	@Override
	public void glUploadUniformContent(String name, int programHandle) {
		if (modified) glUniform3f(glGetUniformLocation(programHandle, name), x, y, z);
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
	public EPHVec3f addVec3f(EPHVec3f vec) {
		modified = true;
		return super.addVec3f(vec);
	}

	@Override
	public EPHVec3f addVec3f(float vx, float vy, float vz) {
		modified = true;
		return super.addVec3f(vx, vy, vz);
	}

	@Override
	public EPHVec3f subVec3f(EPHVec3f vec) {
		modified = true;
		return super.subVec3f(vec);
	}

	@Override
	public EPHVec3f subVec3f(float vx, float vy, float vz) {
		modified = true;
		return super.subVec3f(vx, vy, vz);
	}

	@Override
	public EPHVec3f mulVec3f(EPHVec3f vec) {
		modified = true;
		return super.mulVec3f(vec);
	}

	@Override
	public EPHVec3f mulVec3f(float vx, float vy, float vz) {
		modified = true;
		return super.mulVec3f(vx, vy, vz);
	}

	@Override
	public EPHVec3f mulScalar(float scalar) {
		modified = true;
		return super.mulScalar(scalar);
	}

	@Override
	public EPHVec3f normalize() {
		modified = true;
		return super.normalize();
	}

	@Override
	public EPHVec3f negate() {
		modified = true;
		return super.negate();
	}

	@Override
	public EPHVec3f toLength(float newLength) {
		modified = true;
		return super.toLength(newLength);
	}

	@Override
	public EPHVec3f setX(float x) {
		modified = true;
		return super.setX(x);
	}

	@Override
	public EPHVec3f setY(float y) {
		modified = true;
		return super.setY(y);
	}

	@Override
	public EPHVec3f setZ(float z) {
		modified = true;
		return super.setZ(z);
	}

	@Override
	public EPHVec3f setXYZ(float x, float y, float z) {
		modified = true;
		return super.setXYZ(x, y, z);
	}

}
