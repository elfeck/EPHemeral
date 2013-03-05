/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.math;

public interface EPHVecf {

	public int getDimension();
	public float getN(int index);
	public void setN(int index, float value);

	public float[] toArray();
	public EPHVec2f toVec2f();

}
