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
			axes[i] = poly1.getVertices()[i].copy().subVec2f(poly1.getVertices()[(i + 1) % poly1.getVertexCount()]).getPerpendicular();
		}
		for (int i = poly1.getVertexCount(); i < axes.length; i++) {
			axes[i] = poly2.getVertices()[poly1.getVertexCount() + i].copy()
					.subVec2f(poly2.getVertices()[(i + 1 - poly1.getVertexCount()) % poly2.getVertexCount()]).getPerpendicular();
		}
		for (int i = 0; i < axes.length; i++) {
			if (!projectionOverlap(poly1.projectOntoAxis(axes[i]), poly2.projectOntoAxis(axes[i]))) return false;
		}
		return true;
	}

	public static boolean aabbCollision(EPHCollidable collidable1, EPHCollidable collidable2) {
		EPHPolygon2f poly1 = collidable1.getPolygon();
		EPHPolygon2f poly2 = collidable2.getPolygon();
		EPHVec2f projx1 = poly1.getVertices()[0].copy();
		EPHVec2f projy1 = poly1.getVertices()[0].copy();
		EPHVec2f projx2 = poly2.getVertices()[0].copy();
		EPHVec2f projy2 = poly2.getVertices()[0].copy();
		for (int i = 1; i < poly1.getVertexCount(); i++) {
			if (poly1.getVertices()[i].getX() < projx1.getX()) projx1.setX(poly1.getVertices()[i].getX());
			if (poly1.getVertices()[i].getX() > projx1.getY()) projx1.setY(poly1.getVertices()[i].getX());
			if (poly1.getVertices()[i].getY() < projy1.getX()) projy1.setX(poly1.getVertices()[i].getY());
			if (poly1.getVertices()[i].getY() > projy1.getY()) projy1.setY(poly1.getVertices()[i].getY());
		}
		for (int i = 1; i < poly2.getVertexCount(); i++) {
			if (poly2.getVertices()[i].getX() < projx2.getX()) projx2.setX(poly1.getVertices()[i].getX());
			if (poly2.getVertices()[i].getX() > projx2.getY()) projx2.setY(poly1.getVertices()[i].getX());
			if (poly2.getVertices()[i].getY() < projy2.getX()) projy2.setX(poly1.getVertices()[i].getY());
			if (poly2.getVertices()[i].getY() > projy2.getY()) projy2.setY(poly1.getVertices()[i].getY());
		}
		return projectionOverlap(projx1, projx2) && projectionOverlap(projy1, projy2);

	}

	private static boolean projectionOverlap(EPHVec2f proj1, EPHVec2f proj2) {
		return (Math.min(proj1.getX(), proj2.getY()) + Math.max(proj1.getY(), proj2.getY()) < proj1.getY() - proj1.getX() + proj2.getY() - proj2.getY());
	}

}
