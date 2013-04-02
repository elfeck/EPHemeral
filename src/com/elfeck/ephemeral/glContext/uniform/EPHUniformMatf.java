/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import com.elfeck.ephemeral.math.EPHMatf;


public class EPHUniformMatf extends EPHUniformContent {

	private EPHMatf matrix;

	public EPHUniformMatf(EPHMatf matrix) {
		super();
		this.matrix = matrix;
	}

	@Override
	protected void glUploadUniformContent(String name, int programHandle) {
		if (location < 0) location = glGetUniformLocation(programHandle, name);
		// TODO: other dimensions
		switch (matrix.getDimension()) {
			case 4:
				glUniformMatrix4(location, false, matrix.toBuffer());
				break;
			default:
				System.err.println("Error in EPHUniformMatf. Size out of valid range.");
		}
	}

	public EPHMatf getMatrix() {
		return matrix;
	}

}
