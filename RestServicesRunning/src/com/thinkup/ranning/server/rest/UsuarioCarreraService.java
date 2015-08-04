package com.thinkup.ranning.server.rest;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

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
import com.thinkup.ranning.dao.UsuarioCarreraDAO;
import com.thinkup.ranning.dao.UsuarioDAO;
import com.thinkup.ranning.dtos.Filtro;
import com.thinkup.ranning.dtos.Respuesta;
import com.thinkup.ranning.dtos.UsuarioCarreraDTO;
import com.thinkup.ranning.entities.Carrera;
import com.thinkup.ranning.entities.Usuario;
import com.thinkup.ranning.entities.UsuarioCarrera;
import com.thinkup.ranning.server.rest.exception.EntidadInexistenteException;

@Path("/miscarreras")
public class UsuarioCarreraService implements IUsuarioCarreraProvider {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4110633265795163387L;
	@Inject
	UsuarioCarreraDAO dao;

	@Inject
	CarreraDAO daoCarrera;

	@Inject
	UsuarioDAO daoUsuario;

	/**
	 * Este servicio permite obtener la lista de carreras que se encuentra en la
	 * base de datos.
	 * 
	 * @return Lista de carreras de la base de datos.
	 */

	@Path("/findById/{id}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	@Override
	public Respuesta<UsuarioCarrera> findById(@PathParam("id") Integer id) {
		UsuarioCarrera uc = null;
		try {
			uc = dao.getById(id);
			Respuesta<UsuarioCarrera> r = new Respuesta<>();
			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(uc);
			return r;
		} catch (EntidadInexistenteException e) {
			Respuesta<UsuarioCarrera> r = new Respuesta<>();
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
			r.setDto(uc);
			return r;
		}
	}

	@Path("/findAll")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	@Override
	public Respuesta<List<UsuarioCarrera>> findAll() {
		Respuesta<List<UsuarioCarrera>> r = new Respuesta<List<UsuarioCarrera>>();
		try {

			r.setDto(dao.findAll(UsuarioCarrera.class));
			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			return r;
		} catch (Exception e) {

			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
			r.setDto(new Vector<UsuarioCarrera>());
			return r;
		}
	}

	@Path("/update")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<UsuarioCarrera> update(UsuarioCarrera entidad) {
		Respuesta<UsuarioCarrera> r = new Respuesta<UsuarioCarrera>();
		if (entidad.getId() == null) {
			r.addMensaje("La entidad no existe");
			r.setCodigoRespuesta(Respuesta.CODIGO_NO_ENCONTRADO);
			r.setDto(entidad);

		} else {
			try {
				r.setDto(this.dao.update(entidad));
				r.addMensaje("Operación exitosa");
				r.setCodigoRespuesta(Respuesta.CODIGO_OK);

			} catch (Exception e) {
				r.addMensaje("Entidad no guardada");
				r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
				r.setDto(entidad);

			}
		}
		return r;

	}

	@Path("/upload")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<Integer> uploadUsuarioCarreras(
			Collection<UsuarioCarreraDTO> entidad) {
		Respuesta<Integer> r = new Respuesta<Integer>();
		try {
			UsuarioCarrera uc = null;

			for (UsuarioCarreraDTO usuarioCarrera : entidad) {
				try {
					uc = this.dao.getByUsuarioCarrera(
							usuarioCarrera.getUsuario(),
							usuarioCarrera.getIdCarrera());
					fillUsuarioEntity(uc, usuarioCarrera);

					this.dao.update(uc);
				} catch (EntidadInexistenteException e) {
					uc = new UsuarioCarrera();
					fillUsuarioEntity(uc, usuarioCarrera);
					Usuario u =daoUsuario.getByEmail(usuarioCarrera
							.getUsuario());
					Carrera c = daoCarrera.getById(usuarioCarrera
							.getIdCarrera());
					if(u!= null && c!= null){
						uc.setUsuario(u);
						uc.setCarrera(c);
						this.dao.save(uc);
					}else{
						r.addMensaje("Entidades no guardadas");
						r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
						r.setDto(Respuesta.CODIGO_ERROR_INTERNO);
					}
					
					
				}
			}
			r.addMensaje("Updated");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(Respuesta.CODIGO_OK);
		} catch (Exception e) {
			r.addMensaje("Entidades no guardadas");
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			r.setDto(Respuesta.CODIGO_ERROR_INTERNO);
		}
		return r;

	}

	private void fillUsuarioEntity(UsuarioCarrera uc,
			UsuarioCarreraDTO usuarioCarrera) {
		uc.setCorrida(usuarioCarrera.isCorrida());
		
		uc.setIsAnotado(usuarioCarrera.isAnotado());
		uc.setMeGusta(usuarioCarrera.isMeGusta());
		uc.setTiempo(usuarioCarrera.getTiempo());
		uc.setDistancia(usuarioCarrera.getDistancia());
		uc.setModalidad(usuarioCarrera.getModalidad());
	}

	@Path("/save")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	@Override
	public Respuesta<UsuarioCarrera> grabar(UsuarioCarrera entidad) {
		Respuesta<UsuarioCarrera> r = new Respuesta<UsuarioCarrera>();
		if (entidad.getId() != null) {
			r.addMensaje("La entidad  existe");
			r.setCodigoRespuesta(Respuesta.CODIGO_ENTIDAD_EXISTENTE);
			r.setDto(entidad);

		} else {
			try {
				r.setDto(this.dao.save(entidad));
				r.addMensaje("Operación exitosa");
				r.setCodigoRespuesta(Respuesta.CODIGO_OK);

			} catch (Exception e) {
				r.addMensaje("Entidad no guardada");
				r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
				r.setDto(entidad);

			}
		}
		return r;
	}

	@Path("/findByIdCarrera/{idCarrera}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	@Override
	public Respuesta<UsuarioCarrera> getByIdCarrera(
			@PathParam("idCarrera") int id) {
		UsuarioCarrera uc = null;
		Respuesta<UsuarioCarrera> r = new Respuesta<>();
		try {
			uc = dao.getByIdCarrera(id);
			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(uc);
		} catch (Exception e) {
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			r.setDto(uc);

		}
		return r;
	}

	@Path("/misTiemposByFiltro")
	@POST()
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	@Override
	public Respuesta<List<UsuarioCarrera>> findTiemposByFiltro(Filtro filtro) {
		Respuesta<List<UsuarioCarrera>> r = new Respuesta<List<UsuarioCarrera>>();
		try {
			List<UsuarioCarrera> carreras = this.dao.getByFiltro(filtro);
			r.addMensaje("Operación ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(carreras);
		} catch (Exception e) {

			r.addMensaje("Error inesperado");
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			r.setDto(null);
		}

		return r;
	}

}
