package de.greenOwlProduction.ephemeral.math;

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
