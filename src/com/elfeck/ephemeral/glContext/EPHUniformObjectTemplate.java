package com.elfeck.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.glContext.uniform.EPHUniformObject;


public class EPHUniformObjectTemplate {

	private List<EPHUniformObject> uniformTemplates;

	protected EPHUniformObjectTemplate(List<EPHUniformObject> uniformTemplates) {
		this.uniformTemplates = uniformTemplates;
	}

	protected EPHUniformObjectTemplate() {
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

	public void removeUniformEntry(int key) {
		for (EPHUniformObject uo : uniformTemplates) {
			uo.removeEntry(key);
		}
	}

}
