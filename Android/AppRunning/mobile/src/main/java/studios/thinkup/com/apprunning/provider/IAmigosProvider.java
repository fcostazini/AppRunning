package studios.thinkup.com.apprunning.provider;

import java.util.List;

import studios.thinkup.com.apprunning.model.entity.AmigoRequest;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;
import studios.thinkup.com.apprunning.model.entity.CarreraAmigoDTO;

/**
 * Created by Facundo on 11/08/2015.
 * Provider de amigos
 */
public interface IAmigosProvider {
    /**
     * Obtiene todos los amigos de un Amigo
     *
     * @param id del usuario
     * @return lista vacia en caso de no encontrar resultados
     */
    List<AmigosDTO> getAmigosByUsuarioId(Integer id);


    /**
     * Obtiene todos los amigos de un Amigo
     *
     * @param idUsuario del usuario
     * @param idCarrera usuario
     * @return lista vacia en caso de no encontrar resultados
     */
    List<AmigosDTO> getAmigosEnCarrera(Integer idUsuario, Integer idCarrera);


    /**
     * Obtiene todos los Amigo que tengan nombre o email que cumplan con "LIKE param%"
     *
     * @param idOwner   solicitante
     * @param parametro para buscar
     * @return lista vacia en caso de no encontrar resultados
     */
    List<AmigosDTO> getUsuarios(Integer idOwner, String parametro);

    /**
     * Actualiza el estado de un amigo con relacion al usuario
     *
     * @param param
     * @return
     */
    AmigosDTO actualizarEstadoAmigo(AmigoRequest param);

    /**
     * Obtiene todas las carreras donde este inscripto un amigo
     *
     * @param id del usuario
     * @return lista vacia en caso de no encontrar resultados
     */
    List<CarreraAmigoDTO> getCarrerasAmigo(Integer id);

}
