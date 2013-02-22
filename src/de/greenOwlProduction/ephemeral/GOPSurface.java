package de.greenOwlProduction.ephemeral;

/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

import java.util.ArrayList;
import java.util.List;

import de.greenOwlProduction.ephemeral.entity.GOPEntity;
import de.greenOwlProduction.ephemeral.glContext.GOPVertexArrayObject;


public abstract class GOPSurface {

	private List<GOPEntity> entities, newEntities;
	private List<GOPVertexArrayObject> allVaos, newVaos;

	public GOPSurface() {
		entities = new ArrayList<GOPEntity>();
		newEntities = new ArrayList<GOPEntity>();
		allVaos = new ArrayList<GOPVertexArrayObject>();
		newVaos = new ArrayList<GOPVertexArrayObject>();
	}

	public void execLogic(long delta) {
		for (int i = 0; i < entities.size(); i++) {
			GOPEntity currentEntity = entities.get(i);
			if (currentEntity.isDead()) {
				entities.remove(i--);
				continue;
			}
			currentEntity.doLogic(delta);
		}
		entities.addAll(newEntities);
		newEntities.clear();
	}

	public void addEntity(GOPEntity entity) {
		newEntities.add(entity);
	}

	public void addEntity(int index, GOPEntity entity) {
		newEntities.add(index, entity);
	}

	public void addVao(GOPVertexArrayObject vao) {
		newVaos.add(vao);
	}

	protected void updateVaos() {
		allVaos.addAll(newVaos);
		newVaos.clear();
	}

	protected List<GOPVertexArrayObject> getVaos() {
		return allVaos;
	}

	protected void destroyVaos() {
		updateVaos();
		for (GOPVertexArrayObject vao : allVaos) {
			vao.glDispose();
		}
	}

}
