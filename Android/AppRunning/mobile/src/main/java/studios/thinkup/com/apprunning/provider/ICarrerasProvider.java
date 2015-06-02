package studios.thinkup.com.apprunning.provider;

import java.util.List;

import studios.thinkup.com.apprunning.model.Carrera;
import studios.thinkup.com.apprunning.model.CarreraCabecera;
import studios.thinkup.com.apprunning.model.Filtro;

/**
 * Created by FaQ on 30/05/2015.
 */
public interface ICarrerasProvider {
    List<CarreraCabecera> getCarreras(Filtro filtro);

    Integer getCantidadCarreras(Filtro filtro);

    Carrera getCarreraByCodigo(int codigo);

    void anotarEnCarrera(Integer codigoCarrera, Integer id);

    void desanotarEnCarrera(Integer codigoCarrera, Integer id);

    void meGusta(Integer codigoCarrera, Integer id);

    void noMegusta(Integer codigoCarrera, Integer id);

    void carreraCorrida(Integer codigoCarrera, Integer id);

    void carreraNoCorrida(Integer codigoCarrera, Integer id);
}
