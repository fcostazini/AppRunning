package studios.thinkup.com.apprunning.model.entity;


import java.util.ArrayList;

/**
 * Created by FaQ on 13/06/2015.
 * Interfaz de marca para entidades
 */
public interface IEntity {
    /**
     * Retorna el Id de la entidad
     *
     * @return Integer Id, null si no tiene
     */
    Integer getId();

    void setId(Integer id);

    String getNombreId();

    ArrayList<String> getIgnoredFields();
}
