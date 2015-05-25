package studios.thinkup.com.apprunning.model;

import android.app.Application;

/**
 * Created by FaQ on 23/05/2015.
 * Application
 */
public class RunningApplication extends Application {
    private DefaultSettings defaultSettings;

    public RunningApplication(){
        super();
        defaultSettings = new DefaultSettings();

    }

    public DefaultSettings getDefaultSettings() {
        return defaultSettings;
    }

}