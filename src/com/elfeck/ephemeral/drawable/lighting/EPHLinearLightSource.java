/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.drawable.lighting;

import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.ephemeral.math.EPHVec4f;


public class EPHLinearLightSource implements EPHLightSource {

	private String name;
	private EPHVec4f uniformData;

	public EPHLinearLightSource(float x, float y, float factor, float offset, String name) {
		this.name = name;
		uniformData = new EPHVec4f(x, y, -factor, offset + 1);
	}

	@Override
	public void register(EPHVaoEntry entry) {
		entry.registerUniformEntry(name, uniformData.asUniformVecf());
	}

	public EPHVec4f getLightUniform() {
		return uniformData;
	}

	public void setPosition(float x, float y) {
		uniformData.setX(x);
		uniformData.setY(y);
	}

	public void setFactor(float factor) {
		uniformData.setZ(factor);
	}

	public void setOffset(float offset) {
		uniformData.setW(offset);
	}

	public EPHVec2f getPosition() {
		return uniformData.stripZW();
	}

	public float getFactor() {
		return uniformData.getZ();
	}

	public float getOffset() {
		return uniformData.getW();
	}

}
