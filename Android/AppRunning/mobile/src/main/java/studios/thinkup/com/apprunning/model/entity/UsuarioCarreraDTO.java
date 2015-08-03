
package studios.thinkup.com.apprunning.model.entity;

/**
 * Created by Facundo on 01/08/2015.
 * UsuarioCarreraDTO para poder hacer el upload mas simple
 */
public class UsuarioCarreraDTO {

    private Integer idUsuarioCarrera;
    private Integer idCarrera;
    private boolean corrida;
    private Integer distancia;
    private String modalidad;
    private boolean meGusta;
    private boolean anotado;
    private String usuario;
    private Long tiempo;

    public UsuarioCarreraDTO(UsuarioCarrera usuarioCarrera, String usuarioEmail) {
        this.idCarrera = usuarioCarrera.getCarrera().getId();
        this.idUsuarioCarrera = usuarioCarrera.getId();
        this.anotado = usuarioCarrera.isAnotado();
        this.corrida = usuarioCarrera.isCorrida();
        this.meGusta = usuarioCarrera.isMeGusta();
        this.distancia = usuarioCarrera.getDistancia();
        this.modalidad = usuarioCarrera.getModalidad();
        this.tiempo = usuarioCarrera.getTiempo();
        this.usuario = usuarioEmail;

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

    public Integer getDistancia() {
        return distancia;
    }

    public void setDistancia(Integer distancia) {
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

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Long getTiempo() {
        return tiempo;
    }

    public void setTiempo(Long tiempo) {
        this.tiempo = tiempo;
    }
}
