package studios.thinkup.com.apprunning.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import studios.thinkup.com.apprunning.model.entity.Genero;

/**
 * Created by fcostazini on 22/05/2015.
 * <p/>
 * Filtros de busqueda para carreras
 */
public class Filtro implements Serializable {

    private String nombreCarrera;
    private Date fechaDesde;
    private Date fechaHasta;
    private Integer distanciaMin;
    private Integer distanciaMax;
    private Genero genero;
    private String zona;
    private long idUsuario;
    private Boolean meGusta = null;
    private Boolean inscripto = null;
    private Boolean corrida = null;


    public Filtro(DefaultSettings defaultSettings) {
        this.idUsuario = -1;
        this.nombreCarrera = "";
        this.fechaDesde = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(this.fechaDesde);
        c.add(Calendar.DATE, defaultSettings.getDiasBusqueda());
        this.fechaHasta = c.getTime();
        this.zona = defaultSettings.getZona();
        this.distanciaMin = defaultSettings.getDistanciaMin();
        this.distanciaMax = defaultSettings.getDistanciaMax();
        this.genero = defaultSettings.getGenero();
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


    public Genero getGenero() {
        return this.genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getZona() {

        return this.zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    @Override
    public String toString() {
        String s = "";
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        if (!this.getZona().isEmpty()) s += "zona = '" + this.getZona() + "'; \n";

        if (this.getNombreCarrera() != null && !this.getNombreCarrera().isEmpty())
            s += "nombre = '" + this.getNombreCarrera() + "'; \n";
        if (this.getFechaDesde() != null) {
            s += "desde = '" + sf.format(this.getFechaDesde()) + "'; \n";
        }

        if (this.getFechaHasta() != null) {
            s += "hasta = '" + sf.format(this.getFechaHasta()) + "'; \n";
        }
        if (this.getDistanciaMin() != null) {
            s += "Km desde = '" + this.getDistanciaMin() + "'; \n";
        }
        if (this.getDistanciaMax() != null) {
            s += "Km hasta = '" + this.getDistanciaMax() + "'; \n";
        }
        if (this.getGenero() != null) s += "genero = '" + this.getGenero() + "'; \n";
        return s;
    }

    public Integer getDistanciaMin() {
        return distanciaMin;
    }

    public void setDistanciaMin(Integer distanciaMin) {
        this.distanciaMin = distanciaMin;
    }

    public Integer getDistanciaMax() {

        return distanciaMax;

    }

    public void setDistanciaMax(Integer distanciaMax) {
        this.distanciaMax = distanciaMax;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Filtro filtro = (Filtro) o;

        if (corrida != null ? !corrida.equals(filtro.corrida) : filtro.corrida != null)
            return false;
        if (distanciaMax != null ? !distanciaMax.equals(filtro.distanciaMax) : filtro.distanciaMax != null)
            return false;
        if (distanciaMin != null ? !distanciaMin.equals(filtro.distanciaMin) : filtro.distanciaMin != null)
            return false;
        if (fechaDesde != null ? !fechaDesde.equals(filtro.fechaDesde) : filtro.fechaDesde != null)
            return false;
        if (fechaHasta != null ? !fechaHasta.equals(filtro.fechaHasta) : filtro.fechaHasta != null)
            return false;
        if (genero != filtro.genero) return false;
        if (idUsuario == filtro.idUsuario)
            return false;
        if (inscripto != null ? !inscripto.equals(filtro.inscripto) : filtro.inscripto != null)
            return false;
        if (meGusta != null ? !meGusta.equals(filtro.meGusta) : filtro.meGusta != null)
            return false;
        if (nombreCarrera != null ? !nombreCarrera.equals(filtro.nombreCarrera) : filtro.nombreCarrera != null)
            return false;
        if (zona != null ? !zona.equals(filtro.zona) : filtro.zona != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = nombreCarrera != null ? nombreCarrera.hashCode() : 0;
        result = 31 * result + (fechaDesde != null ? fechaDesde.hashCode() : 0);
        result = 31 * result + (fechaHasta != null ? fechaHasta.hashCode() : 0);
        result = 31 * result + (distanciaMin != null ? distanciaMin.hashCode() : 0);
        result = 31 * result + (distanciaMax != null ? distanciaMax.hashCode() : 0);
        result = 31 * result + (genero != null ? genero.hashCode() : 0);
        result = 31 * result + (zona != null ? zona.hashCode() : 0);
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
        f.setZona(this.zona);
        f.setDistanciaMax(this.distanciaMax);
        f.setDistanciaMin(this.distanciaMin);
        f.setIdUsuario(this.getIdUsuario());
        f.setGenero(this.genero);
        f.setNombreCarrera(this.nombreCarrera);
        return f;

    }
}
