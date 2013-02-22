package de.greenOwlProduction.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;
import de.greenOwlProduction.ephemeral.glContext.uniform.GOPUniformObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GOPVertexArrayObject {

	private boolean dead, updated;
	private int mode, size, usage, handle, currentKey;
	private List<int[][]> entries;
	private GOPVertexBufferObject vbo;
	private GOPIndexBufferObject ibo;
	private GOPUniformBufferObject ubo;
	private GOPShaderObject program;

	public GOPVertexArrayObject(int mode, int usage, List<GOPVertexAttribute> vertexAttributes, List<GOPUniformObject> uniforms, String vertSPath, String fragSPath) {
		dead = false;
		updated = false;
		this.mode = mode;
		this.size = 0;
		this.usage = usage;
		handle = -1;
		currentKey = 0;
		entries = new ArrayList<int[][]>();
		vbo = new GOPVertexBufferObject(vertexAttributes);
		ibo = new GOPIndexBufferObject();
		ubo = new GOPUniformBufferObject(uniforms);
		program = new GOPShaderObject(vertSPath, fragSPath, vertexAttributesToMap(vertexAttributes));
	}

	public GOPVertexArrayObject(List<GOPVertexAttribute> vertexAttributes, List<GOPUniformObject> uniforms, String vertSPath, String fragSPath) {
		this(GOPRenderUtils.TYPE_TRIANGLES, GOPRenderUtils.MODE_STATIC_DRAW, vertexAttributes, uniforms, vertSPath, fragSPath);
	}

	private void glInit() {
		if (handle < 0) {
			program.glInit();
			handle = glGenVertexArrays();
		}
		vbo.glInit(usage);
		ibo.glInit(usage);

		glBindVertexArray(handle);
		vbo.glBind();
		ibo.glBind();
		glBindVertexArray(0);
		updated = true;
	}

	private Map<Integer, String> vertexAttributesToMap(List<GOPVertexAttribute> vertexAttributes) {
		Map<Integer, String> names = new HashMap<Integer, String>();
		for (GOPVertexAttribute va : vertexAttributes) {
			names.put(va.index, va.name);
		}
		return names;
	}

	public void render() {
		if (handle < 0 || !updated) glInit();
		if (size > 0) {
			glBindVertexArray(handle);
			program.glBind();
			for (int[][] ref : entries) {
				ubo.useUniforms(program.getProgramHandle(), ref[2][0]);
				glDrawElements(mode, ref[1][1] - ref[1][0] + 1, GL_UNSIGNED_INT, ref[1][0] * 4);
			}
			program.glUnbind();
			glBindVertexArray(0);
		}
	}
	public void glDispose() {
		vbo.glDispose();
		ibo.glDispose();
		program.glDispose();
		glDeleteBuffers(handle);
	}

	public int[][] addData(List<Float> vertexValues, List<Integer> indices) {
		int[][] references = {
								{ vbo.getCurrentIndex(), vbo.getCurrentIndex() + vertexValues.size() - 1 },
								{ ibo.getCurrentIndex(), ibo.getCurrentIndex() + indices.size() - 1 },
								{ currentKey++ }
		};
		ibo.addData(indices, vbo.addData(vertexValues));
		size += indices.size();
		entries.add(references);
		updated = false;
		return references;
	}

	public void removeData(int[][] references) {
		ibo.removeData(references[1], vbo.removeData(references[0]));
		ubo.removeUniformEntry(references[2][0]);
		size -= (references[1][1] - references[1][0]) + 1;
		int deletedVertexValues = references[0][1] - references[0][0] + 1;
		int deletedIndices = references[1][1] - references[1][0] + 1;
		for (int i = entries.indexOf(references) + 1; i < entries.size(); i++) {
			int[][] currentRef = entries.get(i);
			currentRef[0][0] -= deletedVertexValues;
			currentRef[0][1] -= deletedVertexValues;
			currentRef[1][0] -= deletedIndices;
			currentRef[1][1] -= deletedIndices;
		}
		entries.remove(references);
		updated = false;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

}
