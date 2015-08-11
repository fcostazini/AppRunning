package studios.thinkup.com.apprunning.provider.restProviders;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.provider.ICarreraProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntityNotFoundException;

/**
 * Created by fcostazini on 31/07/2015.
 */
public class CarreraProviderRemote extends RemoteService implements ICarreraProvider {
    private static final String MODULO_CARRERAS = "/carreras";
    private static final String GET_BY_ID = "/carreraByNro/";

    public CarreraProviderRemote(Context context) {
        super(context);
    }

    @Override
    public Carrera getById(Integer id) throws EntityNotFoundException {
        // the request
        try {
            URL url = new URL(this.getBaseURL() + GET_BY_ID + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(10000);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoInput(true);
            Gson g = new Gson();
            Respuesta<Carrera> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(con.getInputStream())), new TypeToken<Respuesta<Carrera>>() {
            }.getType());

            if (r.getCodigoRespuesta().equals(Respuesta.CODIGO_OK) && r.getDto() != null) {
                return r.getDto();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected String getModule() {
        return MODULO_CARRERAS;
    }
}
