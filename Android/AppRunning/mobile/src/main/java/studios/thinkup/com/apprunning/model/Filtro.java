package studios.thinkup.com.apprunning.model;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import studios.thinkup.com.apprunning.model.entity.Modalidad;

/**
 * Created by fcostazini on 22/05/2015.
 * <p/>
 * Filtros de busqueda para carreras
 */
public class Filtro implements Serializable {

    private static final long serialVersionUID = 244741194428126838L;

    private String nombreCarrera;
    private Date fechaDesde;
    private Date fechaHasta;
    private Integer minDistancia;
    private Integer maxDistancia;
    private Modalidad modalidad;
    private String ciudad;
    private String provincia;
    private long idUsuario;
    private Boolean meGusta = null;
    private Boolean inscripto = null;
    private Boolean corrida = null;

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public Filtro(DefaultSettings defaultSettings) {
        this.idUsuario = -1;
        this.nombreCarrera = "";
        this.fechaDesde = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(this.fechaDesde);
        c.add(Calendar.DATE, defaultSettings.getDiasBusqueda());
        this.fechaHasta = c.getTime();
        //this.ciudad = defaultSettings.getZona();
        this.minDistancia = defaultSettings.getDistanciaMin();
        this.maxDistancia = defaultSettings.getDistanciaMax();
        this.modalidad = defaultSettings.getModalidad();
        this.nombreCarrera = "";
        meGusta = null;
        inscripto = null;
        corrida = null;
    }

    public Boolean getMeGusta() {
        return meGusta;
    }

    public void setMeGusta(Boolean meGusta) {
        this.meGusta = meGusta;
    }

    public Boolean getInscripto() {
        return inscripto;
    }

    public void setInscripto(Boolean inscripto) {
        this.inscripto = inscripto;
    }

    public Boolean getCorrida() {
        return corrida;
    }

    public void setCorrida(Boolean corrida) {
        this.corrida = corrida;
    }

    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Filtro() {
        this.minDistancia = 0;
        this.maxDistancia = 100;
    }


    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public Date getFechaDesde() {

        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {

        return this.fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }


    public Modalidad getModalidad() {
        return this.modalidad;
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad;
    }

    public String getCiudad() {

        return this.ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public Integer getMinDistancia() {
        return minDistancia;
    }

    public void setMinDistancia(Integer minDistancia) {
        this.minDistancia = minDistancia;
    }

    public Integer getMaxDistancia() {
        return maxDistancia;
    }

    public void setMaxDistancia(Integer maxDistancia) {
        this.maxDistancia = maxDistancia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filtro filtro = (Filtro) o;

        if (corrida != null ? !corrida.equals(filtro.corrida) : filtro.corrida != null)
            return false;
        if (fechaDesde != null ? !fechaDesde.equals(filtro.fechaDesde) : filtro.fechaDesde != null)
            return false;
        if (fechaHasta != null ? !fechaHasta.equals(filtro.fechaHasta) : filtro.fechaHasta != null)
            return false;
        if (modalidad != filtro.modalidad) return false;
        if (idUsuario == filtro.idUsuario)
            return false;
        if (inscripto != null ? !inscripto.equals(filtro.inscripto) : filtro.inscripto != null)
            return false;
        if (meGusta != null ? !meGusta.equals(filtro.meGusta) : filtro.meGusta != null)
            return false;
        if (nombreCarrera != null ? !nombreCarrera.equals(filtro.nombreCarrera) : filtro.nombreCarrera != null)
            return false;
        if (ciudad != null ? !ciudad.equals(filtro.ciudad) : filtro.ciudad != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nombreCarrera != null ? nombreCarrera.hashCode() : 0;
        result = 31 * result + (fechaDesde != null ? fechaDesde.hashCode() : 0);
        result = 31 * result + (fechaHasta != null ? fechaHasta.hashCode() : 0);
        result = 31 * result + (modalidad != null ? modalidad.hashCode() : 0);
        result = 31 * result + (ciudad != null ? ciudad.hashCode() : 0);
        result = 31 * result + (meGusta != null ? meGusta.hashCode() : 0);
        result = 31 * result + (inscripto != null ? inscripto.hashCode() : 0);
        result = 31 * result + (corrida != null ? corrida.hashCode() : 0);
        return result;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Filtro f = new Filtro();
        f.setCorrida(this.corrida);
        f.setMeGusta(this.meGusta);
        f.setInscripto(this.inscripto);
        f.setFechaDesde(this.fechaDesde);
        f.setFechaHasta(this.fechaHasta);
        f.setCiudad(this.ciudad);
        f.setProvincia(this.provincia);
        f.setMinDistancia(this.minDistancia);
        f.setMaxDistancia(this.maxDistancia);
        f.setIdUsuario(this.getIdUsuario());
        f.setModalidad(this.modalidad);
        f.setNombreCarrera(this.nombreCarrera);
        return f;

    }
}
