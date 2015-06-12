package studios.thinkup.com.apprunning.provider;

import android.content.Context;

import java.util.List;

import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.provider.dbProviders.GenericProvider;
import studios.thinkup.com.apprunning.provider.helper.DataBaseHelper;

/**
 * Created by FaQ on 23/05/2015.
 * Provider de las Zonas
 */
public class ZonaProvider {
    private GenericProvider provider;

    public ZonaProvider(Context context) {
        this.provider = new GenericProvider(new DataBaseHelper(context));
    }

    public List<String> getZonas() {

        List<String> zonas = this.provider.getDistinctColumns("CARRERA", Carrera.CIUDAD_FIELD);


        return zonas;
    }
}
