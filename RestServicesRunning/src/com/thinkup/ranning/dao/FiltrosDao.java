package com.thinkup.ranning.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.thinkup.ranning.dtos.ProvinciaCiudadDTO;


@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class FiltrosDao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2342232105443464257L;

	@PersistenceContext(name = "appRunning")
	private EntityManager entityManager;

	public List<String> getProvincias() {

		try {
			List<String> provincias = this.entityManager.createNativeQuery(
					"select Distinct(provincia) from carrera").getResultList();

			return provincias;
		} catch (NoResultException e) {
			return new Vector<>();
		}
	}
	
	public List<String> getCiudades(String provincia) {

		try {
			List<String> provincias = this.entityManager.createNativeQuery(
					"select Distinct(ciudad) from carrera where provincia = :provincia and ciudad like :ciudad")
					.setParameter("provincia", provincia)
					.getResultList();

			return provincias;
		} catch (NoResultException e) {
			return new Vector<>();
		}
	}
	
	public List<ProvinciaCiudadDTO> getFiltrosProvinciaCiudad(){
		try {
			List<ProvinciaCiudadDTO> filtros = this.entityManager.createNativeQuery(
					"select Distinct provincia, ciudad from carrera order by provincia, ciudad",ProvinciaCiudadDTO.class)
					.getResultList();

			return filtros;
		} catch (NoResultException e) {
			return new Vector<>();
		}
	}
	
	

}
