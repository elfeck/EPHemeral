/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.glContext.uniform.EPHUniformContent;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformObject;


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

	protected void registerUniformEntry(String name, int key, EPHUniformContent content) {
		for (EPHUniformObject uo : uniformTemplates) {
			if (uo.getName().equals(name)) uo.addEntry(key, content);
		}
	}

	protected void removeUniformEntry(int key) {
		for (EPHUniformObject uo : uniformTemplates) {
			uo.removeEntry(key);
		}
	}

}
