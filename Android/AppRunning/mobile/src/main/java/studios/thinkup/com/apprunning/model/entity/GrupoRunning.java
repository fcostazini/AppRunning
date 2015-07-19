package studios.thinkup.com.apprunning.model.entity;

import android.database.Cursor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Facundo on 19/07/2015.
 */
public class GrupoRunning implements Serializable,IEntity{

    public static final String ID ="ID";
    public static final String NOMBRE = "NOMBRE";

    private  Integer id;
    private  String nombre;
    public GrupoRunning(Cursor c) {
        this.id = c.getInt(c.getColumnIndex(ID));
        this.nombre = c.getString(c.getColumnIndex(NOMBRE));
    }

    public GrupoRunning(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
        return ID;
    }

    @Override
    public ArrayList<String> getIgnoredFields() {
        return new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
}
