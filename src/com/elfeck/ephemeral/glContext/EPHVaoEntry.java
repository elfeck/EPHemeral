package com.elfeck.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public class EPHVaoEntry {

	protected int vboLowerBound, vboUpperBound;
	protected int iboLowerBound, iboUpperBound;
	protected int uniformKey;
	protected String programKey;

	public EPHVaoEntry() {
		vboLowerBound = -1;
		vboUpperBound = -1;
		iboLowerBound = -1;
		iboUpperBound = -1;
		uniformKey = -1;
		programKey = null;
	}

	public EPHVaoEntry(int vboL, int vboU, int iboL, int iboU, int uniform, String program) {
		vboLowerBound = vboL;
		vboUpperBound = vboU;
		iboLowerBound = iboL;
		iboUpperBound = iboU;
		uniformKey = uniform;
		programKey = program;
	}

	public int getUniformKey() {
		return uniformKey;
	}

	public String getProgramKey() {
		return programKey;
	}

	public void setProgram(String key) {
		programKey = key;
	}

}
