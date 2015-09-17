package com.thinkup.ranning.dao;

import java.util.List;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.thinkup.ranning.dtos.AmigosDTO;
import com.thinkup.ranning.entities.Amigos;
import com.thinkup.ranning.entities.Usuario;
import com.thinkup.ranning.entities.UsuarioCarrera;
import com.thinkup.ranning.exceptions.PersistenciaException;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Stateless
public class AmigoDao {
	@PersistenceContext(name = "appRunning")
	private EntityManager entityManager;

	public List<Amigos> getAmigosByOwner(Integer idUsuarioOwner) {
		List<Amigos> amigos = new Vector<>();
		amigos = this.entityManager
				.createNamedQuery(Amigos.GET_BY_OWNER, Amigos.class)
				.setParameter(Amigos.OWNER_ID, idUsuarioOwner).getResultList();
		return amigos;

	}

	public List<AmigosDTO> buscarAmigos(int idOwner, String param)
			throws PersistenciaException {
		List<AmigosDTO> amigos = new Vector<>();
		StringBuffer query = new StringBuffer();
		query.append(" select :" + Amigos.OWNER_ID + "  as idOwner, ");
		query.append("        u.id as idAmigo, ");
		query.append("        u.nick, ");
		query.append("        u.email,");
		query.append("        u.foto_perfil as urlFoto,");
		query.append("        COALESCE(g.nombre,'Ninguno') as grupo, ");
		query.append("        coalesce(am.es_amigo,false) as esAmigo, ");
		query.append("        coalesce(am.es_bloqueado,false) as esBloqueado, ");
		query.append("        coalesce(am.es_pendiente,false) as esPendiente  ");
		query.append(" from usuario_app u ");
		query.append(" left join amigos_usuario am ");
		query.append(" on u.id = am.usuario_amigo ");
		query.append("  and am.usuario_owner = :" + Amigos.OWNER_ID);
		query.append("  left join grupos_running g  ");
		query.append("  on u.grupo_id = g.id	");
		query.append(" where u.id <> :" + Amigos.OWNER_ID);
		query.append(" and COALESCE(am.es_amigo,false) = false  ");
		query.append(" and (upper(u.nick) LIKE :param OR upper(u.email) LIKE :param )  ");
		query.append(" order by u.nick ");
		try {
			amigos = entityManager
					.createNativeQuery(query.toString(), AmigosDTO.class)
					.setParameter(Amigos.OWNER_ID, idOwner)
					.setParameter("param", param.toUpperCase() + "%")
					.getResultList();
		} catch (Exception e) {
			throw new PersistenciaException("Error al buscar", e);
		}

		return amigos;
	}

	public Amigos guardarEstadoAmigo(Amigos amigo) throws PersistenciaException {

		return entityManager.merge(amigo);

	}

	public Amigos getAmigosByPk(Integer idAmigo, Integer idOwner)
			throws PersistenciaException {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("Select a from Amigos a ");
		queryStr.append(" where a.usuarioOwner.id = :idOwner");
		queryStr.append(" and a.usuarioAmigo.id = :idAmigo");
		try {
			return entityManager.createQuery(queryStr.toString(), Amigos.class)
					.setParameter("idOwner", idOwner)
					.setParameter("idAmigo", idAmigo).getSingleResult();
		} catch (NoResultException e) {
			throw new PersistenciaException("Sin resultados", e);
		}

	}

	public List<AmigosDTO> getAmigosEnCarrera(int idOwner, int idCarrera)
			throws PersistenciaException {
		StringBuffer query = new StringBuffer();
		query.append(" select ");
		query.append(" :" + Amigos.OWNER_ID + " as idOwner, ");
		query.append("       u.id as idAmigo, ");
		query.append("       u.nick, ");
		query.append("       u.email, ");
		query.append("       u.foto_perfil as urlFoto, ");
		query.append("       COALESCE(g.nombre,'Ninguno') as grupo, ");
		query.append("       coalesce(a.es_amigo,false) as esAmigo, ");
		query.append("       coalesce(a.es_bloqueado,false) as esBloqueado, ");
		query.append("       coalesce(a.es_pendiente,false) as esPendiente ");
		query.append(" from usuario_carrera uc ");
		query.append(" join amigos_usuario a ");
		query.append("	on a.usuario_amigo = uc.usuario_id ");
		query.append("	and a.es_bloqueado = false ");
		query.append("	and a.es_amigo = true ");
		query.append("	and a.usuario_owner = :" + Amigos.OWNER_ID);
		query.append(" join usuario_app u ");
		query.append("	on a.usuario_amigo = u.id ");
		query.append("  left join grupos_running g  ");
		query.append("  on u.grupo_id = g.id	");
		query.append(" where uc.anotado = true and uc.carrera_id = :"
				+ UsuarioCarrera.PARAM_ID_CARRERA);
		try {

			List<AmigosDTO> amigos = entityManager
					.createNativeQuery(query.toString(), AmigosDTO.class)
					.setParameter(Amigos.OWNER_ID, idOwner)
					.setParameter(UsuarioCarrera.PARAM_ID_CARRERA, idCarrera)
					.getResultList();
			return amigos;
		} catch (Exception e) {
			throw new PersistenciaException("Error al buscar", e);
		}
	
	}
	public Amigos getAmigosByPkSocialId(String socialId, Integer idOwner)
			throws PersistenciaException {
		StringBuffer queryStr = new StringBuffer();
		queryStr.append("Select a from Amigos a ");
		queryStr.append(" where a.usuarioOwner.id = :idOwner");
		queryStr.append(" and a.usuarioAmigo.socialId = :" + Usuario.PARAM_SOCIALID);
		try {
			return entityManager.createQuery(queryStr.toString(), Amigos.class)
					.setParameter("idOwner", idOwner)
					.setParameter(Usuario.PARAM_SOCIALID, socialId).getSingleResult();
		} catch (NoResultException e) {
			throw new PersistenciaException("Sin resultados", e);
		}

	}

}
