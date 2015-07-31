package studios.thinkup.com.apprunning.provider.restProviders;

import android.content.Context;
import android.os.AsyncTask;

import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.ICarreraProvider;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntityNotFoundException;

/**
 * Created by Facundo on 29/07/2015.
 * Servicio Para obtener una carrera de Usuario por id
 */
public class UsuarioCarreraService extends AsyncTask<Integer, Integer, UsuarioCarrera> {

    private OnSingleResultHandler<UsuarioCarrera> handler;
    private Context context;
    private Integer idUsuario;

    public UsuarioCarreraService(OnSingleResultHandler<UsuarioCarrera> handler, Context context, Integer idUsuario) {
        this.handler = handler;
        this.context = context;
        this.idUsuario = idUsuario;
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
        IUsuarioCarreraProvider ucp = new UsuarioCarreraProvider(this.context, this.idUsuario);
        UsuarioCarrera uc = ucp.getByIdCarrera(id[0]);
        if( uc == null){

            ICarreraProvider cp = new CarreraProviderRemote(context);
            Carrera c = null;
            try {
                c = cp.getById(id[0]);
            } catch (EntityNotFoundException e) {
                return null;
            }
            uc = new UsuarioCarrera(c);
            uc.setUsuario(this.idUsuario);
        }
        return uc;

    }
}



