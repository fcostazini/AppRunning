package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.entity.CarreraAmigoDTO;
import studios.thinkup.com.apprunning.provider.restProviders.AmigosProviderRemote;
import studios.thinkup.com.apprunning.provider.restProviders.Respuesta;

/**
 * Created by fcostazini on 13/08/2015.
 * Obtiene todas las carreras en las que un amigo se encuentra inscripto
 */
public class CarrerasAmigosService extends AsyncTask<Integer, Integer, List<CarreraAmigoDTO>> {
    private Context context;
    private IAmigosProvider ap;
    private IServicioActualizacionAmigoHandler handler;

    public CarrerasAmigosService(Context activity, IServicioActualizacionAmigoHandler handler) {
        this.context = activity;
        this.handler = handler;


    }

    @Override
    protected void onPostExecute(List<CarreraAmigoDTO> amigos) {
        super.onPostExecute(amigos);
        if (handler != null) {
            if (amigos == null) {
                handler.onError(Respuesta.CODIGO_NO_ENCONTRADO);
            } else {
                handler.onOk(amigos);

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
            handler.onOk(null);
        }
    }

    @Override
    protected List<CarreraAmigoDTO> doInBackground(Integer... params) {

        return this.ap.getCarrerasAmigo(params[0]);

    }

    public interface IServicioActualizacionAmigoHandler {
        void onOk(List<CarreraAmigoDTO> amigos);

        void onError(Integer estado);
    }
}
