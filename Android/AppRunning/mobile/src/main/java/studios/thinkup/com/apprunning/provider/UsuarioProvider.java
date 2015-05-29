package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.database.Cursor;

import studios.thinkup.com.apprunning.model.UsuarioApp;
import studios.thinkup.com.apprunning.provider.dbProviders.GenericProvider;
import studios.thinkup.com.apprunning.provider.helper.DataBaseHelper;

/**
 * Created by FaQ on 28/05/2015.
 */
public class UsuarioProvider {
    private GenericProvider provider;

    public UsuarioProvider(Context context){
        this.provider = new GenericProvider(new DataBaseHelper(context));
    }

    public UsuarioApp getUsuarioByEmail(String email){
        Cursor c = this.provider.executeQuery("select * from usuario where email = '" + email + "'");
        c.moveToFirst();
        UsuarioApp u = new UsuarioApp(c.getString(c.getColumnIndex("nombre")),
                                      c.getInt(c.getColumnIndex("id_usuario")));
        return u;
    }

}
