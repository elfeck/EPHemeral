package com.elfeck.ephemeral.glContext;

import com.elfeck.ephemeral.math.EPHMatf;
import com.elfeck.ephemeral.math.EPHVecf;


/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public class EPHVaoEntry {

	protected int vboLowerBound, vboUpperBound;
	protected int iboLowerBound, iboUpperBound;
	protected int uniformKey;
	protected String programKey;
	protected EPHUniformTemplateBuffer utb;

	public EPHVaoEntry() {
		vboLowerBound = -1;
		vboUpperBound = -1;
		iboLowerBound = -1;
		iboUpperBound = -1;
		uniformKey = -1;
		programKey = null;
	}

	public EPHVaoEntry(int vboL, int vboU, int iboL, int iboU, int uniform, String program, EPHUniformTemplateBuffer utb) {
		vboLowerBound = vboL;
		vboUpperBound = vboU;
		iboLowerBound = iboL;
		iboUpperBound = iboU;
		uniformKey = uniform;
		programKey = program;
		this.utb = utb;
	}

	public void registerVecUniformEntry(String name, EPHVecf vec) {
		utb.registerVecUniformEntry(name, uniformKey, vec);
	}

	public void registerMatUniformEntry(String name, EPHMatf mat) {
		utb.registerMatUniformEntry(name, uniformKey, mat);
	}

	public void deleteUniformEntries() {
		utb.removeUniformEntry(uniformKey);
	}

	public String getProgramKey() {
		return programKey;
	}

	public void setProgram(String key) {
		programKey = key;
	}

}
