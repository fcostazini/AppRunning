package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarreraDTO;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraDTOProvider;
import studios.thinkup.com.apprunning.provider.UsuarioProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioCarreraProviderRemote;

/**
 * Created by fcostazini on 07/08/2015.
 * Activity StartUp
 */
public class StartUpActivity extends Activity {
    private UsuarioApp usuario;
    private ProgressBar pb;
    private TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_activity);
        initUsuario(savedInstanceState);
        this.pb = (ProgressBar)findViewById(R.id.progressBar);
        this.txt = (TextView)findViewById(R.id.txt_status);
        new UpdateAppData(this.txt, this.pb).execute(this.usuario);

    }

    private void initUsuario(Bundle savedInstanceState){
        if(this.getIntent().getExtras()!=null &&
                this.getIntent().getExtras().containsKey("usuario")){
            this.usuario = (UsuarioApp) this.getIntent().getExtras().getSerializable("usuario");
        }

        if(this.usuario == null && savedInstanceState != null &&
                savedInstanceState.containsKey("usuario")){
            this.usuario = (UsuarioApp) savedInstanceState.getSerializable("usuario");
        }
        if(this.usuario == null){
            Intent i = new Intent(this, SeleccionarUsuarioActivity.class);
            startActivity(i);
        }


    }

    private class UpdateAppData extends  AsyncTask<UsuarioApp, Integer, Integer>{
        private TextView txt;
        private ProgressBar pb;

        public UpdateAppData(TextView txt, ProgressBar pb) {
            this.txt = txt;
            this.pb = pb;
        }

        @Override
        protected Integer doInBackground(UsuarioApp... usuarioApps) {
            UsuarioProvider up = new UsuarioProvider(StartUpActivity.this);
            if(up.getUsuarioByEmail(usuarioApps[0].getEmail()) == null){
                try {
                    up.grabar(usuarioApps[0]);
                    publishProgress(15);
                    UsuarioCarreraProviderRemote upr = new UsuarioCarreraProviderRemote(StartUpActivity.this);
                    List<UsuarioCarreraDTO> carreras = upr.getUsuarioCarrerasById(UsuarioCarrera.class,usuarioApps[0].getId());
                    publishProgress(35);
                    UsuarioCarreraDTOProvider upLocal = new UsuarioCarreraDTOProvider(StartUpActivity.this);
                    for(UsuarioCarreraDTO uc : carreras){
                        upLocal.grabar(uc);
                    }


                } catch (EntidadNoGuardadaException e) {
                    publishProgress(100);
                    return 9;
                }
            }
            setProgress(50);
            if(NetworkUtils.getConnectivityStatus(StartUpActivity.this) == NetworkUtils.NETWORK_STATUS_NOT_CONNECTED){
                publishProgress(100);
            }else{

            }

            return 1;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            switch (values[0]){
                case 0:
                    pb.setProgress(5);
                    txt.setText("Guardando usuario...");
                    break;
                case 15:
                    pb.setProgress(15);
                    txt.setText("Sincorizando info...");
                    break;
                case 35:
                    pb.setProgress(35);
                    txt.setText("Guardando tus estadisticas...");
                    break;
                case 50:
                    pb.setProgress(50);
                    txt.setText("Guardando Carreras...");
                    break;
            }

        }



        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            ((RunningApplication) StartUpActivity.this.getApplication()).setUsuario(StartUpActivity.this.usuario);
            Intent i = new Intent(StartUpActivity.this,RecomendadosActivity.class);
            startActivity(i);

        }
    }
}
