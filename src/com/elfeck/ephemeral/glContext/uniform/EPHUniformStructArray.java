/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import java.util.ArrayList;
import java.util.List;


public class EPHUniformStructArray implements EPHUniformContent {

	private List<EPHUniformStruct> structs;
	private List<Integer> registeredUniformKeys;

	public EPHUniformStructArray() {
		structs = new ArrayList<EPHUniformStruct>();
		registeredUniformKeys = new ArrayList<Integer>();
	}

	@Override
	public void glUploadUniformContent(int programKey, String name, int programHandle) {
		for (int i = 0; i < structs.size(); i++) {
			structs.get(i).glUploadUniformContent(programKey, name + "[" + i + "]", programHandle);
		}
	}

	@Override
	public void addUniformEntry(int uniformKey) {
		registeredUniformKeys.add(uniformKey);
		for (int i = 0; i < structs.size(); i++) {
			structs.get(i).addUniformEntry(uniformKey);
		}

	}

	@Override
	public void removeUniformEntry(int uniformKey) {
		registeredUniformKeys.remove((Integer) uniformKey);
		for (int i = 0; i < structs.size(); i++) {
			structs.get(i).removeUniformEntry(uniformKey);
		}
	}

	public void addStruct(EPHUniformStruct struct) {
		for (Integer uniformKey : registeredUniformKeys) {
			struct.addUniformEntry(uniformKey);
		}
		structs.add(struct);
	}

	public void removeStruct(EPHUniformStruct struct) {
		for (Integer uniformKey : registeredUniformKeys) {
			struct.removeUniformEntry(uniformKey);
		}
		structs.remove(struct);
	}

	public List<EPHUniformStruct> getStructs() {
		return structs;
	}

}
