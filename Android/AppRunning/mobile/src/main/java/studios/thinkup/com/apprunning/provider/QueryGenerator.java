package studios.thinkup.com.apprunning.provider;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.CamposOrdenEnum;
import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.model.entity.Modalidad;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;

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
        String query = " Where 'a' = 'a' \n";
        if(filtro == null){
            return "";
        }
        if (filtro.getNombreCarrera() != null && !filtro.getNombreCarrera().isEmpty()) {
            query += " AND upper(" + Carrera.NOMBRE + ") LIKE upper('%" + filtro.getNombreCarrera() + "%') \n";
        }
        if (filtro.getProvincia() != null && !filtro.getProvincia().isEmpty() && !filtro.getProvincia().equals(FiltrosProvider.TODAS_LAS_PROVINCIAS)) {
            query += " AND " + Carrera.PROVINCIA + " = '" + filtro.getProvincia() + "'\n";
        }

        if (filtro.getCiudad() != null && !filtro.getCiudad().isEmpty() && !filtro.getCiudad().equals(FiltrosProvider.TODAS_LAS_CIUDADES)) {
            query += " AND " + Carrera.CIUDAD + " = '" + filtro.getCiudad() + "'\n";
        }
        if (filtro.getModalidad() != null && !filtro.getModalidad().equals(Modalidad.TODOS)) {
            query += " AND " + Carrera.MODALIDADES + " LIKE '%" + filtro.getModalidad() + "%'\n";
        }

        query += getFechaRange(Carrera.FECHA_INICIO, filtro.getFechaDesde(), filtro.getFechaHasta());
        if (filtro.getIdUsuario() >= 0) {
            query += " AND usuario = " + filtro.getIdUsuario();
        }
        if (filtro.getMeGusta() != null) {
            query += " AND me_gusta = " + this.getIntFromBool(filtro.getMeGusta());
        }

        if (filtro.getInscripto() != null) {
            query += " AND anotado = " + this.getIntFromBool(filtro.getInscripto());
        }

        if (filtro.getCorrida() != null) {
            query += " AND corrida = " + this.getIntFromBool(filtro.getCorrida());
        }
        if (filtro.getIdUsuario() >= 0 && filtro.getCorrida() == null && filtro.getInscripto() == null
                && filtro.getMeGusta() == null) {
            query += " AND ( corrida = 1" +
                    " OR  anotado = 1" +
                    " OR  me_gusta = 1 )";
        }
        if (filtro.getRecomendadas() != null) {
            query += " AND " + Carrera.RECOMENDADA + " = " + this.getIntFromBool(filtro.getRecomendadas()) + " ";
        }
        query += this.getIntegerRange(UsuarioCarrera.DISTANCIA, filtro.getMinDistancia(),filtro.getMaxDistancia());
        query += this.getOrderBy();
        return query;
    }

    private String getOrderBy() {
        String orderBy = "";
        if (filtro.getOrdenarPor() != null
                && !filtro.getOrdenarPor().isEmpty()
                && !filtro.getOrdenarPor().equals(CamposOrdenEnum.NINGUNO.getLabel())) {
            orderBy += "ORDER BY " + CamposOrdenEnum.getCampoByLabel(filtro.getOrdenarPor());
            if (filtro.getSentido() != null && !filtro.getSentido().isEmpty()) {
                if (filtro.getSentido().equals(Filtro.SENTIDO_ORDEN[0])) {
                    orderBy += " ASC ";
                } else {
                    orderBy += " DESC ";
                }
            }
        }


        return orderBy;
    }


    private int getIntFromBool(boolean bool) {
        return bool ? 1 : 0;
    }

    private String getIntegerRange(String field, Integer min, Integer max) {
        String resultado = "";

        if (min != null && min > 0 && max != null && max > 0) {
            if (min == max) {
                resultado += " AND COALESCE(" + field + "," + min + ") = " + min + "\n";
            } else {
                resultado += " AND COALESCE(" + field + "," + min + ") BETWEEN " + min + " AND " + max + " \n";
            }
        } else {

            if (min != null && min > 0)
                resultado += " AND COALESCE(" + field + "," + min + ") >= " + min + " \n";
            if (max != null && max > 0)
                resultado += " AND COALESCE(" + field + "," + max + ") <= " + max + " \n";
        }

        return resultado;
    }

    private String getFechaRange(String field, Date min, Date max) {
        String resultado = "";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        if (min == null && max == null) {
            return resultado;
        }
        if (min != null && max != null) {
            if (min.equals(max)) {
                resultado += " AND " + field + " LIKE '" + sf.format(min) + "%'\n";
            } else {
                resultado += " AND " + field + " BETWEEN '" + sf.format(min) + "' AND '" + sf.format(max) + "' \n";
            }
        } else {

            if (min != null)
                resultado += " AND " + field + " >= '" + sf.format(min) + "' \n";
            if (max != null)
                resultado += " AND " + field + " <= '" + sf.format(max) + "' \n";
        }

        return resultado;
    }

}
