package studios.thinkup.com.apprunning.provider.exceptions;

/**
 * Created by FaQ on 13/06/2015.
 * Excepcion que se lanza si no se pudo guardar la entidad
 */
public class EntidadNoGuardadaException extends Exception {
    public EntidadNoGuardadaException(String detailMessage) {
        super(detailMessage);
    }

    public EntidadNoGuardadaException(Throwable throwable) {
        super(throwable);
    }
}
