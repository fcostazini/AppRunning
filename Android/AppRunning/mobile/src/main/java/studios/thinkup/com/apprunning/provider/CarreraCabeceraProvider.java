package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;
import studios.thinkup.com.apprunning.provider.dbProviders.GenericProvider;
import studios.thinkup.com.apprunning.provider.helper.DataBaseHelper;

/**
 * Created by FaQ on 08/06/2015.
 */
public class CarreraCabeceraProvider implements ICarreraCabeceraProvider {

    private GenericProvider dbProvider;
    private Context context;


    public CarreraCabeceraProvider(Context context) {
        this.context = context;
        dbProvider = new GenericProvider(new DataBaseHelper(context));

    }
    @Override
    public List<CarreraCabecera> getCarrerasByFiltro(Filtro filtro) {
       QueryGenerator qGen = new QueryGenerator(filtro);
        SQLiteOpenHelper db =  new DataBaseHelper(context);

        String fields = "c.ID, c.nombre,c.fecha_inicio, c.distancia," +
                " c.descripcion, c.url_imagen, c.zona, ifnull(uc.me_gusta,0) as ME_GUSTA," +
                " ifnull(uc.anotado,0) as ANOTADO, ifnull(uc.corrida,0) as CORRIDA ";


        String query = "Select "+ fields + " from CARRERA  c" +
                " left join USUARIO_CARRERA  uc on c.id = uc.carrera ";
        query += qGen.getWhereCondition();
        Cursor c = db.getReadableDatabase().rawQuery(query,null);
        if(c.getCount()>0){


                return this.toCarrerasCabecera(c);

        }
        return new Vector<>();
    }

    private List<CarreraCabecera> toCarrerasCabecera(Cursor cursor) {
        List<CarreraCabecera> resultados = new Vector<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            resultados.add(CarreraCabecera.getBuilder()
                    .codigoCarrera(cursor.getInt(cursor.getColumnIndex("ID")))
                    .nombre(cursor.getString(cursor.getColumnIndex("NOMBRE")))
                    .fechaInicio(new Date(cursor.getLong(cursor.getColumnIndex("FECHA_INICIO"))))
                    .distancia(cursor.getInt(cursor.getColumnIndex("DISTANCIA")))
                    .descripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")))
                    .urlImage(cursor.getString(cursor.getColumnIndex("URL_IMAGEN")))
                    .zona(cursor.getString(cursor.getColumnIndex("ZONA")))
                    .meGusta(cursor.getInt(cursor.getColumnIndex("ME_GUSTA")) == 1)
                    .fueCorrida(cursor.getInt(cursor.getColumnIndex("CORRIDA")) == 1)
                    .estoyInscripto(cursor.getInt(cursor.getColumnIndex("ANOTADO")) == 1)
                    .build());
            cursor.moveToNext();
        }
        cursor.close();
        return resultados;
    }
}
