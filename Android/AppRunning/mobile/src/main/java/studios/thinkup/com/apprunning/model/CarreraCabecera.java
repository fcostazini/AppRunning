package studios.thinkup.com.apprunning.model;

import android.content.Intent;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fcostazini on 22/05/2015.
 * Cabecera de de carrera
 */
public class CarreraCabecera implements Serializable{

    private Integer codigoCarrera;
    private String nombre;
    private Date fechaInicio;
    private String distancia;
    private String descripcion;
    private String urlImage;
    private EstadoCarrera estadoCarrera;

    public CarreraCabecera(Integer codigo, String nombre, Date fechaInicio, String distancia, String descripcion, String urlImage, EstadoCarrera estado) {
        this. codigoCarrera = codigo;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.distancia = distancia;
        this.descripcion = descripcion;
        this.urlImage = urlImage;
        this.estadoCarrera = estado;
    }

    public CarreraCabecera(Integer codigo,String nombre, Date fechaInicio, String distancia, String descripcion, String urlImage) {
        this. codigoCarrera = codigo;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.distancia = distancia;
        this.descripcion = descripcion;
        this.urlImage = urlImage;
        this.estadoCarrera = null;
    }

    public EstadoCarrera getEstadoCarrera() {
        return estadoCarrera;
    }

    public void setEstadoCarrera(EstadoCarrera estadoCarrera) {
        this.estadoCarrera = estadoCarrera;
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

    public Integer getCodigoCarrera(){return this.codigoCarrera;}
}
