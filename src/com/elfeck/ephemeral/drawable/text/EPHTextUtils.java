/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.drawable.text;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.font.GlyphVector;
import java.awt.geom.PathIterator;
import java.awt.image.BufferedImage;
import java.util.Arrays;


public class EPHTextUtils {

	public static final int STYLE_PLAIN = Font.PLAIN;
	public static final int STYLE_ITALIC = Font.ITALIC;
	public static final int STYLE_BOLD = Font.BOLD;
	public static final int STYLE_ITALIC_BOLD = Font.ITALIC | Font.BOLD;

	public static final String NAME_LUCIDA_SANS_REGULAR = "Lucida Sans Regular";
	public static final String NAME_LUCIDA_SANS_BOLD = "Lucida Sans Bold";
	public static final String NAME_LUCIDA_SANS_OBLIQUE = "Lucida Sans Oblique";
	public static final String NAME_LUCIDA_SANS_BOLD_OBLIQUE = "Lucida Sans Bold Oblique";

	public static final char[] characters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
												'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
												'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '!', '?', '.', ',', '-', '_', '/', '(', ')', '[', ']', '{', '}', '#', '+', '*',
												'~', '#', '%', '&' };

	static {
		Arrays.sort(characters);
	}

	private EPHTextUtils() {

	}

	private static PathIterator getGlyphOutline(String name, int style, char character) {
		Font font = new Font(name, style, 1);
		BufferedImage dummy = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = dummy.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		GlyphVector glyphVector = font.createGlyphVector(g2.getFontRenderContext(), characters);
		g2.dispose();
		Shape shape = glyphVector.getGlyphOutline(Arrays.binarySearch(characters, character));
		return shape.getPathIterator(null);
	}

	private static EPHGlyph createGlyph(PathIterator iter) {
		EPHGlyph glyph = new EPHGlyph();
		EPHGlyphSegment currentSegment = null;
		float[] currentCoords = new float[6];
		while (!iter.isDone()) {
			switch (iter.currentSegment(currentCoords)) {
				case PathIterator.SEG_CLOSE:
					break;
				case PathIterator.SEG_MOVETO:
					glyph.addSegment(currentSegment = new EPHGlyphSegment(iter.getWindingRule(), currentCoords));
					break;
				case PathIterator.SEG_LINETO:
					currentSegment.addLineSegment(currentCoords);
					break;
				case PathIterator.SEG_QUADTO:
					currentSegment.addQuadCurveSegment(currentCoords);
					break;
				case PathIterator.SEG_CUBICTO:
					currentSegment.addCubicCurveSegment(currentCoords);
					break;
			}
			iter.next();
		}
		return glyph;
	}

	public static EPHGlyph createGlyph(String name, int style, char character) {
		return createGlyph(getGlyphOutline(name, style, character));
	}
}
