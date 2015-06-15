package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.provider.helper.DataBaseHelper;

/**
 * Created by FaQ on 23/05/2015.
 * Provider de las Zonas
 */
public class FiltrosProvider {
    private DataBaseHelper dbProvider;

    public FiltrosProvider(Context context) {
        this.dbProvider = new DataBaseHelper(context);
    }

    public List<String> getProvincias() {
        List<String> resultados = new Vector<>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = this.dbProvider.getReadableDatabase();
            c = db.rawQuery("SELECT DISTINCT " + Carrera.PROVINCIA + " FROM CARRERA", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                resultados.add("TODAS");
                while (!c.isAfterLast()) {
                    resultados.add(c.getString(c.getColumnIndex(Carrera.PROVINCIA)));
                    c.moveToNext();
                }
            }
            return resultados;
        } catch (Exception e) {
            return null;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
    }

    public List<String> getCiudades(String provincia) {
        List<String> resultados = new Vector<>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = this.dbProvider.getReadableDatabase();
            String[] param = {provincia};
            if (provincia.equals("TODOS")) {
                c = db.rawQuery("SELECT DISTINCT " + Carrera.CIUDAD + " FROM CARRERA ", null);
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        resultados.add(c.getString(c.getColumnIndex(Carrera.CIUDAD)));
                    }
                }
            } else {
                c = db.rawQuery("SELECT DISTINCT " + Carrera.CIUDAD + " FROM CARRERA Where " + Carrera.PROVINCIA + " = ? ", param);
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    while (!c.isAfterLast()) {
                        resultados.add(c.getString(c.getColumnIndex(Carrera.CIUDAD)));
                        c.moveToNext();
                    }
                }
            }
            return resultados;
        } catch (Exception e) {
            return null;
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
            if (c != null && !c.isClosed()) {
                c.close();
            }
        }
    }
}
