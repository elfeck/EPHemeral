package com.elfeck.ephemeral.test;

/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.EPHVertexArrayObject;
import com.elfeck.ephemeral.glContext.EPHVertexAttribute;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformMatf;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformObject;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVecf;
import com.elfeck.ephemeral.math.EPHMatf;
import com.elfeck.ephemeral.math.EPHVecf;

public class EPHVaoContainer {
	
	private EPHVaoEntry vaoReference;
	private EPHVertexArrayObject vao;
	
	private List<Float> vertexValues;
	private List<Integer> indices;
	private List<EPHUniformObject> uniforms;
	private List<EPHVertexAttribute> attributes;
	
	public EPHVaoContainer() {
		vaoReference = null;
		vao = null;
		
		vertexValues = new ArrayList<Float>();
		indices = new ArrayList<Integer>();
		uniforms = new ArrayList<EPHUniformObject>();
		attributes = new ArrayList<EPHVertexAttribute>();
	}
	
	public void submitVaoData() {
		vaoReference = vao.addData(vertexValues, indices, "test");
	}
	
	public void createVao() {
		vao = new EPHVertexArrayObject(attributes, uniforms);
	}
	
	public void registerUniformVecf(String name, EPHVecf vec) {
		for(EPHUniformObject uo : uniforms) {
			if(uo.getName().equals(name) && uo instanceof EPHUniformVecf) {
				((EPHUniformVecf) uo).addEntry(vaoReference.getUniformKey(), vec);
			}
		}
	}
	
	public void registerUniformMatf(String name, EPHMatf mat) {
		for(EPHUniformObject uo : uniforms) {
			if(uo.getName().equals(name) && uo instanceof EPHUniformMatf) ((EPHUniformMatf) uo).addEntry(vaoReference.getUniformKey(), mat);
		}
	}
	
	public void delete() {
		vao.removeData(vaoReference);
	}
	
	public void setVertexData(List<Float> vertexValues) {
		this.vertexValues = vertexValues;
	}
	
	public void addVertexData(List<Float> vertexValues) {
		this.vertexValues.addAll(vertexValues);
	}
	
	public void addVertexData(float value) {
		vertexValues.add(value);
	}
	
	public void setIndexData(List<Integer> indices) {
		this.indices = indices;
	}
	
	public void addIndexData(List<Integer> indices) {
		this.indices.addAll(indices);
	}
	
	public void addIndexData(int index) {
		indices.add(index);
	}
	
	public void setVertexAttributes(List<EPHVertexAttribute> attributes) {
		this.attributes = attributes;
	}
	
	public void addVertexAttributes(List<EPHVertexAttribute> attributes) {
		this.attributes.addAll(attributes);
	}
	
	public void addVertexAttribute(EPHVertexAttribute attribute) {
		attributes.add(attribute);
	}
	
	public void addVertexAttribute(int index, int size, int stride, int offset, String name) {
		attributes.add(new EPHVertexAttribute(index, size, stride, offset, name));
	}
	
	public void setUniforms(List<EPHUniformObject> uniforms) {
		this.uniforms = uniforms;
	}
	
	public void addUniforms(List<EPHUniformObject> uniforms) {
		this.uniforms.addAll(uniforms);
	}
	
	public void addUniformVecf(String name) {
		uniforms.add(new EPHUniformVecf(name));
	}
	
	public void addUniformMatf(String name) {
		uniforms.add(new EPHUniformMatf(name));
	}
	
	public EPHVertexArrayObject getVao() {
		return vao;
	}
		
}
