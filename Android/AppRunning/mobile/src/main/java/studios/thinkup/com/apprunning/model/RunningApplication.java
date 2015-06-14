package studios.thinkup.com.apprunning.model;

import android.app.Application;
import android.content.ContextWrapper;

import java.io.File;
import java.io.IOException;

import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.helper.DataBaseHelper;

/**
 * Created by FaQ on 23/05/2015.
 * Application
 */
public class RunningApplication extends Application{
    private DefaultSettings defaultSettings;
    private UsuarioApp usuario;

    public RunningApplication(){
        super();
        defaultSettings = new DefaultSettings();

    }

    @Override
    public void onCreate() {
        init();
        FontsOverride.overrideFont(getApplicationContext(), "SERIF", "fonts/milkrun.ttf");

    }
   private void init() {
        initDB();
    }

    private void initDB() {
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        try {
            dbHelper.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean doesDatabaseExist(ContextWrapper context, String dbName)       {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }

    public DefaultSettings getDefaultSettings() {
        return defaultSettings;
    }
    public UsuarioApp getUsuario(){return this.usuario;}
    public void setUsuario(UsuarioApp usuario) {
        this.usuario = usuario;
    }
}
