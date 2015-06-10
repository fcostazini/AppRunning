package studios.thinkup.com.apprunning.model;

/**
 * Created by FaQ on 08/06/2015.
 */
public interface IObservableCarrera {
    /**
     * Registra un observador de carrera a ser notificado en caso de que cambie la carrera
     * @param observador IObservadorCarrera
     */
    public void registrarObservador(IObservadorCarrera observador);

    void actualizarObservadores();
}
