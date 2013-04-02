/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.drawable.text;

import java.util.ArrayList;
import java.util.List;


public class EPHGlyph {

	private List<EPHGlyphSegment> segments;

	public EPHGlyph() {
		segments = new ArrayList<EPHGlyphSegment>();
	}

	protected void addSegment(EPHGlyphSegment segment) {
		segments.add(segment);
	}

	public List<EPHGlyphSegment> getSegments() {
		return segments;
	}

}
