package studios.thinkup.com.apprunning.provider;

import java.io.Serializable;
import java.util.List;

import studios.thinkup.com.apprunning.model.entity.IEntity;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;

/**
 * Created by FaQ on 13/06/2015.
 * Provider de entidades
 *
 * @param <T> Entidad
 */

public interface IProvider<T extends IEntity> extends Serializable {
    /**
     * Retorna la entidad correspondiente al ID
     *
     * @param clazz tipo de la entidad
     * @param id    de la entidad
     * @return NUll si no hay resultados
     */
    T findById(Class<T> clazz, Integer id);

    /**
     * Obtiene todas las entidades del tipo T
     *
     * @param clazz tipo
     * @return LIsta vacia si no hay resultados
     */
    List<T> findAll(Class<T> clazz);

    /**
     * Realiza un update sobre la entidad de parametro
     *
     * @param entidad a ser actualizada
     * @return la entidad actualizada
     * @throws EntidadNoGuardadaException en caso de no poder hacer update
     */
    T update(T entidad) throws EntidadNoGuardadaException;


    /**
     * Realiza un Insert sobre la entidad de parametro
     *
     * @param entidad a ser insertada
     * @return la entidad Insertada con nuevo ID
     * @throws EntidadNoGuardadaException en caso de no poder hacer update
     */
    T grabar(T entidad) throws EntidadNoGuardadaException;
}
