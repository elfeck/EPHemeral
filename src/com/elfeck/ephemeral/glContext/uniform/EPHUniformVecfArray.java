/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;



public class EPHUniformVecfArray implements EPHUniformContent {

	private EPHUniformVecf[] vectors;

	public EPHUniformVecfArray(EPHUniformVecf[] vectors) {
		this.vectors = vectors;
	}

	@Override
	public void glUploadUniformContent(String name, int programHandle) {
		for (int i = 0; i < vectors.length; i++) {
			vectors[i].glUploadUniformContent(name + "[" + i + "]", programHandle);
		}

	}

}
