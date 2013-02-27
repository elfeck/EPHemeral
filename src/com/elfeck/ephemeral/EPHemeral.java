package com.elfeck.ephemeral;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import java.util.List;

import com.elfeck.ephemeral.glContext.EPHRenderContext;
import com.elfeck.ephemeral.glContext.EPHVertexArrayObject;



public class EPHemeral {

	public static void main(String[] args) {
		new EPHemeral();
	}

	public static final int WIDTH = 480, HEIGHT = 320;
	public static final String VERSION = "0.0.00";

	private EPHSurface surface;
	private EPHRenderContext renderContext;
	private EPHRunnableContext mainContext;
	private Thread mainThread;

	public EPHemeral() {
		renderContext = new EPHRenderContext(this);
		mainThread = new Thread(mainContext = new EPHRunnableContext(this, 1));
		surface = null;

		mainThread.start();
	}

	protected void reqLogic(long delta) {
		surface.execLogic(delta);
	}

	protected void reqRender() {
		renderContext.render();
	}

	public List<EPHVertexArrayObject> getVaos() {
		return surface.getVaos();
	}

	public void updateVaos() {
		surface.updateVaos();
	}

	public void destroyVaos() {
		surface.destroyVaos();
	}

	public void destroy() {
		renderContext.destroy();
		mainContext.destroy();
	}

}
