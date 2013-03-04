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

	public EPHemeral(int width, int height, int fpsCap, int lpsCap, String shaderParentPath) {
		this.width = width;
		this.height = height;
		renderContext = new EPHRenderContext(this, Math.min(1000, Math.max(1, fpsCap)), shaderParentPath);
		renderThread = new Thread(renderJob = new EPHRenderJob(this, Math.max(1, (int) Math.round((1.0 / fpsCap) * 1000 - 5))), "RenderThread");
		logicThread = new Thread(logicJob = new EPHLogicJob(this, Math.max(1, (int) Math.round((1.0 / lpsCap) * 1000))), "LogicThread");
		renderThread.setPriority(Thread.MAX_PRIORITY);
		logicThread.setPriority(Thread.MIN_PRIORITY);
		surface = null;
	}

	public EPHemeral(int width, int height) {
		this(width, height, 60, 1000, "shader/");
	}

	protected synchronized void reqLogic(long delta) {
		if (surface != null) surface.execLogic(delta);
	}

	protected synchronized void reqRender() {
		if (surface != null) renderContext.glRender();
	}

	public void start() {
		renderThread.start();
		logicThread.start();
	}

	public synchronized void updateVaos() {
		surface.updateVaos();
	}

	public synchronized void glDestroyVaos() {
		surface.destroyVaos();
		EPHVertexArrayObject.glDisposeShaderPrograms();
	}

	public synchronized void destroy() {
		renderContext.glDestroy();
		renderJob.destroy();
		logicJob.destroy();
	}

	public synchronized List<EPHVertexArrayObject> getVaos() {
		return surface.getVaos();
	}

	public synchronized void setSurface(EPHSurface surface) {
		this.surface = surface;
	}

	public synchronized EPHSurface getSurface() {
		return surface;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setDebug(int renderPrintDelay, int logicPrintDelay, int renderWarningThreshold, int logicWarningThreshold) {
		renderJob.setConsoleDebug(renderPrintDelay, renderWarningThreshold);
		logicJob.setConsoleDebug(logicPrintDelay, logicWarningThreshold);
	}

}
