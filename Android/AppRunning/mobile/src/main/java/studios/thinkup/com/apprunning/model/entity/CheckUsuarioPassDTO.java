package studios.thinkup.com.apprunning.model.entity;

import java.io.Serializable;

/**
 * Created by Facundo on 17/08/2015.
 *
 */
public class CheckUsuarioPassDTO implements Serializable{

    String usuario;
    String password;



    public CheckUsuarioPassDTO(String usuario, String password) {
        this.usuario = usuario;
        this.password = password;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
