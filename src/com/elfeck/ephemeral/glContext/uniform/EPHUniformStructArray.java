/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import java.util.ArrayList;
import java.util.List;


public class EPHUniformStructArray implements EPHUniformContent {

	private List<EPHUniformStruct> structs;

	public EPHUniformStructArray() {
		structs = new ArrayList<EPHUniformStruct>();
	}

	@Override
	public void glUploadUniformContent(int programKey, String name, int programHandle) {
		for (int i = 0; i < structs.size(); i++) {
			structs.get(i).glUploadUniformContent(programKey, name + "[" + i + "]", programHandle);
		}
	}

	@Override
	public void addUniformEntry(int uniformKey) {
		for (int i = 0; i < structs.size(); i++) {
			structs.get(i).addUniformEntry(uniformKey);
		}

	}

	@Override
	public void removeUniformEntry(int uniformKey) {
		for (int i = 0; i < structs.size(); i++) {
			structs.get(i).removeUniformEntry(uniformKey);
		}
	}

	public void addStruct(EPHUniformStruct struct) {
		structs.add(struct);
	}

	public void removeStruct(EPHUniformStruct struct) {
		structs.remove(struct);
	}

	public List<EPHUniformStruct> getStructs() {
		return structs;
	}

}
