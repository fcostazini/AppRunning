package com.thinkup.ranning.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.thinkup.ranning.dtos.CarreraCabeceraDTO;
import com.thinkup.ranning.dtos.Filtro;
import com.thinkup.ranning.entities.Carrera;
import com.thinkup.ranning.entities.Usuario;
import com.thinkup.ranning.exceptions.PersistenciaException;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CarreraDAO {
	@PersistenceContext(name = "appRunning")
	private EntityManager entityManager;

	private QueryGenerator qGen = new QueryGenerator();

	public Carrera findCarreraWithNumero(int nro) throws PersistenciaException {
		try {
			Carrera c = this.entityManager
					.createNamedQuery(Carrera.QUERY_BY_NRO_CARRERA,
							Carrera.class)
					.setParameter(Carrera.PARAM_NRO_CARRERA, nro)
					.getSingleResult();

			return c;
		} catch (NoResultException e) {
			String mensaje = "No existe carrera con el numero" + nro;
			throw new PersistenciaException(mensaje, e);
		}
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

	public List<CarreraCabeceraDTO> getCarrerasDTO(Filtro filtro) {
		String query = this.crearQuery(filtro);
		if (filtro != null) {
			query += this.qGen.getWhereCondition(filtro);
		}
		@SuppressWarnings("unchecked")
		List<CarreraCabeceraDTO> carreras = this.entityManager
				.createNativeQuery(query, CarreraCabeceraDTO.class)
				.getResultList();

		return carreras;
	}

	private String crearQuery(Filtro filtro) {

		StringBuffer select = new StringBuffer();
		select.append(" SELECT c.id as codigoCarrera, c.nombre as nombre, c.fecha_inicio as fechaInicio, EXTRACT(HOUR FROM c.fecha_inicio)||':'||EXTRACT(MINUTE  FROM c.fecha_inicio) AS hora, c.distancia_disponible as distanciaDisponible, c.descripcion as descripcion, c.url_imagen as urlImagen, ");
		select.append(" c.provincia as provincia, c.ciudad as ciudad ");
		select.append(" FROM public.carrera c ");
		if (filtro != null && filtro.getIdUsuario() > 0) {
			select.append(" left join public.usuario_carrera uc on uc.carrera_id = c.id ");
		}

		return select.toString();
	}

	public Carrera getById(Integer id) throws PersistenciaException {
		try {
			Carrera carrera = this.entityManager
					.createNamedQuery(Carrera.GET_BY_ID, Carrera.class)
					.setParameter(Carrera.PARAM_ID, id).getSingleResult();
			return carrera;
		} catch (NoResultException e) {
			String mensaje = "No existe carrera con el id " + id;
			throw new PersistenciaException(mensaje, e);

		}

	}

}
