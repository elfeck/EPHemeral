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


public class EPHPolygon implements EPHDrawable {

	private String programKey;
	private EPHVaoEntry vaoRef;
	private Map<String, EPHVecf> vecUniforms;
	private Map<String, EPHMatf> matUniforms;

	private EPHVertex[] vertices;
	private EPHTriangle[] triangles;

	public EPHPolygon(String programKey, EPHVertex[] vertices) {
		this.programKey = programKey;
		vaoRef = null;
		vecUniforms = new HashMap<String, EPHVecf>();
		matUniforms = new HashMap<String, EPHMatf>();
		this.vertices = vertices;
		tessellate();
	}

	public EPHPolygon(String programKey, EPHVertex[] vertices, Map<String, EPHVecf> vecUniforms, Map<String, EPHMatf> matUniforms) {
		this(programKey, vertices);
		vecUniforms.putAll(vecUniforms);
		matUniforms.putAll(matUniforms);
	}

	private void tessellate() {
		triangles = new EPHTriangle[vertices.length - 2];
		for (int i = 1; i < vertices.length - 1; i++) {
			triangles[i - 1] = new EPHTriangle(new EPHVertex[] { vertices[0], vertices[i], vertices[i + 1] });

		}
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
		vaoRef = vao.addData(assembleVertexValues(), assembleIndices(), programKey);
		for (String key : vecUniforms.keySet()) {
			vaoRef.registerVecUniformEntry(key, vecUniforms.get(key));
		}
		for (String key : matUniforms.keySet()) {
			vaoRef.registerMatUniformEntry(key, matUniforms.get(key));
		}
	}

	public void removeDataFromVao(EPHVertexArrayObject vao) {
		vaoRef.deleteUniformEntries();
		vao.removeData(vaoRef);
	}

	public void addUniformVecf(String name, EPHVecf uniform) {
		vecUniforms.put(name, uniform);
	}

	public void addUniformMatf(String name, EPHMatf uniform) {
		matUniforms.put(name, uniform);
	}

}
