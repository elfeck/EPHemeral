/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.elfeck.ephemeral.glContext.uniform.EPHUniformLookup;


public class EPHShaderProgramPool {

	private Map<String, EPHShaderProgram> programs;

	public EPHShaderProgramPool(String parentPath) {
		programs = new HashMap<String, EPHShaderProgram>();
		initShaderProgramPool(parentPath);
	}

	private void initShaderProgramPool(String parentPath) {
		Map<String, String[]> shaderSrcPairs = new HashMap<String, String[]>();
		loadShaderFiles(new File(parentPath), shaderSrcPairs);
		for (String key : shaderSrcPairs.keySet()) {
			programs.put(key, new EPHShaderProgram(shaderSrcPairs.get(key)[0], shaderSrcPairs.get(key)[1], uniformStringToShaderUniforms(shaderSrcPairs.get(key)[2])));
		}
	}

	private void loadShaderFiles(File folder, Map<String, String[]> shaderSrcPairs) {
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				loadShaderFiles(fileEntry, shaderSrcPairs);
			} else
				if (fileEntry.getName().contains("_frag.glsl") || fileEntry.getName().contains("_vert.glsl")) {
					String type = fileEntry.getName().substring(fileEntry.getName().length() - 9, fileEntry.getName().length());
					String name = fileEntry.getName().substring(0, fileEntry.getName().length() - 10);
					System.out.println(type + " " + name);
					if (type.equals("vert.glsl")) {
						if (shaderSrcPairs.containsKey(name)) {
							shaderSrcPairs.get(name)[0] = loadShaderSource(fileEntry);
						} else {
							shaderSrcPairs.put(name, new String[] { loadShaderSource(fileEntry), null, null });
						}
						shaderSrcPairs.get(name)[2] = extractUniforms(shaderSrcPairs.get(name)[0]);
					} else {
						if (type.equals("frag.glsl")) {
							if (shaderSrcPairs.containsKey(name)) {
								shaderSrcPairs.get(name)[1] = loadShaderSource(fileEntry);
							} else {
								shaderSrcPairs.put(name, new String[] { null, loadShaderSource(fileEntry), null });
							}
						}
					}
				}
		}
	}

	private String extractUniforms(String src) {
		String result = "";
		String[] rawTokens = src.split(";");
		for (String s : rawTokens) {
			s = s.replaceAll("\n", "");
			if (s.startsWith("uniform")) {
				String[] lineTokens = s.split(" ");
				result += lineTokens[1] + "#" + lineTokens[2] + "%";
			}
		}
		return result;
	}

	private EPHShaderUniformCollection uniformStringToShaderUniforms(String uniformString) {
		String[] uniforms = uniformString.split("%");
		EPHShaderUniformCollection shaderUniforms = new EPHShaderUniformCollection();
		for (String s : uniforms) {
			String[] cut = s.split("#");
			shaderUniforms.addUniformLookup(new EPHUniformLookup(cut[1]));
		}
		return shaderUniforms;
	}

	private String loadShaderSource(File file) {
		StringBuilder source = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.contains("//@insert")) {
					String nested = loadShaderSource(new File(file.getParent() + "/" + (line = line.replaceAll("//@insert", "").replaceAll(" ", ""))));
					System.out.println(nested);
					source.append(nested).append('\n');
				} else {
					source.append(line).append('\n');
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return source.toString();
	}

	protected void glInit() {
		for (String key : programs.keySet()) {
			programs.get(key).glCompileShaderSrc();
		}
	}

	protected EPHShaderProgram getShaderProgram(String key) {
		return programs.get(key);
	}

	public void glDisposeShaderPrograms() {
		for (String key : programs.keySet()) {
			programs.get(key).glDispose();
		}
	}

}
