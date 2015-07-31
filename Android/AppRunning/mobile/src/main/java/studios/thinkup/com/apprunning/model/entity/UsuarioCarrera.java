package studios.thinkup.com.apprunning.model.entity;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by FaQ on 08/06/2015.
 */
public class UsuarioCarrera implements Serializable, IEntity {
    public static final String CARRERA      =  "CARRERA"     ;
    public static final String TIEMPO       =  "TIEMPO"      ;
    public static final String ANOTADO      =  "ANOTADO"     ;
    public static final String ME_GUSTA     =  "ME_GUSTA"    ;
    public static final String CORRIDA      =  "CORRIDA"     ;
    public static final String ID           =  "ID_USUARIO_CARRERA"          ;
    public static final String ID_USUARIO           =  "USUARIO"          ;
    public static final String DISTANCIA           =  "DISTANCIA";
    public static final String MODALIDAD           =  "MODALIDAD"          ;
    private static final long serialVersionUID = 24474114445126838L;
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

    public UsuarioCarrera(Carrera c) {
        this.idUsuarioCarrera = null;
        this.anotado = false;
        this.corrida = false;
        this.meGusta = false;
        this.tiempo = 0l;

        this.distancia = 0;
        this.modalidad = "";
        this.carrera = c;
    }

    public UsuarioCarrera(Cursor c) {
        if (c.getInt(c.getColumnIndex(UsuarioCarrera.ID)) == 0) {
            this.idUsuarioCarrera = null;
            this.anotado = false;
            this.corrida = false;
            this.meGusta = false;
            this.tiempo = 0l;

            this.distancia = 0;
            this.modalidad = "";
        } else {
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

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
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
    }

    public boolean isMeGusta() {
        return meGusta;
    }

    public void setMeGusta(boolean meGusta) {
        this.meGusta = meGusta;
    }

    public boolean isAnotado() {
        return anotado;
    }

    public void setAnotado(boolean anotado) {
        this.anotado = anotado;
    }

    public Long getTiempo() {
        return tiempo;
    }

    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }

    public long getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(long velocidad) {
        this.velocidad = velocidad;
    }

    public String getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(String recorrido) {
        this.recorrido = recorrido;
    }

    public Integer getCodigoCarrera() {
        return carrera.getCodigo();
    }

    public String getFechaInicio() {
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

    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
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

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
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
