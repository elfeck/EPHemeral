package de.greenOwlProduction.ephemeral;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import java.util.List;

import de.greenOwlProduction.ephemeral.glContext.GOPRenderContext;
import de.greenOwlProduction.ephemeral.glContext.GOPVertexArrayObject;
import de.greenOwlProduction.ephemeral.test.TestSurface;

public class Ephemeral {

	public static void main(String[] args) {
		new Ephemeral();
	}

	public static final int WIDTH = 480, HEIGHT = 320;
	public static final String VERSION = "0.0.01";

	private GOPSurface surface;
	private GOPRenderContext renderContext;
	private GOPRunnableContext mainContext;
	private Thread mainThread;

	public Ephemeral() {
		renderContext = new GOPRenderContext(this);
		mainThread = new Thread(mainContext = new GOPRunnableContext(this, 1));
		surface = new TestSurface();

		mainThread.start();
	}

	protected void reqLogic(long delta) {
		surface.execLogic(delta);
	}

	protected void reqRender() {
		renderContext.render();
	}

	public List<GOPVertexArrayObject> getVaos() {
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
