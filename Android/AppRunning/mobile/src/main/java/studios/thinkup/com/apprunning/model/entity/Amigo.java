package studios.thinkup.com.apprunning.model.entity;

/**
 * Created by Facundo on 11/08/2015.
 * Amigo
 */
public class Amigo extends UsuarioApp{
    public static final String FIELD_ID = "amigo";
    private boolean esAmigo;
    private boolean solicitudEnviada;
    private boolean esBloqueado;

    public boolean isEsAmigo() {
        return esAmigo;
    }

    public void setEsAmigo(boolean esAmigo) {
        this.esAmigo = esAmigo;
    }

    public boolean isSolicitudEnviada() {
        return solicitudEnviada;
    }

    public void setSolicitudEnviada(boolean solicitudEnviada) {
        this.solicitudEnviada = solicitudEnviada;
    }

    public boolean isEsBloqueado() {
        return esBloqueado;
    }

    public void setEsBloqueado(boolean esBloqueado) {
        this.esBloqueado = esBloqueado;
    }
}
