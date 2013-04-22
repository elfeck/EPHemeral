/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

package com.elfeck.ephemeral.math;

public class EPHResettableVec3f extends EPHVec3f {

	private EPHVec3f savedVec;

	public EPHResettableVec3f(float x, float y, float z) {
		super(x, y, z);
		savedVec = new EPHVec3f(x, y, z);
	}

	public EPHResettableVec3f() {
		this(0, 0, 0);
	}

	public EPHResettableVec3f save() {
		savedVec.setXYZ(x, y, z);
		return this;
	}

	public EPHResettableVec3f reset() {
		setXYZ(savedVec.x, savedVec.y, savedVec.z);
		return this;
	}

}
