package studios.thinkup.com.apprunning.provider;

import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.exceptions.EntityNotFoundException;

/**
 * Created by FaQ on 30/05/2015.
 */
public interface ICarrerasProvider {

    UsuarioCarrera getByIdCarrera(long carrera);


}
