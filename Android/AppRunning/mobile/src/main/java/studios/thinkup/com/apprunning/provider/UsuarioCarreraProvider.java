package studios.thinkup.com.apprunning.provider;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.broadcast.handler.UpdateBuffer;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.IEntity;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;

/**
 * Created by fcostazini on 22/05/2015.
 * <p/>
 * Provider de las carreras
 */
public class UsuarioCarreraProvider extends GenericProvider<UsuarioCarrera> implements IUsuarioCarreraProvider {


    private UsuarioApp u;

    public UsuarioCarreraProvider(Context c, UsuarioApp usuarioApp) {
        super(c);
        this.u = usuarioApp;
    }


    @Override

    public UsuarioCarrera getByIdCarrera(long carrera) {
        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            db = this.dbProvider.getReadableDatabase();
            String fields = getStringFields();
            String[] params = {String.valueOf(this.u.getId()), String.valueOf(carrera)};
            c = db.rawQuery("SELECT " + fields + " FROM CARRERA c LEFT JOIN USUARIO_CARRERA uc ON c.ID_CARRERA = uc.CARRERA AND uc.USUARIO = ? WHERE c.ID_CARRERA = ?", params);
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

    @Override
    public List<UsuarioCarrera> findTiemposByFiltro(Filtro filtro) {
        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            db = this.dbProvider.getReadableDatabase();
            String fields = getStringFields();
            QueryGenerator qGen = new QueryGenerator(filtro);
            String query = "SELECT " + fields + " FROM CARRERA c JOIN USUARIO_CARRERA uc ON c.ID_CARRERA = uc.CARRERA and uc.TIEMPO > 0 ";
            query += qGen.getWhereCondition();
            c = db.rawQuery(query, null);
            return this.toList(c);
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

    private String getStringFields() {
        String fields = "c.NOMBRE";
        fields += ", c.DISTANCIA_DISPONIBLE";
        fields += ", c.MODALIDADES";
        fields += ", c.PROVINCIA";
        fields += ", c.CIUDAD";
        fields += ", c.DIRECCION";
        fields += ", c.FECHA_INICIO";
        fields += ", c.HORA_INICIO";
        fields += ", c.DESCRIPCION";
        fields += ", c.URL_WEB";
        fields += ", c.RECOMENDADA";
        fields += ", c.URL_IMAGEN";
        fields += ", c.ID_CARRERA";
        fields += ", uc.CARRERA";
        fields += ", uc.TIEMPO";
        fields += ", uc.ANOTADO";
        fields += ", uc.ME_GUSTA";
        fields += ", uc.CORRIDA";
        fields += ", uc.DISTANCIA";
        fields += ", uc.MODALIDAD";
        fields += ", uc.ID_USUARIO_CARRERA";
        fields += ", uc.USUARIO";
        return fields;
    }

    @Override
    public UsuarioCarrera actualizarCarrera(UsuarioCarrera usuarioCarrera) throws EntidadNoGuardadaException {
        UsuarioCarrera uc = null;
        if (usuarioCarrera.getUsuario() == null || usuarioCarrera.getUsuario() == 0) {
            usuarioCarrera.setUsuario(this.u.getId());
        }
        if (usuarioCarrera.getId() != null && usuarioCarrera.getId() > 0) {
            uc = this.update(usuarioCarrera);
        } else {
            uc = this.grabar(usuarioCarrera);
        }


        UpdateBuffer.getInstance().bufferUsuarioCarrera(uc, this.c);

        return uc;
    }


    @Override
    protected UsuarioCarrera toEntity(Cursor c) {
        if (c.getCount() <= 0) {
            return null;
        } else {
            UsuarioCarrera uc = null;
            c.moveToFirst();
            uc = new UsuarioCarrera(c);
            return uc;

        }


    }


    @Override
    protected List<UsuarioCarrera> toList(Cursor c) {
        List<UsuarioCarrera> results = new Vector<>();
        UsuarioCarrera uc = null;
        if (c.getCount() <= 0) {
            return results;
        } else {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                uc = new UsuarioCarrera(c);
                results.add(uc);
                c.moveToNext();
            }

            return results;
        }

    }

    @Override
    protected String getTableName(Class<? extends IEntity> clazz) {
        return "USUARIO_CARRERA";
    }

    @Override
    public UsuarioCarrera findById(Class<UsuarioCarrera> clazz, Integer id) {
        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            db = this.dbProvider.getReadableDatabase();
            String fields = getStringFields();
            String[] params = {String.valueOf(this.u.getId()), String.valueOf(id)};
            c = db.rawQuery("SELECT " + fields + " FROM CARRERA c LEFT JOIN USUARIO_CARRERA uc ON c.ID_CARRERA = uc.CARRERA AND uc.USUARIO = ? WHERE uc.ID_USUARIO_CARRERA = ?", params);
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

    protected String[] getFields(Class<? extends IEntity> clazz) {
        String[] fields = {
                UsuarioCarrera.ANOTADO,
                UsuarioCarrera.CARRERA,
                UsuarioCarrera.ID,
                UsuarioCarrera.ME_GUSTA,
                UsuarioCarrera.DISTANCIA,
                UsuarioCarrera.MODALIDAD,
                UsuarioCarrera.TIEMPO,
                UsuarioCarrera.ID_USUARIO
        };
        return fields;
    }
}


