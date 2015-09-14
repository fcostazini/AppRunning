package studios.thinkup.com.apprunning.provider.restProviders;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.entity.GrupoRunning;
import studios.thinkup.com.apprunning.provider.IGruposProvider;

/**
 * Created by Facundo on 12/08/2015.
 * Consume el servicio rest para obtener los amigos
 */
public class GruposRunningRemote extends RemoteService implements IGruposProvider {
    private static final String MODULO_CARRERA = "/grupos";
    private static final String GET_BY_NOMBRE = "/getGrupos/";

    public GruposRunningRemote(Context context) {
        super(context);
    }

    @Override
    protected String getModule() {
        return MODULO_CARRERA;
    }


    @Override
    public List<GrupoRunning> getGrupoByName(String name) {
        // the request
        try {
            URL url = new URL(this.getBaseURL() + GET_BY_NOMBRE + name);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(7000);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoInput(true);
            Gson g = new Gson();
            Respuesta<List<GrupoRunning>> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(con.getInputStream())), new TypeToken<Respuesta<List<GrupoRunning>>>() {
            }.getType());

            if (r.getCodigoRespuesta().equals(Respuesta.CODIGO_OK) && r.getDto() != null) {
                return r.getDto();
            } else {
                return new Vector<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Vector<>();
        }
    }
}
