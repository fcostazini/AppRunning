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
    private boolean fueCorrida;
    private boolean estoyInscripto;
    private boolean meGusta;
    private long tiempo;

    public static CarreraBuilder getBuilder() {

        return new CarreraBuilder();
    }

    public Carrera(Integer codigoCarrera, String nombre, Date fechaInicio, Integer distancia,
                   String descripcion, String urlImage, Genero genero,
                   String direccion, boolean fueCorrida, boolean estoyInscripto, boolean meGusta) {
        this.codigoCarrera = codigoCarrera;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.distancia = distancia;
        this.descripcion = descripcion;
        this.urlImage = urlImage;
        this.genero = genero;
        this.direccion = direccion;
        this.fueCorrida = fueCorrida;
        this.estoyInscripto = estoyInscripto;
        this.meGusta = meGusta;
    }

    private Carrera() {

    }

    public boolean isMeGusta() {
        return meGusta;
    }

    public boolean isEstoyInscripto() {
        return estoyInscripto;
    }

    public boolean isFueCorrida() {
        return fueCorrida;
    }

    public String getDireccion() {
        return direccion;
    }

    public Genero getGenero() {
        return genero;
    }

    public Integer getCodigoCarrera() {
        return codigoCarrera;
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

    public String getUrlImage() {
        return urlImage;
    }

    public void setCorrida(boolean corrida) {
        this.fueCorrida = corrida;
    }
    public void setMeGusta(boolean meGusta) {
        this.meGusta = meGusta;
    }
    public void setInscripto(boolean inscripto) {
        this.estoyInscripto = inscripto;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }

    public long getTiempo() {
        return tiempo;
    }

    public static class CarreraBuilder {
        private Carrera instance = new Carrera();

        public CarreraBuilder nombre(String nombre) {
            this.instance.nombre = nombre;
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

        public CarreraBuilder fueCorrida(boolean flag) {
            this.instance.fueCorrida = flag;
            return this;
        }

        public CarreraBuilder estoyInscripto(boolean flag) {
            this.instance.estoyInscripto = flag;
            return this;
        }

        public CarreraBuilder meGusta(boolean flag) {
            this.instance.meGusta = flag;
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

        public Carrera build() {

            return this.instance;
        }
    }


}
