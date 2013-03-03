package com.elfeck.ephemeral;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

public abstract class EPHRunnableContext implements Runnable {

	private boolean running;
	private int printDelay, printDelayPassed, fpsSum, loopsPassed;
	protected boolean printing;
	protected long delta;
	protected int sleepTime;

	public EPHRunnableContext(int sleepTime) {
		running = true;
		printing = true;
		delta = 0;
		printDelay = 300;
		printDelayPassed = 0;
		this.sleepTime = sleepTime;
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
			if (printing) print(delta);
		}
		System.exit(0);
	}

	private void print(long delta) {
		long fps = (long) (1e9 / delta);
		if (fps < 150) System.err.println(fps);
		if (printDelayPassed > printDelay) {
			System.out.println(Math.round(fpsSum / loopsPassed));
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

	public void destroy() {
		running = false;
	}

}
