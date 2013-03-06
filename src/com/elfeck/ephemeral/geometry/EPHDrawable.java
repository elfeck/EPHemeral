/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.geometry;

import com.elfeck.ephemeral.glContext.EPHVertexArrayObject;


public interface EPHDrawable {

	public void addDataToVao(EPHVertexArrayObject vao);
	public void removeDataFromVao(EPHVertexArrayObject vao);

}