package com.elfeck.ephemeral.glContext.uniform;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public interface EPHUniformObject {

	public void glUseUniform(int programHandle, int key);
	public void reset();
	public void removeEntry(int key);
	
	public String getName();

}
