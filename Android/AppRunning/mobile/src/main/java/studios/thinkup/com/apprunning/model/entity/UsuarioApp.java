package studios.thinkup.com.apprunning.model.entity;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by FaQ on 28/05/2015.
 */
public class UsuarioApp extends SugarRecord<UsuarioApp> implements Serializable {
    private String nombre;
    private String email;
    private String tipoCuenta;
    public UsuarioApp() {

    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
