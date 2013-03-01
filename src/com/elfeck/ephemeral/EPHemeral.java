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
	private EPHRunnableContext mainContext;
	private Thread mainThread;

	public EPHemeral(int width, int height) {
		this.width = width;
		this.height = height;
		renderContext = new EPHRenderContext(this);
		mainThread = new Thread(mainContext = new EPHRunnableContext(this, 1));
		surface = null;
	}

	protected void reqLogic(long delta) {
		surface.execLogic(delta);
	}

	protected void reqRender() {
		renderContext.glRender();
	}
	
	public void start() {
		mainThread.start();
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
		mainContext.destroy();
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
