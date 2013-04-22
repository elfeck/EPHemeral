/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

package com.elfeck.ephemeral.math;

public class EPHResettableVec4f extends EPHVec4f {

	private EPHVec4f savedVec;

	public EPHResettableVec4f(float x, float y, float z, float w) {
		super(x, y, z, w);
		savedVec = new EPHVec4f(x, y, z, w);
	}

	public EPHResettableVec4f() {
		this(0, 0, 0, 0);
	}

	public EPHResettableVec4f(EPHVec2f vec, float z, float w) {
		this(vec.x, vec.y, z, w);
	}

	public EPHResettableVec4f save() {
		savedVec.setXYZW(x, y, z, w);
		return this;
	}

	public EPHResettableVec4f reset() {
		setXYZW(savedVec.x, savedVec.y, savedVec.z, savedVec.w);
		return this;
	}
}
