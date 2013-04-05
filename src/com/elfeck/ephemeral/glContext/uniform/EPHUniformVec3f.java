/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.elfeck.ephemeral.math.EPHVec3f;


public class EPHUniformVec3f extends EPHVec3f implements EPHUniformVecf {

	private Map<Integer, Boolean> modMap;

	public EPHUniformVec3f(float x, float y, float z) {
		super(x, y, z);
		modMap = new HashMap<Integer, Boolean>();
	}

	public EPHUniformVec3f() {
		this(0, 0, 0);
	}

	@Override
	public void glUploadUniformContent(int uniformKey, String name, int programHandle) {
		boolean modified = modMap.get(uniformKey);
		if (modified) {
			glUniform3f(glGetUniformLocation(programHandle, name), x, y, z);
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
	public EPHVec3f addVec3f(EPHVec3f vec) {
		setModified();
		return super.addVec3f(vec);
	}

	@Override
	public EPHVec3f addVec3f(float vx, float vy, float vz) {
		setModified();
		return super.addVec3f(vx, vy, vz);
	}

	@Override
	public EPHVec3f subVec3f(EPHVec3f vec) {
		setModified();
		return super.subVec3f(vec);
	}

	@Override
	public EPHVec3f subVec3f(float vx, float vy, float vz) {
		setModified();
		return super.subVec3f(vx, vy, vz);
	}

	@Override
	public EPHVec3f mulVec3f(EPHVec3f vec) {
		setModified();
		return super.mulVec3f(vec);
	}

	@Override
	public EPHVec3f mulVec3f(float vx, float vy, float vz) {
		setModified();
		return super.mulVec3f(vx, vy, vz);
	}

	@Override
	public EPHVec3f mulScalar(float scalar) {
		setModified();
		return super.mulScalar(scalar);
	}

	@Override
	public EPHVec3f normalize() {
		setModified();
		return super.normalize();
	}

	@Override
	public EPHVec3f negate() {
		setModified();
		return super.negate();
	}

	@Override
	public EPHVec3f toLength(float newLength) {
		setModified();
		return super.toLength(newLength);
	}

	@Override
	public EPHVec3f setX(float x) {
		setModified();
		return super.setX(x);
	}

	@Override
	public EPHVec3f setY(float y) {
		setModified();
		return super.setY(y);
	}

	@Override
	public EPHVec3f setZ(float z) {
		setModified();
		return super.setZ(z);
	}

	@Override
	public EPHVec3f setXYZ(float x, float y, float z) {
		setModified();
		return super.setXYZ(x, y, z);
	}

	private void setModified() {
		for (Integer key : modMap.keySet()) {
			modMap.put(key, true);
		}
	}

}
