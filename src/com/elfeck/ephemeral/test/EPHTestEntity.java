/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.test;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.drawable.EPHDrawableModel;
import com.elfeck.ephemeral.drawable.EPHDrawablePolygon;
import com.elfeck.ephemeral.drawable.EPHVertex;
import com.elfeck.ephemeral.math.EPHMat4f;
import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.ephemeral.math.EPHVec4f;
import com.elfeck.ephemeral.math.EPHVecf;


public class EPHTestEntity implements EPHEntity {

	private boolean dead;
	private EPHSurface surface;
	private EPHDrawableModel model;
	private EPHDrawablePolygon polygon1, polygon2;
	private EPHVec2f offset1, offset2;
	private EPHMat4f mvpMatrix;

	public EPHTestEntity(EPHSurface surface) {
		this.surface = surface;
		dead = false;
		model = new EPHDrawableModel();
		offset1 = new EPHVec2f(0.4f, 0.0f);
		offset2 = new EPHVec2f(0.0f, 0.0f);
		mvpMatrix = new EPHMat4f(new float[][] {
												{ 1, 0, 0, 0 },
												{ 0, 1, 0, 0 },
												{ 0, 0, 1, 0 },
												{ 0, 0, 0, 1 }
		});
		initModel();
	}

	boolean outward = true;
	EPHVertex modVertex1, modVertex2;

	@Override
	public void doLogic(long delta) {
		polygon2.setVisible(false);
		if (outward) {
			offset1.addVec2f(0.0000f, 0.0001f);
			offset2.subVec2f(0.0000f, 0.0001f);
		} else {
			offset1.subVec2f(0.0000f, 0.0001f);
			offset2.addVec2f(0.0000f, 0.0001f);
		}
		if (offset2.getY() < -0.5f) {
			outward = false;
			polygon1.switchUniformTemplateBuffer("test2");
			polygon1.updateUniformEntries();
			polygon1.switchProgram("test2");

			((EPHVec4f) modVertex1.getVec(0)).setN(0, 0);
			modVertex1.setUpdated(true);

			((EPHVec4f) modVertex2.getVec(0)).setN(0, 0.7f);
			modVertex2.setUpdated(true);

			polygon1.updateVertexData();
		}
		if (offset1.getY() < 0.0f) {
			outward = true;
			polygon1.switchUniformTemplateBuffer("test");
			polygon1.updateUniformEntries();
			polygon1.switchProgram("test");

			((EPHVec4f) modVertex1.getVec(0)).setN(0, -0.5f);
			((EPHVec4f) modVertex1.getVec(0)).setN(1, -0.3f);
			modVertex1.setUpdated(true);

			((EPHVec4f) modVertex2.getVec(0)).setN(0, -0.3f);
			modVertex2.setUpdated(true);

			polygon1.updateVertexData();
		}
		polygon1.collidesWith(polygon2);
	}

	@Override
	public boolean isDead() {
		return dead;
	}

	private void initModel() {
		EPHVertex[] vertices1 = new EPHVertex[4];
		float z1 = 0.4f;
		float z2 = 0.5f;
		vertices1[0] = modVertex1 = new EPHVertex(0, new EPHVecf[] { new EPHVec4f(-0.5f, 0.5f, z1, 1), new EPHVec4f(0.7f, 0, 0, 1) });
		vertices1[1] = new EPHVertex(1, new EPHVecf[] { new EPHVec4f(0.5f, 0.7f, z1, 1), new EPHVec4f(0, 0.7f, 0, 1) });
		vertices1[2] = modVertex2 = new EPHVertex(2, new EPHVecf[] { new EPHVec4f(0.5f, -0.5f, z1, 1), new EPHVec4f(0.7f, 0.7f, 0, 1) });
		vertices1[3] = new EPHVertex(3, new EPHVecf[] { new EPHVec4f(-0.5f, -0.7f, z1, 1), new EPHVec4f(0.7f, 0, 0.7f, 1) });
		EPHVertex[] vertices2 = new EPHVertex[4];
		vertices2[0] = new EPHVertex(0, new EPHVecf[] { new EPHVec4f(-0.5f, 0.7f, z2, 1), new EPHVec4f(0.7f, 0, 0, 1) });
		vertices2[1] = new EPHVertex(1, new EPHVecf[] { new EPHVec4f(0.5f, 0.5f, z2, 1), new EPHVec4f(0, 0.7f, 0, 1) });
		vertices2[2] = new EPHVertex(2, new EPHVecf[] { new EPHVec4f(0.5f, -0.7f, z2, 1), new EPHVec4f(0.7f, 0.7f, 0, 1) });
		vertices2[3] = new EPHVertex(3, new EPHVecf[] { new EPHVec4f(-0.5f, -0.5f, z2, 1), new EPHVec4f(0.7f, 0, 0.7f, 1) });
		model.addAttribute(4, "pos_model");
		model.addAttribute(4, "col_model");
		model.create();
		model.addToSurface(surface);

		polygon1 = new EPHDrawablePolygon(model, "test", vertices1, offset1);
		polygon1.addUniformVecf("offset", offset1);
		polygon1.addUniformMatf("mvp_matrix", mvpMatrix);
		polygon1.addDataToVao();

		polygon2 = new EPHDrawablePolygon(model, "test2", vertices2, offset2);
		polygon2.addUniformVecf("offset", offset2);
		polygon2.addUniformMatf("mvp_matrix", mvpMatrix);
		polygon2.addDataToVao();

		// model.setViewPort(new int[] { EPHTest.WIDTH / 2, EPHTest.HEIGHT / 2,
		// EPHTest.WIDTH / 2, EPHTest.HEIGHT / 2 });
	}
}
