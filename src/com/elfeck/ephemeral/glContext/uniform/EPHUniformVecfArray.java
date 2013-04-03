/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import java.util.ArrayList;
import java.util.List;


public class EPHUniformVecfArray implements EPHUniformContent {

	private List<EPHUniformVecf> vectors;

	public EPHUniformVecfArray() {
		vectors = new ArrayList<EPHUniformVecf>();
	}

	@Override
	public void glUploadUniformContent(String name, int programHandle) {
		for (int i = 0; i < vectors.size(); i++) {
			vectors.get(i).glUploadUniformContent(name + "[" + i + "]", programHandle);
		}

	}

	public void addVector(EPHUniformVecf vector) {
		vectors.add(vector);
	}

	public void removeVector(EPHUniformVecf vector) {
		vectors.remove(vector);
	}

}
