package studios.thinkup.com.apprunning.provider.dbProviders;

import android.database.Cursor;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.provider.helper.DataBaseHelper;

/**
 * Created by dcamarro on 24/02/2015.
 * Provider general
 */
public class GenericProvider {

    protected DataBaseHelper helper;

    public GenericProvider(DataBaseHelper db) {
        this.helper = db;

        this.helper.getReadableDatabase();

    }

    public List<String> getDistinctColumns(String tabla, String columna){

        List<String> resultado = new Vector<>();
        String[] columns = {columna};
        Cursor c = null;
        try {
            c = helper.getReadableDatabase().query(true, tabla, columns, null, null, null, null, columns[0], null);
            // looping through all rows and adding to list
            if (c.moveToFirst()) {
                do {
                    resultado.add(c.getString(c.getColumnIndex(columns[0])));
                } while (c.moveToNext());
            }
            helper.close();
            c.close();
        }finally {
            helper.close();
           if(c!= null) c.close();
        }
        return resultado;

    }

    public Cursor getAllByWhere(String tabla, String where, String orderBy){
        String query = "select * from "+tabla+" ";
        if (where != null)
            query += where;
        if(orderBy != null){
            query += " " + orderBy;
        }

        return helper.getReadableDatabase().rawQuery(query, null);
    }

    public Cursor getFildsByWhere(String tabla, String fields, String where, String orderBy){
        String query = "select " +  fields +" from "+tabla+" ";
        if (where != null)
            query += where;
        if(orderBy != null){
            query += " " + orderBy;
        }

        return helper.getReadableDatabase().rawQuery(query, null);
    }

    public String matarCaracteresEspeciales(String s){
        String sinCarEs = "REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(upper(";
        sinCarEs += s;
        sinCarEs += "),'Á','A'), 'É','E'),'Í','I'),'Ó','O'),'Ú','U'),'Ñ','N')";
        return sinCarEs;
    }

    public Cursor executeQuery(String query){
        if(query!= null && !query.isEmpty()){
            return helper.getReadableDatabase().rawQuery(query, null);
        }
        return null;
    }
}
