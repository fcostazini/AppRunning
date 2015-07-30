package com.thinkup.ranning.server.rest;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.thinkup.ranning.dao.CarreraDAO;
import com.thinkup.ranning.dtos.CarreraDTO;
import com.thinkup.ranning.dtos.Filtro;
import com.thinkup.ranning.dtos.Respuesta;
import com.thinkup.ranning.entities.Carrera;
import com.thinkup.ranning.exceptions.PersistenciaException;

/**
 * Servicio de carreras.
 * 
 * @author Dario Camarro
 *
 */
@Path("/carreras")
public class CarreraService {

	@Inject
	CarreraDAO service;

	/**
	 * Este servicio permite obtener la lista de carreras que se encuentra en la
	 * base de datos.
	 * 
	 * @return Lista de carreras de la base de datos. Vacío en caso de ser nulo.
	 */
	@Path("/carrerasDtoAll")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<List<CarreraDTO>> getAllDto() {
		Respuesta<List<CarreraDTO>> r = new Respuesta<List<CarreraDTO>>();
		List<CarreraDTO> carrerasDto = this.service.getCarrerasDTO(null);
		if(carrerasDto.isEmpty()){
			r.addMensaje("No se encontraron resultados");
			r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
			r.setDto(carrerasDto);
		}
		else{
			r.addMensaje("Operación ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(carrerasDto);
		}
		return r;
	}
	
	/**
	 * Este servicio permite obtener la lista de carreras que se encuentra en la
	 * base de datos aplicando los filtros pasados por parametro.
	 * 
	 * @return Lista de carreras de la base de datos.
	 */
	@Path("/carrerasDtoAll")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<List<CarreraDTO>> getCarrerasByFiltro(Filtro filtro) {
		Respuesta<List<CarreraDTO>> r = new Respuesta<List<CarreraDTO>>();
		List<CarreraDTO> carrerasDto = this.service.getCarrerasDTO(filtro);
		if(carrerasDto.isEmpty()){
			r.addMensaje("No se encontraron resultados");
			r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
			r.setDto(carrerasDto);
		}
		else{
			r.addMensaje("Operación ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(carrerasDto);
		}
		return r;
	}

	/**
	 * Este método permite buscar una carrera por nro de carrera.
	 *  
	 * @param nroCarrera
	 * @return La carrera completa con todos sus atributos.
	 */
	@Path("/carreraByNro/{nroCarrera}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<Carrera> getCarreraByNro(@PathParam("nroCarrera") int nroCarrera) {
		Respuesta<Carrera> r = new Respuesta<Carrera>();
		Carrera c = null;
		try {
			c = service.findCarreraWithNumero(nroCarrera);
			
				r.addMensaje("Se encontró la carrera con éxito.");
				r.setCodigoRespuesta(Respuesta.CODIGO_OK);
				r.setDto(c);
				
			return r;
		} catch (PersistenciaException e) {
			r.addMensaje("La carrera numero "+nroCarrera+" no éxiste.");
			r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
			r.setDto(c);
			return r;
		} 
		
	}

	/**
	 * Este servicio permite crear una nueva carrera.
	 * 
	 * @param carrera
	 * @return La respuesta del servicio contiene el codigo de respuesta y los
	 *         mensajes asociados.
	 */
	@Path("/createCarrera")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Respuesta<Carrera> createCarrera(Carrera carrera) {
		try {
			service.saveCarrera(carrera);
			Respuesta<Carrera> r = new Respuesta<Carrera>();
			r.addMensaje("La carrera se persistió con exito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_CREACION_MODIFICACION_OK);
			return r;

		} catch (PersistenciaException e) {
			Respuesta<Carrera> r = new Respuesta<Carrera>();
			r.addMensaje("No se pudo persistir por problemas en la base de datos.");
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			return r;
		}
	}

}