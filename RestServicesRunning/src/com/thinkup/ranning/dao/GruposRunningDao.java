package com.thinkup.ranning.dao;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.thinkup.ranning.entities.Carrera;
import com.thinkup.ranning.entities.GruposRunning;
import com.thinkup.ranning.exceptions.PersistenciaException;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class GruposRunningDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2342232432343464257L;

	@PersistenceContext(name = "appRunning")
	private EntityManager entityManager;

	public List<GruposRunning> getGruposByNombre(String nombre) {
		TypedQuery<GruposRunning> q = entityManager.createNamedQuery(
				GruposRunning.QUERY_BY_NOMBRE_LIKE, GruposRunning.class);
		q.setParameter(GruposRunning.PARAM_NOMBRE, nombre.toUpperCase() + "%");
		return q.getResultList();

	}

	public GruposRunning getGrupoById(Integer id) {
		TypedQuery<GruposRunning> q = entityManager.createNamedQuery(
				GruposRunning.QUERY_BY_ID, GruposRunning.class);
		q.setParameter(GruposRunning.PARAM_ID, id);
		return q.getSingleResult();

	}
	
	public GruposRunning guardarGrupo(GruposRunning grupo)
			throws PersistenciaException {
		try {
			if (grupo.getId() != null) {
				entityManager.merge(grupo);

			} else {
				entityManager.persist(grupo);
			}
			return grupo;
		} catch (Exception e) {
			throw new PersistenciaException("No se pude persistir", e);
		}

	}

	public void borrarGrupo(GruposRunning grupo) throws PersistenciaException{
		try{
			entityManager.remove(entityManager.find(GruposRunning.class,grupo.getId()));	
		}catch (Exception  e){
			throw new PersistenciaException("NO SE PUEDE BORRAR", e);
		}
		
		
	}


}
