package com.elfeck.ephemeral.test;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.geometry.EPHModel;


public class EPHTestSurface extends EPHSurface {

	public EPHTestSurface() {
		EPHModel model = new EPHModel(this);
		model.addAttribute(4, "pos_model");
		model.addAttribute(4, "col_model");
		model.create();
	}

}
