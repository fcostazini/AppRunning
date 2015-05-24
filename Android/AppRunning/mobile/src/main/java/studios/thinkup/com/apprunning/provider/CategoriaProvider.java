package studios.thinkup.com.apprunning.provider;

import android.util.Log;

import java.util.List;

import studios.thinkup.com.apprunning.model.Categoria;
import studios.thinkup.com.apprunning.model.Filtro;

/**
 * Created by fcostazini on 22/05/2015.
 * Provider de Categoria
 */
public class CategoriaProvider {
    private DummyCategoriaProvider dummyProvider;

    public CategoriaProvider() {
        dummyProvider = new DummyCategoriaProvider();

    }

    public List<Categoria> getCategorias(Filtro filtro) {
        Log.println(0,Filtro.class.getSimpleName(),filtro.toString());
        if(filtro.getSubcategoria()!= null) {
            switch (filtro.getSubcategoria()) {
                case ZONA:
                    return this.dummyProvider.getAllZona();

                case DISTANCIA:
                    return this.dummyProvider.getAllDistancia();

                case GENERO:
                    return this.dummyProvider.getAllGenero();
                default:
                    return this.dummyProvider.getAllZona();
            }
        }

        return this.dummyProvider.getAllZona();

    }
}