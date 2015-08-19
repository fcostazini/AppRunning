package studios.thinkup.com.apprunning.model;

/**
 * Created by fcostazini on 06/08/2015.
 */
public enum TutorialesPaginaEnum {
    BUSQUEDA(1), DETALLE(2), PREFERENCIAS(3), BUSCAR_AMIGO(4), MIS_TIEMPOS(5), FILTROS(6), MIS_CARRERAS(7);
    private int codigo;

    TutorialesPaginaEnum(int id) {
        this.codigo = id;
    }

    public static TutorialesPaginaEnum getById(int id) {
        for (TutorialesPaginaEnum en : values()) {
            if (en.codigo == id) {
                return en;
            }
        }
        return null;
    }


    public int getId() {
        return codigo;
    }
}
