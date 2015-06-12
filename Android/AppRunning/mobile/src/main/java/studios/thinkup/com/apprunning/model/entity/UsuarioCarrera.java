package studios.thinkup.com.apprunning.model.entity;

import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.IObservableCarrera;
import studios.thinkup.com.apprunning.model.IObservadorCarrera;

/**
 * Created by FaQ on 08/06/2015.
 */
public class UsuarioCarrera extends SugarRecord<Carrera> implements IObservableCarrera,Serializable {

    private Carrera carrera;
    private boolean corrida;
    private boolean meGusta;
    private boolean anotado;
    private long tiempo;
    @Ignore
    private long velocidad;
    @Ignore
    private String recorrido;
    @Ignore
    private List<IObservadorCarrera> observadores;

    public UsuarioCarrera() {
        this.observadores = new Vector<>();
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public boolean isCorrida() {
        return corrida;
    }

    public void setCorrida(boolean corrida) {
        this.corrida = corrida;
        this.actualizarObservadores();
    }

    public boolean isMeGusta() {
        return meGusta;
    }

    public void setMeGusta(boolean meGusta) {
        this.meGusta = meGusta;
        this.actualizarObservadores();
    }

    public boolean isAnotado() {
        return anotado;
    }

    public void setAnotado(boolean anotado) {
        this.anotado = anotado;
        this.actualizarObservadores();
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
        this.actualizarObservadores();
    }

    public long getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(long velocidad) {
        this.velocidad = velocidad;
        this.actualizarObservadores();
    }

    public String getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(String recorrido) {
        this.recorrido = recorrido;
        this.actualizarObservadores();
    }

    @Override
    public void registrarObservador(IObservadorCarrera observador) {
        this.observadores.add(observador);
    }

    @Override
    public void actualizarObservadores() {
     this.save();
    }

    public Integer getCodigoCarrera() {
        return carrera.getCodigo();
    }

    public Date getFechaInicio() {
        return carrera.getFechaInicio();
    }

    public String getDireccion() {
        return carrera.getDireccion();
    }

    public String getUrlWeb() {
        return carrera.getUrlWeb();
    }

    public Integer getDistancia() {
        return carrera.getDistancia();
    }

    public String getUrlImage() {
        return carrera.getUrlImagen();
    }

    public String getNombre() {
        return carrera.getNombre();
    }


    public Integer getCodigo() {
        return carrera.getCodigo();
    }

    public String getCiudad() {
        return carrera.getCiudad();
    }

    public String getModalidad() {
        return carrera.getModalidad();
    }

    public String getUrlImagen() {
        return carrera.getUrlImagen();
    }

    public String getDescripcion() {
        return carrera.getDescripcion();
    }
}
