package studios.thinkup.com.apprunning.provider.tables;

/**
 * Created by FaQ on 23/05/2015.
 * Generos
 */
public enum CarreraTable {
    CODIGO(1,"codigo"),
    NOMBRE(2,"nombre"),
    FECHA_LARGADA(3,"fecha_largada" ),
    DISTANCIA(4,"distancia"),
    DESCRIPCION(5,"descripcion"),
    URL_IMAGEN(6,"url_imagen"),
    GENERO(7,"genero"),
    ZONA(8,"zona"),
    DIRECCION(9,"direccion" );

    private Integer orden;
    private String nombre;
    CarreraTable(Integer orden, String nombre) {
        this.orden = orden;
        this.nombre = nombre;
    }

    public Integer getOrden(){return orden;}
    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public static CarreraTable getByName(String nombre) {
        for (CarreraTable g : CarreraTable.values()) {
            if (g.getNombre().equals(nombre)){
                return g;
            }
        }
        return null;
    }
}
