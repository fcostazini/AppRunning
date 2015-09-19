package com.thinkup.ranning.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.thinkup.ranning.dtos.UsuarioBusquedaDto;
import com.thinkup.ranning.dtos.UsuarioDTO;
import com.thinkup.ranning.entities.GruposRunning;
import com.thinkup.ranning.entities.Usuario;
import com.thinkup.ranning.exceptions.PersistenciaException;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class UsuarioDAO {

	@PersistenceContext(name = "appRunning")
	private EntityManager entityManager;

	public List<Usuario> getAllUsuarios() {
		List<Usuario> usuario = this.entityManager.createNamedQuery(
				Usuario.QUERY_ALL, Usuario.class).getResultList();
		return usuario;
	}

	public Usuario save(Usuario u) throws PersistenciaException {
		try {
			return this.entityManager.merge(u);
		} catch (Exception e) {
			throw new PersistenciaException("Error al guardar el usuario", e);
		}
	}

	public List<Usuario> getAllUsuariosByData(Integer idOwner, String param) {
		List<Usuario> usuario = this.entityManager
				.createNamedQuery(Usuario.QUERY_ALL_BY_PARAM, Usuario.class)
				.setParameter(Usuario.PARAM_USUARIO_ID, idOwner)
				.setParameter(Usuario.PARAM_NOMBRE, param + "%")
				.setParameter(Usuario.PARAM_EMAIL, param + "%").getResultList();
		return usuario;
	}

	public Usuario getById(int id) throws PersistenciaException {
		try {
			Usuario usuario = this.entityManager
					.createNamedQuery(Usuario.QUERY_USUARIO_BY_ID,
							Usuario.class)
					.setParameter(Usuario.PARAM_USUARIO_ID, id)
					.getSingleResult();
			return usuario;
		} catch (NoResultException e) {
			String mensaje = "No existe usuario con el id " + id;
			throw new PersistenciaException(mensaje, e);
		}
	}

	public Usuario getByEmail(String email) {
		try {
			Usuario usuario = this.entityManager
					.createNamedQuery(Usuario.QUERY_BY_EMAIL, Usuario.class)
					.setParameter(Usuario.PARAM_EMAIL, email).getSingleResult();
			return usuario;
		} catch (NoResultException e) {
			return null;
		}
	}

	public void modificarUsuario(UsuarioDTO usuarioDTO)
			throws PersistenciaException {
		Usuario usuario = this.getById(usuarioDTO.getId());
		if (usuario == null) {
			throw new PersistenciaException("El usuario no existe.", null);
		}
		this.updateDatosUsuario(usuario, usuarioDTO);
		this.entityManager.persist(usuario);
	}

	public void saveUsuario(UsuarioDTO usuarioDTO) throws PersistenciaException {
		this.saveUsuario(usuarioDTO, null);
	}

	public void saveUsuario(UsuarioDTO usuarioDTO, String token)
			throws PersistenciaException {
		Usuario usuario = new Usuario();
		this.updateDatosUsuario(usuario, usuarioDTO);
		this.entityManager.persist(usuario);
		if (token != null && !token.isEmpty()) {
			usuario.setToken(token);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, 1);
			usuario.setFechaVigencia(c.getTime());
		}
		usuarioDTO.setId(usuario.getId());
	}

	private void updateDatosUsuario(Usuario usuario, UsuarioDTO usuariosDTO)
			throws PersistenciaException {
		usuario.setApellido(usuariosDTO.getApellido());
		usuario.setEmail(usuariosDTO.getEmail());

		try {
			Date date = new Date();
			date = new SimpleDateFormat(UsuarioDTO.FORMAT_DATE)
					.parse(usuariosDTO.getFechaNacimiento());
			usuario.setFechaNacimiento(date);
		} catch (ParseException e) {
			throw new PersistenciaException(
					"El formato de la fecha es erroneo.", e);
		}
		usuario.setFotoPerfil(usuariosDTO.getFotoPerfil());
		usuario.setNick(usuariosDTO.getNick());
		usuario.setNombre(usuariosDTO.getNombre());
		usuario.setPassword(usuariosDTO.getPassword());
		usuario.setTipoCuenta(usuariosDTO.getTipoCuenta());
		usuario.setVerificado(usuariosDTO.getVerificado());
		usuario.setSocialId(usuariosDTO.getSocialId());
		if (!usuariosDTO.getGrupoId().trim().equals("")) {
			TypedQuery<GruposRunning> q = entityManager.createNamedQuery(
					GruposRunning.QUERY_BY_NOMBRE, GruposRunning.class);
			q.setParameter(GruposRunning.PARAM_NOMBRE, usuariosDTO.getGrupoId());
			try {
				GruposRunning g = q.getSingleResult();
				usuario.setGrupo(g);
			} catch (NoResultException e) {
				throw new PersistenciaException("Grupo no encontrado", e);
			}

		}
	}

	public Usuario getBySocialId(String socialId) {
		try {
			Usuario usuario = this.entityManager
					.createNamedQuery(Usuario.QUERY_BY_SOCIAL_ID, Usuario.class)
					.setParameter(Usuario.PARAM_SOCIALID, socialId).getSingleResult();
			return usuario;
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<Usuario> getUsuariosByFilter(UsuarioBusquedaDto usuarioForm) {
		StringBuffer queryBf = new StringBuffer();
		List<QueryParam> parametros = new Vector<>();
		queryBf.append("Select u from Usuario u WHERE 1 = 1 ");
		if(stringNotEmpty(usuarioForm.getNombre())){
			queryBf.append(" AND upper(u.nombre) like :nombre");
			parametros.add(new QueryParam("nombre", usuarioForm.getNombre().toUpperCase() + "%"));
		}
		if(stringNotEmpty(usuarioForm.getApellido())){
			queryBf.append(" AND upper(u.apellido) like :apellido");
			parametros.add(new QueryParam("apellido", usuarioForm.getApellido().toUpperCase() + "%"));
		}
		if(stringNotEmpty(usuarioForm.getEmail())){
			queryBf.append(" AND upper(u.email) = :email");
			parametros.add(new QueryParam("email", usuarioForm.getEmail().toUpperCase()));
		}
		if(stringNotEmpty(usuarioForm.getNickName())){
			queryBf.append(" AND upper(u.nick) like :nick");
			parametros.add(new QueryParam("nick", usuarioForm.getNickName().toUpperCase() + "%"));
		}
		if(stringNotEmpty(usuarioForm.getGrupo())){
			if(usuarioForm.getGrupo().toUpperCase().equals("NINGUNO")){
				queryBf.append(" AND u.grupo is null");
			}else{
			queryBf.append(" AND upper(u.grupo.nombre) like :grupo");
			parametros.add(new QueryParam("grupo", usuarioForm.getGrupo().toUpperCase() + "%"));
			}
		}
		
		TypedQuery<Usuario> q = entityManager.createQuery(queryBf.toString(),Usuario.class);
		if (parametros.size() > 0) {
			for (QueryParam queryParam : parametros) {
				q.setParameter(queryParam.getNombre(), queryParam.getValor());

			}
		}
		try {
			
			return q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return new Vector<Usuario>();
	}

	private boolean stringNotEmpty(String nombre) {
		// TODO Auto-generated method stub
		return nombre!=null && !nombre.isEmpty();
	}

}
