package com.elfeck.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;


public class EPHShaderObject {

	private int vertexSHandle, fragmentSHandle, programHandle;
	private String vertPath, fragPath;
	private Map<Integer, String> attribMap;

	protected EPHShaderObject(String vertPath, String fragPath, Map<Integer, String> attribMap) {
		vertexSHandle = -1;
		fragmentSHandle = -1;
		programHandle = -1;
		this.vertPath = vertPath;
		this.fragPath = fragPath;
		this.attribMap = attribMap;
	}

	private StringBuilder loadShaderSource(String path) {
		StringBuilder source = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = br.readLine()) != null) {
				source.append(line).append('\n');
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return source;
	}

	private void checkCompilation(int sHandle) {
		if (glGetShaderi(sHandle, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println("Error compiling Shader");
			System.err.println(glGetShaderInfoLog(sHandle, 10000));
			System.exit(1);
		}
	}

	private void checkValidation() {
		if (glGetProgrami(programHandle, GL_VALIDATE_STATUS) == GL_FALSE) {
			System.err.println("Error validating Program");
			System.err.println(glGetProgramInfoLog(programHandle, 10000));
			System.exit(1);
		}
	}

	private void bindAttribLocation() {
		for (Integer i : attribMap.keySet()) {
			glBindAttribLocation(programHandle, i, attribMap.get(i));
		}
	}

	protected void glInit() {
		programHandle = glCreateProgram();
		vertexSHandle = glCreateShader(GL_VERTEX_SHADER);
		fragmentSHandle = glCreateShader(GL_FRAGMENT_SHADER);

		glShaderSource(vertexSHandle, loadShaderSource(vertPath));
		glCompileShader(vertexSHandle);
		checkCompilation(vertexSHandle);

		glShaderSource(fragmentSHandle, loadShaderSource(fragPath));
		glCompileShader(fragmentSHandle);
		checkCompilation(fragmentSHandle);

		glAttachShader(programHandle, vertexSHandle);
		glAttachShader(programHandle, fragmentSHandle);
		bindAttribLocation();
		glLinkProgram(programHandle);
		glValidateProgram(programHandle);
		checkValidation();
	}

	protected void glBind() {
		if (programHandle < 0) glInit();
		glUseProgram(programHandle);
	}

	protected void glUnbind() {
		glUseProgram(0);
	}

	protected void glDispose() {
		glDeleteShader(vertexSHandle);
		glDeleteShader(fragmentSHandle);
		glDeleteProgram(programHandle);
	}

	protected int getProgramHandle() {
		return programHandle;
	}
}
