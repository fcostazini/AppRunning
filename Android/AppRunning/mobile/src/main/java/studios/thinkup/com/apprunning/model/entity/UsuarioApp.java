package studios.thinkup.com.apprunning.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

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
    private String apellido;
    private String nick;
    private String email;
    private Date fechaNacimiento;
    private String tipoCuenta;
    private String fotoPerfilUrl;

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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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

    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    @Override
    public ArrayList<String> getIgnoredFields() {
        return new ArrayList<>();
    }
}
