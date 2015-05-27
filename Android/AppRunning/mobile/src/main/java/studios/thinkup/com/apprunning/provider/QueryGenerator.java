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
        if (filtro != null && filtro.getNombreCarrera().isEmpty()) {
            query += " AND " + Carrera.NOMBRE_FIELD + " LIKE '" + filtro.getNombreCarrera() + "%' \n";
        }
        if (!filtro.getZona().isEmpty()) {
            query += " AND " + Carrera.ZONA_FIELD + " = '" + filtro.getZona() + "'\n";
        }
        if (filtro.getGenero() != null && !filtro.getGenero().equals(Genero.TODOS)) {
            query += " AND " + Carrera.GENERO_FIELD + " = '" + filtro.getGenero() + "'\n";
        }

        query += getFechaRange(Carrera.FECHA_LARGADA_FIELD,filtro.getFechaDesde(),filtro.getFechaHasta());
        query += getIntegerRange(Carrera.DISTANCIA_FIELD,filtro.getDistanciaMin(),filtro.getDistanciaMax());

        return query;
    }

    private String getIntegerRange(String field, int min, int max) {
        String resultado = " AND ";

        if (min > 0 && max > 0) {
            if (min==max) {
                resultado += field + " = " + min + "\n";
            } else {
                resultado += field + " BETWEEN " + min + " AND " + max + " \n";
            }
        } else {

            if (min > 0)
                resultado += field + " >= " + min + " \n";
            if (max > 0)
                resultado += field + " <= " + max + " \n";
        }

        return resultado;
    }

    private String getFechaRange(String field, Date min, Date max) {
        String resultado = " AND ";

        if (min != null && max != null) {
            if (min.equals(max)) {
                resultado += field + " = " + min.getTime() + "\n";
            } else {
                resultado += field + " BETWEEN " + min.getTime() + " AND " + max.getTime() + " \n";
            }
        } else {

            if (min != null)
                resultado += field + " >= " + min.getTime() + " \n";
            if (max != null)
                resultado += field + " <= " + max.getTime() + " \n";
        }

         return resultado;
    }

}
