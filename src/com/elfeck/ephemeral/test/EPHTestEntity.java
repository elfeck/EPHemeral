/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.test;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.drawable.EPHModel;
import com.elfeck.ephemeral.math.EPHMat4f;


public class EPHTestEntity implements EPHEntity {

	private boolean dead;
	@SuppressWarnings("unused")
	private EPHSurface surface;
	@SuppressWarnings("unused")
	private EPHModel model;
	@SuppressWarnings("unused")
	private EPHMat4f mvpMatrix;

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

	}
}
