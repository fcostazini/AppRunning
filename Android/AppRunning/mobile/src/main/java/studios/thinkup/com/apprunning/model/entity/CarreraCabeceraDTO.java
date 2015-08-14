package studios.thinkup.com.apprunning.model.entity;

import java.io.Serializable;

public class CarreraCabeceraDTO implements Serializable {


    /**
     *
     */
    private static final long serialVersionUID = 1L;

    //Datos de carrera
    /**
     * Corresponde al id de la carrera.
     */

    private Integer codigoCarrera;

    /**
     * Corresponde al nombre de la carrera.
     */
    private String nombre;

    /**
     * Corresponde a la fecha de la carrera.
     */
    private String fechaInicio;

    /**
     * Corresponde a la horaInicio de la carrera. Lo tenemos que obtener de la fecha.
     */
    private String horaInicio;

    /**
     * Corresponde a las distancias disponibles de la carrera.
     */
    private String distanciaDisponible;

    /**
     * Corresponde a la descripción de la carrera.
     */
    private String descripcion;

    /**
     * Corresponde a la url de la imagen de la carrera.
     */
    private String urlImagen;

    /**
     * Corresponde a la provincia donde se corre la carrera.
     */
    private String provincia;

    /**
     * Corresponde a la ciudad en donde se corre la carrera.
     */
    private String ciudad;

    public CarreraCabeceraDTO() {
        super();
    }

    public Integer getCodigoCarrera() {
        return codigoCarrera;
    }

    public void setCodigoCarrera(Integer codigoCarrera) {
        this.codigoCarrera = codigoCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getDistanciaDisponible() {
        return distanciaDisponible;
    }

    public void setDistanciaDisponible(String distanciaDisponible) {
        this.distanciaDisponible = distanciaDisponible;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public void setUrlImagen(String urlImagen) {
        this.urlImagen = urlImagen;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }


}
