/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.elfeck.ephemeral.math.EPHMat4f;
import com.elfeck.ephemeral.math.EPHVec4f;


public class EPHUniformMat4f extends EPHMat4f implements EPHUniformContent {

	private Map<Integer, Boolean> modMap;

	public EPHUniformMat4f(float[][] matrix) {
		super(matrix);
		modMap = new HashMap<Integer, Boolean>();
	}

	public EPHUniformMat4f() {
		modMap = new HashMap<Integer, Boolean>();
	}

	@Override
	public void glUploadUniformContent(int uniformKey, String name, int programHandle) {
		boolean modified = modMap.get(uniformKey);
		if (modified) {
			glUniformMatrix4(glGetUniformLocation(programHandle, name), false, toBuffer());
			modMap.put(uniformKey, false);
		}
	}

	@Override
	public void addUniformEntry(int uniformKey) {
		modMap.put(uniformKey, true);
	}

	@Override
	public void removeUniformEntry(int uniformKey) {
		modMap.remove(uniformKey);
	}

	@Override
	public EPHMat4f addMat4f(EPHMat4f mat) {
		setModified();
		return super.addMat4f(mat);
	}

	@Override
	public EPHMat4f subMat4f(EPHMat4f mat) {
		setModified();
		return super.subMat4f(mat);
	}

	@Override
	public EPHMat4f mulScalar(float scalar) {
		setModified();
		return super.mulScalar(scalar);
	}

	@Override
	public EPHMat4f mulMat4f(EPHMat4f mat) {
		setModified();
		return super.mulMat4f(mat);
	}

	@Override
	public EPHVec4f mulVec4f(EPHVec4f vec) {
		setModified();
		return super.mulVec4f(vec);
	}

	@Override
	public EPHMat4f negate() {
		setModified();
		return super.negate();
	}

	@Override
	public float[] getC(int c) {
		setModified();
		return super.getC(c);
	}

	@Override
	public float[][] getMatrix() {
		setModified();
		return super.getMatrix();
	}

	@Override
	public EPHMat4f setMatrix(float[][] matrix) {
		setModified();
		return super.setMatrix(matrix);
	}

	@Override
	public EPHMat4f setColumn(int c, float[] values) {
		setModified();
		return super.setColumn(c, values);
	}

	@Override
	public EPHMat4f copyToCL(int c, int l, float value) {
		setModified();
		return super.copyToCL(c, l, value);
	}

	@Override
	public EPHMat4f copyToColumn(int c, float[] values) {
		setModified();
		return super.copyToColumn(c, values);
	}

	@Override
	public EPHMat4f copyToLine(int l, float[] values) {
		setModified();
		return super.copyToLine(l, values);
	}

	@Override
	public EPHMat4f copyToMatrix(float[][] values) {
		setModified();
		return super.copyToMatrix(values);
	}

	private void setModified() {
		for (Integer key : modMap.keySet()) {
			modMap.put(key, true);
		}
	}

}
