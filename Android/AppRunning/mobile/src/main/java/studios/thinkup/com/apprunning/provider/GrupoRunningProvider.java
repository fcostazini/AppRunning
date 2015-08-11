package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.database.Cursor;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.entity.GrupoRunning;
import studios.thinkup.com.apprunning.model.entity.IEntity;


/**
 * Created by Facundo on 19/07/2015.
 * Provider para Grupos de running
 */
public class GrupoRunningProvider extends GenericProvider<GrupoRunning> implements IGrupoRunningProvider {

    public GrupoRunningProvider(Context c) {
        super(c);
    }

    @Override
    protected String[] getFields(Class<? extends IEntity> clazz) {
        return new String[]{GrupoRunning.ID, GrupoRunning.NOMBRE};


    }

    @Override
    protected GrupoRunning toEntity(Cursor c) {
        if (c.getCount() <= 0) {
            return null;
        } else {
            c.moveToFirst();
            return new GrupoRunning(c);

        }
    }

    @Override
    protected List<GrupoRunning> toList(Cursor c) {
        List<GrupoRunning> results = new Vector<>();

        if (c.getCount() <= 0) {
            return results;
        } else {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                results.add(new GrupoRunning(c));
                c.moveToNext();
            }
            Comparator<GrupoRunning> comp = new Comparator<GrupoRunning>() {

                @Override
                public int compare(GrupoRunning grupoRunning, GrupoRunning t1) {
                    return grupoRunning.getNombre().compareTo(t1.getNombre());
                }
            };
            Collections.sort(results, comp);
            results.add(0, new GrupoRunning(999, "Ninguno"));
            return results;
        }
    }

    @Override
    protected String getTableName(Class<? extends IEntity> clazz) {
        return "GRUPOS_RUNNING";
    }
}
