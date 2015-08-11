package studios.thinkup.com.apprunning.provider.restProviders;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioProvider;

/**
 * Created by Facundo on 29/07/2015.
 */
public class TiemposService extends AsyncTask<Filtro,Integer,List<UsuarioCarrera>> {

    private OnResultHandler<UsuarioCarrera> handler;
    private Context context;

    public TiemposService(OnResultHandler<UsuarioCarrera> handler, Context context) {
        this.handler = handler;
        this.context = context;
    }

    @Override
    protected void onPostExecute(List<UsuarioCarrera> usuarioCarreras) {
        super.onPostExecute(usuarioCarreras);
        handler.actualizarResultados(usuarioCarreras);

    }

    @Override
    protected List<UsuarioCarrera> doInBackground(Filtro... params) {

        UsuarioProvider up = new UsuarioProvider(context);
        IUsuarioCarreraProvider carrerasProvider = new UsuarioCarreraProvider(this.context, up.findById(UsuarioApp.class, params[0].getIdUsuario()));
        List<UsuarioCarrera> resultados = carrerasProvider.findTiemposByFiltro(params[0]);
        if(resultados == null){
            return new Vector<>();
        }
        return resultados;
    }
}