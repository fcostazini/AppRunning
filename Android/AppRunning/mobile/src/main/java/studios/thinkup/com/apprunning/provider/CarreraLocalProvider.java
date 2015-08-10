package studios.thinkup.com.apprunning.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.model.entity.IEntity;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;

/**
 * Created by Facundo on 02/08/2015.
 * Provider Local de Carreras
 */
public class CarreraLocalProvider extends GenericProvider<Carrera> implements ICarreraProvider {
    public CarreraLocalProvider(Context c) {
        super(c);
    }

    @Override
    protected String[] getFields(Class<? extends IEntity> clazz) {
        String[] fields = {
                Carrera.ID,
                Carrera.CIUDAD,
                Carrera.RECOMENDADA,
                Carrera.PROVINCIA,
                Carrera.MODALIDADES,
                Carrera.FECHA_INICIO,
                Carrera.HORA_INICIO,
                Carrera.DESCRIPCION,
                Carrera.DIRECCION,
                Carrera.DISTANCIAS_DISPONIBLE,
                Carrera.NOMBRE,
                Carrera.URL_IMAGEN,
                Carrera.URL_WEB};
        return fields;
    }

    @Override
    protected Carrera toEntity(Cursor c) {
        if (c.getCount() <= 0) {
            return null;
        } else {
            c.moveToFirst();
            return new Carrera(c);


        }
    }

    public Carrera actualizarCarrera(Carrera carrera) throws EntidadNoGuardadaException {
        Carrera uc = this.getById(carrera.getId());
        if (uc != null) {
            return this.update(carrera);
        } else {
            return this.grabar(carrera);
        }


    }

    @Override
    protected List<Carrera> toList(Cursor c) {
        List<Carrera> results = new Vector<>();
        if (c.getCount() <= 0) {
            return results;
        } else {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                results.add(new Carrera(c));
                c.moveToNext();
            }
            return results;
        }

    }


    @Override
    public Carrera getById(Integer id) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = this.dbProvider.getReadableDatabase();
            String[] params = {id.toString()};
            c = null;

            c = db.query("CARRERA", this.getFields(Carrera.class), "ID_CARRERA = ?", params, null, null, "ID_CARRERA");

            return this.toEntity(c);


        } catch (Exception e) {
            return null;
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }

        }

    }

    private ContentValues getUpdateFields(Carrera ent) {


        ContentValues parametros = new ContentValues();
        parametros.put( Carrera.ID, ent.getId());
        parametros.put( Carrera.CIUDAD, ent.getCiudad());
        parametros.put( Carrera.PROVINCIA, ent.getProvincia());
        parametros.put( Carrera.MODALIDADES, ent.getModalidades());
        parametros.put( Carrera.FECHA_INICIO, ent.getFechaInicio());
        parametros.put( Carrera.HORA_INICIO, ent.getHoraInicio());
        parametros.put( Carrera.DESCRIPCION, ent.getDescripcion());
        parametros.put( Carrera.DIRECCION,ent.getDireccion());
        parametros.put( Carrera.DISTANCIAS_DISPONIBLE, ent.getDistanciaDisponible());
        parametros.put( Carrera.NOMBRE,ent.getNombre());
        parametros.put( Carrera.URL_IMAGEN, ent.getUrlImagen());
        parametros.put( Carrera.URL_WEB, ent.getUrlWeb());
        return  parametros;


    }

    @Override
    public Carrera update(Carrera entidad) throws EntidadNoGuardadaException {

        SQLiteDatabase db = null;
        Cursor c = null;
        String[] params = {entidad.getId().toString()};
        try {
            db = this.dbProvider.getWritableDatabase();

            int result = db.update(this.getTableName(entidad.getClass()),
                    getUpdateFields(entidad), entidad.getNombreId() + " = ?", params);
            if (result > 0) {
                return entidad;
            } else {
                throw new EntidadNoGuardadaException("No se realizó el update" + entidad.getId().toString());
            }
        } catch (Exception e) {
            throw new EntidadNoGuardadaException(e);
        } finally {
            if (c != null && !c.isClosed()) {
                c.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }


    }

    @Override
    public Carrera grabar(Carrera entidad) throws EntidadNoGuardadaException {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = this.dbProvider.getWritableDatabase();

            long result = db.insertOrThrow(this.getTableName(entidad.getClass()),
                    null, this.getUpdateFields(entidad));
            if (result >= 0) {
                entidad.setId(Long.valueOf(result).intValue());
                return entidad;
            } else {
                throw new EntidadNoGuardadaException("No se realizó el insert" + entidad.getId().toString());
            }
        } catch (Exception e) {
            throw new EntidadNoGuardadaException(e);
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }


    }

}
