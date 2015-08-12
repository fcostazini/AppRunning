package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.restProviders.AmigosProviderRemote;

/**
 * Created by Facundo on 11/08/2015.
 * <p/>
 * Busqueda de amigos por Nombre UNION Email Like "Param%"
 */
public class BuscarNuevosAmigosService extends AsyncTask<String, Integer, List<AmigosDTO>> {
    private Context context;
    private IAmigosProvider ap;
    private UsuarioApp owner;
    private IServiceAmigosHandler handler;

    public BuscarNuevosAmigosService(Context activity, UsuarioApp usuarioApp, IServiceAmigosHandler handler) {
        this.context = activity;
        this.owner = usuarioApp;
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
    protected void onCancelled() {
        super.onCancelled();
        if (handler != null) {
            handler.onDataRetrived(new Vector<AmigosDTO>());
        }
    }

    @Override
    protected List<AmigosDTO> doInBackground(String... params) {

        return this.ap.getUsuarios(this.owner.getId(),params[0]);

    }

    public interface IServiceAmigosHandler {
        void onDataRetrived(List<AmigosDTO> amigos);

        void onError(String error);
    }


}
