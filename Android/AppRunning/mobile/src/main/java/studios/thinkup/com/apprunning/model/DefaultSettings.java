package studios.thinkup.com.apprunning.model;

import java.io.Serializable;

/**
 * Created by FaQ on 23/05/2015.
 * <p/>
 * Filtros preconfigurados
 */
public class DefaultSettings implements Serializable {

    private Integer distanciaMin = 0;
    private Integer distanciaMax = 100;
    private Genero genero = Genero.TODOS;
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

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }
}
