package studios.thinkup.com.apprunning.provider.restProviders;

import android.content.Context;
import android.os.AsyncTask;

import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;

/**
 * Created by Facundo on 29/07/2015.
 * Servicio Para obtener una carrera de Usuario por id
 */
public class UsuarioCarreraService extends AsyncTask<Integer, Integer, UsuarioCarrera> {

    private OnSingleResultHandler<UsuarioCarrera> handler;
    private Context context;

    public UsuarioCarreraService(OnSingleResultHandler<UsuarioCarrera> handler, Context context) {
        this.handler = handler;
        this.context = context;
    }

    @Override
    protected void onPostExecute(UsuarioCarrera usuarioCarrera) {
        super.onPostExecute(usuarioCarrera);
        if (usuarioCarrera != null) {
            this.handler.actualizarResultado(usuarioCarrera);
        }
    }

    @Override
    protected UsuarioCarrera doInBackground(Integer... id) {
        IUsuarioCarreraProvider cp = new UsuarioCarreraProviderRemote(this.context);
        return cp.getByIdCarrera(id[0]);

    }
}



