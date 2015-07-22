package com.thinkup.ranning.server.rest;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.thinkup.ranning.dao.UsuarioDAO;
import com.thinkup.ranning.entities.Usuario;


/**
 * Servicio de usuarios.
 * 
 * @author Dario Camarro
 *
 */
@Path("/usuarios")
public class UsuarioService {
	

	@Inject
	UsuarioDAO service;
	
	/**
	 * Este servicio permite obtener la lista de carreras que se encuentra en la
	 * base de datos.
	 * 
	 * @return Lista de carreras de la base de datos.
	 */
	@Path("/usuariosAll")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public List<Usuario> getAll() {

		return service.getAllUsuarios();
	}

}
