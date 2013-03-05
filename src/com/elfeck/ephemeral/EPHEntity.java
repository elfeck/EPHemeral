/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral;

public interface EPHEntity {

	public void doLogic(long delta);
	public boolean isDead();

}
