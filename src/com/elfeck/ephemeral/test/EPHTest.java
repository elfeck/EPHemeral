package com.elfeck.ephemeral.test;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import com.elfeck.ephemeral.EPHemeral;


public class EPHTest {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 320;

	public static void main(String[] args) {
		EPHemeral eph = new EPHemeral(WIDTH, HEIGHT);
		eph.setDebug(500, 5000, 40, -1);
		eph.setSurface(new EPHTestSurface());
		eph.start();
		eph.getSurface().addEntity(new EPHTestEntity(eph.getSurface()));
	}

}
