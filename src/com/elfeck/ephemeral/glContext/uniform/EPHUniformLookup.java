/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext.uniform;

import java.util.HashMap;
import java.util.Map;


public class EPHUniformLookup {

	private String name;
	private Map<Integer, EPHUniformContent> entries;

	public EPHUniformLookup(String name) {
		this.name = name;
		entries = new HashMap<Integer, EPHUniformContent>();
	}

	/* should be protected */
	public void glUseUniform(int programHandle, int key) {
		if (entries.containsKey(key)) entries.get(key).glUploadUniformContent(key, name, programHandle);
	}

	public void addEntry(int key, EPHUniformContent content) {
		entries.put(key, content);
		content.addUniformEntry(key);
	}

	public void removeEntry(int key) {
		entries.get(key).removeUniformEntry(key);
		entries.remove(key);
	}

	public String getName() {
		return name;
	}

}
