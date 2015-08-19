/**
 * 
 */
package com.thinkup.ranning.dtos;

/**
 * @author Facundo Tipo de cuenta del usuario
 */
public enum TipoCuenta {
	FACEBOOK("F"), GMAIL("G"), PROPIA("P");
	private String tipo;

	private TipoCuenta(String tipo) {
		this.tipo = tipo;
	}

	public String getTipo() {
		return tipo;
	}

	public static TipoCuenta getByTipo(String tipo) {
		for (TipoCuenta e : values()) {
			if (e.tipo.equals(tipo)) {
				return e;
			}
		}
		return null;
	}
}
