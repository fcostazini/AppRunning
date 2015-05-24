package studios.thinkup.com.apprunning.provider;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.Categoria;

/**
 * Created by FaQ on 23/05/2015.
 * Provider de las Zonas
 */
public class ZonaProvider {
    private DummyCategoriaProvider dummyCategoriaProvider;

    public ZonaProvider() {
        this.dummyCategoriaProvider = new DummyCategoriaProvider();
    }

    public List<String> getZonas(){
        List<Categoria> categorias = this.dummyCategoriaProvider.getAllZona();
        List<String> zonas = new Vector<>();
        for(Categoria c : categorias){
            zonas.add(c.getNombre());
        }

        return zonas;
    }
}
