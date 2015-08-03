package studios.thinkup.com.apprunning.provider.restProviders;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.CarreraCabeceraProvider;
import studios.thinkup.com.apprunning.provider.ICarreraCabeceraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;

/**
 * Created by Facundo on 29/07/2015.
 * Service para recuperar carrerasCabecera
 */
public class CarreraCabeceraService extends AsyncTask<Filtro, Integer, List<CarreraCabecera>> {

    private OnResultsHandler handler;
    private Context context;
    private UsuarioApp usuario;

    public CarreraCabeceraService(OnResultsHandler handler, Context context, UsuarioApp usuario) {
        this.handler = handler;
        this.context = context;
        this.usuario = usuario;
    }

    @Override
    protected List<CarreraCabecera> doInBackground(Filtro... params) {
        List<CarreraCabecera> resultados = new Vector<>();
        ICarreraCabeceraProvider carrerasProvider = null;
        if (params[0].getIdUsuario() >= 0) {
            carrerasProvider = new CarreraCabeceraProvider(context);
            resultados = carrerasProvider.getCarrerasByFiltro(params[0]);
            if (resultados == null) {
                resultados = new Vector<>();
            }
            return resultados;
        } else {
            carrerasProvider = new CarreraCabeceraProviderRemote(this.context);
            resultados = carrerasProvider.getCarrerasByFiltro(params[0]);

            if (resultados == null) {
                resultados = new Vector<>();
            } else {
                UsuarioCarreraProvider up = new UsuarioCarreraProvider(context, this.usuario);
                UsuarioCarrera uc = null;
                for (CarreraCabecera cc : resultados) {
                    uc = up.getByIdCarrera(cc.getCodigoCarrera());
                    if (uc != null) {
                        cc.setUsuarioCarrera(uc);
                    }
                }
            }
            return resultados;
        }

    }

    @Override
    protected void onPostExecute(List<CarreraCabecera> carreraCabeceras) {
        super.onPostExecute(carreraCabeceras);
        this.handler.actualizarResultados(carreraCabeceras);


    }

    public interface OnResultsHandler {

        void actualizarResultados(List<CarreraCabecera> resultados);

    }
}



