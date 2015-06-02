package studios.thinkup.com.apprunning.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fcostazini on 22/05/2015.
 * Cabecera de de carrera
 */
public class CarreraCabecera implements Serializable {

    private Integer codigoCarrera;
    private String nombre;
    private Date fechaInicio;
    private Integer distancia;
    private String descripcion;
    private String urlImage;
    private String zona;
    private boolean fueCorrida;
    private boolean estoyInscripto;
    private boolean meGusta;

    public CarreraCabecera(Integer codigoCarrera, String nombre, Date fechaInicio,
                           Integer distancia, String descripcion, String urlImage,
                           boolean fueCorrida, boolean estoyInscripto, boolean meGusta, String zona) {
        this.codigoCarrera = codigoCarrera;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.distancia = distancia;
        this.descripcion = descripcion;
        this.urlImage = urlImage;
        this.fueCorrida = fueCorrida;
        this.estoyInscripto = estoyInscripto;
        this.meGusta = meGusta;
        this.zona = zona;
    }

    private CarreraCabecera(){


    }

    public String getZona() {
        return zona;
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

    public Integer getCodigoCarrera() {
        return this.codigoCarrera;
    }

    public boolean isFueCorrida() {
        return fueCorrida;
    }

    public boolean isEstoyInscripto() {
        return estoyInscripto;
    }

    public boolean isMeGusta() {
        return meGusta;
    }
    public static CarreraCabeceraBuilder getBuilder(){ return new CarreraCabeceraBuilder();}
    public static class CarreraCabeceraBuilder {
        private CarreraCabecera instance = new CarreraCabecera();

        public CarreraCabeceraBuilder nombre(String nombre) {
            this.instance.nombre = nombre;
            return this;
        }

        public CarreraCabeceraBuilder fechaInicio(Date fechaInicio) {
            this.instance.fechaInicio = fechaInicio;
            return this;
        }

        public CarreraCabeceraBuilder codigoCarrera(Integer codigo) {
            this.instance.codigoCarrera = codigo;
            return this;
        }

        public CarreraCabeceraBuilder distancia(Integer distancia) {
            this.instance.distancia = distancia;
            return this;
        }

        public CarreraCabeceraBuilder descripcion(String descripcion) {
            this.instance.descripcion = descripcion;
            return this;
        }

        public CarreraCabeceraBuilder urlImage(String urlImage) {
            this.instance.urlImage = urlImage;
            return this;
        }

        public CarreraCabeceraBuilder fueCorrida(boolean flag) {
            this.instance.fueCorrida = flag;
            return this;
        }

        public CarreraCabeceraBuilder estoyInscripto(boolean flag) {
            this.instance.estoyInscripto = flag;
            return this;
        }

        public CarreraCabeceraBuilder meGusta(boolean flag) {
            this.instance.meGusta = flag;
            return this;
        }

        public CarreraCabeceraBuilder zona(String zona){
            this.instance.zona = zona;
            return this;
        }
        public CarreraCabecera build() {

            return this.instance;
        }
    }
}
