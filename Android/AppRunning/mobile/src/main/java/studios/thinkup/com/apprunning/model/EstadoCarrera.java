package studios.thinkup.com.apprunning.model;

/**
 * Created by FaQ on 22/05/2015.
 */
public enum EstadoCarrera {
    INSCRIPTO("Inscripto"),ME_GUSTA("Me Gusta"),CORRIDA("Corrida");
    private String nombre;
    EstadoCarrera(String nombre){
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    public static EstadoCarrera getByName(String nombre) {
        for(EstadoCarrera e : EstadoCarrera.values()){
            if(e.nombre.equals(nombre)){
                return e;
            }
        }
        return null;
    }
}
