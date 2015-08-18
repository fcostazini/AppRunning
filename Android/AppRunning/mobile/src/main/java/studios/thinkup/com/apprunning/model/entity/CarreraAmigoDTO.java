package studios.thinkup.com.apprunning.model.entity;

import java.io.Serializable;

public class CarreraAmigoDTO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -2155324398094355901L;


    private Integer idCarrera;

    private String nombreCarrera;

    private String logoCarrera;

    private boolean corrida;

    private Integer distancia;

    private String modalidad;

    private boolean meGusta;

    private boolean anotado;

    private Integer usuario;

    private Long tiempo;

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getLogoCarrera() {
        return logoCarrera;
    }

    public void setLogoCarrera(String logoCarrera) {
        this.logoCarrera = logoCarrera;
    }

    public boolean isCorrida() {
        return corrida;
    }

    public void setCorrida(boolean corrida) {
        this.corrida = corrida;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public boolean isMeGusta() {
        return meGusta;
    }

    public void setMeGusta(boolean meGusta) {
        this.meGusta = meGusta;
    }

    public boolean isAnotado() {
        return anotado;
    }

    public void setAnotado(boolean anotado) {
        this.anotado = anotado;
    }

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Long getTiempo() {
        return tiempo;
    }

    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }

}
