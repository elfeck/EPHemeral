package de.greenOwlProduction.ephemeral.glContext;

import de.greenOwlProduction.ephemeral.glContext.uniform.GOPUniformObject;

import java.util.List;


public class GOPUniformBufferObject {

	private List<GOPUniformObject> uniforms;

	protected GOPUniformBufferObject(List<GOPUniformObject> uniforms) {
		this.uniforms = uniforms;
	}

	protected void useUniforms(int programHandle, int key) {
		for (GOPUniformObject uo : uniforms) {
			uo.useUniform(programHandle, key);
		}
	}

	protected void addUniform(GOPUniformObject uniform) {
		uniforms.add(uniform);
	}

	protected void resetUniforms() {
		for (GOPUniformObject uo : uniforms) {
			uo.reset();
		}
	}

	public void removeUniformEntry(int key) {
		for (GOPUniformObject uo : uniforms) {
			uo.removeEntry(key);
		}
	}

}
