package studios.thinkup.com.apprunning.model.entity;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.IObservableCarrera;
import studios.thinkup.com.apprunning.model.IObservadorCarrera;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;
import studios.thinkup.com.apprunning.provider.helper.Id;
import studios.thinkup.com.apprunning.provider.helper.Ignore;

/**
 * Created by FaQ on 08/06/2015.
 */
public class UsuarioCarrera implements IObservableCarrera, Serializable, IEntity {
    private static final long serialVersionUID = 24474114445126838L;
    public static final String CARRERA      =  "CARRERA"     ;
    public static final String TIEMPO       =  "TIEMPO"      ;
    public static final String ANOTADO      =  "ANOTADO"     ;
    public static final String ME_GUSTA     =  "ME_GUSTA"    ;
    public static final String CORRIDA      =  "CORRIDA"     ;
    public static final String ID           =  "ID_USUARIO_CARRERA"          ;
    public static final String ID_USUARIO           =  "USUARIO"          ;
    public static final String DISTANCIA           =  "DISTANCIA";
    public static final String MODALIDAD           =  "MODALIDAD"          ;



    private Integer idUsuarioCarrera;
    private Carrera carrera;
    private boolean corrida;
    private Integer distancia;
    private String modalidad;
    private boolean meGusta;
    private boolean anotado;
    private Integer usuario;
    private Long tiempo;

    private long velocidad;

    private String recorrido;

    private List<IObservadorCarrera> observadores;

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
        this.actualizarObservadores();
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
        this.actualizarObservadores();
    }

    public UsuarioCarrera() {
        this.observadores = new Vector<>();
    }
    public UsuarioCarrera(Cursor c){
        this();
        if( c.getInt(c.getColumnIndex(UsuarioCarrera.ID) )== 0){
            this.idUsuarioCarrera =null;
            this.anotado = false;
            this.corrida = false;
            this.meGusta = false;
            this.tiempo = 0l;

            this.distancia = 0;
            this.modalidad = "";
        }else{
            this.idUsuarioCarrera = c.getInt(c.getColumnIndex(UsuarioCarrera.ID));
            this.anotado = c.getInt(c.getColumnIndex(UsuarioCarrera.ANOTADO)) == 1;
            this.corrida = c.getInt(c.getColumnIndex(UsuarioCarrera.CORRIDA)) == 1;
            this.meGusta = c.getInt(c.getColumnIndex(UsuarioCarrera.ME_GUSTA)) == 1;
            this.tiempo = c.getLong(c.getColumnIndex(UsuarioCarrera.TIEMPO));

            this.distancia = c.getInt(c.getColumnIndex(UsuarioCarrera.DISTANCIA));
            this.modalidad = c.getString(c.getColumnIndex(UsuarioCarrera.MODALIDAD));
        }
        this.usuario = c.getInt(c.getColumnIndex(UsuarioCarrera.ID_USUARIO));
        this.setCarrera(new Carrera(c));
    }

    public String getProvincia() {
        return carrera.getProvincia();
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

    public Long getTiempo() {
        return tiempo;
    }

    public void setTiempo(Long tiempo) {
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
        UsuarioCarrera uc = null;
        try {
            for (IObservadorCarrera ob : this.observadores) {
                uc = ob.actualizarCarrera(this);
            }
        } catch (EntidadNoGuardadaException e) {
            e.printStackTrace();
        }
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
        return this.distancia;
    }

    public String getUrlImage() {
        return carrera.getUrlImagen();
    }

    public String getNombre() {
        return carrera.getNombre();
    }

    public Integer getId() {
        return idUsuarioCarrera;
    }

    @Override
    public void setId(Integer id) {
        this.idUsuarioCarrera = id;
    }

    @Override
    public String getNombreId() {
        return "ID_USUARIO_CARRERA";
    }

    @Override
    public ArrayList<String> getIgnoredFields() {
        ArrayList<String> ignored = new ArrayList<>();
        ignored.add("idUsuarioCarrera");
        ignored.add("velocidad");
        ignored.add("observadores");
        ignored.add("recorrido");
        return ignored;
    }

    public Integer getCodigo() {
        return carrera.getCodigo();
    }

    public String getCiudad() {
        return carrera.getCiudad();
    }

    public String getModalidad() {
        return this.modalidad;
    }
    public String getModalidades() { return this.getCarrera().getModalidades();}

    public String getDistancias() { return this.getCarrera().getDistancias();}

    public String getUrlImagen() {
        return carrera.getUrlImagen();
    }

    public String getDescripcion() {
        return carrera.getDescripcion();
    }
    public String getHora(){return carrera.getHora();}
    public String getFullDireccion() {
        String direccionStr ="";
        if(this.getDireccion()!= null && !this.getDireccion().isEmpty() ){
            direccionStr = this.getDireccion();
        }
        if(this.getCiudad()!= null && !this.getCiudad().isEmpty()){
            direccionStr += direccionStr.isEmpty()?this.getCiudad(): ", " + this.getCiudad();
        }
        if(this.getProvincia()!= null && !this.getProvincia().isEmpty()){
            if(this.getCiudad()!=null && !this.getCiudad().isEmpty() && !this.getProvincia().equals(this.getCiudad())) {
                direccionStr += direccionStr.isEmpty() ? this.getProvincia() : ", " + this.getProvincia();

            }
        }

        return direccionStr;
    }

}
