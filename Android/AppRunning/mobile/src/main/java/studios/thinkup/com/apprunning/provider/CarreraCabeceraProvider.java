package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.SimpleFormatter;

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

        String fields = "c.ID_CARRERA, c.NOMBRE,c.FECHA_INICIO, c.DISTANCIA," +
                " c.DESCRIPCION, c.URL_IMAGEN, c.PROVINCIA, c.CIUDAD, uc.ID_USUARIO_CARRERA, ifnull(uc.me_gusta,0) as ME_GUSTA," +
                " ifnull(uc.ANOTADO,0) as ANOTADO, ifnull(uc.CORRIDA,0) as CORRIDA ";


        String query = "Select "+ fields + " from CARRERA  c" +
                " left join USUARIO_CARRERA  uc on c.id_carrera = uc.carrera ";
        query += qGen.getWhereCondition();
        Cursor c = db.getReadableDatabase().rawQuery(query,null);
        if(c.getCount()>0){


                return this.toCarrerasCabecera(c);

        }
        return new Vector<>();
    }

    @Override
    public List<CarreraCabecera> getCarrerasRecomendadas() {

        SQLiteOpenHelper db =  new DataBaseHelper(context);

        String fields = "c.ID_CARRERA, c.NOMBRE,c.FECHA_INICIO, c.DISTANCIA," +
                " c.DESCRIPCION, c.URL_IMAGEN, c.PROVINCIA, c.CIUDAD, uc.ID_USUARIO_CARRERA, ifnull(uc.me_gusta,0) as ME_GUSTA," +
                " ifnull(uc.ANOTADO,0) as ANOTADO, ifnull(uc.CORRIDA,0) as CORRIDA ";


        String query = "Select "+ fields + " from CARRERA  c" +
                " left join USUARIO_CARRERA  uc on c.id_carrera = uc.carrera Where recomendada = 1";

        Cursor c = db.getReadableDatabase().rawQuery(query,null);
        if(c.getCount()>0){


            return this.toCarrerasCabecera(c);

        }
        return new Vector<>();
    }

    private List<CarreraCabecera> toCarrerasCabecera(Cursor cursor)  {
        List<CarreraCabecera> resultados = new Vector<>();
        cursor.moveToFirst();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            while (!cursor.isAfterLast()) {
                resultados.add(CarreraCabecera.getBuilder()
                        .codigoCarrera(cursor.getInt(cursor.getColumnIndex("ID_CARRERA")))
                        .nombre(cursor.getString(cursor.getColumnIndex("NOMBRE")))
                        .fechaInicio(sf.parse(cursor.getString(cursor.getColumnIndex("FECHA_INICIO"))))
                        .distancia(cursor.getInt(cursor.getColumnIndex("DISTANCIA")))
                        .descripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")))
                        .urlImage(cursor.getString(cursor.getColumnIndex("URL_IMAGEN")))
                        .provincia(cursor.getString(cursor.getColumnIndex("PROVINCIA")))
                        .zona(cursor.getString(cursor.getColumnIndex("CIUDAD")))
                        .meGusta(cursor.getInt(cursor.getColumnIndex("ME_GUSTA")) == 1)
                        .fueCorrida(cursor.getInt(cursor.getColumnIndex("CORRIDA")) == 1)
                        .estoyInscripto(cursor.getInt(cursor.getColumnIndex("ANOTADO")) == 1)
                        .build());
                cursor.moveToNext();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        cursor.close();
        return resultados;
    }
}
