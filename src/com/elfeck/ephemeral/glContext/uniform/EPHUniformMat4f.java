/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import com.elfeck.ephemeral.math.EPHMat4f;
import com.elfeck.ephemeral.math.EPHVec4f;


public class EPHUniformMat4f extends EPHMat4f implements EPHUniformContent {

	private boolean modified;

	public EPHUniformMat4f(float[][] matrix) {
		super(matrix);
		modified = true;
	}

	public EPHUniformMat4f() {
		super();
		modified = true;
	}

	@Override
	public void glUploadUniformContent(String name, int programHandle) {
		if (modified) glUniformMatrix4(glGetUniformLocation(programHandle, name), false, toBuffer());
		modified = false;
	}

	@Override
	public EPHMat4f addMat4f(EPHMat4f mat) {
		modified = true;
		return super.addMat4f(mat);
	}

	@Override
	public EPHMat4f subMat4f(EPHMat4f mat) {
		modified = true;
		return super.subMat4f(mat);
	}

	@Override
	public EPHMat4f mulScalar(float scalar) {
		modified = true;
		return super.mulScalar(scalar);
	}

	@Override
	public EPHMat4f mulMat4(EPHMat4f mat) {
		modified = true;
		return super.mulMat4(mat);
	}

	@Override
	public EPHVec4f mulVec4(EPHVec4f vec) {
		modified = true;
		return super.mulVec4(vec);
	}

	@Override
	public EPHMat4f negate() {
		modified = true;
		return super.negate();
	}

	@Override
	public float[] getC(int c) {
		modified = true;
		return super.getC(c);
	}

	@Override
	public float[][] getMatrix() {
		modified = true;
		return super.getMatrix();
	}

	@Override
	public EPHMat4f setMatrix(float[][] matrix) {
		modified = true;
		return super.setMatrix(matrix);
	}

	@Override
	public EPHMat4f setColumn(int c, float[] values) {
		modified = true;
		return super.setColumn(c, values);
	}

	@Override
	public EPHMat4f copyToCL(int c, int l, float value) {
		modified = true;
		return super.copyToCL(c, l, value);
	}

	@Override
	public EPHMat4f copyToColumn(int c, float[] values) {
		modified = true;
		return super.copyToColumn(c, values);
	}

	@Override
	public EPHMat4f copyToLine(int l, float[] values) {
		modified = true;
		return super.copyToLine(l, values);
	}

	@Override
	public EPHMat4f copyToMatrix(float[][] values) {
		modified = true;
		return super.copyToMatrix(values);
	}

}
