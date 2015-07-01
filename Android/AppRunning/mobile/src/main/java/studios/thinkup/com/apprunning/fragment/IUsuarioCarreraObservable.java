package studios.thinkup.com.apprunning.fragment;

import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;

/**
 * Created by fcostazini on 29/06/2015.
 */
public interface IUsuarioCarreraObservable {
    void registrarObservadorUsuario(IUsuarioCarreraObserver ob);
    UsuarioCarrera getUsuarioCarrera();
}
