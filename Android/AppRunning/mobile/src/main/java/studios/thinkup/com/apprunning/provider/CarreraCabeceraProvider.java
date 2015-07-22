package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
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
    private Filtro filtro;


    public CarreraCabeceraProvider(Context context) {
        this.context = context;
        dbProvider = new GenericProvider(new DataBaseHelper(context));

    }

    @Override
    public List<CarreraCabecera> getCarrerasByFiltro(Filtro filtro) {
        QueryGenerator qGen = new QueryGenerator(filtro);
        SQLiteOpenHelper db = new DataBaseHelper(context);
        this.filtro = filtro;
        String fields = "c.ID_CARRERA, c.NOMBRE,c.FECHA_INICIO, c.HORA_INICIO, c.DISTANCIA_DISPONIBLE," +
                " c.DESCRIPCION, c.URL_IMAGEN, c.PROVINCIA, c.CIUDAD, uc.ID_USUARIO_CARRERA, uc.DISTANCIA, uc.MODALIDAD, " +
                " ifnull(uc.me_gusta,0) as ME_GUSTA," +
                " ifnull(uc.ANOTADO,0) as ANOTADO, ifnull(uc.CORRIDA,0) as CORRIDA ";


        String query = "Select " + fields + " from CARRERA  c" +
                " left join USUARIO_CARRERA  uc on c.id_carrera = uc.carrera ";
        query += qGen.getWhereCondition();
        Cursor c = null;
        try {
            c = db.getReadableDatabase().rawQuery(query, null);
            if (c.getCount() > 0) {


                List<CarreraCabecera> resultados = this.toCarrerasCabecera(c);
                List<CarreraCabecera> resultadosFinales = getCarreraCabecerasFiltradasPorDistancia(filtro, resultados);
                if (resultadosFinales != null) return resultadosFinales;

            }
            return new Vector<>();
        } catch (Exception e) {
            return new Vector<>();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
            if(db!= null){
                db.close();
            }

        }
    }

    private List<CarreraCabecera> getCarreraCabecerasFiltradasPorDistancia(Filtro filtro, List<CarreraCabecera> resultados) {

            List<CarreraCabecera> resultadosFinales = new Vector<>();

            String[] distancias = null;
            for (CarreraCabecera cc : resultados) {
                distancias = cc.getDistanciaDisponible().split("/");
                for (String s : distancias) {
                    if (Double.valueOf(s.trim()) >= filtro.getMinDistancia() && Double.valueOf(s.trim()) <= filtro.getMaxDistancia()) {
                        resultadosFinales.add(cc);
                        break;
                    }
                }
            }
            return resultadosFinales;


    }

    @Override
    public List<CarreraCabecera> getCarrerasRecomendadas(Filtro filtro) {

        SQLiteOpenHelper db = new DataBaseHelper(context);

        String fields = "c.ID_CARRERA, c.NOMBRE,c.FECHA_INICIO, c.HORA_INICIO, c.DISTANCIA_DISPONIBLE," +
                " c.DESCRIPCION, c.URL_IMAGEN, c.PROVINCIA, c.CIUDAD, uc.ID_USUARIO_CARRERA, uc.DISTANCIA, uc.MODALIDAD, " +
                " ifnull(uc.me_gusta,0) as ME_GUSTA," +
                " ifnull(uc.ANOTADO,0) as ANOTADO, ifnull(uc.CORRIDA,0) as CORRIDA ";


        String query = "Select " + fields + " from CARRERA  c" +
                " left join USUARIO_CARRERA  uc on c.id_carrera = uc.carrera";
        QueryGenerator qGen = new QueryGenerator(filtro);
        query += qGen.getWhereCondition();


        Cursor c = null;
        try {
            c = db.getReadableDatabase().rawQuery(query, null);
            if (c.getCount() > 0) {

                return getCarreraCabecerasFiltradasPorDistancia(filtro,this.toCarrerasCabecera(c));


            }
            return new Vector<>();
        } catch (Exception e) {
            return new Vector<>();
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
            if(db!= null){
                db.close();
            }

        }

    }

    private List<CarreraCabecera> toCarrerasCabecera(Cursor cursor) {
        List<CarreraCabecera> resultados = new Vector<>();
        cursor.moveToFirst();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            while (!cursor.isAfterLast()) {
                resultados.add(CarreraCabecera.getBuilder()
                        .codigoCarrera(cursor.getInt(cursor.getColumnIndex("ID_CARRERA")))
                        .nombre(cursor.getString(cursor.getColumnIndex("NOMBRE")))
                        .fechaInicio(sf.parse(cursor.getString(cursor.getColumnIndex("FECHA_INICIO"))))
                        .distanciaDisponible(cursor.getString(cursor.getColumnIndex("DISTANCIA_DISPONIBLE")))
                        .descripcion(cursor.getString(cursor.getColumnIndex("DESCRIPCION")))
                        .urlImage(cursor.getString(cursor.getColumnIndex("URL_IMAGEN")))
                        .provincia(cursor.getString(cursor.getColumnIndex("PROVINCIA")))
                        .zona(cursor.getString(cursor.getColumnIndex("CIUDAD")))
                        .hora(cursor.getString(cursor.getColumnIndex("HORA_INICIO")))
                        .meGusta(cursor.getInt(cursor.getColumnIndex("ME_GUSTA")) == 1)
                        .fueCorrida(cursor.getInt(cursor.getColumnIndex("CORRIDA")) == 1)
                        .estoyInscripto(cursor.getInt(cursor.getColumnIndex("ANOTADO")) == 1)
                        .distancia(cursor.getInt(cursor.getColumnIndex("DISTANCIA")))
                        .build());
                cursor.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        cursor.close();
        return resultados;
    }
}
