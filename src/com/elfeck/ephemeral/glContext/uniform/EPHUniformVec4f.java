/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.ephemeral.math.EPHVec4f;


public class EPHUniformVec4f extends EPHVec4f implements EPHUniformVecf {

	private Map<Integer, Boolean> modMap;

	public EPHUniformVec4f(float x, float y, float z, float w) {
		super(x, y, z, w);
		modMap = new HashMap<Integer, Boolean>();
	}

	public EPHUniformVec4f(EPHVec2f vec, float z, float w) {
		this(vec.getX(), vec.getY(), z, w);
	}

	public EPHUniformVec4f() {
		this(0, 0, 0, 0);
	}

	@Override
	public void glUploadUniformContent(int uniformKey, String name, int programHandle) {
		boolean modified = modMap.get(uniformKey);
		if (modified) {
			glUniform4f(glGetUniformLocation(programHandle, name), x, y, z, w);
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
	public void setN(int index, float value) {
		setModified();
		super.setN(index, value);
	}

	@Override
	public void addToN(int index, float value) {
		setModified();
		super.addToN(index, value);
	}

	@Override
	public EPHVec4f addVec4f(EPHVec4f vec) {
		setModified();
		return super.addVec4f(vec);
	}

	@Override
	public EPHVec4f addVec4f(float vx, float vy, float vz, float vw) {
		setModified();
		return super.addVec4f(vx, vy, vz, vw);
	}

	@Override
	public EPHVec4f subVec4f(EPHVec4f vec) {
		setModified();
		return super.subVec4f(vec);
	}

	@Override
	public EPHVec4f subVec4f(float vx, float vy, float vz, float vw) {
		setModified();
		return super.subVec4f(vx, vy, vz, vw);
	}

	@Override
	public EPHVec4f mulVec4f(EPHVec4f vec) {
		setModified();
		return super.mulVec4f(vec);
	}

	@Override
	public EPHVec4f mulVec4f(float vx, float vy, float vz, float vw) {
		setModified();
		return super.mulVec4f(vx, vy, vz, vw);
	}

	@Override
	public EPHVec4f mulScalar(float scalar) {
		setModified();
		return super.mulScalar(scalar);
	}

	@Override
	public EPHVec4f normalize() {
		setModified();
		return super.normalize();
	}

	@Override
	public EPHVec4f negate() {
		setModified();
		return super.negate();
	}

	@Override
	public EPHVec4f toLength(float newLength) {
		setModified();
		return super.toLength(newLength);
	}

	@Override
	public EPHVec4f setX(float x) {
		setModified();
		return super.setX(x);
	}

	@Override
	public EPHVec4f setY(float y) {
		setModified();
		return super.setY(y);
	}

	@Override
	public EPHVec4f setZ(float z) {
		setModified();
		return super.setZ(z);
	}

	@Override
	public EPHVec4f setW(float w) {
		setModified();
		return super.setW(w);
	}

	@Override
	public EPHVec4f setXYZW(float x, float y, float z, float w) {
		setModified();
		return super.setXYZW(x, y, z, w);
	}

	private void setModified() {
		for (Integer key : modMap.keySet()) {
			modMap.put(key, true);
		}
	}

}
