package studios.thinkup.com.apprunning.model.entity;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by FaQ on 28/05/2015.
 */
public class UsuarioApp extends SugarRecord<UsuarioApp> implements Serializable {
    private String nombre;

    public UsuarioApp() {

    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
