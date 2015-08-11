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
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.restProviders.Respuesta;

/**
 * Created by Facundo on 11/08/2015.
 * Servicio para obtener los amigos
 */
public class ActualizarAmigoService extends AsyncTask<Amigo, Integer, Integer> {
    private Context context;
    private IAmigosProvider ap;
    private UsuarioApp usuarioApp;
    private IServicioActualizacionAmigoHandler handler;

    public ActualizarAmigoService(Context activity, IServicioActualizacionAmigoHandler handler,UsuarioApp usuarioApp) {
        this.context = activity;
        this.usuarioApp = usuarioApp;
        this.handler = handler;


    }

    @Override
    protected void onPostExecute(Integer estado) {
        super.onPostExecute(estado);
        if (handler != null) {
            if (estado == Respuesta.CODIGO_OK) {
                handler.onOk();
            } else {
                handler.onError(estado);

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
            handler.onOk();
        }
    }

    @Override
    protected Integer doInBackground(Amigo... params) {

        return this.ap.actualizarEstadoAmigo(params[0],this.usuarioApp.getId());

    }

    public interface IServicioActualizacionAmigoHandler {
        void onOk();

        void onError(Integer estado);
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
         * @return
         */
        @Override
        public Integer actualizarEstadoAmigo(Amigo param, Integer idUsuario) {
            return Respuesta.CODIGO_OK;
        }
    }
}
