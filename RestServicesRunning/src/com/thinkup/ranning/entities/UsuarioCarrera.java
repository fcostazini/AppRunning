package com.thinkup.ranning.entities;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que representa la tabla: usuario_app
 *
 */
@Entity
@Table(name = "public.usuario_carrera")
@XmlRootElement
public class UsuarioCarrera implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "tiempo")
	private String tiempo;
	
	@Column(name = "anotado")
	private Boolean isAnotado;
	
	@Column(name = "me_gusta")
	private Boolean meGusta;
	
	@Column(name = "corrida")
	private Boolean corrida;
	
	@Column(name = "distancia")
	private String distancia;
	
	@Column(name = "modalidad")
	private String modalidad;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "carrera_id", nullable = true)
	private Carrera carrera;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id", nullable = true)
	private Usuario usuario;
	
	public UsuarioCarrera() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTiempo() {
		return tiempo;
	}

	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}

	public Boolean getIsAnotado() {
		return isAnotado;
	}

	public void setIsAnotado(Boolean isAnotado) {
		this.isAnotado = isAnotado;
	}

	public Boolean getMeGusta() {
		return meGusta;
	}

	public void setMeGusta(Boolean meGusta) {
		this.meGusta = meGusta;
	}

	public Boolean getCorrida() {
		return corrida;
	}

	public void setCorrida(Boolean corrida) {
		this.corrida = corrida;
	}

	public String getDistancia() {
		return distancia;
	}

	public void setDistancia(String distancia) {
		this.distancia = distancia;
	}

	public String getModalidad() {
		return modalidad;
	}

	public void setModalidad(String modalidad) {
		this.modalidad = modalidad;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}
}
