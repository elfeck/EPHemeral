/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.elfeck.ephemeral.math.EPHVecf;


public class EPHUniformVecf implements EPHUniformObject {

	private int index;
	private String name;
	private Map<Integer, EPHVecf> entries;

	public EPHUniformVecf(String name) {
		index = -1;
		this.name = name;
		entries = new HashMap<Integer, EPHVecf>();
	}

	@Override
	public void glUseUniform(int programHandle, int key) {
		if (index < 0) index = glGetUniformLocation(programHandle, name);
		float[] vector = entries.get(key).toArray();
		if (vector != null) {
			switch (vector.length) {
				case 1:
					glUniform1f(index, vector[0]);
					break;
				case 2:
					glUniform2f(index, vector[0], vector[1]);
					break;
				case 3:
					glUniform3f(index, vector[0], vector[1], vector[2]);
					break;
				case 4:
					glUniform4f(index, vector[0], vector[1], vector[2], vector[3]);
					break;
				default:
					System.err.println("Error in GOPUniformxfv. Size out of valid range.");
			}
		}
	}

	@Override
	public void reset() {
		index = -1;
	}

	@Override
	public void removeEntry(int key) {
		entries.remove(key);
	}

	@Override
	public String getName() {
		return name;
	}

	public void addEntry(int key, EPHVecf vector) {
		entries.put(key, vector);
	}

}
