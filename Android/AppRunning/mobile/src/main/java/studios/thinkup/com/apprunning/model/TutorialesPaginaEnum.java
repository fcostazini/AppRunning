package studios.thinkup.com.apprunning.model;

import studios.thinkup.com.apprunning.TutorialActivity;

/**
 * Created by fcostazini on 06/08/2015.
 */
public enum TutorialesPaginaEnum {
    BUSQUEDA(1), DETALLE(2), PREFERENCIAS(3), RECOMENDADAS(4), TEMPORIZADOR(5);
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
