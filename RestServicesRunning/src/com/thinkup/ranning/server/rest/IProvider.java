package com.thinkup.ranning.server.rest;

import java.io.Serializable;
import java.util.List;

import com.thinkup.ranning.dtos.Respuesta;
import com.thinkup.ranning.server.rest.exception.EntidadNoGuardadaException;

/**
 * Created by FaQ on 13/06/2015.
 * Provider de entidades
 * @param <T> Entidad
 */

public interface IProvider<T> extends Serializable{
    /**
     * Retorna la entidad correspondiente al ID
     * @param clazz tipo de la entidad
     * @param id de la entidad
     * @return NUll si no hay resultados
     */
    Respuesta<T> findById(Integer id);

    /**
     * Obtiene todas las entidades del tipo T
     * 
     * @return LIsta vacia si no hay resultados
     */
    Respuesta<List<T>> findAll();

    /**
     * Realiza un update sobre la entidad de parametro
     * @param entidad a ser actualizada
     * @return la entidad actualizada
     * @throws EntidadNoGuardadaException en caso de no poder hacer update
     */
    Respuesta<T> update(T entidad);


    /**
     * Realiza un Insert sobre la entidad de parametro
     * @param entidad a ser insertada
     * @return la entidad Insertada con nuevo ID
     * @throws EntidadNoGuardadaException en caso de no poder hacer update
     */
    Respuesta<T> grabar(T entidad);
}
