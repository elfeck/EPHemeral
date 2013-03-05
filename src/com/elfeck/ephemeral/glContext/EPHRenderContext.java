/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.glContext;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import com.elfeck.ephemeral.EPHemeral;


public class EPHRenderContext {

	protected static final Object initMonitor = new Object();
	private static int[] windowDimensions = new int[4];

	private boolean initialized, resizable, resizableTriggered, resizedTriggered;
	private int fpsCap;
	private String shaderParentPath, title;
	private EPHemeral main;

	public EPHRenderContext(EPHemeral main, int fpsCap, String shaderParentPath, String title) {
		this.main = main;
		this.fpsCap = fpsCap;
		this.shaderParentPath = shaderParentPath;
		this.title = title;
		initialized = false;
		resizable = false;
		resizableTriggered = false;
		resizedTriggered = false;
	}

	private void glInitContext() {
		DisplayMode displayMode = new DisplayMode(main.getWidth(), main.getHeight());
		windowDimensions = new int[] { 0, 0, main.getWidth(), main.getHeight() };
		PixelFormat pixelFormat = new PixelFormat().withSamples(8);
		try {
			Display.setDisplayMode(displayMode);
			Display.create(pixelFormat);
			Display.setTitle(title + " using EPHemeral v. " + EPHemeral.VERSION);
			Display.setResizable(resizable);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_MULTISAMPLE);
		glEnable(GL_SCISSOR_TEST);
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

	private void glCheckResized() {
		if (Display.wasResized()) {
			windowDimensions[2] = main.getWidth();
			windowDimensions[3] = main.getHeight();
			glViewport(0, 0, main.getWidth(), main.getHeight());
		}
		if (resizableTriggered) {
			Display.setResizable(resizable);
			resizableTriggered = false;
		}
		if (resizedTriggered) {
			try {
				Display.setDisplayMode(new DisplayMode(main.getWidth(), main.getHeight()));
			} catch (LWJGLException e) {
				e.printStackTrace();
			}
			Display.update();
			resizedTriggered = false;
			windowDimensions[2] = main.getWidth();
			windowDimensions[3] = main.getHeight();
			glViewport(0, 0, main.getWidth(), main.getHeight());
		}
	}
	public void glRender() {
		if (!initialized) glInitContext();
		if (glHandleCloseRequest()) return;
		glCheckResized();
		glClearDisplay();
		glDraw();
		Display.update();
		Display.sync(fpsCap);
		main.updateVaos();
	}

	public void glDestroy() {
		if (!initialized) return;
		main.glDestroyVaos();
		Display.destroy();
	}

	public void setResizable(boolean resizable) {
		this.resizable = resizable;
		resizableTriggered = true;
	}

	public void resize() {
		resizedTriggered = true;
	}

	public boolean wasResized() {
		return Display.wasResized();
	}

	public int getWidth() {
		return Display.getWidth();
	}

	public int getHeight() {
		return Display.getHeight();
	}

	protected static int[] getWindowDimensions() {
		return windowDimensions;
	}

}
