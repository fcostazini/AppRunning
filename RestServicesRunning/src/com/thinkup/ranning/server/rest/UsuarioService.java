package com.thinkup.ranning.server.rest;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.thinkup.ranning.dao.UsuarioDAO;
import com.thinkup.ranning.dtos.CheckUsuarioPassDTO;
import com.thinkup.ranning.dtos.GSMTokenUsuario;
import com.thinkup.ranning.dtos.PasswordEncoder;
import com.thinkup.ranning.dtos.PasswordWrapper;
import com.thinkup.ranning.dtos.Respuesta;
import com.thinkup.ranning.dtos.TipoCuenta;
import com.thinkup.ranning.dtos.UsuarioBusquedaDto;
import com.thinkup.ranning.dtos.UsuarioDTO;
import com.thinkup.ranning.entities.Usuario;
import com.thinkup.ranning.exceptions.PersistenciaException;
import com.thinkup.ranning.server.app.EmailService;
import com.thinkup.ranning.server.app.TokenGenerator;

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

	@Inject
	EmailService emailService;

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
	public Respuesta<List<UsuarioDTO>> getAll() {

		List<UsuarioDTO> usuariosDto = new Vector<>();
		List<Usuario> usuarios = service.getAllUsuarios();
		for (Usuario usuario : usuarios) {
			usuariosDto.add(new UsuarioDTO(usuario));
		}

		Respuesta<List<UsuarioDTO>> r = new Respuesta<List<UsuarioDTO>>();
		r.addMensaje("Operacion ejecutada con éxito.");

		if (usuarios.isEmpty()) {
			r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
		} else
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);

		r.setDto(usuariosDto);

		return r;
	}

	@Path("/buscarUsuarios")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Respuesta<List<UsuarioDTO>> buscarUsuarios(
			UsuarioBusquedaDto usuarioForm) {
		List<UsuarioDTO> usuariosDto = new Vector<>();
		List<Usuario> usuarios = service.getUsuariosByFilter(usuarioForm);
		for (Usuario usuario : usuarios) {
			usuariosDto.add(new UsuarioDTO(usuario));
		}

		Respuesta<List<UsuarioDTO>> r = new Respuesta<List<UsuarioDTO>>();
		r.addMensaje("Operacion ejecutada con éxito.");

		if (usuarios.isEmpty()) {
			r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
		} else
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);

		r.setDto(usuariosDto);

		return r;

	}

	/**
	 * Este servicio permite obtener la lista de carreras que se encuentra en la
	 * base de datos.
	 * 
	 * @return Lista de carreras de la base de datos.
	 */
	@Path("/usuariosById/{id}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<UsuarioDTO> findById(@PathParam("id") int id) {

		UsuarioDTO usuarioDto = null;
		Usuario usuario;
		try {
			usuario = service.getById(id);
			if (usuario != null) {
				usuarioDto = new UsuarioDTO(usuario);
			}
			Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(usuarioDto);
			return r;
		} catch (PersistenciaException e) {
			Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
			r.setDto(usuarioDto);
			return r;
		}

	}

	/**
	 * Este servicio permite obtener la lista de carreras que se encuentra en la
	 * base de datos.
	 * 
	 * @return Lista de carreras de la base de datos.
	 */
	@Path("/usuariosByEmail/{email}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<UsuarioDTO> findByEmail(@PathParam("email") String email) {

		UsuarioDTO usuarioDto = null;
		Usuario usuario;
		try {
			usuario = service.getByEmail(email);
			if (usuario != null) {
				usuarioDto = new UsuarioDTO(usuario);
			}
			Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
			r.addMensaje("Operacion ejecutada con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(usuarioDto);
			return r;
		} catch (Exception e) {
			Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
			r.setDto(usuarioDto);
			return r;
		}

	}

	/**
	 * Este servicio permite obtener la lista de carreras que se encuentra en la
	 * base de datos.
	 * 
	 * @return Lista de carreras de la base de datos.
	 */
	@Path("/recuperarPasswordRequest/{email}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Respuesta<UsuarioDTO> solicitarRecupero(
			@PathParam("email") String email) {

		UsuarioDTO usuarioDto = null;
		Usuario usuario;
		try {
			usuario = this.service.getByEmail(email);

			if (usuario == null) {
				Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
				r.addMensaje("Usuario No Existente");
				r.setCodigoRespuesta(Respuesta.CODIGO_SOLICITUD_INCORRECTA);
				return r;
			} else if (usuario.getTipoCuenta().equals(
					TipoCuenta.PROPIA.getTipo())) {

				usuario.setVerificado(false);
				String token = TokenGenerator.getInstance().nextTokenId();
				usuario.setToken(token);
				Calendar c = Calendar.getInstance();
				c.add(Calendar.DATE, 1);
				usuario.setFechaVigencia(c.getTime());
				usuario.setIntentos(5);
				service.save(usuario);
				UsuarioDTO uDto = new UsuarioDTO(usuario);
				new EmailSenderTask(uDto, token, TipoEmail.RECUPERAR_PASS)
						.start();
				Respuesta<UsuarioDTO> r = new Respuesta<>();
				r.setDto(uDto);
				r.setCodigoRespuesta(Respuesta.CODIGO_OK);
				return r;
			} else {
				Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
				r.addMensaje("Sin resultado");
				r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
				r.setDto(usuarioDto);
				return r;
			}
		} catch (Exception e) {
			Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_SIN_RESULTADOS);
			r.setDto(usuarioDto);
			return r;
		}

	}

	/**
	 * Este servicio permite modificar un usuario.
	 * 
	 * @param carrera
	 * @return La respuesta del servicio contiene el codigo de respuesta y los
	 *         mensajes asociados.
	 */
	@Path("/updateUsuario")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Respuesta<UsuarioDTO> updateUsuario(UsuarioDTO usuariosDTO) {
		try {
			service.modificarUsuario(usuariosDTO);
			Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
			r.addMensaje("El usuario se modificó con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_CREACION_MODIFICACION_OK);
			r.setDto(usuariosDTO);
			return r;

		} catch (PersistenciaException e) {
			Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_SOLICITUD_INCORRECTA);
			return r;
		}
	}

	/**
	 * Este servicio permite modificar un usuario.
	 * 
	 * @param carrera
	 * @return La respuesta del servicio contiene el codigo de respuesta y los
	 *         mensajes asociados.
	 */
	@Path("/saveUsuario")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Respuesta<UsuarioDTO> saveUsuario(UsuarioDTO usuariosDTO) {
		try {
			if (this.service.getByEmail(usuariosDTO.getEmail()) != null) {
				Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
				r.addMensaje("Usuario Existente");
				r.setCodigoRespuesta(Respuesta.CODIGO_SOLICITUD_INCORRECTA);
				return r;
			}

			if (usuariosDTO.getTipoCuenta().equals(TipoCuenta.PROPIA.getTipo())) {

				usuariosDTO.setVerificado(false);
				String token = TokenGenerator.getInstance().nextTokenId();
				service.saveUsuario(usuariosDTO, token);
				new EmailSenderTask(usuariosDTO, token,
						TipoEmail.CONFIRMAR_USUARIO).start();

			} else {
				usuariosDTO.setVerificado(true);
				service.saveUsuario(usuariosDTO);

			}

			Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
			r.addMensaje("El usuario se creo con éxito.");
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(usuariosDTO);
			return r;

		} catch (PersistenciaException e) {
			Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_SOLICITUD_INCORRECTA);
			return r;
		}
	}

	/**
	 * Este servicio permite obtener la lista de carreras que se encuentra en la
	 * base de datos.
	 * 
	 * @return Lista de carreras de la base de datos.
	 */
	@Path("/token/{email}/{token}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response verificarUsuario(@PathParam("email") String email,
			@PathParam("token") String token,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		Usuario usuario;
		String contextPath = request.getContextPath();
		try {
			usuario = service.getByEmail(email);

			if (usuario != null
					&& usuario.getFechaVigencia().compareTo(new Date()) > 0
					&& usuario.getToken().equals(token)) {
				usuario.setVerificado(true);
				service.save(usuario);
				response.sendRedirect(contextPath
						+ "/resources/views/confirmado.html");
				return Response.status(Status.ACCEPTED).build();

			} else {
				response.sendRedirect(contextPath
						+ "/resources/views/no_confirmado.html");
				return Response.status(Status.ACCEPTED).build();

			}

		} catch (Exception e) {

			try {
				response.sendRedirect(contextPath
						+ "/resources/views/no_confirmado.html");
				return Response.status(Status.ACCEPTED).build();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			}
		}

	}

	/**
	 * Este servicio permite obtener la lista de carreras que se encuentra en la
	 * base de datos.
	 * 
	 * @return Lista de carreras de la base de datos.
	 */
	@Path("/recuperar/{email}/{token}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response recuperarPassword(@PathParam("email") String email,
			@PathParam("token") String token,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {

		Usuario usuario;
		String contextPath = request.getContextPath();
		try {
			usuario = service.getByEmail(email);

			if (usuario != null
					&& usuario.getFechaVigencia().compareTo(new Date()) > 0
					&& usuario.getToken().equals(token)) {
				usuario.setVerificado(true);
				service.save(usuario);
				response.sendRedirect(contextPath
						+ "/resources/views/cambiar_pass.html?email=" + email
						+ "&token=" + token);
				return Response.status(Status.ACCEPTED).build();

			} else {
				response.sendRedirect(contextPath
						+ "/resources/views/no_confirmado.html");
				return Response.status(Status.ACCEPTED).build();

			}

		} catch (Exception e) {

			try {
				response.sendRedirect(contextPath
						+ "/resources/views/no_confirmado.html");
				return Response.status(Status.ACCEPTED).build();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			}
		}

	}

	@Path("/gcmregister")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Respuesta<String> registerNotificaciones(GSMTokenUsuario token) {
		try {
			Respuesta<String> r = new Respuesta<>();
			Usuario u = service.getById(token.getIdUsuario());
			if (u == null) {
				r.setCodigoRespuesta(Respuesta.CREDENCIALES_ERRONEAS);
				r.addMensaje("Credenciales Invalidas");
				r.setDto(null);

			} else {
				u.setGsmToken(token.getToken());
				this.service.save(u);
				r.setCodigoRespuesta(Respuesta.CODIGO_OK);
				r.setDto("Actualizado");

			}
			return r;
		} catch (PersistenciaException e) {
			Respuesta<String> r = new Respuesta<>();
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_ERROR_INTERNO);
			return r;
		}

	}

	@Path("/login")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Respuesta<UsuarioDTO> loggin(CheckUsuarioPassDTO check) {
		try {
			Usuario u = service.getByEmail(check.getUsuario());
			Respuesta<UsuarioDTO> r = new Respuesta<>();
			if (u == null) {
				r.setCodigoRespuesta(Respuesta.CREDENCIALES_ERRONEAS);
				r.addMensaje("Credenciales Invalidas");
				r.setDto(null);
				return r;
			}
			if (u.getIntentos() != null && u.getIntentos() >= 5) {
				r.setCodigoRespuesta(Respuesta.USUARIO_BLOQUEADO);
				r.addMensaje("Usuario Bloqueado");
				r.setDto(null);
				return r;
			}
			if (!u.getVerificado()) {
				r.setCodigoRespuesta(Respuesta.USUARIO_INVALIDO);
				r.addMensaje("Usuario No Verificado");
				r.setDto(null);
				return r;
			}
			if (!u.getPassword().equals(check.getPassword())) {
				if (u.getIntentos() == null) {
					u.setIntentos(1);
				} else {
					u.setIntentos(u.getIntentos() + 1);
				}

				service.save(u);
				r.setCodigoRespuesta(Respuesta.CREDENCIALES_ERRONEAS);
				r.addMensaje("Credenciales Invalidas");
				r.setDto(null);
				return r;
			}
			r.setCodigoRespuesta(Respuesta.CODIGO_OK);
			r.setDto(new UsuarioDTO(u));
			return r;

		} catch (Exception e) {
			Respuesta<UsuarioDTO> r = new Respuesta<UsuarioDTO>();
			r.addMensaje(e.getMessage());
			r.setCodigoRespuesta(Respuesta.CODIGO_SOLICITUD_INCORRECTA);
			return r;
		}
	}

	@Path("/changePass")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response changePass(PasswordWrapper pass,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) {
		String contextPath = request.getContextPath();
		try {

			Usuario usuario = service.getByEmail(pass.getEmail());
			if (usuario == null) {
				response.sendRedirect(contextPath
						+ "/resources/views/no_confirmado.html?code=302");
				return Response.status(Status.ACCEPTED).build();
			}
			if (pass.getPass().length() < 8) {
				return Response.status(Status.BAD_REQUEST)
						.entity("Longitud Minima 8 caracteres").build();
			}
			if (usuario.getIntentos() == null || usuario.getIntentos() < 5) {
				return Response
						.status(Status.BAD_REQUEST)
						.entity("No se esperaba recuperar password. Usuario Invalido.")
						.build();
			}
			usuario.setPassword(PasswordEncoder.encodePass(pass.getPass()));
			usuario.setIntentos(0);
			usuario.setToken("");
			service.save(usuario);

			return Response.ok().status(Status.ACCEPTED)
					.entity("Password actualizada").build();

		} catch (Exception e) {

			return Response.status(Status.BAD_REQUEST).build();
		}
	}

	private class EmailSenderTask extends Thread {
		private UsuarioDTO usuario;
		private String token;
		private TipoEmail tipo;

		public EmailSenderTask(UsuarioDTO usuario, String token, TipoEmail tipo) {
			super();
			this.usuario = usuario;
			this.token = token;
			this.tipo = tipo;
		}

		@Override
		public void run() {

			emailService.sendConfirmacion(usuario, token, tipo);
			// TODO Auto-generated method stub

		}

	}

}
