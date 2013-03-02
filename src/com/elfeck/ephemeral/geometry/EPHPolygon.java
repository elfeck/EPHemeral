package com.elfeck.ephemeral.geometry;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.EPHVertexArrayObject;
import com.elfeck.ephemeral.math.EPHMatf;
import com.elfeck.ephemeral.math.EPHVecf;


public abstract class EPHPolygon {

	private String programKey;
	private EPHVaoEntry vaoRef;
	private Map<String, EPHVecf> vecUniforms;
	private Map<String, EPHMatf> matUniforms;

	protected int vertexCount;
	protected EPHVertex[] vertices;
	protected EPHTriangle[] triangles;

	public EPHPolygon(String programKey, EPHVertex[] vertices) {
		this.programKey = programKey;
		vaoRef = null;
		vecUniforms = new HashMap<String, EPHVecf>();
		matUniforms = new HashMap<String, EPHMatf>();
		this.vertices = vertices;
		tessellate();
	}

	private List<Float> assembleVertexValues() {
		List<Float> vertexValues = new ArrayList<Float>();
		for (int i = 0; i < vertices.length; i++) {
			vertices[i].fetchFloatData(vertexValues);
		}
		return vertexValues;
	}

	private List<Integer> assembleIndices() {
		List<Integer> indices = new ArrayList<Integer>();
		for (EPHTriangle triangle : triangles) {
			triangle.fetchIndexData(indices);
		}
		return indices;
	}

	public void addDataToVao(EPHVertexArrayObject vao) {
		// TODO: register uniforms
		vaoRef = vao.addData(assembleVertexValues(), assembleIndices(), programKey);
	}

	public void removeDataFromVao(EPHVertexArrayObject vao) {
		vao.removeData(vaoRef);
	}

	public void addUniformVecf(String name, EPHVecf uniform) {
		vecUniforms.put(name, uniform);
	}

	public void addUniformMatf(String name, EPHMatf uniform) {
		matUniforms.put(name, uniform);
	}

	protected abstract void tessellate();

}
