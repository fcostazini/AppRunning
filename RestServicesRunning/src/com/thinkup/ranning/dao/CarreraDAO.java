package com.thinkup.ranning.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.thinkup.ranning.entities.Carrera;
import com.thinkup.ranning.exceptions.PersistenciaException;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CarreraDAO {

	@PersistenceContext(unitName = "appRunningPostgreDS")
	private EntityManager entityManager;

	public Carrera findCarreraWithName(int nro) {
		Carrera c = this.entityManager
				.createNamedQuery(Carrera.QUERY_BY_NRO_CARRERA, Carrera.class)
				.setParameter(Carrera.PARAM_NRO_CARRERA, nro).getSingleResult();

		return c;
	}

	public List<Carrera> getAllCarreras() {
		List<Carrera> carreras = this.entityManager.createNamedQuery(
				Carrera.QUERY_ALL, Carrera.class).getResultList();
		return carreras;
	}

	public void saveCarrera(Carrera carrera) throws PersistenciaException {
		try {
			this.entityManager.persist(carrera);
		} catch (Exception e) {
			throw new PersistenciaException("No se pude persistir", e);
		}
	}
}
