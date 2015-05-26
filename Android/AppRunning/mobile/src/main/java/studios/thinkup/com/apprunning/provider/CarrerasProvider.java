package studios.thinkup.com.apprunning.provider;

import java.util.List;

import studios.thinkup.com.apprunning.model.CarreraCabecera;
import studios.thinkup.com.apprunning.model.Filtro;

/**
 * Created by fcostazini on 22/05/2015.
 *
 * Provider de las carreras
 *
 */
public class CarrerasProvider {

    private DummyCarreraProvider dummyProvider;


    public CarrerasProvider() {

        dummyProvider = new DummyCarreraProvider();

    }

    public List<CarreraCabecera> getCarreras(Filtro filtro) {

        return dummyProvider.getCarreras();
    }

    public Integer getCantidadCarreras(Filtro filtro) {
        QueryGenerator queryGenerator  = new QueryGenerator(filtro);
        String query = "Select Count(codigo) from Carreras \n " + queryGenerator.getWhereCondition();

        return 30;

    }


}
