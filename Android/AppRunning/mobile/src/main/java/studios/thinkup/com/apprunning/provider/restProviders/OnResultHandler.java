package studios.thinkup.com.apprunning.provider.restProviders;

import java.util.List;

import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;

/**
 * Created by Facundo on 29/07/2015.
 */
public interface OnResultHandler<T> {

    void actualizarResultados(List<T> resultados);
}
