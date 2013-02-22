package de.greenOwlProduction.ephemeral.math;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import java.nio.FloatBuffer;


public interface GOPMatf {

	public int getDimension();

	public FloatBuffer toBuffer();
	public float[][] toArray();

}
