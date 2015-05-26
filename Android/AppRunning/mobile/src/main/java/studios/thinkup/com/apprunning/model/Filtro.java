package studios.thinkup.com.apprunning.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by fcostazini on 22/05/2015.
 *
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
    private DefaultSettings defaultSettings;
    private Subcategoria subcategoria;

    public Subcategoria getSubcategoria() {
        if(this.subcategoria == null){
            return Subcategoria.ZONA;
        }
        return subcategoria;
    }

    public void setSubcategoria(Subcategoria subcategoria) {
        this.subcategoria = subcategoria;
    }

    public Filtro(DefaultSettings defaultSettings) {
        this.fechaDesde = new Date();
        this.defaultSettings = defaultSettings;
        this.nombreCarrera ="";
    }

    public DefaultSettings getDefaultSettings() {
        return defaultSettings;
    }

    public Subcategoria nextCategoria(Subcategoria tipo) {
        if(this.getSubcategoria()!=null) {
            switch (tipo) {
                case ZONA:
                    return Subcategoria.DISTANCIA;
                case DISTANCIA:
                    return Subcategoria.GENERO;
                case GENERO:
                    return Subcategoria.ZONA;
                default:
                    return Subcategoria.ZONA;
            }
        }else{
            return Subcategoria.ZONA;
        }

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
        if (this.fechaHasta != null) {
            return this.fechaHasta;
        } else {
            Calendar c = Calendar.getInstance();
            c.setTime(this.getFechaDesde());
            c.add(Calendar.DATE,defaultSettings.getDiasBusqueda());
            return c.getTime();
        }
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }


    public Genero getGenero() {
        if (this.genero != null) {
            return this.genero;
        } else {
            return this.getDefaultSettings().getGenero();
        }

    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getZona() {
        if (this.zona != null) {
            return this.zona;
        } else {
            if (this.defaultSettings.getZona() != null) {
                return this.getDefaultSettings().getZona();
            } else {
                return "";
            }

        }

    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    @Override
    public String toString() {
        String s = "";
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
        if (!this.getZona().isEmpty()) s += "zona = '" + this.getZona() + "'; \n";

        if (this.getNombreCarrera()!= null && !this.getNombreCarrera().isEmpty())
            s += "nombre = '" + this.getNombreCarrera() + "'; \n";
        if (this.getFechaDesde() != null) {
            s += "desde = '" + sf.format(this.getFechaDesde()) + "'; \n";
        }

        if (this.getFechaHasta() != null) {
            s += "hasta = '" + sf.format(this.getFechaHasta()) + "'; \n";
        }
        if(this.getDistanciaMin()!=null){
            s += "Km desde = '" + this.getDistanciaMin() + "'; \n";
        }
        if(this.getDistanciaMax()!=null){
            s += "Km hasta = '" + this.getDistanciaMax() + "'; \n";
        }
        if (this.getGenero() != null) s += "genero = '" + this.getGenero() + "'; \n";

        return s;
    }

    public Integer getDistanciaMin() {
        if(this.distanciaMin!= null){
            return distanciaMin;
        }else{
            return this.defaultSettings.getDistanciaMin();
        }

    }

    public void setDistanciaMin(Integer distanciaMin) {
        this.distanciaMin = distanciaMin;
    }

    public Integer getDistanciaMax() {
        if(this.distanciaMax!= null){
            return distanciaMax;
        }else{
            return this.defaultSettings.getDistanciaMax();
        }
    }

    public void setDistanciaMax(Integer distanciaMax) {
        this.distanciaMax = distanciaMax;
    }
}
