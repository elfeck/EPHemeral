/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.glContext.uniform.EPHUniformContent;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformLookup;


public class EPHShaderUniformCollection {

	private List<EPHUniformLookup> uniformLookups;

	protected EPHShaderUniformCollection(List<EPHUniformLookup> uniformTemplates) {
		this.uniformLookups = uniformTemplates;
	}

	protected EPHShaderUniformCollection() {
		uniformLookups = new ArrayList<EPHUniformLookup>();
	}

	protected void glUseUniforms(int programHandle, int key) {
		for (EPHUniformLookup uo : uniformLookups) {
			uo.glUseUniform(programHandle, key);
		}
	}

	protected void addUniformLookup(EPHUniformLookup uniform) {
		uniformLookups.add(uniform);
	}

	protected void addUniformLookups(List<EPHUniformLookup> uniforms) {
		uniforms.addAll(uniforms);
	}

	protected void registerUniformEntry(String name, int key, EPHUniformContent content) {
		for (EPHUniformLookup uo : uniformLookups) {
			if (uo.getName().equals(name)) uo.addEntry(key, content);
		}
	}

	protected void removeUniformEntry(int key) {
		for (EPHUniformLookup uo : uniformLookups) {
			uo.removeEntry(key);
		}
	}

}
