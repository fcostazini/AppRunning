package studios.thinkup.com.apprunning.model;

import java.io.Serializable;

import studios.thinkup.com.apprunning.model.entity.Modalidad;

/**
 * Created by FaQ on 23/05/2015.
 * <p/>
 * Filtros preconfigurados
 */
public class DefaultSettings implements Serializable {

    private Integer distanciaMin = 0;
    private Integer distanciaMax = 100;
    private Modalidad modalidad = Modalidad.TODOS;
    private String zona = "Buenos Aires";
    private Integer diasBusqueda = 15;


    public Integer getDiasBusqueda() {
        return diasBusqueda;
    }

    public void setDiasBusqueda(Integer diasBusqueda) {
        this.diasBusqueda = diasBusqueda;
    }

    public Integer getDistanciaMin() {
        return distanciaMin;
    }

    public void setDistanciaMin(Integer distanciaMin) {
        this.distanciaMin = distanciaMin;
    }

    public Integer getDistanciaMax() {
        return distanciaMax;
    }

    public void setDistanciaMax(Integer distanciaMax) {
        this.distanciaMax = distanciaMax;
    }

    public Modalidad getModalidad() {
        return modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}
