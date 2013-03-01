package com.elfeck.ephemeral.test;

import com.elfeck.ephemeral.EPHemeral;

public class EPHTest {
	
	public static final int WIDTH = 480;
	public static final int HEIGHT = 320;
	
	public static void main(String[] args) {
		EPHemeral eph = new EPHemeral(WIDTH, HEIGHT);
		eph.setSurface(new EPHTestSurface());
		eph.start();
	}

}
