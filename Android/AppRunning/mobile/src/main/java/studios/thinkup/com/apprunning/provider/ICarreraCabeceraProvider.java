package studios.thinkup.com.apprunning.provider;

import java.util.List;

import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;

/**
 * Created by FaQ on 08/06/2015.
 * Provider de Carreras cabecera
 */
public interface ICarreraCabeceraProvider {



    /**
     * Carreras recomendadas por el administrador
     * @param filtro filtros
      * @return lista vacia en caso de no haber resultados
     */
    List<CarreraCabecera> getCarrerasByFiltro(Filtro filtro);
}
