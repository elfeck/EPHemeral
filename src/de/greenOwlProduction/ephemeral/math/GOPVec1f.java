package de.greenOwlProduction.ephemeral.math;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public class GOPVec1f implements GOPVecf {

	protected float x;

	public GOPVec1f(float x) {
		this.x = x;
	}

	public GOPVec1f() {
		x = 0;
	}

	@Override
	public float getN(int index) {
		if (index == 0) return x;
		return Float.NaN;
	}

	@Override
	public void setN(int index, float value) {
		if (index == 0) x = value;

	}

	@Override
	public float[] toArray() {
		return new float[] { x };
	}

}
