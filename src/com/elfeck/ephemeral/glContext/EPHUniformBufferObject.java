package com.elfeck.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */


import java.util.List;

import com.elfeck.ephemeral.glContext.uniform.EPHUniformObject;


public class EPHUniformBufferObject {

	private List<EPHUniformObject> uniforms;

	protected EPHUniformBufferObject(List<EPHUniformObject> uniforms) {
		this.uniforms = uniforms;
	}

	protected void useUniforms(int programHandle, int key) {
		for (EPHUniformObject uo : uniforms) {
			uo.useUniform(programHandle, key);
		}
	}

	protected void addUniform(EPHUniformObject uniform) {
		uniforms.add(uniform);
	}

	protected void resetUniforms() {
		for (EPHUniformObject uo : uniforms) {
			uo.reset();
		}
	}

	public void removeUniformEntry(int key) {
		for (EPHUniformObject uo : uniforms) {
			uo.removeEntry(key);
		}
	}

}
