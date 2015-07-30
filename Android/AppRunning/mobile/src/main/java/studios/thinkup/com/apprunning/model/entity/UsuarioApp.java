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
    public static final String APELLIDO = "APELLIDO";
    public static final String FOTO_PERFIL_URL = "FOTO_PERFIL_URL";
    public static final String FECHA_NACIMIENTO = "FECHA_NACIMIENTO";
    public static final String NICK = "NICK";
    public static final String GRUPO_ID ="GRUPO_ID";


    @Id
    private Integer id;
    private String nombre ="";
    private String apellido ="";
    private String password ="";
    private String nick ="";
    private String email ="";
    private String fechaNacimiento = new String();
    private String tipoCuenta ="";
    private String fotoPerfil;
    private String grupoId;

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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
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

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    @Override
    public ArrayList<String> getIgnoredFields() {
        return new ArrayList<>();
    }

    public String getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(String grupo) {
        this.grupoId = grupo;
    }
}
