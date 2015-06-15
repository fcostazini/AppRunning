package studios.thinkup.com.apprunning.model.entity;

import java.io.Serializable;
import java.util.ArrayList;

import studios.thinkup.com.apprunning.provider.helper.Id;

/**
 * Created by FaQ on 28/05/2015.
 */
public class UsuarioApp implements Serializable, IEntity {

    public static final String ID = "ID";
    public static final String NOMBRE = "NOMBRE";
    public static final String EMAIL = "EMAIL";
    public static final String TIPO_CUENTA = "TIPO_CUENTA";


    @Id
    private Integer id;
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
        return "id";
    }

    @Override
    public ArrayList<String> getIgnoredFields() {
        return new ArrayList<>();
    }
}
