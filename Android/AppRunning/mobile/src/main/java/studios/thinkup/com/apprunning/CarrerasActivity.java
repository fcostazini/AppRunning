package studios.thinkup.com.apprunning;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import studios.thinkup.com.apprunning.fragment.CarrerasResultadoFragment;
import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.ConfigProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;


public class CarrerasActivity extends ResultadosFiltrablesActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CarrerasResultadoFragment fragment = new CarrerasResultadoFragment();
        Bundle b = new Bundle();
        b.putSerializable(Filtro.FILTRO_ID, this.filtro);
        fragment.setArguments(b);
        fragmentTransaction.replace(R.id.content_fragment, fragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void defineContentView() {
        setContentView(R.layout.activity_resultados);
    }


}
