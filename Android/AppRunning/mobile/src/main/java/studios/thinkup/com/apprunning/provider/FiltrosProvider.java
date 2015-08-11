package studios.thinkup.com.apprunning.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.model.entity.ProvinciaCiudadDTO;
import studios.thinkup.com.apprunning.provider.helper.DataBaseHelper;

/**
 * Created by FaQ on 23/05/2015.
 * Provider de las Zonas
 */
public class FiltrosProvider implements IFiltrosProvider {
    public static final String TODAS_LAS_PROVINCIAS = "Todas las Provincias";
    public static final String TODAS_LAS_CIUDADES = "Todas las Ciudades";
    public static final String CAPITAL = "Capital Federal";
    public static final Integer MIN_DISTANCIA = 0;
    public static final Integer MAX_DISTANCIA = 100;
    private DataBaseHelper dbProvider;

    public FiltrosProvider(Context context) {
        this.dbProvider = new DataBaseHelper(context);
    }

    @Override
    public List<String> getProvincias() {
        List<String> resultados = new Vector<>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = this.dbProvider.getReadableDatabase();
            c = db.rawQuery("SELECT DISTINCT " + Carrera.PROVINCIA + " FROM PROVINCIAS_CIUDADES", null);
            if (c.getCount() > 0) {
                c.moveToFirst();
                resultados.add(TODAS_LAS_PROVINCIAS);
                while (!c.isAfterLast()) {
                    resultados.add(c.getString(c.getColumnIndex(Carrera.PROVINCIA)));
                    c.moveToNext();
                }
            }
            return resultados;
        } catch (Exception e) {
            e.printStackTrace();
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

    @Override
    public List<String> getCiudades(String provincia) {
        List<String> resultados = new Vector<>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = this.dbProvider.getReadableDatabase();
            String[] param = {provincia};
            if (provincia.equals(TODAS_LAS_PROVINCIAS)) {
                resultados.add(TODAS_LAS_CIUDADES);

            } else {
                c = db.rawQuery("SELECT DISTINCT " + Carrera.CIUDAD + " FROM PROVINCIAS_CIUDADES Where " + Carrera.PROVINCIA + " = ? ", param);
                if (c.getCount() > 0) {
                    c.moveToFirst();
                    resultados.add(TODAS_LAS_CIUDADES);
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

    public void actualizarFiltros(List<ProvinciaCiudadDTO> filtros) {

        List<ProvinciaCiudadDTO> previos = new Vector<>();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {

            db = this.dbProvider.getWritableDatabase();
            db.beginTransaction();
            db.delete("PROVINCIAS_CIUDADES", null, null);
            for (ProvinciaCiudadDTO pr : filtros) {
                db.insertOrThrow("PROVINCIAS_CIUDADES", null, getContentValues(pr));
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db != null && db.isOpen()) {
                db.endTransaction();
                db.close();
            }
        }

    }

    private ContentValues getContentValues(ProvinciaCiudadDTO pr) {
        ContentValues cv = new ContentValues();
        cv.put("PROVINCIA", pr.getProvincia());
        cv.put("CIUDAD", pr.getCiudad());
        return cv;
    }
}
