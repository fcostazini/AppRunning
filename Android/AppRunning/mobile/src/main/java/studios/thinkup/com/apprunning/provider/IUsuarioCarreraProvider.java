package studios.thinkup.com.apprunning.provider;

import java.util.List;

import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.exceptions.EntityNotFoundException;

/**
 * Created by FaQ on 30/05/2015.
 * Provider de carreras asociadas a un usuario
 */
public interface IUsuarioCarreraProvider extends IProvider<UsuarioCarrera>{

    UsuarioCarrera getByIdCarrera(long carrera);

    /**
     * Buscar todas las carreras con tiempo de un usuario segun el filtro
     * @param filtro .
     * @return lista vacia en caso de no haber resultados
     */
    List<UsuarioCarrera> findTiemposByFiltro(Filtro filtro);
}
