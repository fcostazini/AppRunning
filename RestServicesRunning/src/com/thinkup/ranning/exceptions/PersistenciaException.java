package com.thinkup.ranning.exceptions;

public class PersistenciaException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersistenciaException(String mensaje, Exception e) {
		super(mensaje, e);
	}

}
