package studios.thinkup.com.apprunning.model.entity;

import android.database.Cursor;

import java.io.Serializable;
import java.util.Date;

import studios.thinkup.com.apprunning.provider.helper.Id;

/**
 * Created by fcostazini on 22/05/2015.
 * Entidad de Modelo CARRERA
 */
public class Carrera implements Serializable, IEntity {

    public static final String NOMBRE       =  "NOMBRE"      ;
    public static final String DISTANCIA    =  "DISTANCIA"   ;
    public static final String MODALIDAD    =  "MODALIDAD"   ;
    public static final String CIUDAD       =  "CIUDAD"      ;
    public static final String DIRECCION    =  "DIRECCION"   ;
    public static final String FECHA_INICIO =  "FECHA_INICIO";
    public static final String DESCRIPCION  =  "DESCRIPCION" ;
    public static final String URL_WEB      =  "URL_WEB"     ;
    public static final String RECOMENDADA  =  "RECOMENDADA" ;
    public static final String URL_IMAGEN   =  "URL_IMAGEN"  ;
    public static final String ID           =  "ID_CARRERA"          ;

    @Id
    private Integer id;
    private String   nombre;
    private String  modalidad;
    private String  ciudad;
    private String  direccion;
    private Date   fechaInicio;
    private String descripcion;
    private Integer distancia;
    private String urlWeb;
    private String urlImagen;


    public Carrera() {

    }
    public Carrera(Cursor c){
        this.id = c.getInt(c.getColumnIndex(Carrera.ID));
        this.nombre = c.getString(c.getColumnIndex(NOMBRE));
        this.modalidad = c.getString(c.getColumnIndex(MODALIDAD));
        this.ciudad = c.getString(c.getColumnIndex(CIUDAD));
        this.direccion = c.getString(c.getColumnIndex(DIRECCION));
        this.fechaInicio = new Date(c.getInt(c.getColumnIndex(FECHA_INICIO)));
        this.descripcion = c.getString(c.getColumnIndex(DESCRIPCION));
        this.distancia = c.getInt(c.getColumnIndex(DISTANCIA));
        this.urlWeb = c.getString(c.getColumnIndex(URL_WEB));
        this.urlImagen= c.getString(c.getColumnIndex(URL_IMAGEN));

    }
    public Integer getCodigo() {
        return this.getId().intValue();
    }

    public String getNombre() {
        return nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public String getModalidad() {
        return modalidad;
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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getNombreId() {
        return "ID_CARRERA";
    }
}