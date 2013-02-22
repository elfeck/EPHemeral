package de.greenOwlProduction.ephemeral.glContext.uniform;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public interface GOPUniformObject {

	void useUniform(int programHandle, int key);
	void reset();
	void removeEntry(int key);

}
