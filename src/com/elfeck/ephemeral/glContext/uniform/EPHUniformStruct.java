/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import java.util.Map;


public class EPHUniformStruct implements EPHUniformContent {

	private Map<String, EPHUniformContent> members;

	public EPHUniformStruct(Map<String, EPHUniformContent> members) {
		this.members = members;
	}

	@Override
	public void glUploadUniformContent(int programKey, String name, int programHandle) {
		for (String key : members.keySet()) {
			members.get(key).glUploadUniformContent(programKey, name + "." + key, programHandle);
		}
	}

	@Override
	public void addUniformEntry(int uniformKey) {
		for (String s : members.keySet()) {
			members.get(s).addUniformEntry(uniformKey);
		}
	}

	@Override
	public void removeUniformEntry(int uniformKey) {
		for (String s : members.keySet()) {
			members.get(s).removeUniformEntry(uniformKey);
		}
	}

	public Map<String, EPHUniformContent> getMembers() {
		return members;
	}

}
