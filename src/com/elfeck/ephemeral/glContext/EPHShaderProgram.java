/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.util.Map;


public class EPHShaderProgram {

	private boolean isLinked;
	private int vertexSHandle, fragmentSHandle;
	private int programHandle;
	private String vertSrc, fragSrc;
	private EPHShaderUniformCollection shaderUniforms;

	protected EPHShaderProgram(String vertSrc, String fragSrc, EPHShaderUniformCollection shaderUniforms) {
		this.vertSrc = vertSrc;
		this.fragSrc = fragSrc;
		this.shaderUniforms = shaderUniforms;
		isLinked = false;
		vertexSHandle = -1;
		fragmentSHandle = -1;
		programHandle = -1;
	}

	private void glBindAttributeLocation(Map<Integer, String> attribMap) {
		for (Integer i : attribMap.keySet()) {
			glBindAttribLocation(programHandle, i, attribMap.get(i));
		}
	}

	private void glCheckCompilation(int sHandle) {
		if (glGetShaderi(sHandle, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Error compiling Shader");
			System.err.println(glGetShaderInfoLog(sHandle, 10000));
			System.exit(1);
		}
	}

	private void glCheckValidation() {
		if (glGetProgrami(programHandle, GL_VALIDATE_STATUS) == GL_FALSE) {
			System.err.println("Error validating Program");
			System.err.println(glGetProgramInfoLog(programHandle, 10000));
			System.exit(1);
		}
	}

	protected void glCompileShaderSrc() {
		vertexSHandle = glCreateShader(GL_VERTEX_SHADER);
		fragmentSHandle = glCreateShader(GL_FRAGMENT_SHADER);

		glShaderSource(vertexSHandle, vertSrc);
		glCompileShader(vertexSHandle);
		glCheckCompilation(vertexSHandle);

		glShaderSource(fragmentSHandle, (fragSrc));
		glCompileShader(fragmentSHandle);
		glCheckCompilation(fragmentSHandle);
	}

	protected void glAttachAndLinkProgram(Map<Integer, String> attribMap) {
		programHandle = glCreateProgram();
		glAttachShader(programHandle, vertexSHandle);
		glAttachShader(programHandle, fragmentSHandle);
		glBindAttributeLocation(attribMap);
		glLinkProgram(programHandle);
		glValidateProgram(programHandle);
		glCheckValidation();
		isLinked = true;
	}

	protected void glBind() {
		glUseProgram(programHandle);
	}

	protected void glUseUniforms(int key) {
		if (shaderUniforms != null) shaderUniforms.glUseUniforms(programHandle, key);
	}

	protected void glUnbind() {
		glUseProgram(0);
	}

	protected void glDispose() {
		glDeleteShader(vertexSHandle);
		glDeleteShader(fragmentSHandle);
		glDeleteProgram(programHandle);
	}

	protected boolean isLinked() {
		return isLinked;
	}

	protected int getProgramHandle() {
		return programHandle;
	}

	protected EPHShaderUniformCollection getShaderUniforms() {
		return shaderUniforms;
	}

}
