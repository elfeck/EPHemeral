/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import java.util.ArrayList;
import java.util.List;


public class EPHUniformVecfArray implements EPHUniformContent {

	private List<EPHUniformVecf> vectors;
	private List<Integer> registeredUniformKeys;

	public EPHUniformVecfArray() {
		vectors = new ArrayList<EPHUniformVecf>();
		registeredUniformKeys = new ArrayList<Integer>();
	}

	@Override
	public void glUploadUniformContent(int uniformKey, String name, int programHandle) {
		for (int i = 0; i < vectors.size(); i++) {
			vectors.get(i).glUploadUniformContent(uniformKey, name + "[" + i + "]", programHandle);
		}

	}

	@Override
	public void addUniformEntry(int uniformKey) {
		registeredUniformKeys.add(uniformKey);
		for (EPHUniformVecf uvecf : vectors) {
			uvecf.addUniformEntry(uniformKey);
		}
	}

	@Override
	public void removeUniformEntry(int uniformKey) {
		registeredUniformKeys.remove((Integer) uniformKey);
		for (EPHUniformVecf uvecf : vectors) {
			uvecf.removeUniformEntry(uniformKey);
		}
	}

	public void addVector(EPHUniformVecf vector) {
		for (Integer uniformKey : registeredUniformKeys) {
			vector.addUniformEntry(uniformKey);
		}
		vectors.add(vector);
	}

	public void removeVector(EPHUniformVecf vector) {
		for (Integer uniformKey : registeredUniformKeys) {
			vector.removeUniformEntry(uniformKey);
		}
		vectors.remove(vector);
	}

}
