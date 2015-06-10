package studios.thinkup.com.apprunning.model.entity;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fcostazini on 22/05/2015.
 * Entidad de Modelo CARRERA
 */
public class Carrera extends SugarRecord<Carrera> implements Serializable {

    public static final String ID = "ID";
    public static final String NOMBRE_FIELD = "NOMBRE";
    public static final String ZONA_FIELD = "ZONA";
    public static final String GENERO_FIELD = "GENERO";
    public static final String FECHA_LARGADA_FIELD = "FECHA_INICIO";
    public static final String DISTANCIA_FIELD = "DISTANCIA";

    private Integer codigo;
    private String nombre;
    private Date fechaInicio;
    private Integer distancia;
    private String descripcion;
    private String urlImagen;
    private Genero genero;
    private String direccion;
    private String zona;
    private String urlWeb;

    public Carrera(){

    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public Genero getGenero() {
        return genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getZona() {
        return zona;
    }

    public String getUrlWeb() {
        return urlWeb;
    }
}