package studios.thinkup.com.apprunning.model;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;

import studios.thinkup.com.apprunning.model.entity.CamposOrdenEnum;
import studios.thinkup.com.apprunning.model.entity.IEntity;
import studios.thinkup.com.apprunning.model.entity.Modalidad;
import studios.thinkup.com.apprunning.provider.FiltrosProvider;

/**
 * Created by FaQ on 23/05/2015.
 * <p/>
 * Filtros preconfigurados
 */
public class DefaultSettings implements Serializable, IEntity {


    public static final String ID = "ID";
    public static final String ID_USUARIO = "ID_USUARIO";
    public static final String DISTANCIA_MIN = "DISTANCIA_MIN";
    public static final String DISTANCIA_MAX = "DISTANCIA_MAX";
    public static final String MODALIDAD = "MODALIDAD";
    public static final String PROVINCIA = "PROVINCIA";
    public static final String CIUDAD = "CIUDAD";
    public static final String MESES_BUSQUEDA = "MESES_BUSQUEDA";
    public static final String ORDENAR_POR = "ORDENAR_POR";
    public static final String SENTIDO_ORDEN = "SENTIDO_ORDEN";


    private Integer idUsuario;
    private Integer distanciaMin = FiltrosProvider.MIN_DISTANCIA;
    private Integer distanciaMax = FiltrosProvider.MAX_DISTANCIA;
    private String modalidad = Modalidad.TODOS.getNombre();
    private String provincia = FiltrosProvider.TODAS_LAS_PROVINCIAS;
    private String ciudad = FiltrosProvider.TODAS_LAS_CIUDADES;
    private Integer mesesBusqueda = 6;
    private Integer id;
    private String ordenarPor = CamposOrdenEnum.FECHA.getLabel();
    private String sentidoOrden = Filtro.SENTIDO_ORDEN[0];

    public DefaultSettings(Integer idUsuario) {
        this.idUsuario = idUsuario;
        distanciaMin = 0;
        distanciaMax = 100;
        modalidad = Modalidad.TODOS.getNombre();
        provincia = FiltrosProvider.TODAS_LAS_PROVINCIAS;
        ciudad = FiltrosProvider.TODAS_LAS_CIUDADES;
        ordenarPor = CamposOrdenEnum.FECHA.getLabel();
        sentidoOrden = Filtro.SENTIDO_ORDEN[0];
        mesesBusqueda = 6;
    }

    public DefaultSettings(Cursor c) {
        this.setId(c.getInt(c.getColumnIndex(ID)));
        this.setIdUsuario(c.getInt(c.getColumnIndex(ID_USUARIO)));
        this.setDistanciaMin(c.getInt(c.getColumnIndex(DISTANCIA_MIN)));
        this.setDistanciaMax(c.getInt(c.getColumnIndex(DISTANCIA_MAX)));
        this.setModalidad(Modalidad.getByName(c.getString(c.getColumnIndex(MODALIDAD))));
        this.setProvincia(c.getString(c.getColumnIndex(PROVINCIA)));
        this.setCiudad(c.getString(c.getColumnIndex(CIUDAD)));
        this.setMesesBusqueda(c.getInt(c.getColumnIndex(MESES_BUSQUEDA)));
        this.setOrdenarPor(c.getString(c.getColumnIndex(ORDENAR_POR)));
        this.setSentido(c.getString(c.getColumnIndex(SENTIDO_ORDEN)));
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getOrdenarPor() {
        return ordenarPor;
    }

    public void setOrdenarPor(String ordenarPor) {
        this.ordenarPor = ordenarPor;
    }

    public String getSentido() {
        return sentidoOrden;
    }

    public void setSentido(String sentido) {
        this.sentidoOrden = sentido;
    }

    public Integer getMesesBusqueda() {
        return mesesBusqueda;
    }

    public void setMesesBusqueda(Integer mesesBusqueda) {
        this.mesesBusqueda = mesesBusqueda;
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

    public Modalidad getModalidad() {
        return Modalidad.getByName(modalidad);
    }

    public void setModalidad(Modalidad modalidad) {
        this.modalidad = modalidad.getNombre();
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String getNombreId() {
        return "ID";
    }

    @Override
    public ArrayList<String> getIgnoredFields() {
        return new ArrayList<>();
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }
}
