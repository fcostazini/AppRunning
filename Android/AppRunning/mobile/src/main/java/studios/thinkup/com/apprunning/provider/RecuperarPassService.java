package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.provider.exceptions.UsuarioInexistenteException;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioProviderRemote;

/**
 * Created by Facundo on 11/08/2015.
 * Servicio para obtener los amigos
 */
public class RecuperarPassService extends AsyncTask<String, Integer, Boolean> {
    private Context context;
    private IUsuarioProvider ap;

    private IServicioActualizacionAmigoHandler handler;
    private String error = "";

    public RecuperarPassService(Context activity, IServicioActualizacionAmigoHandler handler) {
        this.context = activity;
        this.handler = handler;


    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if (handler != null) {
            if (result) {
                if(this.error.isEmpty()){
                    handler.onOk();
                }else{
                    handler.onError(this.error);
                }

            } else {
                handler.onError("No se puede recuperar el password");

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
            handler.onOk();
        }
    }

    @Override
    protected Boolean doInBackground(String... params) {

        try {
            return this.ap.recuperarPass(params[0]);
        } catch (UsuarioInexistenteException e) {
            this.error = "Usuario no registrado";
            return false;

        }
    }

    public interface IServicioActualizacionAmigoHandler {
        void onOk();

        void onError(String error);
    }


}
