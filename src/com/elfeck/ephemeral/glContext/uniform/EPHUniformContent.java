/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

public interface EPHUniformContent {

	public void glUploadUniformContent(int uniformKey, String name, int programHandle);
	public void addUniformEntry(int uniformKey);
	public void removeUniformEntry(int uniformKey);

}
