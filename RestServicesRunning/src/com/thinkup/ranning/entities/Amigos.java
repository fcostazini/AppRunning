package com.thinkup.ranning.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Parameter;
import javax.persistence.Table;

@Entity
@Table(name = "amigos_usuario")
@NamedQueries(value = {
			@NamedQuery(name = Amigos.GET_BY_OWNER, 
query = "Select a FROM Amigos a WHERE a.esBloqueado = false  AND a.esAmigo = true"
		+ " AND a.usuarioOwner.id = :"
				+ Amigos.OWNER_ID) })
public class Amigos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4057295711928628388L;
	public static final String GET_BY_OWNER = "getByOwner";
	public static final String OWNER_ID = "usuario_owner";
	public static final String EMAIL_AMIGO = "amigoEmail";
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="usuario_owner", referencedColumnName ="id")
	private Usuario usuarioOwner;
	
	@ManyToOne
	@JoinColumn(name="usuario_amigo", referencedColumnName="id")
	private Usuario usuarioAmigo;
	
	
	@Column(name = "es_amigo")
	private Boolean esAmigo;
	
	@Column(name = "es_bloqueado")
	private Boolean esBloqueado;
	
	@Column(name = "es_pendiente")
	private Boolean esPendiente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	

	public Usuario getUsuarioOwner() {
		return usuarioOwner;
	}

	public void setUsuarioOwner(Usuario usuarioOwner) {
		this.usuarioOwner = usuarioOwner;
	}

	public Usuario getUsuarioAmigo() {
		return usuarioAmigo;
	}

	public void setUsuarioAmigo(Usuario usuarioAmigo) {
		this.usuarioAmigo = usuarioAmigo;
	}

	public Boolean getEsAmigo() {
		return esAmigo;
	}

	public void setEsAmigo(Boolean esAmigo) {
		this.esAmigo = esAmigo;
	}

	public Boolean getEsBloqueado() {
		return esBloqueado;
	}

	public void setEsBloqueado(Boolean esBloqueado) {
		this.esBloqueado = esBloqueado;
	}

	public Boolean getEsPendiente() {
		return esPendiente;
	}

	public void setEsPendiente(Boolean esPendiente) {
		this.esPendiente = esPendiente;
	}

}
