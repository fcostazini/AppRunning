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

import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.IUsuarioProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;

/**
 * Created by fcostazini on 30/07/2015.
 */
public class UsuarioProviderRemote extends RemoteService implements IUsuarioProvider {
    private static final String MODULO_USUARIO = "/usuarios";
    private static final String GET_BY_EMAIL_SERVICE = "/usuariosByEmail/";
    private static final String GET_BY_ID = "/usuariosById/";
    private static final String SAVE_USUARIO = "/saveUsuario";
    private static final String UPDATE_USUARIO = "/updateUsuario";

    public UsuarioProviderRemote(Context context) {
        super(context);
    }

    @Override
    public UsuarioApp getUsuarioByEmail(String email) {
        // the request
        try {
            URL url = new URL(this.getBaseURL() + GET_BY_EMAIL_SERVICE + email);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(10000);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoInput(true);
            Gson g = new Gson();
            Respuesta<UsuarioApp> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(con.getInputStream())), new TypeToken<Respuesta<UsuarioApp>>(){}.getType());

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
    public UsuarioApp findById(Class<UsuarioApp> clazz, Integer id) {

        // the request
        try {
            URL url = new URL(this.getBaseURL() + GET_BY_ID + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setConnectTimeout(10000);
            con.setDoInput(true);

            Gson g = new Gson();
            Respuesta<UsuarioApp> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(con.getInputStream())), new TypeToken<Respuesta<UsuarioApp>>(){}.getType());

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
    public List<UsuarioApp> findAll(Class<UsuarioApp> clazz) {
        return null;
    }

    @Override
    public UsuarioApp update(UsuarioApp entidad) throws EntidadNoGuardadaException {
        // the request
        try {
            HttpURLConnection conn = getPostHttpURLConnection(entidad, UPDATE_USUARIO);
            Gson g = new Gson();
            Respuesta<UsuarioApp> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(conn.getInputStream())), new TypeToken<Respuesta<UsuarioApp>>(){}.getType());

            if (r.getCodigoRespuesta().equals(Respuesta.CODIGO_CREACION_MODIFICACION_OK) && r.getDto() != null) {
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
    public UsuarioApp grabar(UsuarioApp entidad) throws EntidadNoGuardadaException {
        // the request
        try {
            HttpURLConnection conn = getPostHttpURLConnection(entidad, SAVE_USUARIO);
            Gson g = new Gson();
            Respuesta<UsuarioApp> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(conn.getInputStream())), new TypeToken<Respuesta<UsuarioApp>>(){}.getType());

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

    protected HttpURLConnection getPostHttpURLConnection(UsuarioApp entidad, String service) throws IOException {
        URL url = new URL(this.getBaseURL() + service);
        Gson g = new Gson();
        String json = g.toJson(entidad);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(5000);
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

    @Override
    protected String getModule() {
        return MODULO_USUARIO;
    }
}
