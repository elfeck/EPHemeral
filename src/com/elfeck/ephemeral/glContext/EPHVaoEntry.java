/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import com.elfeck.ephemeral.math.EPHMatf;
import com.elfeck.ephemeral.math.EPHVecf;


public class EPHVaoEntry {

	protected volatile boolean visible;
	protected int vboLowerBound, vboUpperBound;
	protected int iboLowerBound, iboUpperBound;
	protected int uniformKey;
	protected volatile String programKey;
	protected volatile EPHUniformTemplateBuffer utb;

	protected EPHVaoEntry() {
		visible = true;
		vboLowerBound = -1;
		vboUpperBound = -1;
		iboLowerBound = -1;
		iboUpperBound = -1;
		uniformKey = -1;
		programKey = null;
	}

	protected EPHVaoEntry(int vboL, int vboU, int iboL, int iboU, int uniform, String program, EPHUniformTemplateBuffer utb) {
		visible = true;
		vboLowerBound = vboL;
		vboUpperBound = vboU;
		iboLowerBound = iboL;
		iboUpperBound = iboU;
		uniformKey = uniform;
		programKey = program;
		this.utb = utb;
	}

	public EPHVaoEntry(String programKey) {
		this();
		this.programKey = programKey;
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

	/*
	 * Not feeling good about this access!
	 */
	public void switchUniformTemplateBuffer(String futureProgramKey) {
		utb = EPHVertexArrayObject.getShaderProgramPool().getShaderProgram(futureProgramKey).getUniformTemplateBuffer();
	}

	public void switchProgram(String programKey) {
		if (!this.programKey.equals(programKey)) EPHVertexArrayObject.getShaderProgramPool().getShaderProgram(this.programKey).
				getUniformTemplateBuffer().removeUniformEntry(uniformKey);
		this.programKey = programKey;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
