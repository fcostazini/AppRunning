package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.database.Cursor;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.CarreraCabecera;
import studios.thinkup.com.apprunning.model.EstadoCarrera;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.provider.dbProviders.GenericProvider;
import studios.thinkup.com.apprunning.provider.helper.DataBaseHelper;

/**
 * Created by fcostazini on 22/05/2015.
 *
 * Provider de las carreras
 *
 */
public class CarrerasProvider {

    private GenericProvider dbProvider;


    public CarrerasProvider(Context context) {

        dbProvider = new GenericProvider(new DataBaseHelper(context));

    }

    public List<CarreraCabecera> getCarreras(Filtro filtro) {
    QueryGenerator qGen = new QueryGenerator(filtro);
        String fields = "codigo, nombre, fecha_largada, distancia, descripcion, url_imagen, ''";
        Cursor c = dbProvider.getFildsByWhere("carrera", fields, qGen.getWhereCondition(),"");
        return this.toCarrerasCabecera(c);
    }

    public Integer getCantidadCarreras(Filtro filtro) {
        QueryGenerator queryGenerator  = new QueryGenerator(filtro);
        Cursor c = this.dbProvider.getFildsByWhere("carrera"," Count(codigo) ",queryGenerator.getWhereCondition(),"");
        c.moveToFirst();
        return c.getInt(0);

    }
    private List<CarreraCabecera> toCarrerasCabecera(Cursor cursor) {
        List<CarreraCabecera> resultados = new Vector<>();
        Integer codigo;
        String nombre;
        Date fechaLargada = new Date();
        String distancia;
        String descripcion;
        String urlImage;
        EstadoCarrera estado;

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            codigo = cursor.getInt(0);
            nombre = cursor.getString(1);
            fechaLargada.setTime(cursor.getLong(2));
            distancia = cursor.getString(3);
            descripcion = cursor.getString(4);
            urlImage = cursor.getString(5);
            estado = EstadoCarrera.getByName(cursor.getString(6));
            resultados.add(new CarreraCabecera(codigo,
                    nombre, fechaLargada, distancia, descripcion, urlImage, estado));
            cursor.moveToNext();
        }

        return resultados;
    }

}
