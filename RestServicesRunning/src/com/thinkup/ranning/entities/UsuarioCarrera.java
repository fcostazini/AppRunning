package com.thinkup.ranning.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que representa la tabla: usuario_app
 *
 */
@Entity
@Table(name = "public.usuario_carrera")
@NamedQueries(value = {
		@NamedQuery(name = UsuarioCarrera.GET_BY_ID_CARRERA, query = "Select u FROM UsuarioCarrera u "
				+ "WHERE u.carrera.id = :" + UsuarioCarrera.PARAM_ID_CARRERA),
		@NamedQuery(name = UsuarioCarrera.GET_ALL, query = "Select u FROM UsuarioCarrera u "),
		@NamedQuery(name = UsuarioCarrera.GET_BY_ID, query = "Select u FROM UsuarioCarrera u WHERE u.id = :"
				+ UsuarioCarrera.PARAM_ID) })
@XmlRootElement
public class UsuarioCarrera implements Serializable {

	public static final String GET_BY_ID = "getById";
	public static final String PARAM_ID = "id";
	private static final long serialVersionUID = 1L;
	public static final String GET_ALL = "getAll";
	public static final String GET_BY_ID_CARRERA = "getByIdCarrera";
	public static final String PARAM_ID_CARRERA = "idCarrera";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "tiempo")
	private long tiempo;
	
	@Column(name = "anotado")
	private Boolean isAnotado;
	
	@Column(name = "me_gusta")
	private Boolean meGusta;
	
	@Column(name = "corrida")
	private Boolean corrida;
	
	@Column(name = "distancia")
	private Integer distancia;
	
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
		this.tiempo = 0l;
	}

	public Integer getId() {
		return id;
		
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getTiempo() {
		return tiempo;
	}

	public void setTiempo(Long tiempo) {
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

	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
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
