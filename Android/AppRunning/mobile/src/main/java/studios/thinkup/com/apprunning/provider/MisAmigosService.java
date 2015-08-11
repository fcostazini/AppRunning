package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;
import java.util.Random;
import java.util.Vector;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.entity.Amigo;

/**
 * Created by Facundo on 11/08/2015.
 * Servicio para obtener los amigos
 */
public class MisAmigosService extends AsyncTask<Integer, Integer, List<Amigo>> {
    private Context context;
    private IAmigosProvider ap;
    private IServiceAmigosHandler handler;

    public MisAmigosService(Context activity, IServiceAmigosHandler handler) {
        this.context = activity;

        this.handler = handler;


    }

    @Override
    protected void onPostExecute(List<Amigo> usuarioApps) {
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
            handler.onDataRetrived(new Vector<Amigo>());
        }
    }

    @Override
    protected List<Amigo> doInBackground(Integer... params) {

        return this.ap.getAmigosByUsuarioId(params[0]);

    }

    public interface IServiceAmigosHandler {
        void onDataRetrived(List<Amigo> amigos);

        void onError(String error);
    }

    private class AmigosProviderDummy implements IAmigosProvider {
        Random rand = new Random(System.currentTimeMillis());
        public AmigosProviderDummy(Context context) {
        }

        @Override
        public List<Amigo> getAmigosByUsuarioId(Integer id) {
            List<Amigo> usuarios = new Vector<>();
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            usuarios.add(getUsuarioRandom());
            return usuarios;
        }

        private Amigo getUsuarioRandom() {
            Amigo u = new Amigo();
            int i = rand.nextInt();
            u.setNombre("Nombre " + i);
            u.setNick("Nick " + i);
            u.setEsAmigo(true);
            u.setId(new Integer(i));
            u.setEmail("email" + i + "@gmail.com");
            u.setFotoPerfil("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpf1/v/t1.0-1/" +
                    "p40x40/10394643_10204849992385905_3118802496210341561_n.jpg?oh=2487f81c3c0cae770d1398add769b131&oe=563B62B0&" +
                    "__gda__=1451466838_b656d5e96454d069fdd3d46008963017");
            return u;
        }

        @Override
        public List<Amigo> getUsuarios(String parametro) {
            List<Amigo> usuarios = new Vector<>();
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

        /**
         * Actualiza el estado de un amigo con relacion al usuario
         *
         * @param param
         * @param idUsuario del usuario que solicita
         * @return
         */
        @Override
        public Integer actualizarEstadoAmigo(Amigo param, Integer idUsuario) {
            return null;
        }
    }
}
