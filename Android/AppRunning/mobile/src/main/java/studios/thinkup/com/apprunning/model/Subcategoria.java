package studios.thinkup.com.apprunning.model;

/**
 * Created by fcostazini on 22/05/2015.
 */
public enum Subcategoria {
    ZONA("Zona"), GENERO("Genero"), DISTANCIA("Distancia");
    private String nombre;

    Subcategoria(String nombre) {
        this.nombre = nombre;
    }


    public static Subcategoria getByName(String tipo) {
        for( Subcategoria s : Subcategoria.values()){
            if(s.nombre.equals(tipo)){
                return s;
            }

        }
        return Subcategoria.ZONA;
    }
}
