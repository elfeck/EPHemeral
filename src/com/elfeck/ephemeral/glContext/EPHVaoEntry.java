/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.glContext.uniform.EPHUniformContent;


public class EPHVaoEntry {

	private final EPHVao vao;
	private List<EPHVaoEntryDataSet> dataSets;
	protected boolean visible;
	protected int vboLowerBound, vboUpperBound;
	protected int iboLowerBound, iboUpperBound;
	protected int uniformKey;
	protected String programKey;
	protected EPHShaderUniformCollection shaderUniforms;

	protected EPHVaoEntry(EPHVao vao, int vboL, int vboU, int iboL, int iboU, int uniform, String program, EPHShaderUniformCollection shaderUniforms) {
		this.vao = vao;
		dataSets = new ArrayList<EPHVaoEntryDataSet>();
		visible = true;
		vboLowerBound = vboL;
		vboUpperBound = vboU;
		iboLowerBound = iboL;
		iboUpperBound = iboU;
		uniformKey = uniform;
		programKey = program;
		this.shaderUniforms = shaderUniforms;
	}

	protected EPHVaoEntry(EPHVao vao) {
		this(vao, -1, -1, -1, -1, -1, null, null);
	}

	public EPHVaoEntryDataSet addData(List<Float> newVertexValues, List<Integer> newIndices) {
		EPHVaoEntryDataSet dataSet;
		dataSets.add(dataSet = vao.addData(this, newVertexValues, newIndices));
		return dataSet;
	}

	public void removeData(EPHVaoEntryDataSet dataSet) {
		int deletedVertexValues = dataSet.vboSubUpper - dataSet.vboSubLower + 1;
		int deletedIndices = dataSet.iboSubUpper - dataSet.iboSubLower + 1;
		for (EPHVaoEntryDataSet other : dataSets) {
			if (other.iboSubLower > dataSet.iboSubLower) {
				other.iboSubLower -= deletedIndices;
				other.iboSubUpper -= deletedIndices;
			}
			if (other.vboSubLower > dataSet.vboSubLower) {
				other.vboSubLower -= deletedVertexValues;
				other.vboSubUpper -= deletedVertexValues;
			}
		}
		dataSets.remove(dataSet);
		vao.removeData(this, vboLowerBound + dataSet.vboSubLower, vboLowerBound + dataSet.vboSubUpper, iboLowerBound + dataSet.iboSubLower, iboLowerBound
				+ dataSet.iboSubUpper);
	}

	public void updateVboData(EPHVaoEntryDataSet dataSet, List<Float> vertexValues) {
		vao.updateVboData(vboLowerBound + dataSet.vboSubLower, vboLowerBound + dataSet.vboSubUpper, vertexValues);
	}

	public void registerUniformEntry(String name, EPHUniformContent content) {
		shaderUniforms.registerUniformEntry(name, uniformKey, content);
	}

	public String getProgramKey() {
		return programKey;
	}

	public void switchShaderUniforms(String futureProgramKey) {
		shaderUniforms = EPHVao.shaderProgramPool.getShaderProgram(futureProgramKey).getShaderUniforms();
	}

	public void switchProgram(String futureProgramKey) {
		if (!programKey.equals(futureProgramKey)) EPHVao.shaderProgramPool.getShaderProgram(programKey).getShaderUniforms().removeUniformEntry(uniformKey);
		this.programKey = futureProgramKey;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

}
