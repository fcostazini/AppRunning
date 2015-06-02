package studios.thinkup.com.apprunning.provider;

import java.util.Date;

import studios.thinkup.com.apprunning.model.Carrera;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.Genero;

/**
 * Created by FaQ on 25/05/2015.
 * Genera una query en base a un filtro
 */
public class QueryGenerator {
    private Filtro filtro;

    public QueryGenerator(Filtro filtro) {
        this.filtro = filtro;
    }

    public String getWhereCondition() {
        String query = "WHERE 1 = 1 \n";
        if (filtro != null && filtro.getNombreCarrera() != null && !filtro.getNombreCarrera().isEmpty()) {
            query += " AND " + Carrera.NOMBRE_FIELD + " LIKE '" + filtro.getNombreCarrera() + "%' \n";
        }
        if (filtro.getZona() != null && !filtro.getZona().isEmpty()) {
            query += " AND " + Carrera.ZONA_FIELD + " = '" + filtro.getZona() + "'\n";
        }
        if (filtro.getGenero() != null && !filtro.getGenero().equals(Genero.TODOS)) {
            query += " AND " + Carrera.GENERO_FIELD + " = '" + filtro.getGenero() + "'\n";
        }

        query += getFechaRange(Carrera.FECHA_LARGADA_FIELD, filtro.getFechaDesde(), filtro.getFechaHasta());
        query += getIntegerRange(Carrera.DISTANCIA_FIELD, filtro.getDistanciaMin(), filtro.getDistanciaMax());
        if (filtro.getIdUsuario() != null && filtro.getIdUsuario() >= 0) {
            query += " AND id_usuario = " + filtro.getIdUsuario().intValue();
        }
        if(filtro.getMeGusta()!=null){
            query += " AND me_gusta = " + this.getIntFromBool(filtro.getMeGusta().booleanValue());
        }

        if(filtro.getInscripto()!=null){
            query += " AND anotado = " + this.getIntFromBool(filtro.getInscripto().booleanValue());
        }

        if(filtro.getCorrida()!=null){
            query += " AND corrida = " + this.getIntFromBool(filtro.getCorrida().booleanValue());
        }
        return query;
    }

    private int getIntFromBool(boolean bool) {
        return bool?1:0;
    }

    private String getIntegerRange(String field, Integer min, Integer max) {
        String resultado = "";

        if (min != null && min > 0 && max != null && max > 0) {
            if (min == max) {
                resultado += " AND " + field + " = " + min + "\n";
            } else {
                resultado += " AND " + field + " BETWEEN " + min + " AND " + max + " \n";
            }
        } else {

            if (min != null && min > 0)
                resultado += " AND " + field + " >= " + min + " \n";
            if (max != null && max > 0)
                resultado += " AND " + field + " <= " + max + " \n";
        }

        return resultado;
    }

    private String getFechaRange(String field, Date min, Date max) {
        String resultado = "";

        if (min != null && max != null) {
            if (min.equals(max)) {
                resultado += " AND " + field + " = " + min.getTime() + "\n";
            } else {
                resultado += " AND " + field + " BETWEEN " + min.getTime() + " AND " + max.getTime() + " \n";
            }
        } else {

            if (min != null)
                resultado += " AND " + field + " >= " + min.getTime() + " \n";
            if (max != null)
                resultado += " AND " + field + " <= " + max.getTime() + " \n";
        }

        return resultado;
    }

}
