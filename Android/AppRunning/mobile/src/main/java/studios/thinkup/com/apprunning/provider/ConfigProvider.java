package studios.thinkup.com.apprunning.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.entity.IEntity;
import studios.thinkup.com.apprunning.provider.exceptions.CampoNoMapeableException;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;


/**
 * Created by FaQ on 13/06/2015.
 * Implementacion de Provider generico
 * <p/>
 * DefaultSettings
 */
public class ConfigProvider extends GenericProvider<DefaultSettings> implements IProvider<DefaultSettings> {

    public ConfigProvider(Context c) {
        super(c);

    }

    @Override
    protected String getTableName(Class<? extends IEntity> clazz) {
        return "DEFAULT_FILTRO";
    }

    @Override
    protected String[] getFields(Class<? extends IEntity> clazz) {
        return new String[]{DefaultSettings.ID,
                DefaultSettings.ID_USUARIO,
                DefaultSettings.DISTANCIA_MIN,
                DefaultSettings.DISTANCIA_MAX,
                DefaultSettings.MODALIDAD,
                DefaultSettings.PROVINCIA,
                DefaultSettings.CIUDAD,
                DefaultSettings.MESES_BUSQUEDA,
                DefaultSettings.ORDENAR_POR,
                DefaultSettings.SENTIDO_ORDEN};
    }


    @Override
    public DefaultSettings findById(Class<DefaultSettings> clazz, Integer id) {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = this.dbProvider.getReadableDatabase();
            String[] params = {id.toString()};
            c = null;

            c = db.query(this.getTableName(clazz), this.getFields(clazz), "ID = ?", params, null, null, "ID");

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

    @Override
    protected DefaultSettings toEntity(Cursor c) {
        if (c.getCount() <= 0) {
            return null;
        } else {
            DefaultSettings ds = null;
            c.moveToFirst();
            ds = new DefaultSettings(c);
            return ds;

        }


    }

    @Override
    protected List<DefaultSettings> toList(Cursor c) {
        List<DefaultSettings> results = new Vector<>();
        DefaultSettings ds = null;
        if (c.getCount() <= 0) {
            return results;
        } else {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                ds = new DefaultSettings(c);
                results.add(ds);
                c.moveToNext();
            }

            return results;
        }

    }

    @Override
    public List<DefaultSettings> findAll(Class<DefaultSettings> clazz) {
        SQLiteDatabase db = this.dbProvider.getReadableDatabase();
        Cursor c = null;
        try {
            c = db.query(this.getTableName(clazz), this.getFields(clazz), null, null, null, null, "ID");
            List<DefaultSettings> ent = this.toList(c);
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
    public DefaultSettings update(DefaultSettings entidad) throws EntidadNoGuardadaException {
        if (entidad.getId() != null && entidad.getId() > 0) {
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

        } else {
            return this.grabar(entidad);


        }

    }


    private ContentValues getUpdateFields(DefaultSettings ent) {

        ContentValueFactory cv = new ContentValueFactory();
        try {
            return cv.getContentValues(ent);
        } catch (CampoNoMapeableException e) {
            return new ContentValues();
        }
    }

    @Override
    public DefaultSettings grabar(DefaultSettings entidad) throws EntidadNoGuardadaException {
        if (entidad.getId() == null || entidad.getId() <= 0) {
            SQLiteDatabase db = null;
            Cursor c = null;
            try {
                db = this.dbProvider.getWritableDatabase();

                long result = db.insertOrThrow(this.getTableName(entidad.getClass()),
                        null, this.getUpdateFields(entidad));
                if (result >= 0) {
                    entidad.setId(new Long(result).intValue());
                    return entidad;
                } else {
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

    public DefaultSettings getByUsuario(Integer idUsuario) {
        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            db = this.dbProvider.getReadableDatabase();

            String[] params = {String.valueOf(idUsuario)};
            c = db.query(this.getTableName(DefaultSettings.class), this.getFields(DefaultSettings.class), "ID_USUARIO = ?", params, null, null, "ID");
            return this.toEntity(c);
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

}
