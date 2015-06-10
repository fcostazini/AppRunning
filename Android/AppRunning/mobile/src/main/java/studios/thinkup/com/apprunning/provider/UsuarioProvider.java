package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.database.Cursor;

import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;

import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
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
        List<UsuarioApp> resultados = UsuarioApp.find(UsuarioApp.class, "email = ?", email);
        if(resultados != null && resultados.size()>0){
            return resultados.get(0);
        }else{
            return null;
        }

    }

}
