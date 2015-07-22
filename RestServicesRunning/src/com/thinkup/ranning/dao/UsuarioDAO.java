package com.thinkup.ranning.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.thinkup.ranning.entities.Usuario;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UsuarioDAO {
	
	@PersistenceContext(unitName = "appRunningPostgreDS")
	private EntityManager entityManager;
	
	public List<Usuario> getAllUsuarios() {
		List<Usuario> usuario = this.entityManager.createNamedQuery(
				Usuario.QUERY_ALL, Usuario.class).getResultList();
		return usuario;
	}

}
