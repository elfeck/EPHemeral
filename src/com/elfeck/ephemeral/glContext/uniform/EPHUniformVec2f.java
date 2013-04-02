/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import com.elfeck.ephemeral.math.EPHVec2f;


public class EPHUniformVec2f extends EPHVec2f implements EPHUniformVecf {

	private boolean modified;

	public EPHUniformVec2f(float x, float y) {
		super(x, y);
		modified = true;
	}

	public EPHUniformVec2f() {
		this(0, 0);
	}

	@Override
	public void glUploadUniformContent(String name, int programHandle) {
		if (modified) glUniform2f(glGetUniformLocation(programHandle, name), x, y);
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
	public EPHVec2f addVec2f(EPHVec2f vec) {
		modified = true;
		return super.addVec2f(vec);
	}

	@Override
	public EPHVec2f addVec2f(float vx, float vy) {
		modified = true;
		return super.addVec2f(vx, vy);
	}

	@Override
	public EPHVec2f subVec2f(EPHVec2f vec) {
		modified = true;
		return super.subVec2f(vec);
	}

	@Override
	public EPHVec2f subVec2f(float vx, float vy) {
		modified = true;
		return super.subVec2f(vx, vy);
	}

	@Override
	public EPHVec2f mulVec2f(EPHVec2f vec) {
		modified = true;
		return super.mulVec2f(vec);
	}

	@Override
	public EPHVec2f mulVec2f(float vx, float vy) {
		modified = true;
		return super.mulVec2f(vx, vy);
	}

	@Override
	public EPHVec2f mulScalar(float scalar) {
		modified = true;
		return super.mulScalar(scalar);
	}

	@Override
	public EPHVec2f normalize() {
		modified = true;
		return super.normalize();
	}

	@Override
	public EPHVec2f negate() {
		modified = true;
		return super.negate();
	}

	@Override
	public EPHVec2f toLength(float newLength) {
		modified = true;
		return super.toLength(newLength);
	}

	@Override
	public EPHVec2f setX(float x) {
		modified = true;
		return super.setX(x);
	}

	@Override
	public EPHVec2f setY(float y) {
		modified = true;
		return super.setY(y);
	}

	@Override
	public EPHVec2f setXY(float x, float y) {
		modified = true;
		return super.setXY(x, y);
	}

}
