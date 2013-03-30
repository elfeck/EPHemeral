/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.drawable;

import com.elfeck.ephemeral.math.EPHVecf;


public class EPHVertexFactory {

	private static EPHVertex vertex;

	private EPHVertexFactory() {

	}

	public static void create(int index, int attribCount) {
		vertex = new EPHVertex(index, new EPHVecf[attribCount]);
	}

	public static void attachData(int index, EPHVecf data) {
		vertex.getData()[index] = data;
	}

	public static EPHVertex produce() {
		return vertex;
	}

}
