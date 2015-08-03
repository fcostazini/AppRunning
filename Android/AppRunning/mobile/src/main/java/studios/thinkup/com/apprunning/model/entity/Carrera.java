package studios.thinkup.com.apprunning.model.entity;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by fcostazini on 22/05/2015.
 * Entidad de Modelo CARRERA
 */
public class Carrera implements Serializable, IEntity {

    public static final String NOMBRE = "NOMBRE";
    public static final String MODALIDADES = "MODALIDADES";
    public static final String PROVINCIA = "PROVINCIA";
    public static final String CIUDAD = "CIUDAD";
    public static final String DIRECCION = "DIRECCION";
    public static final String FECHA_INICIO = "FECHA_INICIO";
    public static final String HORA_INICIO = "HORA_INICIO";
    public static final String DESCRIPCION = "DESCRIPCION";
    public static final String URL_WEB = "URL_WEB";
    public static final String RECOMENDADA = "RECOMENDADA";
    public static final String URL_IMAGEN = "URL_IMAGEN";
    public static final String DISTANCIAS_DISPONIBLE = "DISTANCIA_DISPONIBLE";
    public static final String ID = "ID_CARRERA";
    private static final long serialVersionUID = 24474119433126838L;
    private Integer idCarrera;
    private String nombre;
    private String provincia;
    private String ciudad;
    private String direccion;
    private String fechaInicio;
    private String horaInicio;
    private String descripcion;

    private String distanciaDisponible;
    private String modalidades;
    private String urlWeb;
    private String urlImagen;



    public Carrera() {

    }

    public Carrera(Cursor c) {
        try {


            this.idCarrera = c.getInt(c.getColumnIndex(Carrera.ID));
            this.nombre = c.getString(c.getColumnIndex(NOMBRE));
            this.modalidades = c.getString(c.getColumnIndex(MODALIDADES));
            this.provincia = c.getString(c.getColumnIndex(PROVINCIA));
            this.ciudad = c.getString(c.getColumnIndex(CIUDAD));
            this.direccion = c.getString(c.getColumnIndex(DIRECCION));
            this.fechaInicio = c.getString(c.getColumnIndex(FECHA_INICIO));
            this.horaInicio = c.getString(c.getColumnIndex(HORA_INICIO));
            this.descripcion = c.getString(c.getColumnIndex(DESCRIPCION));
            this.distanciaDisponible = c.getString(c.getColumnIndex(DISTANCIAS_DISPONIBLE));
            this.urlWeb = c.getString(c.getColumnIndex(URL_WEB));
            this.urlImagen = c.getString(c.getColumnIndex(URL_IMAGEN));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public Integer getCodigo() {
        return this.getId();
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public String getDistanciaDisponible() {
        return distanciaDisponible;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getModalidades() {
        return modalidades;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getUrlWeb() {
        return urlWeb;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    @Override
    public Integer getId() {
        return idCarrera;
    }

    @Override
    public void setId(Integer id) {
        this.idCarrera = id;
    }

    @Override
    public String getNombreId() {
        return "ID_CARRERA";
    }

    @Override
    public ArrayList<String> getIgnoredFields() {

        ArrayList<String> lista = new ArrayList<>();

        return lista;
    }
}