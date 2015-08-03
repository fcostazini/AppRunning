package studios.thinkup.com.apprunning.provider.restProviders;

import android.content.Context;
import android.os.AsyncTask;

import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.CarreraLocalProvider;
import studios.thinkup.com.apprunning.provider.ICarreraProvider;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;
import studios.thinkup.com.apprunning.provider.exceptions.EntityNotFoundException;

/**
 * Created by Facundo on 29/07/2015.
 * Servicio Para obtener una carrera de Usuario por id
 */
public class UsuarioCarreraService extends AsyncTask<Integer, Integer, UsuarioCarrera> {

    private OnSingleResultHandler<UsuarioCarrera> handler;
    private Context context;
    private UsuarioApp usuarioApp;

    public UsuarioCarreraService(OnSingleResultHandler<UsuarioCarrera> handler, Context context, UsuarioApp usuario) {
        this.handler = handler;
        this.context = context;
        this.usuarioApp = usuario;
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
        Carrera c = null;
        IUsuarioCarreraProvider ucp = new UsuarioCarreraProvider(context, usuarioApp);
        UsuarioCarrera uc = ucp.getByIdCarrera(id[0]);
        if (NetworkUtils.getConnectivityStatus(context) == NetworkUtils.NETWORK_STATUS_NOT_CONNECTED) {
            return getUsuarioCarreraLocal(id[0]);
        }
        ICarreraProvider cp = new CarreraProviderRemote(context);
        try {
            c = cp.getById(id[0]);
            CarreraLocalProvider local = new CarreraLocalProvider(context);
            local.actualizarCarrera(c);
        } catch (EntityNotFoundException e) {
            CarreraLocalProvider local = new CarreraLocalProvider(context);

            c = local.getById(id[0]);
            if (c == null) {
                return null;
            }
        } catch (EntidadNoGuardadaException e) {
            return null;
        }
        if (uc != null) {
            uc.setCarrera(c);
        } else {
            uc = new UsuarioCarrera(c);
        }

        uc.setUsuario(this.usuarioApp.getId());

        return uc;

    }

    private UsuarioCarrera getUsuarioCarreraLocal(Integer id) {
        Carrera c = null;
        IUsuarioCarreraProvider ucp = new UsuarioCarreraProvider(context, this.usuarioApp);
        UsuarioCarrera uc = ucp.getByIdCarrera(id);
        CarreraLocalProvider local = new CarreraLocalProvider(context);
        c = local.getById(id);
        if (c == null) {
            return null;
        }
        if (uc == null) {
            uc = new UsuarioCarrera(c);
        } else {
            uc.setCarrera(c);
        }
        uc.setUsuario(this.usuarioApp.getId());
        return uc;

    }
}



