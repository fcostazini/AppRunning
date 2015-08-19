package studios.thinkup.com.apprunning.model.entity;

import android.database.Cursor;

/**
 * Created by Facundo on 01/08/2015.
 * UsuarioCarreraDTO para poder hacer el upload mas simple
 */
public class UsuarioCarreraDTO {
    public static final String CARRERA = "CARRERA";
    public static final String TIEMPO = "TIEMPO";
    public static final String ANOTADO = "ANOTADO";
    public static final String ME_GUSTA = "ME_GUSTA";
    public static final String CORRIDA = "CORRIDA";
    public static final String ID = "ID_USUARIO_CARRERA";
    public static final String ID_USUARIO = "USUARIO";
    public static final String DISTANCIA = "DISTANCIA";
    public static final String MODALIDAD = "MODALIDAD";

    private Integer idUsuarioCarrera;
    private Integer idCarrera;
    private boolean corrida;
    private Double distancia;
    private String modalidad;
    private boolean meGusta;
    private boolean anotado;
    private Integer usuario;
    private Long tiempo;
    private String nombreCarrera;
    private String fechaCarrera;
    private String horaCarrera;
    private String provincia;

    public UsuarioCarreraDTO(UsuarioCarrera usuarioCarrera) {
        this.idCarrera = usuarioCarrera.getCarrera().getId();
        this.idUsuarioCarrera = usuarioCarrera.getId();
        this.anotado = usuarioCarrera.isAnotado();
        this.corrida = usuarioCarrera.isCorrida();
        this.meGusta = usuarioCarrera.isMeGusta();
        this.distancia = usuarioCarrera.getDistancia();
        this.modalidad = usuarioCarrera.getModalidad();
        this.tiempo = usuarioCarrera.getTiempo();
        this.usuario = usuarioCarrera.getUsuario();

    }

    public UsuarioCarreraDTO(Cursor c) {

        this.idUsuarioCarrera = c.getInt(c.getColumnIndex(UsuarioCarreraDTO.ID));
        this.anotado = c.getInt(c.getColumnIndex(UsuarioCarreraDTO.ANOTADO)) == 1;
        this.corrida = c.getInt(c.getColumnIndex(UsuarioCarreraDTO.CORRIDA)) == 1;
        this.meGusta = c.getInt(c.getColumnIndex(UsuarioCarreraDTO.ME_GUSTA)) == 1;
        this.tiempo = c.getLong(c.getColumnIndex(UsuarioCarreraDTO.TIEMPO));

        this.distancia = c.getDouble(c.getColumnIndex(UsuarioCarreraDTO.DISTANCIA));
        this.modalidad = c.getString(c.getColumnIndex(UsuarioCarreraDTO.MODALIDAD));

        this.usuario = c.getInt(c.getColumnIndex(UsuarioCarreraDTO.ID_USUARIO));
        this.idCarrera = c.getInt(c.getColumnIndex(UsuarioCarreraDTO.CARRERA));


    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public String getFechaCarrera() {
        return fechaCarrera;
    }

    public void setFechaCarrera(String fechaCarrera) {
        this.fechaCarrera = fechaCarrera;
    }

    public String getHoraCarrera() {
        return horaCarrera;
    }

    public void setHoraCarrera(String horaCarrera) {
        this.horaCarrera = horaCarrera;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Integer getIdUsuarioCarrera() {
        return idUsuarioCarrera;
    }

    public void setIdUsuarioCarrera(Integer idUsuarioCarrera) {
        this.idUsuarioCarrera = idUsuarioCarrera;
    }

    public Integer getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(Integer idCarrera) {
        this.idCarrera = idCarrera;
    }

    public boolean isCorrida() {
        return corrida;
    }

    public void setCorrida(boolean corrida) {
        this.corrida = corrida;
    }

    public Double getDistancia() {
        return distancia;
    }

    public void setDistancia(Double distancia) {
        this.distancia = distancia;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
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

    public Integer getUsuario() {
        return usuario;
    }

    public void setUsuario(Integer usuario) {
        this.usuario = usuario;
    }

    public Long getTiempo() {
        return tiempo;
    }

    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }
}
