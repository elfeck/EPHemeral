package com.elfeck.ephemeral;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public class EPHRenderJob extends EPHRunnableContext {

	private EPHemeral ephemeral;

	public EPHRenderJob(EPHemeral main, int sleepTime) {
		super(sleepTime);
		ephemeral = main;
	}

	@Override
	protected void execute() {
		ephemeral.reqRender();
	}

	@Override
	protected String getPrefix() {
		return "[fps] ";
	}

}
