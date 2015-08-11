package studios.thinkup.com.apprunning.model.entity;

import java.util.List;
import java.util.Vector;

/**
 * Created by Facundo on 21/07/2015.
 */
public enum CamposOrdenEnum {
    NINGUNO("Ninguno", "NINGUNO"),
    DISTANCIA("Distancia", Carrera.DISTANCIAS_DISPONIBLE),
    NOMBRE("Nombre", Carrera.NOMBRE),
    FECHA("Fecha Inicio", Carrera.FECHA_INICIO),
    MODALIDAD("Modalidad", Carrera.MODALIDADES),
    PROVINCIA("Provincia", Carrera.PROVINCIA),
    CIUDAD("Ciudad", Carrera.CIUDAD);

    private String label;
    private String campo;

    CamposOrdenEnum(String label, String campo) {
        this.label = label;
        this.campo = campo;

    }

    public static String[] getLabels() {
        String[] a = new String[values().length];
        List<String> labels = new Vector<>();
        for (CamposOrdenEnum e : values()) {
            labels.add(e.label);
        }
        return labels.toArray(a);
    }

    public static String getCampoByLabel(String label) {
        for (CamposOrdenEnum e : values()) {
            if (e.label.equals(label)) {
                return e.campo;
            }
        }
        return "";
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }
}
