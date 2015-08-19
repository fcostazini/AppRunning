package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.ProvinciaCiudadDTO;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarreraDTO;
import studios.thinkup.com.apprunning.provider.CarreraLocalProvider;
import studios.thinkup.com.apprunning.provider.FiltrosProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraDTOProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;
import studios.thinkup.com.apprunning.provider.restProviders.FiltrosRemoteProvider;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioCarreraProviderRemote;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioProviderRemote;

/**
 * Created by fcostazini on 07/08/2015.
 * Activity StartUp
 */
public class StartUpActivity extends Activity {
    private UsuarioApp usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startup_activity);
        initUsuario(savedInstanceState);
        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar);
        TextView txt = (TextView) findViewById(R.id.txt_status);
        new UpdateAppData(txt, pb).execute(this.usuario);

    }

    private void mostrarUsuarioNoConfirmado() {

        findViewById(R.id.loading).setVisibility(View.GONE);
        findViewById(R.id.message).setVisibility(View.VISIBLE);

    }

    private void initUsuario(Bundle savedInstanceState) {
        if (this.getIntent().getExtras() != null &&
                this.getIntent().getExtras().containsKey("usuario")) {
            this.usuario = (UsuarioApp) this.getIntent().getExtras().getSerializable("usuario");
        }

        if (this.usuario == null && savedInstanceState != null &&
                savedInstanceState.containsKey("usuario")) {
            this.usuario = (UsuarioApp) savedInstanceState.getSerializable("usuario");
        }
        if (this.usuario == null) {
            Intent i = new Intent(this, SeleccionarUsuarioActivity.class);
            startActivity(i);
        }


    }

    private class UpdateAppData extends AsyncTask<UsuarioApp, Integer, Integer> {
        private TextView txt;
        private ProgressBar pb;

        public UpdateAppData(TextView txt, ProgressBar pb) {
            this.txt = txt;
            this.pb = pb;
        }


        @Override
        protected void onCancelled(Integer code) {
            if (code != null && code.equals(999)) {
                mostrarUsuarioNoConfirmado();
            }
        }

        @Override
        protected Integer doInBackground(UsuarioApp... usuarioApps) {
            UsuarioProvider up = new UsuarioProvider(StartUpActivity.this);
            UsuarioCarreraProviderRemote upr = new UsuarioCarreraProviderRemote(StartUpActivity.this);
            if (!usuarioApps[0].getVerificado()) {
                if (NetworkUtils.isConnected(StartUpActivity.this)) {
                    UsuarioProviderRemote upRemote = new UsuarioProviderRemote(StartUpActivity.this);
                    UsuarioApp uRemote = upRemote.getUsuarioByEmail(usuarioApps[0].getEmail());
                    if(uRemote == null){
                        cancel(true);
                    }
                    if (uRemote != null && !uRemote.getVerificado()) {
                        cancel(true);
                    }
                    if (isCancelled()) {
                        return 999;
                    }
                } else {
                    cancel(true);
                    if (isCancelled()) {
                        return 999;
                    }
                }
            }


            if (up.getUsuarioByEmail(usuarioApps[0].getEmail()) == null) {
                try {
                    publishProgress(5);
                    up.grabar(usuarioApps[0]);

                    publishProgress(30);
                    List<UsuarioCarrera> carreras = upr.getUsuarioCarrerasById(UsuarioCarrera.class, usuarioApps[0].getId());
                    publishProgress(35);
                    UsuarioCarreraProvider upLocal = new UsuarioCarreraProvider(StartUpActivity.this, usuarioApps[0]);
                    CarreraLocalProvider cLocal = new CarreraLocalProvider(StartUpActivity.this);
                    for (UsuarioCarrera uc : carreras) {
                        uc.setUsuario(usuarioApps[0].getId());
                        upLocal.grabar(uc);
                        cLocal.grabar(uc.getCarrera());
                    }
                } catch (EntidadNoGuardadaException e) {
                    publishProgress(100);
                    return 9;
                }
            } else {
                if (NetworkUtils.isConnected(StartUpActivity.this)) {
                    publishProgress(25);
                    UsuarioCarreraDTOProvider uDtoPr = new UsuarioCarreraDTOProvider(StartUpActivity.this);
                    List<UsuarioCarreraDTO> carrerasLocales = uDtoPr.getAllByUsuario(usuarioApps[0].getId());
                    if (!carrerasLocales.isEmpty()) {
                        try {
                            publishProgress(40);
                            upr.syncCarreras(carrerasLocales);
                        } catch (EntidadNoGuardadaException e) {
                            publishProgress(100);
                            return 9;
                        }
                    }

                }
            }

            publishProgress(50);

            if (NetworkUtils.getConnectivityStatus(StartUpActivity.this) == NetworkUtils.NETWORK_STATUS_NOT_CONNECTED)

            {
                publishProgress(100);
            } else

            {
                publishProgress(70);
                FiltrosRemoteProvider fpr = new FiltrosRemoteProvider(StartUpActivity.this);
                List<ProvinciaCiudadDTO> filtros = fpr.getFiltros();
                if (filtros != null && !filtros.isEmpty()) {
                    FiltrosProvider fp = new FiltrosProvider(StartUpActivity.this);
                    publishProgress(80);
                    fp.actualizarFiltros(filtros);
                }

            }

            return 1;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            switch (values[0]) {
                case 0:
                    pb.setProgress(5);
                    txt.setText("Guardando usuario...");
                    break;
                case 15:
                    pb.setProgress(15);
                    txt.setText("Descargando Tus Carreras...");
                    break;
                case 30:
                    pb.setProgress(30);
                    txt.setText("Sincorizando carreras...");
                    break;

                case 35:
                    pb.setProgress(35);
                    txt.setText("Guardando tus carreras...");
                    break;
                case 25:
                    pb.setProgress(25);
                    txt.setText("Sincronizando carreras...");
                    break;
                case 45:
                    pb.setProgress(45);
                    txt.setText("Actualizando carreras...");
                    break;
                case 50:
                    pb.setProgress(50);
                    txt.setText("Buscando Filtros...");
                    break;
                case 70:
                    pb.setProgress(70);
                    txt.setText("Descargando Filtros...");
                    break;
                case 80:
                    pb.setProgress(80);
                    txt.setText("Actualizando Filtros...");
                    break;
            }

        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            ((RunningApplication) StartUpActivity.this.getApplication()).setUsuario(StartUpActivity.this.usuario);
            Intent i = new Intent(StartUpActivity.this, RecomendadosActivity.class);
            startActivity(i);

        }
    }
}
