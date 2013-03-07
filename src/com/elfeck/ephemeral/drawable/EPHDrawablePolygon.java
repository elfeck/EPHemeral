/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.drawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.EPHVertexArrayObject;
import com.elfeck.ephemeral.math.EPHMatf;
import com.elfeck.ephemeral.math.EPHPolygon2f;
import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.ephemeral.math.EPHVecf;


public class EPHDrawablePolygon implements EPHDrawable, EPHCollidable {

	private String programKey;
	private EPHVaoEntry vaoRef;
	private Map<String, EPHVecf> vecUniforms;
	private Map<String, EPHMatf> matUniforms;

	private EPHPolygon2f polygon;
	private EPHVertex[] vertices;
	private EPHTessellationTriangle[] triangles;

	public EPHDrawablePolygon(String programKey, EPHVertex[] vertices, int coordinateIndex) {
		this.programKey = programKey;
		vaoRef = null;
		vecUniforms = new HashMap<String, EPHVecf>();
		matUniforms = new HashMap<String, EPHMatf>();
		this.vertices = vertices;
		EPHVec2f[] vertices2f = new EPHVec2f[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			vertices2f[i] = vertices[i].getVec(0).toVec2f();
		}
		polygon = new EPHPolygon2f(vertices2f);
		tessellate();
	}

	public EPHDrawablePolygon(String programKey, EPHVertex[] vertices) {
		this(programKey, vertices, 0);
	}

	public EPHDrawablePolygon(String programKey, EPHVertex[] vertices, Map<String, EPHVecf> vecUniforms, Map<String, EPHMatf> matUniforms) {
		this(programKey, vertices);
		vecUniforms.putAll(vecUniforms);
		matUniforms.putAll(matUniforms);
	}

	@Override
	public boolean collidesWith(EPHCollidable other) {
		return EPHCollisionCalc.optimalCollision(this, other);
	}

	@Override
	public EPHPolygon2f getPolygon() {
		return polygon;
	}

	private void tessellate() {
		triangles = new EPHTessellationTriangle[vertices.length - 2];
		for (int i = 1; i < vertices.length - 1; i++) {
			triangles[i - 1] = new EPHTessellationTriangle(new EPHVertex[] { vertices[0], vertices[i], vertices[i + 1] });

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
		for (EPHTessellationTriangle triangle : triangles) {
			triangle.fetchIndexData(indices);
		}
		return indices;
	}

	@Override
	public void addDataToVao(EPHVertexArrayObject vao) {
		vaoRef = vao.addData(assembleVertexValues(), assembleIndices(), programKey);
		updateUniformEntries();
	}

	@Override
	public void removeDataFromVao(EPHVertexArrayObject vao) {
		vaoRef.deleteUniformEntries();
		vao.removeData(vaoRef);
	}

	public void addUniformVecf(String name, EPHVecf uniform) {
		vecUniforms.put(name, uniform);
	}

	public void removeUniformVecf(String name) {
		vecUniforms.remove(name);
	}

	public void removeUniformMatf(String name) {
		vecUniforms.remove(name);
	}

	public void addUniformMatf(String name, EPHMatf uniform) {
		matUniforms.put(name, uniform);
	}

	public void updateUniformEntries() {
		for (String key : vecUniforms.keySet()) {
			vaoRef.registerVecUniformEntry(key, vecUniforms.get(key));
		}
		for (String key : matUniforms.keySet()) {
			vaoRef.registerMatUniformEntry(key, matUniforms.get(key));
		}
	}

	public void switchUniformTemplateBuffer(String futureProgramKey) {
		vaoRef.switchUniformTemplateBuffer(futureProgramKey);
	}

	public void switchProgram(String programKey) {
		vaoRef.switchProgram(programKey);
	}

	public boolean isVisible() {
		return vaoRef.isVisible();
	}

	public void setVisible(boolean visible) {
		vaoRef.setVisible(visible);
	}

}
