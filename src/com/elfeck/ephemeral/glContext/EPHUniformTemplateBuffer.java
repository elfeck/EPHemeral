package com.elfeck.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.glContext.uniform.EPHUniformMatf;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformObject;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVecf;
import com.elfeck.ephemeral.math.EPHMatf;
import com.elfeck.ephemeral.math.EPHVecf;


public class EPHUniformTemplateBuffer {

	private List<EPHUniformObject> uniformTemplates;

	protected EPHUniformTemplateBuffer(List<EPHUniformObject> uniformTemplates) {
		this.uniformTemplates = uniformTemplates;
	}

	protected EPHUniformTemplateBuffer() {
		uniformTemplates = new ArrayList<EPHUniformObject>();
	}

	protected void glUseUniforms(int programHandle, int key) {
		for (EPHUniformObject uo : uniformTemplates) {
			uo.glUseUniform(programHandle, key);
		}
	}

	protected void addUniformTemplate(EPHUniformObject uniform) {
		uniformTemplates.add(uniform);
	}

	protected void addUniformTemplates(List<EPHUniformObject> uniforms) {
		uniforms.addAll(uniforms);
	}

	protected void resetUniforms() {
		for (EPHUniformObject uo : uniformTemplates) {
			uo.reset();
		}
	}

	protected void registerVecUniformEntry(String name, int key, EPHVecf vec) {
		for (EPHUniformObject uo : uniformTemplates) {
			if (uo.getName().equals(name) && uo instanceof EPHUniformVecf) ((EPHUniformVecf) uo).addEntry(key, vec);
		}
	}

	protected void registerMatUniformEntry(String name, int key, EPHMatf mat) {
		for (EPHUniformObject uo : uniformTemplates) {
			if (uo.getName().equals(name) && uo instanceof EPHUniformMatf) ((EPHUniformMatf) uo).addEntry(key, mat);
		}
	}

	protected void removeUniformEntry(int key) {
		for (EPHUniformObject uo : uniformTemplates) {
			uo.removeEntry(key);
		}
	}

}
