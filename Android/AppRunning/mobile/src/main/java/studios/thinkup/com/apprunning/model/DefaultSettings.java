package studios.thinkup.com.apprunning.model;

import java.io.Serializable;

/**
 * Created by FaQ on 23/05/2015.
 * <p/>
 * Filtros preconfigurados
 */
public class DefaultSettings implements Serializable {
    private String zona;
    private Genero genero = Genero.TODOS;
    private Integer maxDias = 365;
    private Integer maxDistancia = 1000;

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Integer getMaxDias() {
        return maxDias;
    }

    public void setMaxDias(Integer maxDias) {
        this.maxDias = maxDias;
    }

    public Integer getMaxDistancia() {
        return maxDistancia;
    }

    public void setMaxDistancia(Integer maxDistancia) {
        this.maxDistancia = maxDistancia;
    }
}
