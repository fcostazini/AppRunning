package studios.thinkup.com.apprunning.model.entity;

import java.io.Serializable;


public class ProvinciaCiudadDTO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2233710546897096340L;

    private String provincia;

    private String ciudad;

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

}
