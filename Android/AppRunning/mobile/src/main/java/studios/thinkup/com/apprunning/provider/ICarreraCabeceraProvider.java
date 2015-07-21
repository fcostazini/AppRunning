package studios.thinkup.com.apprunning.provider;

import java.util.List;

import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;

/**
 * Created by FaQ on 08/06/2015.
 */
public interface ICarreraCabeceraProvider {

    /**
     * Obtiene todas las carreras segun el filtro
     * @param filtro filtro
     * @return
     */
    public List<CarreraCabecera>  getCarrerasByFiltro(Filtro filtro);

    /**
     * Carreras recomendadas por el administrador
     * @param filtro filtros
      * @return lista vacia en caso de no haber resultados
     */
    List<CarreraCabecera> getCarrerasRecomendadas(Filtro filtro);
}
