/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import static org.lwjgl.opengl.GL20.*;

import java.util.HashMap;
import java.util.Map;


public class EPHUniformObject {

	private int index;
	private String name;
	private Map<Integer, EPHUniformContent> entries;

	public EPHUniformObject(String name) {
		this.name = name;
		index = -1;
		entries = new HashMap<Integer, EPHUniformContent>();
	}

	public void glUseUniform(int programHandle, int key) {
		if (index < 0) if (index < 0) index = glGetUniformLocation(programHandle, name);
		if (entries.containsKey(key)) entries.get(key).glUploadUniformContent(index);
	}

	public void addEntry(int key, EPHUniformContent content) {
		entries.put(key, content);
	}

	public void removeEntry(int key) {
		entries.remove(key);
	}

	public void reset() {
		index = -1;
	}

	public String getName() {
		return name;
	}

}
