package studios.thinkup.com.apprunning.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

import studios.thinkup.com.apprunning.model.entity.IEntity;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.exceptions.CampoNoMapeableException;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;
import studios.thinkup.com.apprunning.provider.helper.DataBaseHelper;

/**
 * Created by FaQ on 13/06/2015.
 * Implementacion de Provider generico
 *
 * @param <T> tipo de entidad
 */
public abstract class GenericProvider<T  extends IEntity> implements IProvider<T> {
    protected DataBaseHelper dbProvider;


    public GenericProvider(Context c) {
        this.dbProvider = new DataBaseHelper(c);
    }

    ;

    protected String getTableName(Class<? extends IEntity> clazz) {
        return clazz.getSimpleName().toUpperCase();
    }

    protected abstract String[] getFields(Class<? extends IEntity> clazz);

    @Override
    public T findById(Class<T> clazz, Integer id) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
        db = this.dbProvider.getReadableDatabase();
        String[] params = {id.toString()};
        c = null;

            c = db.query(this.getTableName(clazz), this.getFields(clazz), "ID_USUARIO_CARRERA = ?", params, null, null, "ID_USUARIO_CARRERA");

            return  this.toEntity(c);


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

    protected abstract T toEntity(Cursor c);

    protected abstract List<T> toList(Cursor c);

    @Override
    public List<T> findAll(Class<T> clazz) {
        SQLiteDatabase db = this.dbProvider.getReadableDatabase();
        Cursor c = null;
        try {
            c = db.query(this.getTableName(clazz), this.getFields(clazz), null, null, null, null, "ID_USUARIO_CARRERA");
            List<T> ent = this.toList(c);
            c.close();
            db.close();
            return ent;


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

    @Override
    public T update(T entidad) throws EntidadNoGuardadaException {
        if (entidad.getId() != null && entidad.getId()>0) {
            SQLiteDatabase db = null;
            Cursor c = null;
            String[] params = {entidad.getId().toString()};
            try {
                db = this.dbProvider.getWritableDatabase();

                int result = db.update(this.getTableName(entidad.getClass()),
                                        getUpdateFields(entidad), entidad.getNombreId() + " = ?",params);
                if(result > 0 ){
                    return  entidad;
                }else{
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

        } else {
            throw new EntidadNoGuardadaException("Entidad sin id");
        }

    }



    private ContentValues getUpdateFields(T ent) {

        ContentValueFactory cv = new ContentValueFactory();
        try {
            return cv.getContentValues(ent);
        } catch (CampoNoMapeableException e) {
            return new ContentValues();
        }
    }

    @Override
    public T grabar(T entidad) throws EntidadNoGuardadaException {
        if (entidad.getId() ==null || entidad.getId() <=0 ) {
            SQLiteDatabase db = null;
            Cursor c = null;
            try {
                db = this.dbProvider.getWritableDatabase();

                long result = db.insertOrThrow(this.getTableName(entidad.getClass()),
                        null, this.getUpdateFields(entidad));
                if(result >= 0 ){
                    entidad.setId(new Long(result).intValue());
                    return  entidad;
                }else{
                    throw new EntidadNoGuardadaException("No se realizó el insert" + entidad.getId().toString());
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

        } else {
            throw new EntidadNoGuardadaException("Entidad tiene ID, usar UPDATE");
        }
    }
}
