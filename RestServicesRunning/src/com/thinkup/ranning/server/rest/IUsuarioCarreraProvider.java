package com.thinkup.ranning.server.rest;

import java.util.List;

import com.thinkup.ranning.dtos.Filtro;
import com.thinkup.ranning.dtos.Respuesta;
import com.thinkup.ranning.entities.UsuarioCarrera;

/**
 * Created by FaQ on 30/05/2015. Provider de carreras asociadas a un usuario
 */
public interface IUsuarioCarreraProvider extends IProvider<UsuarioCarrera> {

	Respuesta<UsuarioCarrera> getByIdCarrera(int carrera);

	/**
	 * Buscar todas las carreras con tiempo de un usuario segun el filtro
	 * 
	 * @param filtro
	 *            .
	 * @return lista vacia en caso de no haber resultados
	 */
	Respuesta<List<UsuarioCarrera>> findTiemposByFiltro(Filtro filtro);

}
