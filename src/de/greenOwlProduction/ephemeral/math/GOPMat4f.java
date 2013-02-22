package de.greenOwlProduction.ephemeral.math;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import de.greenOwlProduction.ephemeral.glContext.GOPRenderUtils;

import java.nio.FloatBuffer;
import java.util.Arrays;

public class GOPMat4f implements GOPMatf {

	public final int dimension;
	protected float[][] matrix;

	public GOPMat4f(float[][] matrix) {
		dimension = 4;
		this.matrix = matrix;
	}

	public GOPMat4f() {
		dimension = 4;
		matrix = new float[dimension][dimension];
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public FloatBuffer toBuffer() {
		return GOPRenderUtils.array2DtoBufferf(matrix);
	}

	@Override
	public float[][] toArray() {
		return new float[][] {
								Arrays.copyOf(matrix[0], matrix[0].length),
								Arrays.copyOf(matrix[1], matrix[1].length),
								Arrays.copyOf(matrix[2], matrix[2].length),
								Arrays.copyOf(matrix[3], matrix[3].length)
		};
	}

	private boolean valid() {
		boolean valid = matrix.length == dimension;
		if (valid) {
			for (int c = 0; c < dimension; c++) {
				valid = valid && matrix[c].length == dimension;
			}
		}
		return valid;
	}

	private boolean valid(float[][] values) {
		boolean valid = values.length != dimension;
		if (!valid) {
			for (int c = 0; c < dimension; c++) {
				valid = valid && values[c].length == dimension;
			}
		}
		return valid;
	}

	private boolean indexInRange(int index) {
		return index > -1 && index < dimension;
	}

	private boolean indicesInRange(int i1, int i2) {
		return indexInRange(i1) && indexInRange(i2);
	}

	public GOPMat4f addMat4f(GOPMat4f mat) {
		if (valid() && mat.valid()) {
			for (int c = 0; c < dimension; c++) {
				for (int l = 0; l < dimension; l++) {
					matrix[c][l] += mat.matrix[c][l];
				}
			}
			return this;
		}
		System.err.println("Matrix or argument not valid");
		return null;
	}

	public GOPMat4f subMat4f(GOPMat4f mat) {
		if (valid() && mat.valid()) {
			for (int c = 0; c < dimension; c++) {
				for (int l = 0; l < dimension; l++) {
					matrix[c][l] -= mat.matrix[c][l];
				}
			}
			return this;
		}
		System.err.println("Matrix or argument not valid");
		return null;
	}

	public GOPMat4f mulScalar(float scalar) {
		if (valid()) {
			for (int c = 0; c < dimension; c++) {
				for (int l = 0; l < dimension; l++) {
					matrix[c][l] *= scalar;
				}
			}
			return this;
		}
		System.err.println("Matrix not valid");
		return null;
	}

	public GOPMat4f mulMat4(GOPMat4f mat) {
		if (valid() && mat.valid()) {
			float[] currentLine = new float[4];
			for (int l = 0; l < dimension; l++) {
				for (int c = 0; c < dimension; c++) {
					for (int i = 0; i < dimension; i++) {
						currentLine[c] += matrix[i][l] * mat.matrix[c][i];
					}
				}
				copyToLine(l, currentLine);
				Arrays.fill(currentLine, 0);
			}
			return this;
		}
		System.err.println("Matrix or argument not valid");
		return null;
	}

	public GOPVec4f mulVec4(GOPVec4f vec) {
		if (valid()) {
			GOPVec4f vector = new GOPVec4f();
			for (int l = 0; l < dimension; l++) {
				for (int c = 0; c < dimension; c++) {
					vector.setN(l, vector.getN(l) + matrix[c][l] * vec.getN(c));
				}
			}
			return vector;
		}
		System.err.println("Matrix not valid");
		return null;
	}

	public GOPMat4f negate() {
		if (valid()) {
			for (int c = 0; c < dimension; c++) {
				for (int l = 0; l < dimension; l++) {
					matrix[c][l] = -matrix[c][l];
				}
			}
			return this;
		}
		System.err.println("Matrix not valid");
		return null;
	}

	// invert
	// transpose
	// determinat

	public float getCL(int c, int l) {
		if (valid() && indicesInRange(c, l)) return matrix[c][l];
		System.err.println("Matrix or argument not valid");
		return Float.NaN;
	}

	public float[] getC(int c) {
		if (valid() && indexInRange(c)) return matrix[c];
		System.err.println("Matrix or argument not valid");
		return null;
	}

	public float[] getL(int l) {
		if (valid() && indexInRange(l)) return new float[] { matrix[0][l], matrix[1][l], matrix[2][l], matrix[3][l] };
		System.err.println("Matrix or argument not valid");
		return null;
	}

	public float[][] getMatrix() {
		return matrix;
	}

	public GOPMat4f setMatrix(float[][] matrix) {
		this.matrix = matrix;
		if (valid()) return this;
		System.err.println("Matrix or argument not valid");
		return null;
	}

	public GOPMat4f setColumn(int c, float[] values) {
		if (valid() && indexInRange(c) && values.length == dimension) {
			matrix[c] = values;
			return this;
		}
		System.err.println("Matrix or argument not valid");
		return null;
	}

	public GOPMat4f copyToCL(int c, int l, float value) {
		if (valid() && indicesInRange(c, l)) {
			matrix[c][l] = value;
			return this;
		}
		System.err.println("Matrix or argument not valid");
		return null;
	}

	public GOPMat4f copyToColumn(int c, float[] values) {
		if (valid() && indexInRange(c) && values.length == dimension) {
			for (int l = 0; l < dimension; l++) {
				matrix[c][l] = values[l];
			}
			return this;
		}
		System.err.println("Matrix undefined or argument not valid");
		return null;
	}

	public GOPMat4f copyToLine(int l, float[] values) {
		if (valid() && indexInRange(l) && values.length == dimension) {
			for (int c = 0; c < dimension; c++) {
				matrix[c][l] = values[c];
			}
			return this;
		}
		System.err.println("Matrix or argument not valid");
		return null;
	}

	public GOPMat4f copyToMatrix(float[][] values) {
		if (valid() && valid(values)) {
			for (int c = 0; c < dimension; c++) {
				for (int l = 0; l < dimension; l++) {
					matrix[c][l] = values[c][l];
				}
			}
			return this;
		}
		System.err.println("Matrix or argument not valid");
		return null;
	}

	public boolean isValid() {
		return valid();
	}

	public GOPMat4f copy() {
		return new GOPMat4f().copyToMatrix(matrix);
	}

	public String toString() {
		String result = "";
		result += "Matrix (4x4) is valid: " + valid() + "\n";
		for (int c = 0; c < matrix.length; c++) {
			for (int l = 0; l < matrix[c].length; l++) {
				result += matrix[l][c] + "  ";
			}
			result += "\n";
		}
		return result;
	}

}
