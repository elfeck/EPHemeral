/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.drawable;

import com.elfeck.ephemeral.math.EPHVecf;


public class EPHVertexBuilder {

	private EPHVertex vertex;

	public EPHVertexBuilder(int index, int attribCount) {
		vertex = new EPHVertex(index, new EPHVecf[attribCount]);
	}

	public void attachData(int index, EPHVecf data) {
		vertex.getData()[index] = data;
	}

	public EPHVertex create() {
		return vertex;
	}

	public void reset(int index, int attribCount) {
		vertex = new EPHVertex(index, new EPHVecf[attribCount]);
	}

}
