package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.entity.CheckUsuarioPassDTO;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.exceptions.CredencialesInvalidasException;
import studios.thinkup.com.apprunning.provider.exceptions.UsuarioBloqueadoException;
import studios.thinkup.com.apprunning.provider.exceptions.UsuarioNoVerificadoException;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioProviderRemote;

/**
 * Created by Facundo on 11/08/2015.
 * Servicio para obtener los amigos
 */
public class LoginUsuarioService extends AsyncTask<CheckUsuarioPassDTO, Integer, UsuarioApp> {
    private Context context;
    private IUsuarioProvider ap;
    private UsuarioApp usuarioApp;
    private IServicioActualizacionAmigoHandler handler;
    private String error = "";

    public LoginUsuarioService(Context activity, IServicioActualizacionAmigoHandler handler) {
        this.context = activity;
        this.handler = handler;


    }

    @Override
    protected void onPostExecute(UsuarioApp usuario) {
        super.onPostExecute(usuario);
        if (handler != null) {
            if (usuario == null) {
                if(this.error.isEmpty()){
                    handler.onError("Error al loguear");
                }else{
                    handler.onError(this.error);
                }

            } else {
                handler.onOk(usuario);

            }

        }

    }

    @Override
    protected void onPreExecute() {
        if (NetworkUtils.isConnected(context)) {
            this.ap = new UsuarioProviderRemote(context);
        } else {
            Toast.makeText(context, context.getString(R.string.sin_conexion), Toast.LENGTH_SHORT).show();
            this.cancel(true);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (handler != null) {
            handler.onOk(null);
        }
    }

    @Override
    protected UsuarioApp doInBackground(CheckUsuarioPassDTO... params) {

        try {
            return this.ap.loginUsuario(params[0]);
        } catch (CredencialesInvalidasException e) {
            this.error = "Usuario o Contraseña Invalido";
            return null;
        } catch (UsuarioBloqueadoException e){
            this.error = "Usuario Bloqueado";
            return null;
        } catch (UsuarioNoVerificadoException e) {
            this.error = "Usuario No Verificado";
            return null;
        }
    }

    public interface IServicioActualizacionAmigoHandler {
        void onOk(UsuarioApp amigosDTO);

        void onError(String error);
    }


}
