package studios.thinkup.com.apprunning.model.entity;

import java.io.Serializable;

public class AmigosDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7297125409717679879L;
	public static final String FIELD_ID = "amigo";
	private Integer idOwner;
	private Integer idAmigo;
	private String nick;
	private String grupo;
	private String urlFoto;
	private Boolean esAmigo = false;
	private Boolean esPendiente = false;
	private Boolean esBloqueado = false;
	
	public AmigosDTO(){
		
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
	
	
	
}
