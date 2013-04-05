/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.elfeck.ephemeral.math.EPHVec1f;


public class EPHUniformVec1f extends EPHVec1f implements EPHUniformVecf {

	private Map<Integer, Boolean> modMap;

	public EPHUniformVec1f(float x) {
		super(x);
		modMap = new HashMap<Integer, Boolean>();
	}

	public EPHUniformVec1f() {
		this(0);
	}

	@Override
	public void glUploadUniformContent(int uniformKey, String name, int programHandle) {
		boolean modified = modMap.get(uniformKey);
		if (modified) {
			glUniform1f(glGetUniformLocation(programHandle, name), x);
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
	public void setX(float value) {
		setModified();
		super.setX(value);
	}

	private void setModified() {
		for (Integer key : modMap.keySet()) {
			modMap.put(key, true);
		}
	}

}
