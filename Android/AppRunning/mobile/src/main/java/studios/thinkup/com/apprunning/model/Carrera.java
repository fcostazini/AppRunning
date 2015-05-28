package studios.thinkup.com.apprunning.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fcostazini on 22/05/2015.
 * Entidad de Modelo CARRERA
 */
public class Carrera implements Serializable {

    public static final String NOMBRE_FIELD = "nombre";
    public static final String ZONA_FIELD = "zona";
    public static final String GENERO_FIELD = "genero";
    public static final String DISTANCIA_FIELD = "distancia";
    public static final String FECHA_LARGADA_FIELD = "fecha_largada";
    public static final String ID = "ID";

    private Integer codigoCarrera;
    private String nombre;
    private Date fechaInicio;
    private Integer distancia;
    private String descripcion;
    private String urlImage;
    private Genero genero;
    private String direccion;
    private EstadoCarrera estadoCarrera;

    public static CarreraBuilder getBuilder() {

        return new CarreraBuilder();
    }

    public Carrera(Integer codigoCarrera, String nombre, Date fechaInicio, Integer distancia,
                   String descripcion, String urlImage, Genero genero, EstadoCarrera estadoCarrera, String direccion) {
        this.codigoCarrera = codigoCarrera;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.distancia = distancia;
        this.descripcion = descripcion;
        this.urlImage = urlImage;
        this.genero = genero;
        this.estadoCarrera = estadoCarrera;
        this.direccion = direccion;
    }

    private Carrera() {

    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public Integer getCodigoCarrera() {
        return codigoCarrera;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Integer getDistancia() {
        return distancia;
    }

    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public EstadoCarrera getEstadoCarrera() {
        return estadoCarrera;
    }

    public void setEstadoCarrera(EstadoCarrera estadoCarrera) {
        this.estadoCarrera = estadoCarrera;
    }

    public static class CarreraBuilder {
        private Carrera instance = new Carrera();

        public CarreraBuilder nombre(String nombre) {
            this.instance.setNombre(nombre);
             return this;
        }

        public CarreraBuilder fechaInicio(Date fechaInicio) {
            this.instance.fechaInicio = fechaInicio;
             return this;
        }

        public CarreraBuilder codigoCarrera(Integer codigo) {
            this.instance.codigoCarrera = codigo;
             return this;
        }

        public CarreraBuilder distancia(Integer distancia) {
            this.instance.distancia = distancia;
             return this;
        }

        public CarreraBuilder descripcion(String descripcion) {
            this.instance.descripcion = descripcion;
             return this;
        }

        public CarreraBuilder urlImage(String urlImage) {
            this.instance.urlImage = urlImage;
             return this;
        }

        public CarreraBuilder estado(EstadoCarrera estado) {
            this.instance.estadoCarrera = estado;
            return this;
        }
        public CarreraBuilder genero(Genero genero) {
            this.instance.genero = genero;
             return this;
        }

        public CarreraBuilder direccion(String direccion) {
            this.instance.direccion = direccion;
            return this;
        }
        public Carrera build(){

             return this.instance;
        }
    }
}
