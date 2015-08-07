package studios.thinkup.com.apprunning.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import studios.thinkup.com.apprunning.model.entity.UsuarioCarreraDTO;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;
import studios.thinkup.com.apprunning.provider.helper.DataBaseHelper;

/**
 * Created by fcostazini on 07/08/2015.
 * Provider de USUARIO CARRERA DTO
 */
public class UsuarioCarreraDTOProvider {
    protected DataBaseHelper dbProvider;
    protected Context c;

    public UsuarioCarreraDTOProvider(Context c) {
        this.dbProvider = new DataBaseHelper(c);
        this.c = c;
    }

    private ContentValues getUpdateFields(UsuarioCarreraDTO ent){

        ContentValues parametros = new ContentValues();
        parametros.put( UsuarioCarreraDTO.ID, ent.getIdUsuarioCarrera());
        parametros.put(UsuarioCarreraDTO.ANOTADO, ent.isAnotado());
        parametros.put(UsuarioCarreraDTO.ME_GUSTA, ent.isMeGusta());
        parametros.put(UsuarioCarreraDTO.CORRIDA, ent.isCorrida());
        parametros.put(UsuarioCarreraDTO.CARRERA, ent.getIdCarrera());
        parametros.put(UsuarioCarreraDTO.DISTANCIA, ent.getDistancia());
        parametros.put(UsuarioCarreraDTO.TIEMPO, ent.getTiempo());
        parametros.put(UsuarioCarreraDTO.ID_USUARIO, ent.getUsuario());
        parametros.put(UsuarioCarreraDTO.MODALIDAD, ent.getModalidad());

        return  parametros;

    }
    public UsuarioCarreraDTO grabar(UsuarioCarreraDTO uc)throws  EntidadNoGuardadaException{
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = this.dbProvider.getWritableDatabase();

            long result = db.insertOrThrow("USUARIO_CARRERA",
                    null, this.getUpdateFields(uc));
            if(result >= 0 ){
                if (uc.getIdUsuarioCarrera() == null || uc.getIdUsuarioCarrera() <= 0) {
                    uc.setIdUsuarioCarrera(Long.valueOf(result).intValue());
                }
                return  uc;
            }else{
                throw new EntidadNoGuardadaException("No se realizó el insert" + uc.getIdUsuarioCarrera().toString());
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
