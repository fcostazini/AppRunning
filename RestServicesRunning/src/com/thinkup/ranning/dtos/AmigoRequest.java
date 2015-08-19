package com.thinkup.ranning.dtos;

import java.io.Serializable;

public class AmigoRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -99882798975628835L;
	private Integer idOwner;
	private Integer idAmigo;
	private TipoRequestEnum tipoRequest;
	public Integer getIdOwner() {
		return idOwner;
	}
	public void setIdOwner(Integer idOwner) {
		this.idOwner = idOwner;
	}
	public Integer getIdAmigo() {
		return idAmigo;
	}
	public void setIdAmigo(Integer idAmigo) {
		this.idAmigo = idAmigo;
	}
	public TipoRequestEnum getTipoRequest() {
		return tipoRequest;
	}
	public void setTipoRequest(TipoRequestEnum tipoRequest) {
		this.tipoRequest = tipoRequest;
	}

	
	
}
