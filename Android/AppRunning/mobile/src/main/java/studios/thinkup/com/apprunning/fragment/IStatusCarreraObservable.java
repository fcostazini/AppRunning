package studios.thinkup.com.apprunning.fragment;

/**
 * Created by fcostazini on 19/06/2015.
 * Observablke de cambio de estado de carreras
 */
public interface IStatusCarreraObservable {

    void registrarObservador(IStatusCarreraObserver observador);
}
