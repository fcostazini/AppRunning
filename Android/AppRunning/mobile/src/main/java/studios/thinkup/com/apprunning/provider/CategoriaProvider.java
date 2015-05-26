package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.database.Cursor;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.Categoria;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.Subcategoria;
import studios.thinkup.com.apprunning.provider.dbProviders.GenericProvider;
import studios.thinkup.com.apprunning.provider.helper.DataBaseHelper;

/**
 * Created by fcostazini on 22/05/2015.
 * Provider de Categoria
 */
public class CategoriaProvider {
    private GenericProvider provider;

    public CategoriaProvider(Context context) {
        provider = new GenericProvider(new DataBaseHelper(context));

    }

    public List<Categoria> getCategorias(Filtro filtro) {

        if (filtro.getSubcategoria() != null) {
            switch (filtro.getSubcategoria()) {
                case ZONA:
                    return this.toCategorias(this.getData(Subcategoria.ZONA.name(),filtro));

                case DISTANCIA:
                    return this.toCategorias(this.getData(Subcategoria.DISTANCIA.name(), filtro));

                case GENERO:
                    return this.toCategorias(this.getData(Subcategoria.GENERO.name(), filtro));
                default:
                    return this.toCategorias(this.getData(Subcategoria.ZONA.name(), filtro));
            }
        }else{
            return this.toCategorias(this.getData(Subcategoria.ZONA.name(), filtro));
        }

    }

    private Cursor getData(String tipo, Filtro filtro) {
        QueryGenerator qGen = new QueryGenerator(filtro);
        String fields =  "'" + tipo + "' as tipo, "+ tipo.toLowerCase() + ", count(nombre) as cantidad";
        return this.provider.getFildsByWhere("carrera",fields, qGen.getWhereCondition(), " group by " +tipo.toLowerCase()+ " order by cantidad desc; ");

    }


    private List<Categoria> toCategorias(Cursor cursor) {
        List<Categoria> resultados = new Vector<>();
        String nombre;
        Integer cantidad;
        String tipo;
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tipo = cursor.getString(0);
            nombre = cursor.getString(1);
            cantidad = cursor.getInt(2);

            resultados.add(new Categoria(nombre, cantidad, Subcategoria.getByName(tipo)));
            cursor.moveToNext();
        }

        return resultados;
    }
}