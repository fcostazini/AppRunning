package com.thinkup.ranning.entities;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad que representa la tabla: carrera
 *
 */
@Entity
@Table(name = "public.carrera")
@NamedQueries(value = {
		@NamedQuery(name = Carrera.QUERY_ALL, query = "Select c FROM Carrera c"),
		@NamedQuery(name = Carrera.QUERY_BY_NOMBRE, query = "Select c FROM Carrera c WHERE c.nombre = :"
				+ Carrera.PARAM_NOMBRE),
		@NamedQuery(name = Carrera.QUERY_BY_NRO_CARRERA, query = "Select c FROM Carrera c WHERE c.id = :"
				+ Carrera.PARAM_NRO_CARRERA) })
@XmlRootElement
public class Carrera implements Serializable {

	
	// Parametros
	public static final String PARAM_NOMBRE = "nombre";
	public static final String PARAM_NRO_CARRERA = "nroCarrera";

	// Named Queries
	public static final String QUERY_ALL = "getALL";
	public static final String QUERY_BY_NOMBRE = "getCarreraByNombre";
	public static final String QUERY_BY_NRO_CARRERA = "getCarreraBy";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "modalidades")
	private String modalidades;

	@Column(name = "provincia")
	private String provincia;

	@Column(name = "ciudad")
	private String ciudad;

	@Column(name = "direccion")
	private String direccion;

	@Column(name = "fecha_inicio")
	private Date fechaInicio;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "url_web")
	private String urlWeb;

	@Column(name = "recomendada")
	private Boolean recomendada;

	@Column(name = "url_imagen")
	private String urlImagen;

	@Column(name = "distancia_disponible")
	private String distanciaDisponible;
	
	@OneToMany(mappedBy="carrera", fetch = FetchType.EAGER)
	private List<UsuarioCarrera> usuarioCarrera;

	private static final long serialVersionUID = 1L;

	public Carrera() {
		super();
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getModalidades() {
		return this.modalidades;
	}

	public void setModalidades(String modalidades) {
		this.modalidades = modalidades;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUrlWeb() {
		return this.urlWeb;
	}

	public void setUrlWeb(String urlWeb) {
		this.urlWeb = urlWeb;
	}

	public Boolean getRecomendada() {
		return this.recomendada;
	}

	public void setRecomendada(Boolean recomendada) {
		this.recomendada = recomendada;
	}

	public String getUrlImagen() {
		return this.urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public String getDistanciaDisponible() {
		return this.distanciaDisponible;
	}

	public void setDistanciaDisponible(String distanciaDisponible) {
		this.distanciaDisponible = distanciaDisponible;
	}

	public List<UsuarioCarrera> getUsuarioCarrera() {
		return usuarioCarrera;
	}

	public void setUsuarioCarrera(List<UsuarioCarrera> usuarioCarrera) {
		this.usuarioCarrera = usuarioCarrera;
	}
	
}
