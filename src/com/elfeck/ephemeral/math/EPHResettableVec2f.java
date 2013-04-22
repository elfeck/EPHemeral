/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

package com.elfeck.ephemeral.math;

public class EPHResettableVec2f extends EPHVec2f {

	private EPHVec2f savedVec;

	public EPHResettableVec2f(float x, float y) {
		super(x, y);
		savedVec = new EPHVec2f(x, y);
	}

	public EPHResettableVec2f() {
		this(0, 0);
	}

	public EPHResettableVec2f(EPHVec2f vec) {
		this(vec.x, vec.y);
	}

	public EPHResettableVec2f save() {
		savedVec.setXY(x, y);
		return this;
	}

	public EPHResettableVec2f reset() {
		setXY(savedVec.x, savedVec.y);
		return this;
	}

}
