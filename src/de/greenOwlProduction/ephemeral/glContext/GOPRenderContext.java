package de.greenOwlProduction.ephemeral.glContext;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import de.greenOwlProduction.ephemeral.Ephemeral;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;


public class GOPRenderContext {

	private boolean initialized;
	private Ephemeral main;

	public GOPRenderContext(Ephemeral main) {
		initialized = false;
		this.main = main;
	}

	private void initContext() {
		DisplayMode displayMode = new DisplayMode(Ephemeral.WIDTH, Ephemeral.HEIGHT);
		PixelFormat pixelFormat = new PixelFormat().withSamples(8);
		try {
			Display.setDisplayMode(displayMode);
			Display.create(pixelFormat);
			Display.setTitle("Ephemeral " + Ephemeral.VERSION);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		glDisable(GL_DEPTH_TEST);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_MULTISAMPLE);
		initialized = true;
	}

	private boolean handleCloseRequest() {
		if (Display.isCloseRequested()) {
			main.destroy();
			return true;
		}
		return false;
	}

	private void clearDisplay() {
		glClear(GL_COLOR_BUFFER_BIT);
	}

	private void draw() {
		for (int i = 0; i < main.getVaos().size(); i++) {
			GOPVertexArrayObject currentVao = main.getVaos().get(i);
			if (currentVao.isDead()) {
				main.getVaos().remove(i--);
				continue;
			}
			currentVao.render();
		}
	}

	public void render() {
		if (!initialized) initContext();
		if (handleCloseRequest()) return;
		clearDisplay();
		draw();
		Display.update();
		Display.sync(200);
		main.updateVaos();
	}

	public void destroy() {
		if (!initialized) return;
		main.destroyVaos();
		Display.destroy();
	}

}
