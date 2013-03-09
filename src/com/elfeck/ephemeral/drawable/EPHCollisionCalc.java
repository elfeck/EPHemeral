/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.ephemeral.drawable;

import com.elfeck.ephemeral.math.EPHPolygon2f;
import com.elfeck.ephemeral.math.EPHVec2f;


public class EPHCollisionCalc {

	private EPHCollisionCalc() {

	}

	public static boolean pipCollision() {
		return false;
	}

	public static boolean optimalCollision(EPHCollidable collidable1, EPHCollidable collidable2) {
		if (aabbCollision(collidable1, collidable2)) return satCollision(collidable1, collidable2);
		return false;
	}

	public static boolean satCollision(EPHCollidable collidable1, EPHCollidable collidable2) {
		EPHPolygon2f poly1 = collidable1.getPolygon();
		EPHPolygon2f poly2 = collidable2.getPolygon();
		EPHVec2f[] axes = new EPHVec2f[poly1.getVertexCount() + poly2.getVertexCount()];
		for (int i = 0; i < poly1.getVertexCount(); i++) {
			// count1 * 1
			axes[i] = poly1.getVertices()[i].subVec2f(poly1.getVertices()[(i + 1) % poly1.getVertexCount()]).getPerpendicular();
			poly1.getVertices()[i].addVec2f(poly1.getVertices()[(i + 1) % poly1.getVertexCount()]);
		}
		for (int i = poly1.getVertexCount(); i < axes.length; i++) {
			// count2 * 1
			axes[i] = poly2.getVertices()[i - poly1.getVertexCount()].subVec2f(poly2.getVertices()
					[(i + 1 - poly1.getVertexCount()) % poly2.getVertexCount()]).getPerpendicular();
			poly2.getVertices()[i - poly1.getVertexCount()].addVec2f(poly2.getVertices()
					[(i + 1 - poly1.getVertexCount()) % poly2.getVertexCount()]);
		}
		for (int i = 0; i < axes.length; i++) {
			// (count1 + count2) * 2
			if (!projectionOverlap(poly1.projectOntoAxis(axes[i]), poly2.projectOntoAxis(axes[i]))) return false;
		}
		// (count1 + count2) * 3
		return true;
	}
	public static boolean aabbCollision(EPHCollidable collidable1, EPHCollidable collidable2) {
		EPHPolygon2f poly1 = collidable1.getPolygon();
		EPHPolygon2f poly2 = collidable2.getPolygon();
		EPHVec2f axis = new EPHVec2f(1, 0);
		EPHVec2f projx1 = poly1.projectOntoAxis(axis);
		EPHVec2f projx2 = poly2.projectOntoAxis(axis);
		axis = new EPHVec2f(0, 1);
		EPHVec2f projy1 = poly1.projectOntoAxis(axis);
		EPHVec2f projy2 = poly2.projectOntoAxis(axis);;
		return projectionOverlap(projx1, projx2) && projectionOverlap(projy1, projy2);

	}
	private static boolean projectionOverlap(EPHVec2f proj1, EPHVec2f proj2) {
		return (Math.abs(Math.max(proj1.getY(), proj2.getY()) - Math.min(proj1.getX(), proj2.getX()))
		< Math.abs(proj1.getY() - proj1.getX()) + Math.abs(proj2.getY() - proj2.getX()));
	}

}
