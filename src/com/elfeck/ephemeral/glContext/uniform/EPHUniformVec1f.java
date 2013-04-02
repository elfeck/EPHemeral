/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import com.elfeck.ephemeral.math.EPHVec1f;


public class EPHUniformVec1f extends EPHVec1f implements EPHUniformVecf {

	private boolean modified;

	public EPHUniformVec1f(float x) {
		super(x);
		modified = true;
	}

	public EPHUniformVec1f() {
		this(0);
	}

	@Override
	public void glUploadUniformContent(String name, int programHandle) {
		if (modified) glUniform1f(glGetUniformLocation(programHandle, name), x);
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
	public void setX(float value) {
		modified = true;
		super.setX(value);
	}

}
