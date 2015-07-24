package com.thinkup.ranning.dtos;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Filtro {
	
	private String nombreCarrera;

	public String getNombreCarrera() {
		return nombreCarrera;
	}

	public void setNombreCarrera(String nombreCarrera) {
		this.nombreCarrera = nombreCarrera;
	}
	
	

}
