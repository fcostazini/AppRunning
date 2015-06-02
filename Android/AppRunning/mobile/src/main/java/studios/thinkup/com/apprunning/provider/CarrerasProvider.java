package studios.thinkup.com.apprunning.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.Carrera;
import studios.thinkup.com.apprunning.model.CarreraCabecera;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.Genero;
import studios.thinkup.com.apprunning.provider.dbProviders.GenericProvider;
import studios.thinkup.com.apprunning.provider.helper.DataBaseHelper;
import studios.thinkup.com.apprunning.provider.tables.CarreraTable;

/**
 * Created by fcostazini on 22/05/2015.
 * <p/>
 * Provider de las carreras
 */
public class CarrerasProvider implements ICarrerasProvider {

    private GenericProvider dbProvider;
    private Context context;


    public CarrerasProvider(Context context) {
        this.context = context;
        dbProvider = new GenericProvider(new DataBaseHelper(context));

    }

    @Override
    public List<CarreraCabecera> getCarreras(Filtro filtro) {
        QueryGenerator qGen = new QueryGenerator(filtro);
        String fields = "codigo, nombre, fecha_largada, distancia, descripcion, url_imagen, zona, ''";
        String query = "select " + fields + ", ";
        query += "uc.me_gusta, uc.anotado, uc.corrida from carrera as c" +
                " left join usuario_carrera as uc on c.codigo = uc.codigo_carrera ";
        query += qGen.getWhereCondition();
        Cursor c = dbProvider.executeQuery(query);
        List<CarreraCabecera> resultados = this.toCarrerasCabecera(c);
        c.close();

        return resultados;
    }

    @Override
    public Integer getCantidadCarreras(Filtro filtro) {
        QueryGenerator queryGenerator = new QueryGenerator(filtro);
        Cursor c = this.dbProvider.getFildsByWhere("carrera", " Count(codigo) ", queryGenerator.getWhereCondition(), "");
        long rowCount = c.getCount();
        c.close();
        return Long.valueOf(rowCount).intValue();
    }

    private List<CarreraCabecera> toCarrerasCabecera(Cursor cursor) {
        List<CarreraCabecera> resultados = new Vector<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            resultados.add(CarreraCabecera.getBuilder()
                    .codigoCarrera(cursor.getInt(cursor.getColumnIndex(CarreraTable.CODIGO.getNombre())))
                    .nombre(cursor.getString(cursor.getColumnIndex(CarreraTable.NOMBRE.getNombre())))
                    .fechaInicio(new Date(cursor.getLong(cursor.getColumnIndex(CarreraTable.FECHA_LARGADA.getNombre()))))
                    .distancia(cursor.getInt(cursor.getColumnIndex(CarreraTable.DISTANCIA.getNombre())))
                    .descripcion(cursor.getString(cursor.getColumnIndex(CarreraTable.DESCRIPCION.getNombre())))
                    .urlImage(cursor.getString(cursor.getColumnIndex(CarreraTable.URL_IMAGEN.getNombre())))
                    .zona(cursor.getString(cursor.getColumnIndex(CarreraTable.ZONA.getNombre())))
                    .meGusta(cursor.getInt(cursor.getColumnIndex("me_gusta")) == 1)
                    .fueCorrida(cursor.getInt(cursor.getColumnIndex("corrida")) == 1)
                    .estoyInscripto(cursor.getInt(cursor.getColumnIndex("anotado")) == 1)
                    .build());
            cursor.moveToNext();
        }
        cursor.close();
        return resultados;
    }

    @Override
    public Carrera getCarreraByCodigo(int codigo) {


        String fields = "codigo, nombre, fecha_largada, distancia, descripcion, url_imagen, zona, direccion, genero, ";
        String query = "select " + fields;
        query += "uc.me_gusta, uc.anotado, uc.corrida from carrera as c" +
                " left join usuario_carrera as uc on c.codigo = uc.codigo_carrera ";
        query += " where codigo = " + codigo + ";";


        Cursor cursor = this.dbProvider.executeQuery(query);
        if (cursor != null) {
            cursor.moveToFirst();

            Carrera c = Carrera.getBuilder()
                    .codigoCarrera(cursor.getInt(cursor.getColumnIndex(CarreraTable.CODIGO.getNombre())))
                    .nombre(cursor.getString(cursor.getColumnIndex(CarreraTable.NOMBRE.getNombre())))
                    .fechaInicio(new Date(cursor.getLong(cursor.getColumnIndex(CarreraTable.FECHA_LARGADA.getNombre()))))
                    .distancia(cursor.getInt(cursor.getColumnIndex(CarreraTable.DISTANCIA.getNombre())))
                    .descripcion(cursor.getString(cursor.getColumnIndex(CarreraTable.DESCRIPCION.getNombre())))
                    .urlImage(cursor.getString(cursor.getColumnIndex(CarreraTable.URL_IMAGEN.getNombre())))
                    .genero(Genero.getByName(cursor.getString(cursor.getColumnIndex(CarreraTable.GENERO.getNombre()))))
                    .direccion(cursor.getString(cursor.getColumnIndex(CarreraTable.DIRECCION.getNombre())))

                    .meGusta(cursor.getInt(cursor.getColumnIndex("me_gusta")) == 1)
                    .fueCorrida(cursor.getInt(cursor.getColumnIndex("corrida")) == 1)
                    .estoyInscripto(cursor.getInt(cursor.getColumnIndex("anotado")) == 1)
                    .build();

            cursor.close();
            return c;
        }
        return null;
    }

    @Override
    public void anotarEnCarrera(Integer codigoCarrera, Integer id) {

        this.actualizarEstados(codigoCarrera, id, "anotado", true);


    }

    private void actualizarEstados(Integer codigoCarrera, Integer id, String estado, boolean value) {
        int valor = value ? 1 : 0;
        DataBaseHelper helper = new DataBaseHelper(this.context);
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("select Count(codigo_carrera) from usuario_carrera where codigo_carrera = " +
                codigoCarrera + " AND id_usuario = " + id, null);
        long rowCount = 0;
        rowCount = c.getCount();
        c.close();
        if (rowCount > 0) {
            String where = "codigo_carrera = " + codigoCarrera + " AND id_usuario = " + id;
            ContentValues cv = new ContentValues();
            cv.put("id_usuario", id);
            cv.put("codigo_carrera", codigoCarrera);
            cv.put(estado, valor);
            db.beginTransaction();
            rowCount = db.update("usuario_carrera", cv, where, null);
            if (rowCount > 0) {
                db.setTransactionSuccessful();
            }
            db.endTransaction();


        } else {
            ContentValues cv = new ContentValues();
            cv.put("id_usuario", id);
            cv.put("codigo_carrera", codigoCarrera);
            cv.put(estado, valor);
            db.beginTransaction();
            try {
                rowCount = db.insertOrThrow("usuario_carrera", "", cv);
            } catch (Exception e) {
                Log.e("ERROR", e.toString());
            }
            if (rowCount > 0) {
                db.setTransactionSuccessful();
            }
            db.endTransaction();

        }
        c.close();
        db.close();
        helper.close();
    }

    @Override
    public void desanotarEnCarrera(Integer codigoCarrera, Integer id) {
        this.actualizarEstados(codigoCarrera, id, "anotado", false);
    }

    @Override
    public void meGusta(Integer codigoCarrera, Integer id) {
        this.actualizarEstados(codigoCarrera, id, "me_gusta", true);
    }

    @Override
    public void noMegusta(Integer codigoCarrera, Integer id) {
        this.actualizarEstados(codigoCarrera, id, "me_gusta", false);
    }

    @Override
    public void carreraCorrida(Integer codigoCarrera, Integer id) {
        this.actualizarEstados(codigoCarrera, id, "corrida", true);

    }

    @Override
    public void carreraNoCorrida(Integer codigoCarrera, Integer id) {
        this.actualizarEstados(codigoCarrera, id, "corrida", false);

    }


}
