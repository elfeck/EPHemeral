/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import com.elfeck.ephemeral.math.EPHVecf;


public class EPHUniformVecf extends EPHUniformContent {

	private EPHVecf vector;

	public EPHUniformVecf(EPHVecf vector) {
		this.vector = vector;
	}

	@Override
	protected void glUploadUniformContent(int location) {
		switch (vector.getDimension()) {
			case 1:
				glUniform1f(location, vector.getN(0));
				break;
			case 2:
				glUniform2f(location, vector.getN(0), vector.getN(1));
				break;
			case 3:
				glUniform3f(location, vector.getN(0), vector.getN(1), vector.getN(2));
				break;
			case 4:
				glUniform4f(location, vector.getN(0), vector.getN(1), vector.getN(2), vector.getN(3));
				break;
			default:
				System.err.println("Error in EPHUniformVecf. Size out of valid range.");
		}
	}

	public EPHVecf getVector() {
		return vector;
	}

}
