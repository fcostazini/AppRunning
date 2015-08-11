package com.thinkup.ranning.dao;

import java.util.List;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.thinkup.ranning.entities.Amigos;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class AmigoDao {
	@PersistenceContext(name = "appRunning")
	private EntityManager entityManager;
	public List<Amigos> getAmigosByOwner(Integer idUsuarioOwner){
		List<Amigos> amigos = new Vector<>();
		return amigos;
	}
	
	public Amigos guardarEstadoAmigo(Amigos amigo){
		return amigo;
	}
	



}
