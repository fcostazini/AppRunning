package studios.thinkup.com.apprunning.provider;

import java.util.List;

import studios.thinkup.com.apprunning.model.CarreraCabecera;
import studios.thinkup.com.apprunning.model.Categoria;
import studios.thinkup.com.apprunning.model.Filtro;

/**
 * Created by fcostazini on 22/05/2015.
 */
public class CarrerasProvider {

    private DummyCarreraProvider dummyProvider;

    public CarrerasProvider() {
        dummyProvider = new DummyCarreraProvider();

    }

    public List<CarreraCabecera> getCarreras(Filtro filtro) {

        return dummyProvider.getCarreras();
    }
}
