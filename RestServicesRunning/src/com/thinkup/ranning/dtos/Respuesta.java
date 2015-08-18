package com.thinkup.ranning.dtos;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;


public class Respuesta <T> implements Serializable {
	
	public static final int CODIGO_OK = 200;
	public static final int CODIGO_CREACION_MODIFICACION_OK = 201;
	public static final int CODIGO_SIN_RESULTADOS = 204;
	public static final int CODIGO_SOLICITUD_INCORRECTA = 400;
	public static final int CODIGO_NO_ENCONTRADO = 404;
	public static final int CODIGO_ERROR_INTERNO = 500;
	public static final int CODIGO_ENTIDAD_EXISTENTE = 205;
    public static final int USUARIO_BLOQUEADO = 301;
    public static final int USUARIO_INVALIDO = 302;
    public static final int CREDENCIALES_ERRONEAS = 303;
	
	/**
	 *
	 */
	private static final long serialVersionUID = 395965213708977578L;


	// Es un codigo de respuesta correspondiente al mensaje en si (ej: 404, no
	// encontrado)
	private Integer codigoRespuesta;

	// Mensajes de error
	private List<String> mensajes;
	
	private T dto;

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

	public T getDto() {
		return dto;
	}

	public void setDto(T dto) {
		this.dto = dto;
	}
	
	public Respuesta() {
		this.mensajes = new Vector<>();
	}

}
