package com.thinkup.ranning.dtos;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ProvinciaCiudadDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2233710546897096340L;
	@Id
	private String provincia;
	@Id
	private String ciudad;

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

}
