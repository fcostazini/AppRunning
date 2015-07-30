package com.thinkup.ranning.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.thinkup.ranning.dtos.CamposOrdenEnum;
import com.thinkup.ranning.dtos.CarreraDTO;
import com.thinkup.ranning.dtos.Filtro;
import com.thinkup.ranning.entities.Carrera;
import com.thinkup.ranning.exceptions.PersistenciaException;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CarreraDAO {

	@PersistenceContext(unitName = "appRunning")
	private EntityManager entityManager;

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

	public List<CarreraDTO> getCarrerasDTO(Filtro filtro) {
		String query = this.crearQuery();
		if(filtro != null){
			query += this.getWhereCondition(filtro); 
		}
		@SuppressWarnings("unchecked")
		List<CarreraDTO> carreras = this.entityManager.createNativeQuery(
				query, CarreraDTO.class).getResultList();
		
		return carreras;
	}

	private String crearQuery() {
		
		StringBuffer  select = new StringBuffer();
		select.append(" SELECT c.id as codigoCarrera, c.nombre as nombre, c.fecha_inicio as fechaInicio, EXTRACT(HOUR FROM c.fecha_inicio)||':'||EXTRACT(MINUTE  FROM c.fecha_inicio) AS hora, c.distancia_disponible as distanciaDisponible, c.descripcion as descripcion, c.url_imagen as urlImagen, ");
		select.append(" c.provincia as provincia, c.ciudad as ciudad, uc.id as usuarioCarrera, uc.distancia as distancia, uc.me_gusta as meGusta, uc.anotado as estoyAnotado, uc.corrida as corrida ");
		select.append(" FROM public.carrera c ");
		select.append(" inner join public.usuario_carrera uc on uc.carrera_id = c.id ");
				
		return select.toString();
	}
	
	private String getWhereCondition(Filtro filtro) {
        String query = " Where 1 = 1 ";
        if(filtro == null){
            return "";
        }
        if (filtro.getNombreCarrera() != null && !filtro.getNombreCarrera().isEmpty()) {
            query += " AND upper( c.nombre ) LIKE upper('%" + filtro.getNombreCarrera() + "%') \n";
        }
        if (filtro.getProvincia() != null && !filtro.getProvincia().isEmpty() && !filtro.getProvincia().equals("Todas las Provincias")) {
            query += " AND c.provincia = '" + filtro.getProvincia() + "'\n";
        }

        if (filtro.getCiudad() != null && !filtro.getCiudad().isEmpty() && !filtro.getCiudad().equals("Todas las Ciudades")) {
            query += " AND c.ciudad = '" + filtro.getCiudad() + "'\n";
        }
        if (filtro.getModalidad() != null && !filtro.getModalidad().equals("Todas las Modalidades")) {
            query += " AND c.modalidades LIKE '%" + filtro.getModalidad() + "%'\n";
        }
        
        query += getFechaRange("c.fecha_inicio", filtro.getFechaDesde(), filtro.getFechaHasta());
        if (filtro.getIdUsuario() > 0) {
            query += " AND uc.id = " + filtro.getIdUsuario();
        }
        if (filtro.getMeGusta() != null) {
            query += " AND uc.me_gusta is " + filtro.getMeGusta();
        }

        if (filtro.getInscripto() != null) {
            query += " AND uc.anotado is " + filtro.getInscripto();
        }

        if (filtro.getCorrida() != null) {
            query += " AND uc.corrida is " + filtro.getCorrida();
        }
        if (filtro.getIdUsuario() >= 0 && filtro.getCorrida() == null && filtro.getInscripto() == null
                && filtro.getMeGusta() == null) {
            query += " AND ( uc.corrida is true " +
                    " OR  uc.anotado is true " +
                    " OR  uc.me_gusta is true )";
        }
        if (filtro.getRecomendadas() != null) {
            query += " AND  c.recomendada  is " + filtro.getRecomendadas() + " ";
        }
        query += this.getIntegerRange("uc.distancia", filtro.getMinDistancia(),filtro.getMaxDistancia());
        query += this.getOrderBy(filtro);
        return query;
    }

	private String getFechaRange(String field, String min, String max) {
        String resultado = "";
        
        if (min == null && max == null) {
            return resultado;
        }
        if (min != null && max != null) {
        	resultado += " AND " + field +" BETWEEN  '"+ min +"' AND '"+ max +"' \n";
        } else {
            if (min != null)
                resultado += " AND " + field + " >= '" + min + "' \n";
            if (max != null)
                resultado += " AND " + field + " <= '" + max + "' \n";
        }

        return resultado;
    }
	

    
    private String getIntegerRange(String field, Integer min, Integer max) {
        String resultado = "";

        if (min != null && min > 0 && max != null && max > 0) {
            if (min == max) {
                resultado += " AND " + field + " = " + min + "\n";
            } else {
                resultado += " AND " + field  +" BETWEEN " + min + " AND " + max + " \n";
            }
        } else {

            if (min != null && min > 0)
                resultado += " AND " + field + " >= " + min + " \n";
            if (max != null && max > 0)
                resultado += " AND " + field + " <= " + max + " \n";
        }

        return resultado;
    }
    
    private String getOrderBy(Filtro filtro) {
        String orderBy = "";
        if (filtro.getOrdenarPor() != null
                && !filtro.getOrdenarPor().isEmpty()
                && !filtro.getOrdenarPor().equals(CamposOrdenEnum.NINGUNO.getLabel())) {
            orderBy += " ORDER BY " + CamposOrdenEnum.getCampoByLabel(filtro.getOrdenarPor());
            if (filtro.getSentido() != null && !filtro.getSentido().isEmpty()) {
                if (filtro.getSentido().equals(Filtro.SENTIDO_ORDEN[0])) {
                    orderBy += " ASC ";
                } else {
                    orderBy += " DESC ";
                }
            }
        }


        return orderBy;
    }
}
