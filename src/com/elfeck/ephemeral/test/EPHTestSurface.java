package com.elfeck.ephemeral.test;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import com.elfeck.ephemeral.EPHSurface;

public class EPHTestSurface extends EPHSurface {
	
	public EPHTestSurface() {
		addEntity(0, new EPHTriangle(this));
	}

}
