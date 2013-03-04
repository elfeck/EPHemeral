package com.elfeck.ephemeral;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public class EPHLogicJob extends EPHRunnableContext {

	private EPHemeral ephemeral;

	public EPHLogicJob(EPHemeral main, int sleepTime) {
		super(sleepTime);
		ephemeral = main;
	}

	@Override
	protected void execute() {
		ephemeral.reqLogic(delta);
	}

	@Override
	protected String getPrefix() {
		return "[logic] ";
	}

}
