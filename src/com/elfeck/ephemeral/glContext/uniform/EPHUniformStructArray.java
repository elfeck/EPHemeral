/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

public class EPHUniformStructArray extends EPHUniformContent {

	private EPHUniformStruct[] structs;

	public EPHUniformStructArray(EPHUniformStruct[] structs) {
		super();
		this.structs = structs;
	}

	@Override
	protected void glUploadUniformContent(String name, int programHandle) {
		for (int i = 0; i < structs.length; i++) {
			structs[i].glUploadUniformContent(name + "[" + i + "]", programHandle);
		}
	}

}
