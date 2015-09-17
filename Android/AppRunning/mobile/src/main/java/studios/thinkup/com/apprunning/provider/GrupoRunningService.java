package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.entity.GrupoRunning;
import studios.thinkup.com.apprunning.provider.restProviders.GruposRunningRemote;

/**
 * Created by Facundo on 11/08/2015.
 * <p/>
 * Busqueda de Grupos
 */
public class GrupoRunningService extends AsyncTask<String, Integer, List<GrupoRunning>> {
    private Context context;
    private IGruposProvider ap;
    private IServiceGruposHandler handler;

    public GrupoRunningService(Context activity,  IServiceGruposHandler handler) {
        this.context = activity;
        this.handler = handler;


    }

    @Override
    protected void onPostExecute(List<GrupoRunning> usuarioApps) {
        super.onPostExecute(usuarioApps);
        if (handler != null) {
            if (usuarioApps == null) {
                handler.onError("Error al buscar los grupos");
            } else {
                handler.onDataRetrived(usuarioApps);
            }

        }

    }

    @Override
    protected void onPreExecute() {
        if (NetworkUtils.isConnected(context)) {
            this.ap = new GruposRunningRemote(context);
        } else {
            Toast.makeText(context, context.getString(R.string.sin_conexion), Toast.LENGTH_SHORT).show();
            this.cancel(true);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (handler != null) {
            handler.onDataRetrived(new Vector<GrupoRunning>());
        }
    }

    @Override
    protected List<GrupoRunning> doInBackground(String... params) {

        return this.ap.getGrupoByName(params[0]);

    }

    public interface IServiceGruposHandler {
        void onDataRetrived(List<GrupoRunning> amigos);

        void onError(String error);
    }


}
