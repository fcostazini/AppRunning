package com.thinkup.ranning.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "amigos_usuario")
public class Amigos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4057295711928628388L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "usuario_owner")
	private Integer usuarioOwner;
	
	@Column(name = "usuario_amigo")
	private Integer usuarioAmigo;
	
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

	public Integer getUsuarioOwner() {
		return usuarioOwner;
	}

	public void setUsuarioOwner(Integer usuarioOwner) {
		this.usuarioOwner = usuarioOwner;
	}

	public Integer getUsuarioAmigo() {
		return usuarioAmigo;
	}

	public void setUsuarioAmigo(Integer usuarioAmigo) {
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
