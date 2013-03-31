/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.test;

import com.elfeck.ephemeral.EPHemeral;


public class EPHTest {

	public static final int WIDTH = 480;
	public static final int HEIGHT = 320;

	public static void main(String[] args) {
		EPHemeral eph = new EPHemeral(WIDTH, HEIGHT, "EPHtest");
		eph.setDebug(500, 5000, 40, -1);
		eph.setResizable(true);
		eph.setSurface(new EPHTestSurface());
	}

}
