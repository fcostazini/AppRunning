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
	private String socialId;
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
	public String getSocialId() {
		return socialId;
	}
	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}

	
	
}
