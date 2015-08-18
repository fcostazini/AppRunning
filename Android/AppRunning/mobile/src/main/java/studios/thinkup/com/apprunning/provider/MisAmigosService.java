package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;
import studios.thinkup.com.apprunning.provider.restProviders.AmigosProviderRemote;

/**
 * Created by Facundo on 11/08/2015.
 * Servicio para obtener los amigos
 */
public class MisAmigosService extends AsyncTask<Integer, Integer, List<AmigosDTO>> {
    private Context context;
    private IAmigosProvider ap;
    private IServiceAmigosHandler handler;

    public MisAmigosService(Context activity, IServiceAmigosHandler handler) {
        this.context = activity;

        this.handler = handler;


    }

    @Override
    protected void onPostExecute(List<AmigosDTO> usuarioApps) {
        super.onPostExecute(usuarioApps);
        if (handler != null) {
            if (usuarioApps == null) {
                handler.onError("Error al Buscar los amigos");
            } else {
                handler.onDataRetrived(usuarioApps);
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
    protected List<AmigosDTO> doInBackground(Integer... params) {

        return this.ap.getAmigosByUsuarioId(params[0]);

    }

    public interface IServiceAmigosHandler {
        void onDataRetrived(List<AmigosDTO> amigos);

        void onError(String error);
    }


}
