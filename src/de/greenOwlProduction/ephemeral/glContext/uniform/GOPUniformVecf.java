package de.greenOwlProduction.ephemeral.glContext.uniform;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import static org.lwjgl.opengl.GL20.*;
import de.greenOwlProduction.ephemeral.math.GOPVecf;

import java.util.HashMap;
import java.util.Map;


public class GOPUniformVecf implements GOPUniformObject {

	private int index;
	private String name;
	private Map<Integer, GOPVecf> entries;

	public GOPUniformVecf(String name) {
		index = -1;
		this.name = name;
		entries = new HashMap<Integer, GOPVecf>();
	}

	@Override
	public void useUniform(int programHandle, int key) {
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

	public void addEntry(int key, GOPVecf vector) {
		entries.put(key, vector);
	}

}
