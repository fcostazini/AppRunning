package com.thinkup.ranning.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que representa la tabla: usuario_app
 *
 */
@Entity
@Table(name = "public.usuario_app")
@NamedQueries(value = {
		@NamedQuery(name = Usuario.QUERY_ALL, query = "Select u FROM Usuario u"),
		@NamedQuery(name = Usuario.QUERY_BY_NOMBRE, query = "Select u FROM Usuario u WHERE u.nombre = :"
				+ Usuario.PARAM_NOMBRE),
		@NamedQuery(name = Usuario.QUERY_BY_EMAIL, query = "Select u FROM Usuario u WHERE u.email = :"
				+ Usuario.PARAM_EMAIL),
		@NamedQuery(name = Usuario.QUERY_USUARIO_BY_ID, query = "Select u FROM Usuario u WHERE u.id = :"
				+ Usuario.PARAM_USUARIO_ID) })
@XmlRootElement
public class Usuario implements Serializable {

	// Parametros
	public static final String PARAM_USUARIO_ID = "usuario_ID";
	public static final String PARAM_NOMBRE = "nombre";
	public static final String PARAM_EMAIL = "email";

	// Named Queries
	public static final String QUERY_ALL = "getUsuariosById";
	public static final String QUERY_USUARIO_BY_ID = "getAllUsuarios";
	public static final String QUERY_BY_NOMBRE = "getUsuarioByNombre";
	public static final String QUERY_BY_EMAIL = "getUsuarioByEmail";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "email")
	private String email;

	@Column(name = "tipo_cuenta")
	private String tipoCuenta;

	@Column(name = "password")
	private String password;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido")
	private String apellido;

	@Column(name = "fecha_nacimiento")
	private Date fechaNacimiento;

	@Column(name = "foto_perfil")
	private String fotoPerfil;

	@Column(name = "nick")
	private String nick;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grupo_id", nullable = true)
	private GruposRunning grupo;

	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<UsuarioCarrera> usuarioCarrera;

	public Integer getId() {
		return id;
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

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getFotoPerfil() {
		return fotoPerfil;
	}

	public void setFotoPerfil(String fotoPerfil) {
		this.fotoPerfil = fotoPerfil;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public GruposRunning getGrupo() {
		return grupo;
	}

	public void setGrupo(GruposRunning grupo) {
		this.grupo = grupo;
	}

	public List<UsuarioCarrera> getUsuarioCarrera() {
		return usuarioCarrera;
	}

	public void setUsuarioCarrera(List<UsuarioCarrera> usuarioCarrera) {
		this.usuarioCarrera = usuarioCarrera;
	}

	public Usuario() {
		super();
	}
}
