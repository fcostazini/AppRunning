package studios.thinkup.com.apprunning.provider.restProviders;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;
import studios.thinkup.com.apprunning.provider.CarreraCabeceraProvider;

/**
 * Created by Facundo on 29/07/2015.
 */
public class CarreraCabeceraService extends AsyncTask<Filtro, Integer, List<CarreraCabecera>>{

    private OnResultsHandler handler;
    private Context context;

    public CarreraCabeceraService(OnResultsHandler handler, Context context) {
        this.handler = handler;
        this.context = context;
    }

    public interface OnResultsHandler {

        void actualizarResultados(List<CarreraCabecera> resultados);

    }


    @Override
    protected List<CarreraCabecera> doInBackground(Filtro... params) {
        CarreraCabeceraProvider carrerasProvider = new CarreraCabeceraProvider(this.context);
        List<CarreraCabecera> resultados = carrerasProvider.getCarrerasRecomendadas(params[0]);
        if (resultados == null) {
            resultados = new Vector<>();
        }
        return resultados;
    }

    @Override
    protected void onPostExecute(List<CarreraCabecera> carreraCabeceras) {
        super.onPostExecute(carreraCabeceras);
        this.handler.actualizarResultados(carreraCabeceras);


    }
}



