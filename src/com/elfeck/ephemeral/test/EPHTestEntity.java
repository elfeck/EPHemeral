package com.elfeck.ephemeral.test;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.geometry.EPHConvexPolygon;
import com.elfeck.ephemeral.geometry.EPHModel;
import com.elfeck.ephemeral.geometry.EPHVertex;


/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public class EPHTestEntity implements EPHEntity {

	private boolean dead;
	private EPHModel model;

	public EPHTestEntity(EPHSurface surface) {
		dead = false;
		model = new EPHModel(surface);
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
		model.addAttribute(4, "pos_model");
		model.addAttribute(4, "col_model");
		model.addPolygon(new EPHConvexPolygon("test", vertices));
		model.create();
		model.addToSurface();
	}

}
