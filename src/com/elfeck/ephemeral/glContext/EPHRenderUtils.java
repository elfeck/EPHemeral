/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;

import com.elfeck.ephemeral.math.EPHVecf;


public class EPHRenderUtils {

	public static final int MODE_STATIC_DRAW = GL_STATIC_DRAW;
	public static final int MODE_DYNAMIC_DRAW = GL_DYNAMIC_DRAW;
	public static final int MODE_STREAM_DRAW = GL_STREAM_DRAW;

	public static final int TYPE_FLOAT = GL_FLOAT;
	public static final int TYPE_INT = GL_INT;
	public static final int TYPE_SHORT = GL_SHORT;

	public static final int TYPE_TRIANGLES = GL_TRIANGLES;
	public static final int TYPE_LINES = GL_LINES;
	public static final int TYPE_POINTS = GL_POINTS;

	private EPHRenderUtils() {

	}

	public static FloatBuffer listToBufferf(List<Float> list) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(list.size());
		for (Float f : list) {
			buffer.put(f);
		}
		buffer.flip();
		return buffer;
	}

	public static IntBuffer listToBufferi(List<Integer> list) {
		IntBuffer buffer = BufferUtils.createIntBuffer(list.size());
		for (Integer i : list) {
			buffer.put(i);
		}
		buffer.flip();
		return buffer;
	}

	public static FloatBuffer array1DToBufferf(float[] array) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		return buffer;
	}

	public static IntBuffer array1DToBufferi(int[] array) {
		IntBuffer buffer = BufferUtils.createIntBuffer(array.length);
		buffer.put(array);
		buffer.flip();
		return buffer;
	}

	public static FloatBuffer array2DtoBufferf(float[][] array) {
		int length = 0;
		for (int c = 0; c < array.length; c++) {
			length += array[c].length;
		}
		FloatBuffer buffer = BufferUtils.createFloatBuffer(length);
		for (int c = 0; c < array.length; c++) {
			for (int l = 0; l < array[c].length; l++) {
				buffer.put(array[c][l]);
			}
		}
		buffer.flip();
		return buffer;
	}

	public static FloatBuffer vecfArrayToBufferf(EPHVecf[] array) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length * array[0].getDimension());
		for (int i = 0; i < array.length; i++) {
			buffer.put(array[i].toArray());
		}
		buffer.flip();
		return buffer;
	}

}
