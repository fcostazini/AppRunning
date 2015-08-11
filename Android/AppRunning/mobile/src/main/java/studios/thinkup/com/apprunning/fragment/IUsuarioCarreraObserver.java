package studios.thinkup.com.apprunning.fragment;

import studios.thinkup.com.apprunning.model.EstadoCarrera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;

/**
 * Created by fcostazini on 19/06/2015.
 * Observador de un usuario
 */
public interface IUsuarioCarreraObserver {
    /**
     * Actualiza un usuario en base a un cambio de estado
     *
     * @param estado  .
     * @param usuario .
     */
    void actuliazarUsuarioCarrera(UsuarioCarrera usuario, EstadoCarrera estado);
}
