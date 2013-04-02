/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import java.util.Map;


public class EPHUniformStruct implements EPHUniformContent {

	private Map<String, EPHUniformContent> members;

	public EPHUniformStruct() {

	}

	@Override
	public void glUploadUniformContent(String name, int programHandle) {
		for (String key : members.keySet()) {
			members.get(key).glUploadUniformContent(name + "." + key, programHandle);
		}
	}

	public void addMember(String subname, EPHUniformContent member) {
		members.put(subname, member);
	}

	public void removeMember(String subname) {
		members.remove(subname);
	}

}
