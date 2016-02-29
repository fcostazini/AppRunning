package com.thinkup.ranning.server.rest;

import java.util.List;
import java.util.Vector;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.thinkup.ranning.dao.GruposRunningDao;
import com.thinkup.ranning.dtos.Respuesta;
import com.thinkup.ranning.entities.Carrera;
import com.thinkup.ranning.entities.GruposRunning;
import com.thinkup.ranning.exceptions.PersistenciaException;

@Path("/grupos")
public class GruposRunningService {

	@Inject
	private GruposRunningDao dao;

	@Path("/getGrupoById/{idGrupo}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<GruposRunning> getGrupoRunningByNro(
			@PathParam("idGrupo") int nroGrupoRunning) {
		Respuesta<GruposRunning> r = new Respuesta<GruposRunning>();
		GruposRunning c = null;
		try {
			c = dao.getGrupoById(nroGrupoRunning);

			r.addMensaje("Se encontró la grupo con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(c);

			return r;
		} catch (Exception e) {
			r.addMensaje("La grupo numero " + nroGrupoRunning + " no éxiste.");
			r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
			r.setDto(c);
			return r;
		}

	}

	@Path("/getGrupos/{nombre}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	public Respuesta<List<GruposRunning>> getGrupos(
			@PathParam("nombre") String nombre) {
		List<GruposRunning> grupos = new Vector<>();
		Respuesta<List<GruposRunning>> r = new Respuesta<>();
		try {
			grupos = dao.getGruposByNombre(nombre);
			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(grupos);
		} catch (Exception e) {
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			r.setDto(grupos);

		}
		return r;
	}

	@Path("/crearGrupo")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("SUPER_USER")
	public Respuesta<GruposRunning> createCarrera(GruposRunning grupo) {
		try {
			dao.guardarGrupo(grupo);
			Respuesta<GruposRunning> r = new Respuesta<GruposRunning>();
			r.addMensaje("El grupo se guardó correctamente.");
			r.setCodigoRespuesta(Respuesta.CODIGO_CREACION_MODIFICACION_OK);
			return r;

		} catch (PersistenciaException e) {
			Respuesta<GruposRunning> r = new Respuesta<GruposRunning>();
			r.addMensaje("No se pudo guardar por problemas en la base de datos.");
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
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
	@Path("/borrarGrupo")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@RolesAllowed("SUPER_USER")
	public Respuesta<GruposRunning> borrarGrupo( GruposRunning grupo) {
		try {
			dao.borrarGrupo(grupo);
			Respuesta<GruposRunning> r = new Respuesta<GruposRunning>();
			r.addMensaje("El Grupo se borró correctamente.");
			r.setCodigoRespuesta(Respuesta.CODIGO_CREACION_MODIFICACION_OK);
			return r;

		} catch (PersistenciaException e) {
			Respuesta<GruposRunning> r = new Respuesta<GruposRunning>();
			r.addMensaje("No se pudo borrar el Grupo");
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			return r;
		}
	}

}