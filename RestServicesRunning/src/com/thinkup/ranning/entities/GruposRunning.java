package com.thinkup.ranning.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * Entidad que representa la tabla: grupos_running
 *
 */
@Entity
@Table(name = "public.grupos_running")
@NamedQueries(value = {
		@NamedQuery(name = GruposRunning.QUERY_ALL, query = "Select g FROM GruposRunning g"),
		@NamedQuery(name = GruposRunning.QUERY_BY_NOMBRE, query = "Select g FROM GruposRunning g WHERE g.nombre = :"
				+ GruposRunning.PARAM_NOMBRE),
	    @NamedQuery(name = GruposRunning.QUERY_BY_NOMBRE_LIKE, query = "Select g FROM GruposRunning g "
	    		+ " WHERE upper(g.nombre) LIKE :"
						+ GruposRunning.PARAM_NOMBRE ),
		@NamedQuery(name = GruposRunning.QUERY_BY_ID, query = "Select g FROM GruposRunning g WHERE g.id = :"
				+ GruposRunning.PARAM_ID) })
@XmlRootElement
public class GruposRunning implements Serializable {

	// Parametros
	public static final String PARAM_NOMBRE = "nombre";
	public static final String PARAM_ID = "id";

	// Named Queries
	public static final String QUERY_ALL = "getAllGrupos";
	public static final String QUERY_BY_NOMBRE = "getGrupoByNombre";
	public static final String QUERY_BY_NOMBRE_LIKE = "getGrupoByNombreLike";
	public static final String QUERY_BY_ID = "getGrupoByID";

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "nombre")
	private String nombre;
	
//	@OneToMany(mappedBy="grupo", fetch = FetchType.LAZY)
//	@JsonIgnore
//	private List<Usuario> usuarios;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

//	@JsonIgnore
//	public List<Usuario> getUsuarios() {
//		return usuarios;
//	}
//
//	public void setUsuarios(List<Usuario> usuarios) {
//		this.usuarios = usuarios;
//	}
	
	public GruposRunning() {
		super();
	}
	
}
