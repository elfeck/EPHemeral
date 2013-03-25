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
import com.elfeck.ephemeral.math.EPHMatf;
import com.elfeck.ephemeral.math.EPHPolygon2f;
import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.ephemeral.math.EPHVecf;


public class EPHDrawablePolygon implements EPHCollidable {

	private String programKey;
	private EPHVaoEntry vaoRef;
	private Map<String, EPHVecf> vecUniforms;
	private Map<String, EPHMatf> matUniforms;
	private EPHDrawableModel model;

	private EPHVec2f oldPolygonOffset, polygonOffset;
	private EPHPolygon2f polygon;
	private EPHVertex[] vertices;
	private EPHTessellationTriangle[] triangles;

	public EPHDrawablePolygon(EPHDrawableModel model, String programKey, EPHVertex[] vertices, EPHVec2f polygonOffset, int coordinateIndex) {
		this.programKey = programKey;
		vaoRef = null;
		vecUniforms = new HashMap<String, EPHVecf>();
		matUniforms = new HashMap<String, EPHMatf>();
		this.model = model;
		this.vertices = vertices;
		this.polygonOffset = polygonOffset;
		oldPolygonOffset = null;
		EPHVec2f[] vertices2f = new EPHVec2f[vertices.length];
		for (int i = 0; i < vertices.length; i++) {
			vertices2f[i] = vertices[i].getVec(0).toVec2f();
		}
		polygon = new EPHPolygon2f(vertices2f);
		tessellate();
	}

	public EPHDrawablePolygon(EPHDrawableModel model, String programKey, EPHVertex[] vertices, EPHVec2f polygonOffset) {
		this(model, programKey, vertices, polygonOffset, 0);
	}

	public EPHDrawablePolygon(EPHDrawableModel model, String programKey, EPHVertex[] vertices, EPHVec2f polygonOffset,
			Map<String, EPHVecf> vecUniforms, Map<String, EPHMatf> matUniforms) {
		this(model, programKey, vertices, polygonOffset);
		vecUniforms.putAll(vecUniforms);
		matUniforms.putAll(matUniforms);
	}

	@Override
	public boolean collidesWith(EPHCollidable other) {
		return EPHCollisionCalc.optimalCollision(this, other);
	}

	@Override
	public EPHPolygon2f getPolygon() {
		updatePolygonPosition();
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
			vertices[i].fetchVertexData(vertexValues);
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

	private void updatePolygonPosition() {
		for (int i = 0; i < polygon.getVertexCount(); i++) {
			if (oldPolygonOffset == null) {
				polygon.getVertices()[i].addVec2f(polygonOffset);
			} else {
				polygon.getVertices()[i].subVec2f(oldPolygonOffset).addVec2f(polygonOffset);
			}
		}
		oldPolygonOffset = polygonOffset.copy();
	}

	public void addDataToVao() {
		vaoRef = model.addToVao(assembleVertexValues(), assembleIndices(), programKey);
		updateUniformEntries();
	}

	public void removeDataFromVao() {
		vaoRef.deleteUniformEntries();
		model.removeFromVao(vaoRef);
	}

	public void updateVertexData() {
		List<Float> updatedData = new ArrayList<Float>();
		int subLower = -1;
		int subUpper = -1;
		for (int i = 0; i < vertices.length; i++) {
			if (vertices[i].isUpdated() && subLower == -1) subLower = i;
			if (vertices[i].isUpdated()) subUpper = i;
		}
		if (subLower == -1) return;
		for (int i = subLower; i <= subUpper; i++) {
			vertices[i].fetchVertexData(updatedData);
			vertices[i].setUpdated(false);
		}
		model.updateToVbo(vaoRef, subLower, subUpper, updatedData);
	}

	public void updateIndexData() {
		// TODO: Blablabla
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
