/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.drawable;

import com.elfeck.ephemeral.math.geom.EPHPolygon2f;


public interface EPHCollidable {

	public boolean collidesWith(EPHCollidable other);
	public EPHPolygon2f getPolygon();

}
