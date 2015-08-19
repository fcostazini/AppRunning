package studios.thinkup.com.apprunning.provider;

import java.io.Serializable;

import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.provider.exceptions.EntityNotFoundException;

/**
 * Created by fcostazini on 31/07/2015.
 * Obtiene Carreras
 */
public interface ICarreraProvider extends Serializable {
    /**
     * Obtiene una carrera por id
     *
     * @param id .
     * @return Carrera con el id buscado
     * @throws EntityNotFoundException no se encontro
     */
    Carrera getById(Integer id) throws EntityNotFoundException;


}
