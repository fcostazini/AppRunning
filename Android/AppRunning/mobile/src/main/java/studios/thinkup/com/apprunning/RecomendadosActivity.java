package studios.thinkup.com.apprunning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import studios.thinkup.com.apprunning.broadcast.handler.RegistrationIntentService;
import studios.thinkup.com.apprunning.fragment.RecomendadosFragment;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;


public class RecomendadosActivity extends ResultadosFiltrablesActivity {

    @Override
    protected Fragment getFragment() {
        return new RecomendadosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(this.getIntent().getExtras()!=null &&this.getIntent().getExtras().containsKey(DetalleCarreraActivity.ID_CARRERA)){
            Intent i = new Intent(this, DetalleCarreraActivity.class);
            Bundle b1 = new Bundle();
            b1.putInt(DetalleCarreraActivity.ID_CARRERA,this.getIntent().getExtras().getInt(DetalleCarreraActivity.ID_CARRERA));
            i.putExtras(b1);
            startActivity(i);


        }

    }


}
