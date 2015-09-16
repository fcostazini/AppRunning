package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.entity.AmigoRequest;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.restProviders.AmigosProviderRemote;
import studios.thinkup.com.apprunning.provider.restProviders.Respuesta;

/**
 * Created by Facundo on 11/08/2015.
 * Servicio para obtener los amigos
 */
public class ActualizarAmigoService extends AsyncTask<List<AmigoRequest>, Integer, List<AmigosDTO>> {
    private Context context;
    private IAmigosProvider ap;
    private UsuarioApp usuarioApp;
    private IServicioActualizacionAmigoHandler handler;

    public ActualizarAmigoService(Context activity, IServicioActualizacionAmigoHandler handler) {
        this.context = activity;
        this.handler = handler;


    }

    @Override
    protected void onPostExecute(List<AmigosDTO> amigos) {
        super.onPostExecute(amigos);
        if (handler != null) {
            if (amigos == null) {
                handler.onError(Respuesta.CODIGO_NO_ENCONTRADO);
            } else {
                handler.onOk(amigos);

            }

        }

    }

    @Override
    protected void onPreExecute() {
        if (NetworkUtils.isConnected(context)) {
            this.ap = new AmigosProviderRemote(context);
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
    protected List<AmigosDTO> doInBackground(List<AmigoRequest>... params) {

        return this.ap.actualizarEstadoAmigo(params[0]);

    }

    public interface IServicioActualizacionAmigoHandler {
        void onOk(List<AmigosDTO> amigosDTO);

        void onError(Integer estado);
    }


}
