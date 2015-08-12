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

import studios.thinkup.com.apprunning.model.entity.AmigoRequest;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;
import studios.thinkup.com.apprunning.provider.IAmigosProvider;

/**
 * Created by Facundo on 12/08/2015.
 * Consume el servicio rest para obtener los amigos
 */
public class AmigosProviderRemote extends RemoteService implements IAmigosProvider {
    private static final String MODULO_CARRERA = "/amigos";
    private static final String GET_MIS_AMIGOS = "/getAmigos/";
    private static final String GET_AMIGOS_EN_CARRERA = "/getAmigosEnCarrera/";
    private static final String BUSCAR_USUARIOS = "/buscarAmigos/";
    private static final String REQUEST = "/request";
    public AmigosProviderRemote(Context context) {
        super(context);
    }

    @Override
    protected String getModule() {
        return MODULO_CARRERA;
    }

    /**
     * Obtiene todos los amigos de un Amigo
     *
     * @param id del usuario
     * @return lista vacia en caso de no encontrar resultados
     */
    @Override
    public List<AmigosDTO> getAmigosByUsuarioId(Integer id) {
    // the request
        try {
            URL url = new URL(this.getBaseURL() + GET_MIS_AMIGOS + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(7000);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoInput(true);
            Gson g = new Gson();
            Respuesta<List<AmigosDTO>> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(con.getInputStream())), new TypeToken<Respuesta<List<AmigosDTO>>>() {
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

    /**
     * Obtiene todos los amigos de un Amigo
     *
     * @param idUsuario del usuario
     * @param idCarrera usuario
     * @return lista vacia en caso de no encontrar resultados
     */
    @Override
    public List<AmigosDTO> getAmigosEnCarrera(Integer idUsuario, Integer idCarrera) {
        // the request
        try {
            URL url = new URL(this.getBaseURL() + GET_AMIGOS_EN_CARRERA + idUsuario + "/" + idCarrera );
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(7000);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoInput(true);
            Gson g = new Gson();
            Respuesta<List<AmigosDTO>> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(con.getInputStream())), new TypeToken<Respuesta<List<AmigosDTO>>>() {
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

    /**
     * Obtiene todos los Amigo que tengan nombre o email que cumplan con "LIKE param%"
     *
     * @param parametro para buscar
     * @return lista vacia en caso de no encontrar resultados
     */
    @Override
    public List<AmigosDTO> getUsuarios(Integer idOwner, String parametro) {
        // the request
        try {
            URL url = new URL(this.getBaseURL() + BUSCAR_USUARIOS + idOwner +"/" + parametro);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(7000);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoInput(true);
            Gson g = new Gson();
            Respuesta<List<AmigosDTO>> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(con.getInputStream())), new TypeToken<Respuesta<List<AmigosDTO>>>() {
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

    /**
     * Actualiza el estado de un amigo con relacion al usuario
     *
     * @param request peticion
     * @return Integer codigo de respuesta
     */
    @Override
    public AmigosDTO actualizarEstadoAmigo(AmigoRequest request) {
        try {
            HttpURLConnection conn = getPostHttpURLConnection(request,REQUEST);
            Gson g = new Gson();
            Respuesta<AmigosDTO> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(conn.getInputStream())), new TypeToken<Respuesta<AmigosDTO>>() {
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

    protected HttpURLConnection getPostHttpURLConnection(AmigoRequest entidad,String service) throws IOException {
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
}
