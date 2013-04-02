/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

public abstract class EPHUniformContent {

	protected int location;

	public EPHUniformContent() {
		location = -1;
	}

	protected abstract void glUploadUniformContent(String name, int programHandle);

}
