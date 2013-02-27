package com.elfeck.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import org.lwjgl.BufferUtils;


public class EPHRenderUtils {

	public static final int MODE_STATIC_DRAW = GL_STATIC_DRAW;
	public static final int MODE_DYNAMIC_DRAW = GL_DYNAMIC_DRAW;
	public static final int MODE_STREAM_DRAW = GL_STREAM_DRAW;

	public static final int TYPE_FLOAT = GL_FLOAT;

	public static final int TYPE_TRIANGLES = GL_TRIANGLES;

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
			buffer.put(array[c]);
		}
		buffer.flip();
		return buffer;
	}

	/*
	public static List<Float> modelToList(GOPVecxf[] model) {
		List<Float> vertexValues = new ArrayList<Float>();
		for (GOPVecxf vec : model) {
			for (Float f : vec.getVector()) {
				vertexValues.add(f);
			}
		}
		return vertexValues;
	}

	public static GOPVecxf[] readModel(String path, String section, List<Integer> indices) {
		List<GOPVecxf> model = new ArrayList<GOPVecxf>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = null;
			float factor = 1.0f;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("#")) factor = Float.valueOf(line.substring(2));
				if (line.startsWith("[") && line.contains("<" + section)) model.add(extractVec(line, factor));
				if (line.startsWith("<" + section)) extractIndices(line, indices);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return model.toArray(new GOPVecxf[model.size()]);
	}

	private static GOPVecxf extractVec(String line, float factor) {
		line = line.replaceAll(" ", "");
		line = line.substring(4);
		line = line.substring(0, line.indexOf("<"));
		return GOPVecxf.createVec4f(Float.valueOf(line.split(",")[0]) * factor, Float.valueOf(line.split(",")[1]) * factor, 1.0f, 1.0f);
	}

	private static void extractIndices(String line, List<Integer> indices) {
		line = line.replaceAll(" ", "");
		line = line.substring(2);
		indices.add(Integer.valueOf(line.split(",")[0]));
		indices.add(Integer.valueOf(line.split(",")[1]));
		indices.add(Integer.valueOf(line.split(",")[2]));
	}

	public static List<Float> packVertexData(List<GOPVecxf[]> vertexData) {
		List<Float> interleavedData = new ArrayList<Float>();
		for (int i = 0; i < vertexData.get(0).length; i++) {
			for (int j = 0; j < vertexData.size(); j++) {
				for (int k = 0; k < vertexData.get(j)[i].getLength(); k++) {
					interleavedData.add(vertexData.get(j)[i].getXn(k));
				}
			}
		}
		return interleavedData;
	}
	*/

}
