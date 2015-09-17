package com.thinkup.ranning.server.rest;

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

import com.thinkup.ranning.dao.AmigoDao;
import com.thinkup.ranning.dao.UsuarioCarreraDAO;
import com.thinkup.ranning.dao.UsuarioDAO;
import com.thinkup.ranning.dtos.AmigoRequest;
import com.thinkup.ranning.dtos.AmigosDTO;
import com.thinkup.ranning.dtos.CarreraAmigoDTO;
import com.thinkup.ranning.dtos.Respuesta;
import com.thinkup.ranning.entities.Amigos;
import com.thinkup.ranning.exceptions.PersistenciaException;

/**
 * 
 * @author Facundo
 *
 *         Servicio para obtener relaciones entre los usuarios
 */
@Path("/amigos")
public class AmigosService {

	@Inject
	private AmigoDao dao;
	@Inject
	private UsuarioDAO usuarioDao;

	@Inject
	private UsuarioCarreraDAO carreraDao;

	@Path("/getAmigos/{idOwner}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<List<AmigosDTO>> getAmigosByUsuario(
			@PathParam("idOwner") int idOwner) {
		List<Amigos> amigos = new Vector<>();
		Respuesta<List<AmigosDTO>> r = new Respuesta<>();
		try {
			amigos = dao.getAmigosByOwner(idOwner);
			List<AmigosDTO> resultados = new Vector<>();
			for (Amigos a : amigos) {
				resultados.add(new AmigosDTO(a));
			}
			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(resultados);
			return r;
		} catch (Exception e) {
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			r.setDto(new Vector<AmigosDTO>());
			return r;
		}

	}

	@Path("/getAmigosEnCarrera/{idOwner}/{idCarrera}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<List<AmigosDTO>> getAmigosEnCarrera(
			@PathParam("idOwner") int idOwner,
			@PathParam("idCarrera") int idCarrera) {
		List<AmigosDTO> amigos = new Vector<>();
		Respuesta<List<AmigosDTO>> r = new Respuesta<>();
		try {
			amigos = dao.getAmigosEnCarrera(idOwner, idCarrera);

			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(amigos);
			return r;
		} catch (Exception e) {
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			r.setDto(new Vector<AmigosDTO>());
			return r;
		}

	}

	@Path("/buscarAmigos/{idOwner}/{param}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<List<AmigosDTO>> getAmigosByBusqueda(
			@PathParam("idOwner") int idOwner, @PathParam("param") String param) {
		List<AmigosDTO> resultados = new Vector<>();
		Respuesta<List<AmigosDTO>> r = new Respuesta<>();
		try {
			resultados = dao.buscarAmigos(idOwner, param);
			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(resultados);
			return r;
		} catch (Exception e) {
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			r.setDto(new Vector<AmigosDTO>());
			return r;
		}

	}

	@Path("/updateUsuario")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Respuesta<AmigosDTO> actualizarRelacion(AmigosDTO amigos) {
		try {

			Amigos a = dao.getAmigosByPk(amigos.getIdAmigo(),
					amigos.getIdOwner());
			a.setEsAmigo(amigos.getEsAmigo());
			a.setEsBloqueado(amigos.getEsBloqueado());
			a.setEsPendiente(amigos.getEsPendiente());
			a = dao.guardarEstadoAmigo(a);
			Respuesta<AmigosDTO> r = new Respuesta<>();
			r.addMensaje("El usuario se modificó con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_CREACION_MODIFICACION_OK);
			r.setDto(new AmigosDTO(a));
			return r;

		} catch (PersistenciaException e) {
			Respuesta<AmigosDTO> r = new Respuesta<>();
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_SOLICITUD_INCORRECTA);
			return r;
		}
	}

	@Path("/request")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Respuesta<List<AmigosDTO>> manejarRequestAmigos(
			List<AmigoRequest> amigosRequest) {

		try {

			Amigos a;
			List<AmigosDTO> result = new Vector<>();
			for (AmigoRequest amigoRequest : amigosRequest) {

				try {
					if (amigoRequest.getIdAmigo() == null) {
						a = null;
					} else {
						a = dao.getAmigosByPk(amigoRequest.getIdAmigo(),
								amigoRequest.getIdOwner());
					}

				} catch (PersistenciaException e) {
					a = null;
				}
				switch (amigoRequest.getTipoRequest()) {
				case ACEPTAR_AMIGO:
				case RECHAZAR_AMIGO:
				case SOLICITUD_AMIGO:

					if (a == null) {
						a = crearNuevoAmigo(amigoRequest);
					}
					a.setEsAmigo(true);
					break;
				case BLOQUEAR_AMIGO:
					a.setEsAmigo(false);
					a.setEsBloqueado(true);
					break;
				case DESBLOQUEAR_AMIGO:
					a.setEsBloqueado(false);

					break;
				case QUITA_AMIGO:
					a.setEsAmigo(false);
					break;
				default:
					break;
				}
				
				if (a.getUsuarioAmigo() != null) {
					this.dao.guardarEstadoAmigo(a);
					result.add(new AmigosDTO(a));
				}
			}
			Respuesta<List<AmigosDTO>> r = new Respuesta<>();
			r.addMensaje("Guardado Correctamente");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(result);
			return r;
		} catch (PersistenciaException e) {
			Respuesta<List<AmigosDTO>> r = new Respuesta<>();
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_SOLICITUD_INCORRECTA);
			return r;
		}
	}

	@Path("/findCarrerasByUsuario/{id}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<List<CarreraAmigoDTO>> findCarrerasByUsuario(
			@PathParam("id") Integer id) {
		Respuesta<List<CarreraAmigoDTO>> r = new Respuesta<List<CarreraAmigoDTO>>();
		try {

			r.setDto(carreraDao.findCarrerasAmigos(id));
			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			return r;
		} catch (Exception e) {

			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
			r.setDto(new Vector<CarreraAmigoDTO>());
			return r;
		}
	}

	private Amigos crearNuevoAmigo(AmigoRequest ar)
			throws PersistenciaException {
		Amigos a = new Amigos();
		a.setUsuarioOwner(usuarioDao.getById(ar.getIdOwner()));
		if (ar.getIdAmigo() == null) {
			if (ar.getSocialId() != null && !ar.getSocialId().isEmpty()) {
				a.setUsuarioAmigo(usuarioDao.getBySocialId(ar.getSocialId()));
			}

		} else {
			a.setUsuarioAmigo(usuarioDao.getById(ar.getIdAmigo()));
		}
		a.setEsAmigo(false);
		a.setEsBloqueado(false);
		a.setEsPendiente(false);
		return a;
	}
}
