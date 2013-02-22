package de.greenOwlProduction.ephemeral.math;

import java.nio.FloatBuffer;


public interface GOPMatf {

	public int getDimension();

	public FloatBuffer toBuffer();
	public float[][] toArray();

}
