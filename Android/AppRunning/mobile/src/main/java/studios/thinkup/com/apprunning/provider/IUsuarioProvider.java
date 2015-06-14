package studios.thinkup.com.apprunning.provider;

import studios.thinkup.com.apprunning.model.entity.UsuarioApp;

/**
 * Created by FaQ on 13/06/2015.
 */
public interface IUsuarioProvider extends IProvider<UsuarioApp> {
    UsuarioApp getUsuarioByEmail(String email);
}
