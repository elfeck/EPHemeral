/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;

import com.elfeck.ephemeral.math.EPHMatf;


public class EPHUniformMatf implements EPHUniformObject {

	private int index;
	private String name;
	private Map<Integer, EPHMatf> entries;

	public EPHUniformMatf(String name) {
		index = -1;
		this.name = name;
		entries = new HashMap<Integer, EPHMatf>();
	}

	@Override
	public void glUseUniform(int programHandle, int key) {
		if (index < 0) index = glGetUniformLocation(programHandle, name);
		EPHMatf matrix = entries.get(key);
		if (matrix != null) {
			switch (matrix.getDimension()) {
				case 2:
					glUniformMatrix2(index, false, matrix.toBuffer());
					break;
				case 3:
					glUniformMatrix3(index, false, matrix.toBuffer());
					break;
				case 4:
					glUniformMatrix4(index, false, matrix.toBuffer());
					break;
				default:
					System.err.println("Error in GOPUniformxfm. Size out of valid range");
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

	public void addEntry(int key, EPHMatf matrix) {
		entries.put(key, matrix);
	}

}
