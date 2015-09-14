package studios.thinkup.com.apprunning.provider;

import java.util.List;

import studios.thinkup.com.apprunning.model.entity.GrupoRunning;

/**
 * Created by fcostazini on 14/09/2015.
 * Proveedor de Grupos
 */
public interface IGruposProvider {

    /**
     * Obtengo todos los grupos cuyo nombre comience con el param name
     * @param name nombre del grupo
     * @return lista vacia en caso de no encontrar resultados
     */
    List<GrupoRunning> getGrupoByName(String name);
}
