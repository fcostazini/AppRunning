package studios.thinkup.com.apprunning.model.entity;

/**
 * Created by FaQ on 23/05/2015.
 * Generos
 */
public enum Genero {
    TODOS("TODOS"), HOMBRE("HOMBRE"), MUJER("MUJER");
    private String nombre;

    Genero(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public static Genero getByName(String nombre) {
        for (Genero g : Genero.values()) {
            if (g.getNombre().equals(nombre)){
                return g;
            }
        }
        return null;
    }
}
