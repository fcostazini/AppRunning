package com.thinkup.ranning.dtos;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import javax.xml.bind.annotation.XmlRootElement;

import com.thinkup.ranning.entities.Usuario;

@XmlRootElement
public class UsuarioDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String FORMAT_DATE = "dd/MM/yyyy";

	private Integer id;

	private String email;

	private String tipoCuenta;

	private String password;

	private String nombre;

	private String apellido;

	private boolean verificado;
	
	private String fechaNacimiento;

	private String fotoPerfilUrl;

	private String nick;

	private String grupoId;

	public Integer getId() {
		return id;
	}

	public boolean getVerificado() {
		return verificado;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public void setTipoCuenta(String tipoCuenta) {
		this.tipoCuenta = tipoCuenta;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getFotoPerfil() {
		return fotoPerfilUrl;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfilUrl = fotoPerfil;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getGrupoId() {
		return grupoId;
	}

	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(Usuario usuario) {
		this.apellido = usuario.getApellido();
		this.email = usuario.getEmail();
		SimpleDateFormat format = new SimpleDateFormat(UsuarioDTO.FORMAT_DATE);
		String dateToStr = format.format(usuario.getFechaNacimiento());
		this.fechaNacimiento = dateToStr;
		this.fotoPerfilUrl = usuario.getFotoPerfil();
		this.grupoId = usuario.getGrupo() != null ? usuario.getGrupo()
				.getId().toString() : "";
		this.id = usuario.getId();
		this.nick = usuario.getNick();
		this.nombre = usuario.getNombre();
		this.password = usuario.getPassword();
		this.tipoCuenta = usuario.getTipoCuenta();
		this.verificado = usuario.getVerificado();
	}
}
