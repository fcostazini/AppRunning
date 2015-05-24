package studios.thinkup.com.apprunning.model;

import android.app.Application;

/**
 * Created by FaQ on 23/05/2015.
 */
public class RunningApplication extends Application {
    private DefaultSettings defaultSettings;

    public RunningApplication(){
        super();
        defaultSettings = new DefaultSettings();
        defaultSettings.setGenero(Genero.TODOS);
        defaultSettings.setMaxDias(365);
        defaultSettings.setMaxDistancia(100);
        defaultSettings.setZona("");
    }

    public DefaultSettings getDefaultSettings() {
        return defaultSettings;
    }

    public void setDefaultSettings(DefaultSettings defaultSettings) {
        this.defaultSettings = defaultSettings;
    }
}
