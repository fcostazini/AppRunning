package studios.thinkup.com.apprunning.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fcostazini on 22/05/2015.
 * Entidad de Modelo CARRERA
 */
public class Carrera implements Serializable{

    public static final String NOMBRE_FIELD = "nombre";
    public static final String ZONA_FIELD = "zona";
    public static final String GENERO_FIELD = "genero";
    public static final String DISTANCIA_FIELD = "distancia";
    public static final String FECHA_LARGADA_FIELD = "fecha_largada";
    public static final String ID = "ID";

    private Integer codigoCarrera;
    private String nombre;
    private Date fechaInicio;
    private String distancia;
    private String descripcion;
    private String urlImage;
    private EstadoCarrera estadoCarrera;


    public Carrera(Integer codigoCarrera, String nombre, Date fechaInicio, String distancia, String descripcion, String urlImage, EstadoCarrera estadoCarrera) {
        this.codigoCarrera = codigoCarrera;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.distancia = distancia;
        this.descripcion = descripcion;
        this.urlImage = urlImage;
        this.estadoCarrera = estadoCarrera;
    }

    public Integer getCodigoCarrera() {
        return codigoCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public EstadoCarrera getEstadoCarrera() {
        return estadoCarrera;
    }

    public void setEstadoCarrera(EstadoCarrera estadoCarrera) {
        this.estadoCarrera = estadoCarrera;
    }
}
