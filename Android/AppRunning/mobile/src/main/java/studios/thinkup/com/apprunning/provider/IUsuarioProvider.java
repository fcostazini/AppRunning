package studios.thinkup.com.apprunning.provider;

import studios.thinkup.com.apprunning.model.entity.CheckUsuarioPassDTO;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.exceptions.CredencialesInvalidasException;
import studios.thinkup.com.apprunning.provider.exceptions.UsuarioBloqueadoException;
import studios.thinkup.com.apprunning.provider.exceptions.UsuarioNoVerificadoException;

/**
 * Created by FaQ on 13/06/2015.
 */
public interface IUsuarioProvider extends IProvider<UsuarioApp> {
    UsuarioApp getUsuarioByEmail(String email);

    UsuarioApp loginUsuario(CheckUsuarioPassDTO param) throws CredencialesInvalidasException,UsuarioBloqueadoException,UsuarioNoVerificadoException;
}
