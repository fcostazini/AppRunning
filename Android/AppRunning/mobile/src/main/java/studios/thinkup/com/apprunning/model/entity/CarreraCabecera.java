package studios.thinkup.com.apprunning.model.entity;

import java.io.Serializable;

/**
 * Created by fcostazini on 22/05/2015.
 * Cabecera de de carrera
 */
public class CarreraCabecera implements Serializable {

    private Integer codigoCarrera;
    private String nombre;
    private String fechaInicio;
    private String hora;
    private String descripcion;
    private String urlImagen;
    private String provincia;
    private String distanciaDisponible;
    private String zona;
    private UsuarioCarrera usuarioCarrera;


    private CarreraCabecera() {
    }

    public static CarreraCabeceraBuilder getBuilder() {
        return new CarreraCabeceraBuilder();
    }

    public UsuarioCarrera getUsuarioCarrera() {
        return usuarioCarrera;
    }

    public void setUsuarioCarrera(UsuarioCarrera usuarioCarrera) {
        this.usuarioCarrera = usuarioCarrera;
    }

    public String getZona() {
        return this.zona;
    }

    public String getDistanciaDisponible() {
        return distanciaDisponible;
    }

    public String getNombre() {
        return nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public Integer getDistancia() {
        if (this.usuarioCarrera != null && this.isEstoyInscripto()) {
            return this.usuarioCarrera.getDistancia();
        }
        return 0;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUrlImagen() {
        return urlImagen;
    }

    public Integer getCodigoCarrera() {
        return this.codigoCarrera;
    }

    public boolean isFueCorrida() {
        return usuarioCarrera != null && usuarioCarrera.isCorrida();
    }

    public boolean isEstoyInscripto() {
        return usuarioCarrera != null && usuarioCarrera.isAnotado();
    }

    public boolean isMeGusta() {
        return usuarioCarrera != null && usuarioCarrera.isMeGusta();
    }

    public String getHora() {
        return hora;
    }

    public static class CarreraCabeceraBuilder {
        private CarreraCabecera instance = new CarreraCabecera();

        public CarreraCabeceraBuilder nombre(String nombre) {
            this.instance.nombre = nombre;
            return this;
        }

        public CarreraCabeceraBuilder fechaInicio(String fechaInicio) {
            this.instance.fechaInicio = fechaInicio;
            return this;
        }

        public CarreraCabeceraBuilder codigoCarrera(Integer codigo) {
            this.instance.codigoCarrera = codigo;
            return this;
        }

        public CarreraCabeceraBuilder distanciaDisponible(String distancia) {
            this.instance.distanciaDisponible = distancia;
            return this;
        }

        public CarreraCabeceraBuilder descripcion(String descripcion) {
            this.instance.descripcion = descripcion;
            return this;
        }

        public CarreraCabeceraBuilder urlImage(String urlImage) {
            this.instance.urlImagen = urlImage;
            return this;
        }

        public CarreraCabeceraBuilder zona(String zona) {
            this.instance.zona = zona;
            return this;
        }

        public CarreraCabeceraBuilder hora(String hora) {
            if (hora == null || hora.isEmpty()) {
                this.instance.hora = "-:-";
            } else {
                this.instance.hora = hora;
            }

            return this;
        }

        public CarreraCabecera build() {

            return this.instance;
        }

        public CarreraCabeceraBuilder provincia(String provincia) {
            this.instance.provincia = provincia;
            return this;
        }
    }

}
