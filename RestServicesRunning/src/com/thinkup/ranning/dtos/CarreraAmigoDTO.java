package com.thinkup.ranning.dtos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CarreraAmigoDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2155324398094355901L;

	@Id
	private Integer idCarrera;
	@Id
	private String nombreCarrera;
	@Id
	private String logoCarrera;
	@Id
	private boolean corrida;
	@Id
	private Integer distancia;
	@Id
	private String modalidad;
	@Id
	private boolean meGusta;
	@Id
	private boolean anotado;
	@Id
	private Integer usuario;
	@Id
	private Long tiempo;

	public Integer getIdCarrera() {
		return idCarrera;
	}

	public void setIdCarrera(Integer idCarrera) {
		this.idCarrera = idCarrera;
	}

	public String getNombreCarrera() {
		return nombreCarrera;
	}

	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}

	public String getLogoCarrera() {
		return logoCarrera;
	}

	public void setLogoCarrera(String logoCarrera) {
		this.logoCarrera = logoCarrera;
	}

	public boolean isCorrida() {
		return corrida;
	}

	public void setCorrida(boolean corrida) {
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

	public boolean isMeGusta() {
		return meGusta;
	}

	public void setMeGusta(boolean meGusta) {
		this.meGusta = meGusta;
	}

	public boolean isAnotado() {
		return anotado;
	}

	public void setAnotado(boolean anotado) {
		this.anotado = anotado;
	}

	public Integer getUsuario() {
		return usuario;
	}

	public void setUsuario(Integer usuario) {
		this.usuario = usuario;
	}

	public Long getTiempo() {
		return tiempo;
	}

	public void setTiempo(Long tiempo) {
		this.tiempo = tiempo;
	}

}
