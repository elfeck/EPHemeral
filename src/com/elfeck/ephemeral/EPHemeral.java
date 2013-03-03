package com.elfeck.ephemeral;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import java.util.List;

import com.elfeck.ephemeral.glContext.EPHRenderContext;
import com.elfeck.ephemeral.glContext.EPHVertexArrayObject;


public class EPHemeral {

	public static final String VERSION = "0.0.00";

	private int width, height;
	private EPHSurface surface;
	private EPHRenderContext renderContext;
	private EPHRunnableContext renderJob, logicJob;
	private Thread renderThread, logicThread;

	public EPHemeral(int width, int height, String shaderParentPath) {
		this.width = width;
		this.height = height;
		renderContext = new EPHRenderContext(this, shaderParentPath);
		renderThread = new Thread(renderJob = new EPHRenderJob(this, 1));
		logicThread = new Thread(logicJob = new EPHLogicJob(this, 1));
		surface = null;
	}

	public EPHemeral(int width, int height) {
		this(width, height, "shader/");
	}

	protected void reqLogic(long delta) {
		if (surface != null) surface.execLogic(delta);
	}

	protected void reqRender() {
		if (surface != null) renderContext.glRender();
	}

	public void start() {
		renderThread.start();
		logicThread.start();
	}

	public void updateVaos() {
		surface.updateVaos();
	}

	public void glDestroyVaos() {
		surface.destroyVaos();
		EPHVertexArrayObject.glDisposeShaderPrograms();
	}

	public void destroy() {
		renderContext.glDestroy();
		renderJob.destroy();
		logicJob.destroy();
	}

	public List<EPHVertexArrayObject> getVaos() {
		return surface.getVaos();
	}

	public void setSurface(EPHSurface surface) {
		this.surface = surface;
	}

	public EPHSurface getSurface() {
		return surface;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
