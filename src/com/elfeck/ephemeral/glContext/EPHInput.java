/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

public class EPHInput {

	private boolean mleftPressed, mrightPressed, mleftReleased, mrightReleased;
	protected int mx, my, mdx, mdy;

	public EPHInput() {

	}

	public void setMleftPressed(boolean mleft) {
		mleftReleased = mleftPressed && !mleft;
		mleftPressed = mleft;
	}

	public void setMrightPressed(boolean mright) {
		mrightReleased = mrightPressed && !mright;
		mrightPressed = mright;
	}

	public void reset() {
		mrightReleased = false;
		mleftReleased = false;
	}

	public int getMx() {
		return mx;
	}

	public int getMy() {
		return my;
	}

	public int getMdx() {
		return mdx;
	}

	public int getMdy() {
		return mdy;
	}

	public boolean isMleftPressed() {
		return mleftPressed;
	}

	public boolean isMrightPressed() {
		return mrightPressed;
	}

	public boolean isMleftReleased() {
		return mleftReleased;
	}

	public boolean isMrightReleased() {
		return mrightReleased;
	}

}
