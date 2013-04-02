/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import com.elfeck.ephemeral.glContext.EPHRenderUtils;
import com.elfeck.ephemeral.math.EPHVecf;


public class EPHUniformVecfArray extends EPHUniformContent {

	private EPHVecf[] vectors;

	public EPHUniformVecfArray(EPHVecf[] vectors) {
		super();
		this.vectors = vectors;
	}

	@Override
	protected void glUploadUniformContent(String name, int programHandle) {
		if (location < 0) location = glGetUniformLocation(programHandle, name);
		switch (vectors[0].getDimension()) {
			case 1:
				glUniform1(location, EPHRenderUtils.vecfArrayToBufferf(vectors));
				break;
			case 2:
				glUniform2(location, EPHRenderUtils.vecfArrayToBufferf(vectors));
				break;
			case 3:
				glUniform3(location, EPHRenderUtils.vecfArrayToBufferf(vectors));
				break;
			case 4:
				glUniform4(location, EPHRenderUtils.vecfArrayToBufferf(vectors));
				break;
			default:
				System.err.println("Error in EPHUniformVecfArray");
		}
	}

	public EPHVecf[] getVectors() {
		return vectors;
	}

}
