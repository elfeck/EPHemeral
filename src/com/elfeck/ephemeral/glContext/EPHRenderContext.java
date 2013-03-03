package com.elfeck.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.elfeck.ephemeral.EPHemeral;


public class EPHRenderContext {

	protected static final Object initMonitor = new Object();

	private boolean initialized;
	private String shaderParentPath;
	private EPHemeral main;

	public EPHRenderContext(EPHemeral main, String shaderParentPath) {
		this.main = main;
		this.shaderParentPath = shaderParentPath;
		initialized = false;
	}

	private void glInitContext() {
		DisplayMode displayMode = new DisplayMode(main.getWidth(), main.getHeight());
		PixelFormat pixelFormat = new PixelFormat().withSamples(8);
		try {
			Display.setDisplayMode(displayMode);
			Display.create(pixelFormat);
			Display.setTitle("Ephemeral " + EPHemeral.VERSION);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_MULTISAMPLE);
		EPHVertexArrayObject.glInitShaderProgramPool(shaderParentPath);
		initialized = true;
		synchronized (initMonitor) {
			initMonitor.notify();
		}
	}

	private boolean glHandleCloseRequest() {
		if (Display.isCloseRequested()) {
			main.destroy();
			return true;
		}
		return false;
	}

	private void glClearDisplay() {
		glClear(GL_COLOR_BUFFER_BIT);
	}

	private void glDraw() {
		for (int i = 0; i < main.getVaos().size(); i++) {
			EPHVertexArrayObject currentVao = main.getVaos().get(i);
			if (currentVao.isDead()) {
				main.getVaos().remove(i--);
				continue;
			}
			currentVao.glRender();
		}
	}

	public void glRender() {
		if (!initialized) glInitContext();
		if (glHandleCloseRequest()) return;
		glClearDisplay();
		glDraw();
		Display.update();
		Display.sync(200);
		main.updateVaos();
	}

	public void glDestroy() {
		if (!initialized) return;
		main.glDestroyVaos();
		Display.destroy();
	}

}
