/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.math;

import java.nio.FloatBuffer;


public interface EPHMatf {

	public int getDimension();
	public FloatBuffer toBuffer();
	public float[][] toArray();

}
