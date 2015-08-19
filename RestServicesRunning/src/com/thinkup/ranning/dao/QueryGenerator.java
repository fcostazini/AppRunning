package com.thinkup.ranning.dao;

import com.thinkup.ranning.dtos.CamposOrdenEnum;
import com.thinkup.ranning.dtos.Filtro;

public class QueryGenerator {
	private static final String TODAS_PROVINCIAS = "Todas las Provincias";
	private static final String TODAS_CIUDADES = "Todas las Ciudades";
	private static final String TODAS_MODALIDADES = "TODOS";

	public String getWhereCondition(Filtro filtro) {
		String query = " Where 1 = 1 ";
		if (filtro == null) {
			return "";
		}
		if (filtro.getNombreCarrera() != null
				&& !filtro.getNombreCarrera().isEmpty()) {
			query += " AND upper(TRANSLATE(c.nombre,'ÁÉÍÓÚÑáéíóúñ','AEIOUNaeioun')) LIKE "
					+ " upper(TRANSLATE('%"
					+ filtro.getNombreCarrera() + "%','ÁÉÍÓÚÑáéíóúñ','AEIOUNaeioun')) \n";
		}
		if (filtro.getProvincia() != null && !filtro.getProvincia().isEmpty()
				&& !filtro.getProvincia().equals(TODAS_PROVINCIAS)) {
			query += " AND c.provincia = '" + filtro.getProvincia() + "'\n";
		}

		if (filtro.getCiudad() != null && !filtro.getCiudad().isEmpty()
				&& !filtro.getCiudad().equals(TODAS_CIUDADES)) {
			query += " AND c.ciudad = '" + filtro.getCiudad() + "'\n";
		}
		if (filtro.getModalidad() != null
				&& !filtro.getModalidad().equals(TODAS_MODALIDADES)) {
			query += " AND upper(c.modalidades) LIKE '%" + filtro.getModalidad() + "%'\n";
		}

		query += getFechaRange("c.fecha_inicio", filtro.getFechaDesde(),
				filtro.getFechaHasta());

		if (filtro.getRecomendadas() != null) {
			query += " AND  c.recomendada  is " + filtro.getRecomendadas()
					+ " ";
		}
		if (filtro.getIdUsuario() >= 0) {
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
			query += this.getIntegerRange("uc.distancia",
					filtro.getMinDistancia(), filtro.getMaxDistancia());

		}
	

		query += this.getOrderBy(filtro);
		return query;
	}

	private String getFechaRange(String field, String min, String max) {
		String resultado = "";

		if (min == null && max == null) {
			return resultado;
		}
		if (min != null && max != null) {
			resultado += " AND " + field + " BETWEEN  '" + min + "' AND '"
					+ max + "' \n";
		} else {
			if (min != null)
				resultado += " AND " + field + " >= '" + min + "' \n";
			if (max != null)
				resultado += " AND " + field + " <= '" + max + "' \n";
		}

		return resultado;
	}

	private String getIntegerRange(String field, Integer min, Integer max) {
		String resultado = "";

		if (min != null && min > 0 && max != null && max > 0) {
			if (min == max) {
				resultado += " AND COALESCE(" + field + "," + min + ") = "
						+ min + "\n";
			} else {
				resultado += " AND COALESCE(" + field + "," + min
						+ ") BETWEEN " + min + " AND " + max + " \n";
			}
		} else {

			if (min != null && min > 0)
				resultado += " AND COALESCE(" + field + "," + min + ") >= "
						+ min + " \n";
			if (max != null && max > 0)
				resultado += " AND COALESCE(" + field + "," + max + ") <= "
						+ max + " \n";
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
}