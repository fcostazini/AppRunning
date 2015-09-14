package com.thinkup.ranning.server.rest;

import java.util.List;
import java.util.Vector;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.thinkup.ranning.dao.FiltrosDao;
import com.thinkup.ranning.dtos.ProvinciaCiudadDTO;
import com.thinkup.ranning.dtos.Respuesta;

@Path("/filtros")
public class FiltrosService {
	
	@Inject
	private FiltrosDao dao;
	
	@Path("/getProvincias")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<List<String>> getProvincias() {
		List<String> provincias = new Vector<>();
		Respuesta<List<String>> r = new Respuesta<>();
		try {
			provincias = dao.getProvincias();
			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(provincias);
		} catch (Exception e) {
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			r.setDto(provincias);

		}
		return r;
	}
	
	@Path("/getCiudades/{provincia}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<List<String>> getCiudades(@PathParam("provincia") String provincia) {
		List<String> ciudades = new Vector<>();
		Respuesta<List<String>> r = new Respuesta<>();
		try {
			ciudades = dao.getCiudades(provincia);
			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(ciudades);
		} catch (Exception e) {
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			r.setDto(ciudades);

		}
		return r;
	}
	
	@Path("/getFiltrosProvinciaCiudad")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<List<ProvinciaCiudadDTO>> getFiltrosProvinciaCiudad() {
		List<ProvinciaCiudadDTO> filtros = new Vector<>();
		Respuesta<List<ProvinciaCiudadDTO>> r = new Respuesta<>();
		try {
			filtros = dao.getFiltrosProvinciaCiudad();
			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(filtros);
		} catch (Exception e) {
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			r.setDto(filtros);

		}
		return r;
	}
	
}