package com.thinkup.ranning.dtos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.thinkup.ranning.entities.Amigos;
@Entity
public class AmigosDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7297125409717679879L;
	@Id
	private Integer idOwner;
	@Id
	private Integer idAmigo;
	@Id
	private String nick;
	@Id
	private String grupo;
	@Id
	private String urlFoto;
	@Id
	private Boolean esAmigo;
	@Id
	private Boolean esPendiente;
	@Id
	private Boolean esBloqueado;
	@Id
	private String email;
	@Id
	private String socialId;
	
	public AmigosDTO(){
		
	}
	
	public AmigosDTO(Amigos a){
		this.idOwner = a.getUsuarioOwner().getId();
		this.idAmigo = a.getUsuarioAmigo().getId();
		this.esAmigo = a.getEsAmigo();
		this.esBloqueado = a.getEsBloqueado();
		this.esPendiente = a.getEsPendiente();
		this.urlFoto = a.getUsuarioAmigo().getFotoPerfil();
		this.nick = a.getUsuarioAmigo().getNick();
		this.email =a.getUsuarioAmigo().getEmail();
		this.socialId = a.getUsuarioAmigo().getSocialId();
		if(a.getUsuarioAmigo().getGrupo()!=null){
			this.grupo = a.getUsuarioAmigo().getGrupo().getNombre();	
		}else{
			this.grupo = "Ninguno";
		}
		
	}
	
	public String getUrlFoto() {
		return urlFoto;
	}

	public void setUrlFoto(String urlFoto) {
		this.urlFoto = urlFoto;
	}

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
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getGrupo() {
		return grupo;
	}
	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}
	public Boolean getEsAmigo() {
		return esAmigo;
	}
	public void setEsAmigo(Boolean esAmigo) {
		this.esAmigo = esAmigo;
	}
	public Boolean getEsPendiente() {
		return esPendiente;
	}
	public void setEsPendiente(Boolean esPendiente) {
		this.esPendiente = esPendiente;
	}
	public Boolean getEsBloqueado() {
		return esBloqueado;
	}
	public void setEsBloqueado(Boolean esBloqueado) {
		this.esBloqueado = esBloqueado;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSocialId() {
		return socialId;
	}

	public void setSocialId(String socialId) {
		this.socialId = socialId;
	}
	
	
	
}
