package studios.thinkup.com.apprunning.model;

/**
 * Created by FaQ on 23/05/2015.
 */
public enum Genero {
    TODOS("Todos"), HOMBRE("Hombre"), MUJER("Mujer");
    private String nombre;

    Genero(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return  nombre;
    }
}
