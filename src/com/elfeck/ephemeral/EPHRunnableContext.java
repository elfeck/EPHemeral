package com.elfeck.ephemeral;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public abstract class EPHRunnableContext implements Runnable {

	private boolean running;
	private int sleepTime, fpsSum, loopsPassed;
	private int printDelayPassed, printDelay, warningThreshold;
	protected long delta;

	public EPHRunnableContext(int sleepTime) {
		this.sleepTime = sleepTime;
		running = true;
		delta = 0;
		printDelay = -1;
		printDelayPassed = 0;
		warningThreshold = -1;
	}

	@Override
	public void run() {
		while (running) {
			long start = System.nanoTime();
			execute();
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			delta = System.nanoTime() - start;
			if (printDelay > 0) print(delta);
		}
		System.exit(0);
	}

	private void print(long delta) {
		long fps = (long) (1e9 / delta);
		if (fps < warningThreshold && warningThreshold > 0) {
			System.err.println(getPrefix() + fps);
		}
		if (printDelayPassed > printDelay) {
			System.out.println(getPrefix() + Math.round(fpsSum / loopsPassed));
			fpsSum = 0;
			loopsPassed = 0;
			printDelayPassed = 0;
		} else {
			loopsPassed++;
			fpsSum += fps;
			printDelayPassed += (delta / 1e6);
		}
	}

	protected abstract void execute();
	protected abstract String getPrefix();

	public void destroy() {
		running = false;
	}

	public void setConsoleDebug(int printDelay, int warningThreshold) {
		this.printDelay = printDelay;
		this.warningThreshold = warningThreshold;
	}

}
