package com.thinkup.ranning.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;


public class Respuesta implements Serializable {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 395965213708977578L;

	// Es un codigo de respuesta correspondiente al mensaje en si (ej: 404, no
	// encontrado)
	private Integer codigoRespuesta;

	// Mensajes de error
	private List<String> mensajes = new Vector();

	public Integer getCodigoRespuesta() {
		return codigoRespuesta;
	}

	public void setCodigoRespuesta(Integer codigoRespuesta) {
		this.codigoRespuesta = codigoRespuesta;
	}

	public List<String> getMensajes() {
		return mensajes;
	}

	
	public void addMensaje(final String mensaje) {
		this.mensajes.add(mensaje);
	}
	

}
