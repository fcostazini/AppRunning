package studios.thinkup.com.apprunning.provider.restProviders;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;
import studios.thinkup.com.apprunning.provider.ICarreraCabeceraProvider;

/**
 * Created by fcostazini on 30/07/2015.
 */
public class CarreraProviderRemote extends RemoteService implements ICarreraCabeceraProvider {
    private static final String MODULO_CARRERA = "/carreras";
    private static final String GET_BY_FILTROS = "/carrerasDtoAll/";


    public CarreraProviderRemote(Context context) {
        super(context);
    }


    @Override
    protected String getModule() {
        return MODULO_CARRERA;
    }

    @Override
    public List<CarreraCabecera> getCarrerasByFiltro(Filtro filtro) {
        // the request
        try {
            HttpURLConnection conn = getPostHttpURLConnection(filtro, GET_BY_FILTROS);
            Gson g = new Gson();
            Respuesta<List<CarreraCabecera>> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(conn.getInputStream())), new TypeToken<Respuesta<List<CarreraCabecera>>>() {
            }.getType());

            if (r.getCodigoRespuesta().equals(Respuesta.CODIGO_OK) && r.getDto() != null) {
                return getCarreraCabecerasFiltradasPorDistancia(filtro, r.getDto());
            } else {
                return new Vector<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Vector<>();
        }
    }

    protected HttpURLConnection getPostHttpURLConnection(Filtro entidad, String service) throws IOException {
        URL url = new URL(this.getBaseURL() + service);
        Gson g = new Gson();
        String json = g.toJson(entidad);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(7000);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.connect();

        OutputStream outputStream = conn.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        writer.write(json);

        writer.flush();
        writer.close();
        return conn;
    }

    private List<CarreraCabecera> getCarreraCabecerasFiltradasPorDistancia(Filtro filtro, List<CarreraCabecera> resultados) {

        List<CarreraCabecera> resultadosFinales = new Vector<>();

        String[] distancias = null;
        for (CarreraCabecera cc : resultados) {
            distancias = cc.getDistanciaDisponible().split("/");
            for (String s : distancias) {
                try {
                    if (Double.valueOf(s.trim()) >= filtro.getMinDistancia() && Double.valueOf(s.trim()) <= filtro.getMaxDistancia()) {
                        resultadosFinales.add(cc);
                        break;
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        return resultadosFinales;


    }

}
