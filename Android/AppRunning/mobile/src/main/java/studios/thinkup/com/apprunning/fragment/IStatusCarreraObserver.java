package studios.thinkup.com.apprunning.fragment;

import studios.thinkup.com.apprunning.model.EstadoCarrera;

/**
 * Created by fcostazini on 19/06/2015.
 */
public interface IStatusCarreraObserver {

    void actualizarCambioEstadoCarrera(EstadoCarrera estado);
}
