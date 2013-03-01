package com.elfeck.ephemeral.glContext;

/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EPHShaderProgramPool {
	
	private boolean initialized;
	private Map<String, EPHShaderProgram> programs;
	
	public EPHShaderProgramPool() {
		initialized = false;
		programs = new HashMap<String, EPHShaderProgram>();
		initShaderProgramPool();
	}
	
	private void initShaderProgramPool() {
		Map< String, String[]> shaderSrcPairs = new HashMap<String, String[]>();
		loadShaderFiles(new File("shader/"), shaderSrcPairs);
		for(String key : shaderSrcPairs.keySet()) {
			programs.put(key, new EPHShaderProgram(shaderSrcPairs.get(key)[0],shaderSrcPairs.get(key)[1]));
		}
	}

	private void loadShaderFiles(File folder, Map<String, String[]> shaderSrcPairs) {
		for(File fileEntry : folder.listFiles()) {
			if(fileEntry.isDirectory()) {
				loadShaderFiles(fileEntry, shaderSrcPairs);
			} else {
				String[] name = fileEntry.getName().split("_");
				if(name[1].equals("vert.glsl")) {
					if(shaderSrcPairs.containsKey(name[0])) {
						shaderSrcPairs.get(name[0])[0] = loadShaderSource(fileEntry);
					} else {
						shaderSrcPairs.put(name[0], new String[] {loadShaderSource(fileEntry), null});
					}
				} else if(name[1].equals("frag.glsl")) {
					if(shaderSrcPairs.containsKey(name[0])) {
						shaderSrcPairs.get(name[0])[1] = loadShaderSource(fileEntry);
					} else {
						shaderSrcPairs.put(name[0], new String[] {null, loadShaderSource(fileEntry)});
					}
				}
			}
		}
	}
	
	private String loadShaderSource(File file) {
		StringBuilder source = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = br.readLine()) != null) {
				source.append(line).append('\n');
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return source.toString();
	}

	protected void glInit() {
		for(String key : programs.keySet()) {
			programs.get(key).glCompileShaderSrc();
		}
		initialized = true;
	}
	
	protected boolean isInitialized() {
		return initialized;
	}
	
	protected EPHShaderProgram getShaderProgram(String key) {
		return programs.get(key);
	}

	public void glDisposeShaderPrograms() {
		for(String key : programs.keySet()) {
			programs.get(key).glDispose();
		}
	}
	
}
