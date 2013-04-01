/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.test;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.drawable.EPHModel;
import com.elfeck.ephemeral.glContext.EPHRenderUtils;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.math.EPHMat4f;
import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.ephemeral.math.EPHVec4f;


public class EPHTestEntity implements EPHEntity {

	private boolean dead;
	private EPHSurface surface;
	private EPHModel model;
	private EPHMat4f mvpMatrix;
	private EPHVec2f offset;
	private EPHVaoEntry vaoRef;

	public EPHTestEntity(EPHSurface surface) {
		this.surface = surface;
		dead = false;
		model = new EPHModel();
		mvpMatrix = new EPHMat4f(new float[][] {
												{ 1, 0, 0, 0 },
												{ 0, 1, 0, 0 },
												{ 0, 0, 1, 0 },
												{ 0, 0, 0, 1 }
		});
		offset = new EPHVec2f(0, 0);
		initModel();
		initShape();
	}

	@Override
	public void doLogic(long delta) {

	}

	@Override
	public boolean isDead() {
		return dead;
	}

	private void initModel() {
		model.addAttribute(4, "vertex_position");
		model.addAttribute(4, "vertex_color");
		model.create(EPHRenderUtils.TYPE_LINES);
		model.addToSurface(surface);
	}

	private void initShape() {
		List<Float> vertexValues = new ArrayList<Float>();
		List<Integer> indices = new ArrayList<Integer>();
		EPHVec4f color = new EPHVec4f(1f, 1f, 1f, 1f);
		for (int i = 1; i < vertexValues.size(); i++) {
			indices.add(i - 1);
			indices.add(i);
		}
		indices.add(0);
		vaoRef = model.addToVao(vertexValues, indices, "test");
		vaoRef.registerUniformEntry("mvp_matrix", mvpMatrix.asUniformMatf());
		vaoRef.registerUniformEntry("offset", offset.asUniformVecf());
	}
}
