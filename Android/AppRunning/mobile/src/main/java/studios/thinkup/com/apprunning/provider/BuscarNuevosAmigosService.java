package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;

/**
 * Created by Facundo on 11/08/2015.
 * <p/>
 * Busqueda de amigos por Nombre UNION Email Like "Param%"
 */
public class BuscarNuevosAmigosService extends AsyncTask<String, Integer, List<UsuarioApp>> {
    private Context context;
    private IAmigosProvider ap;
    private IServiceAmigosHandler handler;

    public BuscarNuevosAmigosService(Context activity, IServiceAmigosHandler handler) {
        this.context = activity;

        this.handler = handler;


    }

    @Override
    protected void onPostExecute(List<UsuarioApp> usuarioApps) {
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
            this.ap = new AmigosProviderDummy(context);
        } else {
            Toast.makeText(context, context.getString(R.string.sin_conexion), Toast.LENGTH_SHORT).show();
            this.cancel(true);
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (handler != null) {
            handler.onDataRetrived(new Vector<UsuarioApp>());
        }
    }

    @Override
    protected List<UsuarioApp> doInBackground(String... params) {

        return this.ap.getUsuarios(params[0]);

    }

    public interface IServiceAmigosHandler {
        void onDataRetrived(List<UsuarioApp> amigos);

        void onError(String error);
    }

    private class AmigosProviderDummy implements IAmigosProvider {
        Random rand = new Random(System.currentTimeMillis());

        public AmigosProviderDummy(Context context) {
        }

        @Override
        public List<UsuarioApp> getAmigosByUsuarioId(Integer id) {
            List<UsuarioApp> usuarios = new Vector<>();
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            return usuarios;
        }

        private UsuarioApp getUsuarioRandom() {
            UsuarioApp u = new UsuarioApp();
            int i = rand.nextInt();
            u.setNombre("NN " + i);
            u.setNick("Nick " + i);
            u.setId(new Integer(i));
            u.setEmail("ee" + i + "@gmail.com");
            u.setFotoPerfil("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/v/t1.0-1/" +
                    "p40x40/10394643_10204849992385905_3118802496210341561_n.jpg?oh=2487f81c3c0cae770d1398add769b131&oe=563B62B0&" +
                    "__gda__=1451466838_b656d5e96454d069fdd3d46008963017");
            return u;
        }

        @Override
        public List<UsuarioApp> getUsuarios(String parametro) {
            List<UsuarioApp> usuarios = new Vector<>();
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            return usuarios;

        }
    }
}
