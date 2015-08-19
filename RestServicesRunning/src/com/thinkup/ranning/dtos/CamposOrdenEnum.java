package com.thinkup.ranning.dtos;

import java.util.List;
import java.util.Vector;

public enum CamposOrdenEnum {
	
	NINGUNO("Ninguno", "NINGUNO"),
    DISTANCIA("Distancia", "c.DISTANCIA_DISPONIBLE"),
    NOMBRE("Nombre", "c.NOMBRE"),
    FECHA("Fecha Inicio", "c.FECHA_INICIO"),
    MODALIDAD("Modalidad", "c.MODALIDADES"),
    PROVINCIA("Provincia", "c.PROVINCIA"),
    CIUDAD("Ciudad", "c.CIUDAD");

    private String label;
    private String campo;

    CamposOrdenEnum(String label, String campo) {
        this.label = label;
        this.campo = campo;

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

    public static String[] getLabels(){
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

}
