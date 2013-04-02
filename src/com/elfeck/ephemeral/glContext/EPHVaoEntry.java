/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import com.elfeck.ephemeral.glContext.uniform.EPHUniformContent;


public class EPHVaoEntry {

	protected volatile boolean visible;
	protected int vboLowerBound, vboUpperBound;
	protected int iboLowerBound, iboUpperBound;
	protected int uniformKey;
	protected volatile String programKey;
	protected volatile EPHShaderUniformCollection shaderUniforms;

	protected EPHVaoEntry() {
		visible = true;
		vboLowerBound = -1;
		vboUpperBound = -1;
		iboLowerBound = -1;
		iboUpperBound = -1;
		uniformKey = -1;
		programKey = null;
	}

	protected EPHVaoEntry(int vboL, int vboU, int iboL, int iboU, int uniform, String program, EPHShaderUniformCollection shaderUniforms) {
		visible = true;
		vboLowerBound = vboL;
		vboUpperBound = vboU;
		iboLowerBound = iboL;
		iboUpperBound = iboU;
		uniformKey = uniform;
		programKey = program;
		this.shaderUniforms = shaderUniforms;
	}

	public EPHVaoEntry(String programKey) {
		this();
		this.programKey = programKey;
	}

	public void registerUniformEntry(String name, EPHUniformContent content) {
		shaderUniforms.registerUniformEntry(name, uniformKey, content);
	}

	public void deleteUniformEntries() {
		shaderUniforms.removeUniformEntry(uniformKey);
	}

	public String getProgramKey() {
		return programKey;
	}

	public void switchShaderUniforms(String futureProgramKey) {
		shaderUniforms = EPHVertexArrayObject.getShaderProgramPool().getShaderProgram(futureProgramKey).getShaderUniforms();
	}

	public void switchProgram(String programKey) {
		if (!this.programKey.equals(programKey)) EPHVertexArrayObject.getShaderProgramPool().getShaderProgram(this.programKey).
				getShaderUniforms().removeUniformEntry(uniformKey);
		this.programKey = programKey;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
