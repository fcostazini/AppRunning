package com.thinkup.ranning.dao;

import java.util.List;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.thinkup.ranning.dtos.Filtro;
import com.thinkup.ranning.entities.UsuarioCarrera;
import com.thinkup.ranning.exceptions.PersistenciaException;
import com.thinkup.ranning.server.rest.exception.EntidadInexistenteException;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UsuarioCarreraDAO {
	
	@PersistenceContext(name = "appRunning")
	private EntityManager entityManager;

	@Inject
	private CarreraDAO carreraDao;

	private QueryGenerator qGen = new QueryGenerator();

	public UsuarioCarrera getByUsuarioCarrera(String idUsuario,
			Integer idCarrera) throws EntidadInexistenteException {
		try {
			UsuarioCarrera uc = this.entityManager
					.createNamedQuery(UsuarioCarrera.GET_BY_USUARIO_CARRERA,
							UsuarioCarrera.class)
					.setParameter(UsuarioCarrera.PARAM_ID_CARRERA, idCarrera)
					.setParameter(UsuarioCarrera.PARAM_ID_USUARIO, idUsuario)
					.getSingleResult();
			return uc;
		} catch (NoResultException e) {
			String mensaje = "No existe UsuarioCarrera con el id " + idUsuario
					+ "/" + idCarrera;
			throw new EntidadInexistenteException(mensaje, e);
		}

	}

	public UsuarioCarrera getById(Integer id)
			throws EntidadInexistenteException {
		try {
			UsuarioCarrera uc = this.entityManager
					.createNamedQuery(UsuarioCarrera.GET_BY_ID,
							UsuarioCarrera.class)
					.setParameter(UsuarioCarrera.PARAM_ID, id)
					.getSingleResult();
			return uc;
		} catch (NoResultException e) {
			String mensaje = "No existe UsuarioCarrera con el id " + id;
			throw new EntidadInexistenteException(mensaje, e);
		}
	}

	public List<UsuarioCarrera> findAll(Class<UsuarioCarrera> class1) {
		try {
			List<UsuarioCarrera> resultados = this.entityManager
					.createNamedQuery(UsuarioCarrera.GET_ALL,
							UsuarioCarrera.class).getResultList();
			return resultados;
		} catch (Exception e) {
			return new Vector<UsuarioCarrera>();
		}
	}

	public UsuarioCarrera update(UsuarioCarrera entidad)
			throws PersistenciaException {

		try {
			this.entityManager.merge(entidad);
		} catch (Exception e) {
			throw new PersistenciaException("Error inesperado", e);
		}
		return entidad;

	}

	public UsuarioCarrera save(UsuarioCarrera entidad)
			throws PersistenciaException {
		try {
			this.getById(entidad.getId());
			throw new PersistenciaException("Ya existe la entidad", null);
		} catch (EntidadInexistenteException e) {
			this.entityManager.persist(entidad);
			return entidad;
		}

	}

	public UsuarioCarrera getByIdCarrera(Integer id) {
		UsuarioCarrera uc = null;
		try {
			uc = this.entityManager
					.createNamedQuery(UsuarioCarrera.GET_BY_ID_CARRERA,
							UsuarioCarrera.class)
					.setParameter(UsuarioCarrera.PARAM_ID_CARRERA, id)
					.getSingleResult();
			return uc;
		} catch (NoResultException e) {
			uc = new UsuarioCarrera();
			try {
				uc.setCarrera(this.carreraDao.getById(id));
				return uc;
			} catch (PersistenciaException e1) {
				return null;

			}

		}

	}

	public List<UsuarioCarrera> getByFiltro(Filtro filtro)
			throws PersistenciaException {
		StringBuffer select = new StringBuffer();
		select.append(" SELECT c.id as codigoCarrera, c.nombre as nombre, c.fecha_inicio as fechaInicio, EXTRACT(HOUR FROM c.fecha_inicio)||':'||EXTRACT(MINUTE  FROM c.fecha_inicio) AS hora, c.distancia_disponible as distanciaDisponible, c.descripcion as descripcion, c.url_imagen as urlImagen, ");
		select.append(" c.provincia as provincia, c.ciudad as ciudad, uc.id as usuarioCarrera, uc.distancia as distancia, uc.me_gusta as meGusta, uc.anotado as estoyAnotado, uc.corrida as corrida ");
		select.append(" FROM CARRERA c JOIN USUARIO_CARRERA uc ON c.ID_CARRERA = uc.CARRERA and uc.TIEMPO > 0 ");
		String query = select.toString();
		if (filtro != null) {
			query += this.qGen.getWhereCondition(filtro);
		}
		try {
			@SuppressWarnings("unchecked")
			List<UsuarioCarrera> carreras = this.entityManager
					.createNativeQuery(query, UsuarioCarrera.class)
					.getResultList();
			if (carreras == null) {
				return new Vector<UsuarioCarrera>();
			}
			return carreras;
		} catch (Exception e) {
			throw new PersistenciaException("Error inesperado", e);
		}

	}

}
