package com.elfeck.ephemeral;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.glContext.EPHVertexArrayObject;


public abstract class EPHSurface {

	private List<EPHEntity> entities, newEntities;
	private List<EPHVertexArrayObject> allVaos, newVaos;

	public EPHSurface() {
		entities = new ArrayList<EPHEntity>();
		newEntities = new ArrayList<EPHEntity>();
		allVaos = new ArrayList<EPHVertexArrayObject>();
		newVaos = new ArrayList<EPHVertexArrayObject>();
	}

	public void execLogic(long delta) {
		for (int i = 0; i < entities.size(); i++) {
			EPHEntity currentEntity = entities.get(i);
			if (currentEntity.isDead()) {
				entities.remove(i--);
				continue;
			}
			currentEntity.doLogic(delta);
		}
		entities.addAll(newEntities);
		newEntities.clear();
	}

	public void addEntity(EPHEntity entity) {
		newEntities.add(entity);
	}

	public void addEntity(int index, EPHEntity entity) {
		newEntities.add(index, entity);
	}

	public void addVao(EPHVertexArrayObject vao) {
		newVaos.add(vao);
	}

	protected void updateVaos() {
		allVaos.addAll(newVaos);
		newVaos.clear();
	}

	protected List<EPHVertexArrayObject> getVaos() {
		return allVaos;
	}

	protected void destroyVaos() {
		updateVaos();
		for (EPHVertexArrayObject vao : allVaos) {
			vao.glDispose();
		}
	}

}
