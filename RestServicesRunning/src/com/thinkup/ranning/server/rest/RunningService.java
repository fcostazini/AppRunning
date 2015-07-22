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
import javax.ws.rs.core.Response;

import com.thinkup.ranning.dao.CarreraDAO;
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
public class RunningService {

	@Inject
	CarreraDAO service;

	/**
	 * Este servicio permite obtener la lista de carreras que se encuentra en la
	 * base de datos.
	 * 
	 * @return Lista de carreras de la base de datos.
	 */
	@Path("/carrerasAll")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public List<Carrera> getAll() {

		return service.getAllCarreras();
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
	public Carrera getCarreraByNro(@PathParam("nroCarrera") int nroCarrera) {

		return service.findCarreraWithName(nroCarrera);
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
	public Response createCarrera(Carrera carrera) {
		try {
			service.saveCarrera(carrera);
			Respuesta r = new Respuesta();
			r.addMensaje("La carrera se persistió con exito.");
			r.setCodigoRespuesta(200);
			return Response.status(r.getCodigoRespuesta()).entity(r).build();

		} catch (PersistenciaException e) {
			Respuesta r = new Respuesta();
			r.addMensaje("No se pudo persistir por problemas en la base de datos.");
			r.setCodigoRespuesta(200);
			return Response.status(r.getCodigoRespuesta()).entity(r).build();
		}
	}

}