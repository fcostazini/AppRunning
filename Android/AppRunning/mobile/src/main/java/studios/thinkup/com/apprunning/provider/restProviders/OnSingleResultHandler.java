package studios.thinkup.com.apprunning.provider.restProviders;

import java.util.List;

/**
 * Created by Facundo on 29/07/2015.
 */
public interface OnSingleResultHandler<T> {

    void actualizarResultado(T resultado);
}
