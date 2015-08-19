package studios.thinkup.com.apprunning.broadcast.handler;

import android.content.Context;
import android.os.AsyncTask;

import java.util.HashMap;
import java.util.Map;

import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarreraDTO;
import studios.thinkup.com.apprunning.provider.restProviders.Respuesta;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioCarreraProviderRemote;

/**
 * Created by Facundo on 01/08/2015.
 * Handler Singleton para manejo de Buffer de UsuarioCarrera
 */
public class UpdateBuffer {

    private static UpdateBuffer instance;
    private Map<Integer, UsuarioCarreraDTO> ucMap;
    private Map<Integer, UsuarioCarreraDTO> ucMapTemp;
    private boolean isUpdating;

    private UpdateBuffer() {
        this.ucMap = new HashMap<>();
        this.ucMapTemp = new HashMap<>();
        isUpdating = false;

    }

    public static UpdateBuffer getInstance() {
        if (instance == null) {
            instance = new UpdateBuffer();

        }
        return UpdateBuffer.instance;
    }


    public void bufferUsuarioCarrera(UsuarioCarrera uc, Context c) {
        UsuarioCarreraDTO udto = new UsuarioCarreraDTO(uc);
        if (instance.isUpdating) {
            this.ucMapTemp.put(udto.getIdUsuarioCarrera(), udto);
        } else {
            this.ucMap.put(udto.getIdUsuarioCarrera(), udto);
        }
        if (NetworkUtils.isWifiConnected(c)) {
            instance.publishBuffer(c);
        }
    }

    private void cleanBuffer() {
        this.ucMap.clear();
    }

    public boolean hasPendingUpdates() {
        return this.ucMap.size() >= 1;
    }

    public void publishBuffer(Context context) {

        SyncDataTask task = new SyncDataTask(context);
        instance.isUpdating = true;
        task.execute(this.ucMap);

    }

    public boolean isUpdating() {
        return instance.isUpdating;
    }

    private class SyncDataTask extends AsyncTask<Map<Integer, UsuarioCarreraDTO>, Integer, Integer> {
        private Context context;

        public SyncDataTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result != null && result.equals(Respuesta.CODIGO_OK)) {
                UpdateBuffer.getInstance().cleanBuffer();
            }
            UpdateBuffer.getInstance().isUpdating = false;
            for (UsuarioCarreraDTO uc : UpdateBuffer.getInstance().ucMapTemp.values()) {
                UpdateBuffer.getInstance().ucMap.put(uc.getIdUsuarioCarrera(), uc);
            }
            UpdateBuffer.getInstance().ucMapTemp = new HashMap<>();
        }

        @SafeVarargs
        @Override
        protected final Integer doInBackground(Map<Integer, UsuarioCarreraDTO>... params) {
            UsuarioCarreraProviderRemote usuarioCarreraProviderRemote = new UsuarioCarreraProviderRemote(context);
            try {
                return usuarioCarreraProviderRemote.syncCarreras(params[0].values());

            } catch (Exception e) {
                e.printStackTrace();
                return Respuesta.CODIGO_ERROR_INTERNO;
            }

        }
    }
}
