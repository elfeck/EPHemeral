/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.math;

import java.nio.FloatBuffer;
import java.util.Arrays;

import com.elfeck.ephemeral.glContext.EPHRenderUtils;


public class EPHMat4f implements EPHMatf {

	protected int dimension;
	protected float[][] matrix;

	public EPHMat4f(float[][] matrix) {
		dimension = 4;
		this.matrix = matrix;
	}

	public EPHMat4f() {
		dimension = 4;
		matrix = new float[dimension][dimension];
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
								Arrays.copyOf(matrix[2], matrix[2].length),
								Arrays.copyOf(matrix[3], matrix[3].length)
		};
	}

	public EPHMat4f addMat4f(EPHMat4f mat) {
		for (int c = 0; c < dimension; c++) {
			for (int l = 0; l < dimension; l++) {
				matrix[c][l] += mat.matrix[c][l];
			}
		}
		return this;
	}

	public EPHMat4f subMat4f(EPHMat4f mat) {
		for (int c = 0; c < dimension; c++) {
			for (int l = 0; l < dimension; l++) {
				matrix[c][l] -= mat.matrix[c][l];
			}
		}
		return this;
	}

	public EPHMat4f mulScalar(float scalar) {
		for (int c = 0; c < dimension; c++) {
			for (int l = 0; l < dimension; l++) {
				matrix[c][l] *= scalar;
			}
		}
		return this;
	}

	public EPHMat4f mulMat4f(EPHMat4f mat) {
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

	public EPHVec4f mulVec4f(EPHVec4f vec) {
		EPHVec4f vector = new EPHVec4f();
		for (int l = 0; l < dimension; l++) {
			for (int c = 0; c < dimension; c++) {
				vector.setN(l, vector.getN(l) + matrix[c][l] * vec.getN(c));
			}
		}
		return vector;
	}

	public EPHMat4f negate() {
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

	public EPHMat4f setMatrix(float[][] matrix) {
		this.matrix = matrix;
		return this;
	}

	public EPHMat4f setColumn(int c, float[] values) {
		matrix[c] = values;
		return this;
	}

	public EPHMat4f copyToCL(int c, int l, float value) {
		matrix[c][l] = value;
		return this;
	}

	public EPHMat4f copyToColumn(int c, float[] values) {
		for (int l = 0; l < dimension; l++) {
			matrix[c][l] = values[l];
		}
		return this;
	}

	public EPHMat4f copyToLine(int l, float[] values) {
		for (int c = 0; c < dimension; c++) {
			matrix[c][l] = values[c];
		}
		return this;
	}

	public EPHMat4f copyToMatrix(float[][] values) {
		for (int c = 0; c < dimension; c++) {
			for (int l = 0; l < dimension; l++) {
				matrix[c][l] = values[c][l];
			}
		}
		return this;
	}

	public EPHMat4f copy() {
		return new EPHMat4f().copyToMatrix(matrix);
	}

	public String toString() {
		String result = "";
		result += "Matrix (2x2) is valid: \n";
		for (int c = 0; c < matrix.length; c++) {
			for (int l = 0; l < matrix[c].length; l++) {
				result += matrix[l][c] + "  ";
			}
			result += "\n";
		}
		return result;
	}

}
