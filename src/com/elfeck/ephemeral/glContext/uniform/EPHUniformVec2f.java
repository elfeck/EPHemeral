/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.elfeck.ephemeral.math.EPHVec2f;


public class EPHUniformVec2f extends EPHVec2f implements EPHUniformVecf {

	private Map<Integer, Boolean> modMap;

	public EPHUniformVec2f(float x, float y) {
		super(x, y);
		modMap = new HashMap<Integer, Boolean>();
	}

	public EPHUniformVec2f() {
		this(0, 0);
	}

	@Override
	public void glUploadUniformContent(int uniformKey, String name, int programHandle) {
		boolean modified = modMap.get(uniformKey);
		if (modified) {
			glUniform2f(glGetUniformLocation(programHandle, name), x, y);
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
	public EPHVec2f addVec2f(EPHVec2f vec) {
		setModified();
		return super.addVec2f(vec);
	}

	@Override
	public EPHVec2f addVec2f(float vx, float vy) {
		setModified();
		return super.addVec2f(vx, vy);
	}

	@Override
	public EPHVec2f subVec2f(EPHVec2f vec) {
		setModified();
		return super.subVec2f(vec);
	}

	@Override
	public EPHVec2f subVec2f(float vx, float vy) {
		setModified();
		return super.subVec2f(vx, vy);
	}

	@Override
	public EPHVec2f mulVec2f(EPHVec2f vec) {
		setModified();
		return super.mulVec2f(vec);
	}

	@Override
	public EPHVec2f mulVec2f(float vx, float vy) {
		setModified();
		return super.mulVec2f(vx, vy);
	}

	@Override
	public EPHVec2f mulScalar(float scalar) {
		setModified();
		return super.mulScalar(scalar);
	}

	@Override
	public EPHVec2f normalize() {
		setModified();
		return super.normalize();
	}

	@Override
	public EPHVec2f negate() {
		setModified();
		return super.negate();
	}

	@Override
	public EPHVec2f toLength(float newLength) {
		setModified();
		return super.toLength(newLength);
	}

	@Override
	public EPHVec2f setX(float x) {
		setModified();
		return super.setX(x);
	}

	@Override
	public EPHVec2f setY(float y) {
		setModified();
		return super.setY(y);
	}

	@Override
	public EPHVec2f setXY(float x, float y) {
		setModified();
		return super.setXY(x, y);
	}

	private void setModified() {
		for (Integer key : modMap.keySet()) {
			modMap.put(key, true);
		}
	}

}
