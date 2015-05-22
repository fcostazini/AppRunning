package studios.thinkup.com.apprunning.model;

import java.io.Serializable;

/**
 * Created by fcostazini on 22/05/2015.
 */
public class Filtro implements Serializable {

    private Subcategoria subcategoria;

    public Subcategoria getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = subcategoria;
    }

    public Subcategoria nextCategoria() {
        switch (this.getSubcategoria()){
            case ZONA:
                return Subcategoria.DISTANCIA;
            case DISTANCIA:
                return Subcategoria.GENERO;
            case GENERO:
                return Subcategoria.ZONA;
            default:
                return Subcategoria.ZONA;
        }


    }
}
