package com.thinkup.ranning.dtos;

public enum TipoRequestEnum {
	SOLICITUD_AMIGO(0), ACEPTAR_AMIGO(1), RECHAZAR_AMIGO(2), 
	QUITA_AMIGO(3), BLOQUEAR_AMIGO(4), DESBLOQUEAR_AMIGO(5);
	private Integer id;

	public Integer getId() {
		return id;
	}

	private TipoRequestEnum(Integer id) {
		this.id = id;
	}

	public static TipoRequestEnum getById(int idTipo) {
		for (TipoRequestEnum e : values()) {
			if(e.id == idTipo){
				return e;
			}
		}
		return null;
	}

}
