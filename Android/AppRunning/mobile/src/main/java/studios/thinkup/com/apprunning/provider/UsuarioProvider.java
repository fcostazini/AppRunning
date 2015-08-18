package studios.thinkup.com.apprunning.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.entity.CheckUsuarioPassDTO;
import studios.thinkup.com.apprunning.model.entity.IEntity;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.exceptions.CredencialesInvalidasException;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;
import studios.thinkup.com.apprunning.provider.exceptions.UsuarioBloqueadoException;
import studios.thinkup.com.apprunning.provider.exceptions.UsuarioInexistenteException;
import studios.thinkup.com.apprunning.provider.exceptions.UsuarioNoVerificadoException;

/**
 * Created by FaQ on 28/05/2015.
 * Implementacion local de IUsuarioProvider
 */
public class UsuarioProvider extends GenericProvider<UsuarioApp> implements IUsuarioProvider {

    public UsuarioProvider(Context c) {
        super(c);
    }

    @Override

    protected String[] getFields(Class<? extends IEntity> clazz) {
        String[] fields = {
                UsuarioApp.EMAIL,
                UsuarioApp.ID,
                UsuarioApp.TIPO_CUENTA,
                UsuarioApp.NOMBRE,
                UsuarioApp.APELLIDO,
                UsuarioApp.FOTO_PERFIL_URL,
                UsuarioApp.FECHA_NACIMIENTO,
                UsuarioApp.NICK,
                UsuarioApp.GRUPO_ID,
                UsuarioApp.VERIFICADO

        };
        return fields;
    }


    private ContentValues getUpdateFields(UsuarioApp ent) {


        ContentValues parametros = new ContentValues();
        parametros.put(UsuarioApp.ID, ent.getId());
        parametros.put(UsuarioApp.NICK, ent.getNick());
        parametros.put(UsuarioApp.NOMBRE, ent.getNombre());
        parametros.put(UsuarioApp.FECHA_NACIMIENTO, ent.getFechaNacimiento());
        parametros.put(UsuarioApp.FOTO_PERFIL_URL, ent.getFotoPerfil());
        parametros.put(UsuarioApp.APELLIDO, ent.getApellido());
        parametros.put(UsuarioApp.EMAIL, ent.getEmail());
        parametros.put(UsuarioApp.GRUPO_ID, ent.getGrupoId());
        parametros.put(UsuarioApp.TIPO_CUENTA, ent.getTipoCuenta());
        parametros.put(UsuarioApp.VERIFICADO, ent.getVerificado());

        return parametros;


    }

    @Override
    protected String getTableName(Class<? extends IEntity> clazz) {
        return "USUARIO_APP";
    }

    @Override
    public UsuarioApp update(UsuarioApp entidad) throws EntidadNoGuardadaException {
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
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    @Override
    public UsuarioApp grabar(UsuarioApp entidad) throws EntidadNoGuardadaException {
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            List<UsuarioApp> existentes =this.findAll(UsuarioApp.class);

            db = this.dbProvider.getWritableDatabase();
            db.beginTransaction();
            if(existentes.size()>= 1){
                for(UsuarioApp u : existentes){
                    this.deleteUsuario(u);
                }
            }
            long result = db.insertOrThrow(this.getTableName(entidad.getClass()),
                    null, this.getUpdateFields(entidad));
            if (result >= 0) {
                entidad.setId(Long.valueOf(result).intValue());
                db.setTransactionSuccessful();
                return entidad;
            } else {
                throw new EntidadNoGuardadaException("No se realizó el insert" + entidad.getId().toString());
            }
        } catch (Exception e) {
            throw new EntidadNoGuardadaException(e);
        } finally {
            if(db!= null) {
                db.endTransaction();
                if(db.isOpen()){
                    db.close();
                }
            }

        }
    }

    @Override
    public UsuarioApp getUsuarioByEmail(String email) {

        SQLiteDatabase db = null;
        Cursor c = null;

        try {
            db = this.dbProvider.getReadableDatabase();
            String[] params = {email};
            c = db.query(this.getTableName(UsuarioApp.class), this.getFields(UsuarioApp.class),
                    UsuarioApp.EMAIL + " = ? ", params, null, null, null);
            return toEntity(c);
        } catch (Exception e) {
            e.printStackTrace();
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
    public UsuarioApp loginUsuario(CheckUsuarioPassDTO param) throws CredencialesInvalidasException, UsuarioBloqueadoException, UsuarioNoVerificadoException {
        throw new RuntimeException("No implementado");
    }

    @Override
    public Boolean recuperarPass(String email) throws UsuarioInexistenteException {
        throw new RuntimeException("No implementado");
    }

    @Override
    protected UsuarioApp toEntity(Cursor c) {

        if (c.getCount() <= 0) {
            return null;
        } else {
            c.moveToFirst();
            return getUsuarioApp(c);

        }

    }

    private UsuarioApp getUsuarioApp(Cursor c) {
        UsuarioApp ua = new UsuarioApp();
        try {
            ua.setEmail(c.getString(c.getColumnIndexOrThrow(UsuarioApp.EMAIL)));
            ua.setId(c.getInt(c.getColumnIndexOrThrow(UsuarioApp.ID)));
            ua.setNombre(c.getString(c.getColumnIndexOrThrow(UsuarioApp.NOMBRE)));
            ua.setTipoCuenta(c.getString(c.getColumnIndexOrThrow(UsuarioApp.TIPO_CUENTA)));
            ua.setNick(c.getString(c.getColumnIndex(UsuarioApp.NICK)));
            ua.setApellido(c.getString(c.getColumnIndex(UsuarioApp.APELLIDO)));
            ua.setFechaNacimiento(c.getString(c.getColumnIndex(UsuarioApp.FECHA_NACIMIENTO)));
            ua.setFotoPerfil(c.getString(c.getColumnIndex(UsuarioApp.FOTO_PERFIL_URL)));
            ua.setGrupoId(c.getString(c.getColumnIndex(UsuarioApp.GRUPO_ID)));
            ua.setVerificado(c.getInt(c.getColumnIndex(UsuarioApp.VERIFICADO)) == 1);
            return ua;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public UsuarioApp findById(Class<UsuarioApp> clazz, Integer id) {
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
    protected List<UsuarioApp> toList(Cursor c) {
        List<UsuarioApp> results = new Vector<>();

        if (c.getCount() <= 0) {
            return results;
        } else {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                results.add(getUsuarioApp(c));
                c.moveToNext();
            }

            return results;
        }

    }

    public boolean deleteUsuario(UsuarioApp u) {
        UsuarioApp aBorrar = this.findById(UsuarioApp.class, u.getId());
        SQLiteDatabase db = null;
        if (aBorrar != null) {
            try {
                db = this.dbProvider.getWritableDatabase();
                db.delete("USUARIO_CARRERA", "USUARIO = " + u.getId(), null);
                return db.delete("USUARIO_APP", "ID = " + u.getId(), null) > 0;

            } catch (Exception e) {
                return false;
            } finally {
                if (db != null && db.isOpen()) {
                    db.close();
                }

            }
        }else{
            return false;
        }
    }
}

