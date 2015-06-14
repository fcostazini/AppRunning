package studios.thinkup.com.apprunning.model;

import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;

/**
 * Created by FaQ on 08/06/2015.
 */
public interface IObservadorCarrera {
    /**
     * se invoca desde su Observado
     * @param carrera
     */
    public UsuarioCarrera actualizarCarrera(UsuarioCarrera carrera) throws EntidadNoGuardadaException;
}
