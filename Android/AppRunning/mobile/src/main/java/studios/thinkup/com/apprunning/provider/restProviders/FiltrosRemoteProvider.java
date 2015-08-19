package studios.thinkup.com.apprunning.provider.restProviders;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import studios.thinkup.com.apprunning.model.entity.ProvinciaCiudadDTO;

/**
 * Created by FaQ on 23/05/2015.
 * Provider de las Zonas
 */
public class FiltrosRemoteProvider extends RemoteService {

    private static final String MODULO_USUARIO = "/filtros";
    private static final String GET_CIUDADES = "/getFiltrosProvinciaCiudad";

    public FiltrosRemoteProvider(Context context) {
        super(context);
    }


    public List<ProvinciaCiudadDTO> getFiltros() {
        // the request
        try {
            URL url = new URL(this.getBaseURL() + GET_CIUDADES);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setConnectTimeout(17000);
            con.setDoInput(true);

            Gson g = new Gson();
            Respuesta<List<ProvinciaCiudadDTO>> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(con.getInputStream())), new TypeToken<Respuesta<List<ProvinciaCiudadDTO>>>() {
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
        return MODULO_USUARIO;
    }
}
