package com.thinkup.ranning.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.thinkup.ranning.dtos.CamposOrdenEnum;
import com.thinkup.ranning.dtos.Filtro;

public class QueryGenerator {
	private static final String TODAS_PROVINCIAS = "Todas las Provincias";
	private static final String TODAS_CIUDADES = "Todas las Ciudades";
	private static final String TODAS_MODALIDADES = "TODOS";

	public String getWhereCondition(Filtro filtro, List<QueryParam> parametros) {
		String query = " Where 1 = 1 ";
		if (filtro == null) {
			return "";
		}
		if (filtro.getNombreCarrera() != null
				&& !filtro.getNombreCarrera().isEmpty()) {
			query += " AND upper(TRANSLATE(c.nombre,'ÁÉÍÓÚÑáéíóúñ','AEIOUNaeioun')) LIKE "
					+ " upper(TRANSLATE("
					+ ":nombre"
					+ ",'ÁÉÍÓÚÑáéíóúñ','AEIOUNaeioun')) \n";
			parametros.add(new QueryParam("nombre", "'%"
					+ filtro.getNombreCarrera() + "%'"));
		}
		if (filtro.getProvincia() != null && !filtro.getProvincia().isEmpty()
				&& !filtro.getProvincia().equals(TODAS_PROVINCIAS)) {
			query += " AND c.provincia = " + ":provincia" + "\n";
			parametros.add(new QueryParam("provincia", filtro.getProvincia()));
		}

		if (filtro.getCiudad() != null && !filtro.getCiudad().isEmpty()
				&& !filtro.getCiudad().equals(TODAS_CIUDADES)) {
			query += " AND c.ciudad = " + ":ciudad" + "\n";
			parametros.add(new QueryParam("ciudad", filtro.getCiudad()));

		}
		if (filtro.getModalidad() != null
				&& !filtro.getModalidad().equals(TODAS_MODALIDADES)) {
			query += " AND upper(c.modalidades) LIKE " + ":modalidad" + "\n";
			parametros.add(new QueryParam("modalidad", "'%"
					+ filtro.getModalidad() + "%'"));

		}

		query += getFechaRange("fecha", "c.fecha_inicio",
				filtro.getFechaDesde(), filtro.getFechaHasta(), parametros);

		if (filtro.getRecomendadas() != null) {
			query += " AND  c.recomendada  is " + filtro.getRecomendadas();			
		}
		if (filtro.getIdUsuario() > 0) {
			query += " AND uc.id = " + filtro.getIdUsuario();
			if (filtro.getMeGusta() != null) {
				query += " AND uc.me_gusta is " + filtro.getMeGusta();
		
			}

			if (filtro.getInscripto() != null) {
				query += " AND uc.anotado is " + filtro.getInscripto();
				
			}

			if (filtro.getCorrida() != null) {
				query += " AND uc.corrida is " + filtro.getCorrida();
				
			}
			if (filtro.getCorrida() == null && filtro.getInscripto() == null
					&& filtro.getMeGusta() == null) {
				query += " AND ( uc.corrida is true "
						+ " OR  uc.anotado is true "
						+ " OR  uc.me_gusta is true )";
			}
			query += this.getIntegerRange("distancia", "uc.distancia",
					filtro.getMinDistancia(), filtro.getMaxDistancia(),
					parametros);

		}

		query += this.getOrderBy(filtro);
		return query;
	}

	private String getFechaRange(String paramName, String field, String min,
			String max, List<QueryParam> parametros) {
		String resultado = "";
		
		
		if (min == null && max == null) {
			return resultado;
		}
		if (this.getFechaDate(min) != null && this.getFechaDate(max) != null) {
			
			resultado += " AND " + field + " BETWEEN  :" + paramName
					+ "_min AND :" + paramName + "_max \n";
			parametros.add(new QueryParam(paramName + "_min", this.getFechaDate(min)));
			parametros.add(new QueryParam(paramName + "_max", this.getFechaDate(max)));
		} else {
			if (this.getFechaDate(min) != null){				
				resultado += " AND " + field + " >= :" + paramName + "_min \n";
				parametros.add(new QueryParam(paramName + "_min", this.getFechaDate(min)));
			}
			if (this.getFechaDate(max) != null){				
				resultado += " AND " + field + " <= :" + paramName + "_max \n";
				parametros.add(new QueryParam(paramName + "_max", this.getFechaDate(max)));
			}
		}

		return resultado;
	}

	private String getIntegerRange(String paramName, String field, Integer min,
			Integer max, List<QueryParam> parametros) {
		String resultado = "";

		if (min != null && min > 0 && max != null && max > 0) {
			if (min == max) {
				resultado += " AND COALESCE(" + field + ", :" + paramName
						+ "_min) = :" + paramName + "_min \n";
				parametros.add(new QueryParam(paramName + "_min", min));
			} else {
				resultado += " AND COALESCE(" + field + ", :" + paramName
						+ "_min " + ") BETWEEN :" + paramName + "_min AND :"
						+ paramName + "_max \n";

				parametros.add(new QueryParam(paramName + "_min", min));
				parametros.add(new QueryParam(paramName + "_max", max));

			}
		} else {

			if (min != null && min > 0) {
				resultado += " AND COALESCE(" + field + ", :" + paramName
						+ "_min) >= :" + paramName + "_min \n";
				parametros.add(new QueryParam(paramName + "_min", min));
			}
			if (max != null && max > 0) {
				resultado += " AND COALESCE(" + field + ", :" + paramName
						+ "_max) <= :" + paramName + "_max \n";
				parametros.add(new QueryParam(paramName + "_max", max));
			}
		}

		return resultado;
	}

	private String getOrderBy(Filtro filtro) {
		String orderBy = "";
		if (filtro.getOrdenarPor() != null
				&& !filtro.getOrdenarPor().isEmpty()
				&& !filtro.getOrdenarPor().equals(
						CamposOrdenEnum.NINGUNO.getLabel())) {
			orderBy += " ORDER BY "
					+ CamposOrdenEnum.getCampoByLabel(filtro.getOrdenarPor());
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
	
	private Date getFechaDate(String fecha){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		Date fechaDate = null;
		try {
			fechaDate = formatter.parse(fecha);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fechaDate;
	}
}