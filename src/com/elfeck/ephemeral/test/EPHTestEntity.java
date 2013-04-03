/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.test;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.drawable.EPHModel;
import com.elfeck.ephemeral.drawable.text.EPHGlyph;
import com.elfeck.ephemeral.drawable.text.EPHGlyphSegment;
import com.elfeck.ephemeral.drawable.text.EPHTextUtils;
import com.elfeck.ephemeral.glContext.EPHRenderUtils;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformMat4f;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec2f;
import com.elfeck.ephemeral.math.EPHVec4f;


public class EPHTestEntity implements EPHEntity {

	private boolean dead;
	private EPHSurface surface;
	private EPHModel model;
	private EPHUniformMat4f mvpMatrix;
	private EPHUniformVec2f offset;
	private EPHVaoEntry vaoRef;

	public EPHTestEntity(EPHSurface surface) {
		this.surface = surface;
		dead = false;
		model = new EPHModel();
		mvpMatrix = new EPHUniformMat4f(new float[][] {
														{ 1, 0, 0, 0 },
														{ 0, 1, 0, 0 },
														{ 0, 0, 1, 0 },
														{ 0, 0, 0, 1 }
		});
		offset = new EPHUniformVec2f(0, 0);
		initModel();
		initShape();
	}

	@Override
	public void doLogic(long delta) {

	}

	@Override
	public boolean isDead() {
		return dead;
	}

	private void initModel() {
		model.addAttribute(4, "vertex_position");
		model.addAttribute(4, "vertex_color");
		model.create(EPHRenderUtils.TYPE_LINES);
		model.addToSurface(surface);
	}

	private void initShape() {
		EPHVec4f color = new EPHVec4f(1f, 1f, 1f, 1f);
		List<Float> vertexValues = new ArrayList<Float>();
		List<Integer> indices = new ArrayList<Integer>();
		EPHGlyph glyph = EPHTextUtils.createGlyph(EPHTextUtils.NAME_LUCIDA_SANS_REGULAR, EPHTextUtils.STYLE_PLAIN, 'B');
		int indexOffs = 0;
		for (EPHGlyphSegment seg : glyph.getSegments()) {
			for (int i = 0; i < seg.getShape().length; i++) {
				seg.getShape()[i].fetchData(vertexValues);
				// System.out.println(seg.getShape()[i]);
				vertexValues.add(1.0f);
				vertexValues.add(1.0f);
				color.fetchData(vertexValues);
			}
			for (int i = 1; i < vertexValues.size(); i++) {
				indices.add(i - 1 + indexOffs);
				indices.add(i + indexOffs);
			}
			indexOffs = indices.size();
			color.subVec4f(0f, 0.3f, 0.3f, 0);
		}
		vaoRef = model.addToVao(vertexValues, indices, "test");
		vaoRef.registerUniformEntry("mvp_matrix", mvpMatrix);
		vaoRef.registerUniformEntry("offset", offset);
		offset.setX((float) -Math.floor(vertexValues.get(0)));
	}
}
