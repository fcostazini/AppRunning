package studios.thinkup.com.apprunning.model;

import android.app.Application;

/**
 * Created by FaQ on 23/05/2015.
 * Application
 */
public class RunningApplication extends Application {
    private DefaultSettings defaultSettings;
    private UsuarioApp usuario;

    public RunningApplication(){
        super();
        defaultSettings = new DefaultSettings();

    }

    public DefaultSettings getDefaultSettings() {
        return defaultSettings;
    }
    public UsuarioApp getUsuario(){return this.usuario;}
    public void setUsuario(UsuarioApp usuario) {
        this.usuario = usuario;
    }
}
