package com.thinkup.ranning.dao;

public class QueryParam {
	private String nombre;
	private Object valor;

	public QueryParam(String nombre, Object valor) {
		this.nombre = nombre;
		this.valor = valor;
	}

	public String getNombre() {
		return nombre;
	}

	public Object getValor() {
		return valor;
	}

}
