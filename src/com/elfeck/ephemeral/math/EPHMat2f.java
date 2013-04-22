/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

package com.elfeck.ephemeral.math;

import java.nio.FloatBuffer;
import java.util.Arrays;

import com.elfeck.ephemeral.glContext.EPHRenderUtils;


public class EPHMat2f implements EPHMatf {

	protected int dimension;
	protected float[][] matrix;

	public EPHMat2f(float[][] matrix) {
		dimension = 2;
		this.matrix = matrix;
	}

	public EPHMat2f() {
		this(new float[2][2]);
	}

	@Override
	public int getDimension() {
		return dimension;
	}

	@Override
	public FloatBuffer toBuffer() {
		return EPHRenderUtils.array2DtoBufferf(matrix);
	}

	@Override
	public float[][] toArray() {
		return new float[][] {
								Arrays.copyOf(matrix[0], matrix[0].length),
								Arrays.copyOf(matrix[1], matrix[1].length),
		};
	}

	public EPHMat2f addMat2f(EPHMat2f mat) {
		for (int c = 0; c < dimension; c++) {
			for (int l = 0; l < dimension; l++) {
				matrix[c][l] += mat.matrix[c][l];
			}
		}
		return this;
	}

	public EPHMat2f subMat2f(EPHMat2f mat) {
		for (int c = 0; c < dimension; c++) {
			for (int l = 0; l < dimension; l++) {
				matrix[c][l] -= mat.matrix[c][l];
			}
		}
		return this;
	}

	public EPHMat2f mulScalar(float scalar) {
		for (int c = 0; c < dimension; c++) {
			for (int l = 0; l < dimension; l++) {
				matrix[c][l] *= scalar;
			}
		}
		return this;
	}

	public EPHMat2f mulMat2f(EPHMat2f mat) {
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

	public EPHVec2f mulVec2f(EPHVec2f vec) {
		EPHVec2f vector = new EPHVec2f();
		for (int l = 0; l < dimension; l++) {
			for (int c = 0; c < dimension; c++) {
				vector.setN(l, vector.getN(l) + matrix[c][l] * vec.getN(c));
			}
		}
		return vector;
	}

	public EPHMat2f negate() {
		for (int c = 0; c < dimension; c++) {
			for (int l = 0; l < dimension; l++) {
				matrix[c][l] = -matrix[c][l];
			}
		}
		return this;
	}

	public float getCL(int c, int l) {
		return matrix[c][l];
	}

	public float[] getC(int c) {
		return matrix[c];
	}

	public float[] getL(int l) {
		return new float[] { matrix[0][l], matrix[1][l], matrix[2][l], matrix[3][l] };
	}

	public float[][] getMatrix() {
		return matrix;
	}

	public EPHMat2f setMatrix(float[][] matrix) {
		this.matrix = matrix;
		return this;
	}

	public EPHMat2f setColumn(int c, float[] values) {
		matrix[c] = values;
		return this;
	}

	public EPHMat2f copyToCL(int c, int l, float value) {
		matrix[c][l] = value;
		return this;
	}

	public EPHMat2f copyToColumn(int c, float[] values) {
		for (int l = 0; l < dimension; l++) {
			matrix[c][l] = values[l];
		}
		return this;
	}

	public EPHMat2f copyToLine(int l, float[] values) {
		for (int c = 0; c < dimension; c++) {
			matrix[c][l] = values[c];
		}
		return this;
	}

	public EPHMat2f copyToMatrix(float[][] values) {
		for (int c = 0; c < dimension; c++) {
			for (int l = 0; l < dimension; l++) {
				matrix[c][l] = values[c][l];
			}
		}
		return this;
	}

	public EPHMat2f copy() {
		return new EPHMat2f().copyToMatrix(matrix);
	}

	public String toString() {
		String result = "";
		result += "Matrix (2x2): \n";
		for (int c = 0; c < matrix.length; c++) {
			for (int l = 0; l < matrix[c].length; l++) {
				result += matrix[l][c] + "  ";
			}
			result += "\n";
		}
		return result;
	}

}
