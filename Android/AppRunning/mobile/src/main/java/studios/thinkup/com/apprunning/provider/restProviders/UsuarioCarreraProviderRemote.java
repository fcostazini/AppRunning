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
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarreraDTO;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;

/**
 * Created by Facundo on 31/07/2015.
 * <p/>
 * Provider de Usuario Carrera En el servidor
 */
public class UsuarioCarreraProviderRemote extends RemoteService implements IUsuarioCarreraProvider {
    private static final String MODULO_USUARIO_CARRERA = "/miscarreras";
    private static final String GET_BY_ID = "/findById/";
    private static final String GET_BY_CARRERA_ID = "/findByIdCarrera/";
    private static final String SAVE = "/save";
    private static final String UPDATE = "/update";
    private static final String UPLOAD = "/upload";
    private static final String TIEMPOS_BY_FILTRO = "/misTiemposByFiltro";
    private static final String TODOS = "/findAll/";


    public UsuarioCarreraProviderRemote(Context context) {
        super(context);

    }

    @Override
    public UsuarioCarrera getByIdCarrera(long carrera) {
// the request
        try {
            URL url = new URL(this.getBaseURL() + GET_BY_CARRERA_ID + carrera);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(10000);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoInput(true);
            Gson g = new Gson();
            Respuesta<UsuarioCarrera> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(con.getInputStream())), new TypeToken<Respuesta<UsuarioCarrera>>() {
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
    public List<UsuarioCarrera> findTiemposByFiltro(Filtro filtro) {
        // the request
        try {
            HttpURLConnection conn = getPostHttpURLConnection(filtro, TIEMPOS_BY_FILTRO);
            Gson g = new Gson();
            Respuesta<List<UsuarioCarrera>> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(conn.getInputStream())), new TypeToken<Respuesta<List<UsuarioCarrera>>>() {
            }.getType());

            if (r.getCodigoRespuesta().equals(Respuesta.CODIGO_OK) && r.getDto() != null) {
                return getUsuarioCarrerasFiltradasPorDistancia(filtro, r.getDto());
            } else {
                return new Vector<>();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Vector<>();
        }
    }

    @Override
    public UsuarioCarrera actualizarCarrera(UsuarioCarrera usuarioCarrera) throws EntidadNoGuardadaException {
        if (usuarioCarrera.getId() != null) {
            return this.update(usuarioCarrera);
        } else {
            return this.grabar(usuarioCarrera);
        }

    }

    @Override
    public UsuarioCarrera findById(Class<UsuarioCarrera> clazz, Integer id) {
        try {
            URL url = new URL(this.getBaseURL() + GET_BY_ID + id);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(10000);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoInput(true);
            Gson g = new Gson();
            Respuesta<UsuarioCarrera> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(con.getInputStream())), new TypeToken<Respuesta<UsuarioCarrera>>() {
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
    public List<UsuarioCarrera> findAll(Class<UsuarioCarrera> clazz) {
        try {
            URL url = new URL(this.getBaseURL() + TODOS);


            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(10000);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoInput(true);
            Gson g = new Gson();
            Respuesta<List<UsuarioCarrera>> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(con.getInputStream())), new TypeToken<Respuesta<List<UsuarioCarrera>>>() {
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

    public List<UsuarioCarrera> getUsuarioCarrerasById(Class<UsuarioCarrera> clazz, Integer idUsuario) {
        try {
            URL url;
            if (idUsuario != null) {
                url = new URL(this.getBaseURL() + TODOS + idUsuario);
            } else {
                url = new URL(this.getBaseURL() + TODOS);
            }

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setConnectTimeout(10000);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setDoInput(true);
            Gson g = new Gson();
            Respuesta<List<UsuarioCarrera>> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(con.getInputStream())), new TypeToken<Respuesta<List<UsuarioCarrera>>>() {
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
    public UsuarioCarrera update(UsuarioCarrera entidad) throws EntidadNoGuardadaException {
        // the request
        try {
            HttpURLConnection conn = getPostHttpURLConnection(entidad, UPDATE);
            Gson g = new Gson();
            Respuesta<UsuarioCarrera> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(conn.getInputStream())), new TypeToken<Respuesta<UsuarioCarrera>>() {
            }.getType());

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


    public Integer syncCarreras(Collection<UsuarioCarreraDTO> entidades) throws EntidadNoGuardadaException {
        // the request
        try {
            HttpURLConnection conn = getPostHttpURLConnection(entidades, UPLOAD);
            Gson g = new Gson();
            Respuesta<Integer> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(conn.getInputStream())), new TypeToken<Respuesta<Integer>>() {
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
    public UsuarioCarrera grabar(UsuarioCarrera entidad) throws EntidadNoGuardadaException {
        // the request
        try {
            HttpURLConnection conn = getPostHttpURLConnection(entidad, SAVE);
            Gson g = new Gson();
            Respuesta<UsuarioCarrera> r = g.fromJson(new BufferedReader(
                    new InputStreamReader(conn.getInputStream())), new TypeToken<Respuesta<UsuarioCarrera>>() {
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
        return MODULO_USUARIO_CARRERA;
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

    protected HttpURLConnection getPostHttpURLConnection(Collection<UsuarioCarreraDTO> entidad, String service) throws IOException {
        URL url = new URL(this.getBaseURL() + service);
        Gson g = new Gson();
        String json = g.toJson(entidad);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setConnectTimeout(17000);
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

    protected HttpURLConnection getPostHttpURLConnection(UsuarioCarrera entidad, String service) throws IOException {
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

    private List<UsuarioCarrera> getUsuarioCarrerasFiltradasPorDistancia(Filtro filtro, List<UsuarioCarrera> resultados) {
        List<UsuarioCarrera> resultadosFinales = new Vector<>();

        String[] distancias = null;
        for (UsuarioCarrera cc : resultados) {
            distancias = cc.getCarrera().getDistanciaDisponible().split("/");
            for (String s : distancias) {
                try {
                    if (Double.valueOf(s.trim()) >= filtro.getMinDistancia() && Double.valueOf(s.trim()) <= filtro.getMaxDistancia()) {
                        resultadosFinales.add(cc);
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resultadosFinales;


    }

}
