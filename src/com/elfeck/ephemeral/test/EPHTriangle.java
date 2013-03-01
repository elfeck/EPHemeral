package com.elfeck.ephemeral.test;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.math.EPHMat4f;
import com.elfeck.ephemeral.math.EPHVec2f;

public class EPHTriangle implements EPHEntity {
	
	private boolean dead;
	private EPHVaoContainer vao;
	private EPHMat4f mvpMatrix;
	private EPHVec2f offset;
	
	public EPHTriangle(EPHSurface surface) {
		dead = false;
		vao = new EPHVaoContainer();
		offset = new EPHVec2f(0f, 0f);
		mvpMatrix = new EPHMat4f(new float[][] {
		                                     { 1, 0, 0, 0 },
		                                     { 0, 1, 0, 0 },
		                                     { 0, 0, 1, 0 },
		                                     { 0, 0, 0, 1 }
		});
		initVao();
		surface.addVao(vao.getVao());
	}
	
	@Override
	public void doLogic(long delta) {
		offset.addVec2f(0.0001f, 0.0001f);
	}
	
	@Override
	public boolean isDead() {
		return dead;
	}
	
	private void initVao() {
		vao.setShaderSource("shader/test/test_vert.glsl", "shader/test/test_frag.glsl");
		vao.addVertexAttribute(0, 4, 4 * 4 * 2, 4 * 4 * 0, "pos_model");
		vao.addVertexAttribute(1, 4, 4 * 4 * 2, 4 * 4 * 1, "col_model");
		vao.addUniformMatf("mvp_matrix");
		vao.addUniformVecf("offset");
		initModel();
		vao.createVao();
		vao.submitVaoData();
		vao.registerUniformMatf("mvp_matrix", mvpMatrix);
		vao.registerUniformVecf("offset", offset);
	}
	
	private void initModel() {
		vao.addIndexData(0);
		vao.addIndexData(1);
		vao.addIndexData(2);
		
		vao.addVertexData(0);
		vao.addVertexData(0);
		vao.addVertexData(0);
		vao.addVertexData(1);
		vao.addVertexData(1);
		vao.addVertexData(0);
		vao.addVertexData(0);
		vao.addVertexData(1);
		
		vao.addVertexData(0.5f);
		vao.addVertexData(0);
		vao.addVertexData(0);
		vao.addVertexData(1);
		vao.addVertexData(0);
		vao.addVertexData(1);
		vao.addVertexData(0);
		vao.addVertexData(0.4f);
		
		vao.addVertexData(0);
		vao.addVertexData(0.5f);
		vao.addVertexData(0);
		vao.addVertexData(1);
		vao.addVertexData(0);
		vao.addVertexData(0);
		vao.addVertexData(1);
		vao.addVertexData(0.4f);
	}

}
