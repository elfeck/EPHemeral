/*
* Copyright 2013, Sebastian Kreisel. All rights reserved.
* If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
*/

package com.elfeck.ephemeral.glContext;

public class EPHVaoEntryDataSet {

	protected int vboSubLower, vboSubUpper;
	protected int iboSubLower, iboSubUpper;

	protected EPHVaoEntryDataSet(int vboSubLower, int vboSubUpper, int iboSubLower, int iboSubUpper) {
		this.vboSubLower = vboSubLower;
		this.vboSubUpper = vboSubUpper;
		this.iboSubLower = iboSubLower;
		this.iboSubUpper = iboSubUpper;
	}

	protected EPHVaoEntryDataSet() {
		this(0, 0, 0, 0);
	}

}
