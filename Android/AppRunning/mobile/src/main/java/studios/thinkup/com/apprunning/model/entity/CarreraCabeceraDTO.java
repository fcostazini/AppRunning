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
     * Corresponde a la hora de la carrera. Lo tenemos que obtener de la fecha.
     */
    private String hora;

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

    //Datos de usuario carrera
    /**
     * ID del usuario carrera
     */
    private Integer usuarioCarrera;

    /**
     * Distancia corrida por el usuario.
     */
    private String distancia;

    /**
     * Representa al campo de meGusta del usuario_carrera.
     */
    private Boolean meGusta;

    /**
     * Representa el campo anotado de la tabla usuario_carrera.
     */
    private Boolean estoyAnotado;

    /**
     * Representa el campo corrida de la tabla usuario_carrera.
     */
    private Boolean corrida;


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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
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

    public Integer getUsuarioCarrera() {
        return usuarioCarrera;
    }

    public void setUsuarioCarrera(Integer usuarioCarrera) {
        this.usuarioCarrera = usuarioCarrera;
    }

    public String getDistancia() {
        return distancia;
    }

    public void setDistancia(String distancia) {
        this.distancia = distancia;
    }

    public Boolean getMeGusta() {
        return meGusta;
    }

    public void setMeGusta(Boolean meGusta) {
        this.meGusta = meGusta;
    }

    public Boolean getEstoyAnotado() {
        return estoyAnotado;
    }

    public void setEstoyAnotado(Boolean estoyAnotado) {
        this.estoyAnotado = estoyAnotado;
    }

    public Boolean getCorrida() {
        return corrida;
    }

    public void setCorrida(Boolean corrida) {
        this.corrida = corrida;
    }

}
