package com.elfeck.ephemeral.test;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.geometry.EPHConvexPolygon;
import com.elfeck.ephemeral.geometry.EPHModel;
import com.elfeck.ephemeral.geometry.EPHPolygon;
import com.elfeck.ephemeral.geometry.EPHVertex;
import com.elfeck.ephemeral.math.EPHMat4f;
import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.ephemeral.math.EPHVec4f;
import com.elfeck.ephemeral.math.EPHVecf;


/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public class EPHTestEntity implements EPHEntity {

	private boolean dead;
	private EPHModel model;
	private EPHVec2f offset;
	private EPHMat4f mvpMatrix;

	public EPHTestEntity(EPHSurface surface) {
		dead = false;
		model = new EPHModel(surface);
		offset = new EPHVec2f(-0.5f, -0.5f);
		mvpMatrix = new EPHMat4f(new float[][] {
												{ 1, 0, 0, 0 },
												{ 0, 1, 0, 0 },
												{ 0, 0, 1, 0 },
												{ 0, 0, 0, 1 }
		});
		initModel();
	}

	@Override
	public void doLogic(long delta) {

	}

	@Override
	public boolean isDead() {
		return dead;
	}

	private void initModel() {
		EPHVertex[] vertices = new EPHVertex[4];
		vertices[0] = new EPHVertex(0, new EPHVecf[] { new EPHVec4f(0, 0, 0, 1), new EPHVec4f(0.7f, 0, 0, 1) });
		vertices[1] = new EPHVertex(1, new EPHVecf[] { new EPHVec4f(0.9f, 0, 0, 1), new EPHVec4f(0, 0.7f, 0, 1) });
		vertices[3] = new EPHVertex(2, new EPHVecf[] { new EPHVec4f(0.9f, 0.9f, 0, 1), new EPHVec4f(0.7f, 0.7f, 0, 1) });
		vertices[2] = new EPHVertex(3, new EPHVecf[] { new EPHVec4f(0, 0.9f, 0, 1), new EPHVec4f(0.7f, 0, 0.7f, 1) });
		model.addAttribute(4, "pos_model");
		model.addAttribute(4, "col_model");
		EPHPolygon polygon = new EPHConvexPolygon("test", vertices);
		polygon.addUniformVecf("offset", offset);
		polygon.addUniformMatf("mvp_matrix", mvpMatrix);
		model.addPolygon(polygon);
		model.create();
		model.addToSurface();
	}
}
