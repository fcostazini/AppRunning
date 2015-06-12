package studios.thinkup.com.apprunning.model.entity;

import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fcostazini on 22/05/2015.
 * Entidad de Modelo CARRERA
 */
public class Carrera extends SugarRecord<Carrera> implements Serializable {

    public static final String ID = "ID";
    public static final String NOMBRE_FIELD = "NOMBRE";
    public static final String CIUDAD_FIELD = "CIUDAD";
    public static final String MODALIDAD_FIELD = "MODALIDAD";
    public static final String FECHA_LARGADA_FIELD = "FECHA_INICIO";
    public static final String DISTANCIA_FIELD = "DISTANCIA";


    private String nombre;
    private String modalidad;
    private String ciudad;
    private String direccion;
    private Date fechaInicio;
    private String descripcion;
    private Integer distancia;
    private String urlWeb;
    private String urlImagen;





    public Carrera(){

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
}