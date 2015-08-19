package studios.thinkup.com.apprunning.provider.exceptions;

/**
 * Created by FaQ on 13/06/2015.
 * No se puede mapear
 */
public class CampoNoMapeableException extends Throwable {
    public CampoNoMapeableException(String name) {
        super(name);
    }
}
