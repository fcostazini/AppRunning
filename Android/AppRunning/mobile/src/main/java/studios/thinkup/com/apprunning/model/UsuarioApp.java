package studios.thinkup.com.apprunning.model;

/**
 * Created by FaQ on 28/05/2015.
 */
public class UsuarioApp {
    private String nombre;
    private Integer id;

    public UsuarioApp(String nombre, Integer id) {
        this.nombre = nombre;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
