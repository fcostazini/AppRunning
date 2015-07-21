package studios.thinkup.com.apprunning;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import studios.thinkup.com.apprunning.fragment.CarrerasResultadoFragment;
import studios.thinkup.com.apprunning.fragment.TiemposCarrerasFragment;
import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.ConfigProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;


public class TiemposCarrerasActivity extends ResultadosFiltrablesActivity {
    @Override
    protected void initFiltro() {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Filtro.FILTRO_ID)) {
            this.filtro = (Filtro) getIntent().getExtras().getSerializable(Filtro.FILTRO_ID);
        } else {
            this.filtro = new Filtro(getDefaultSettings());
            this.filtro.clean();
            this.filtro.setIdUsuario(getIdUsuario());
            this.filtro.setInscripto(true);
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            this.filtro.setIdUsuario(((RunningApplication) getApplication()).getUsuario().getId());
            this.filtro.setInscripto(true);
            TiemposCarrerasFragment fragment = new TiemposCarrerasFragment();
            Bundle b = new Bundle();
            b.putSerializable(Filtro.FILTRO_ID,this.filtro);
            fragment.setArguments(b);
            fragmentTransaction.replace(R.id.content_fragment, fragment);
            fragmentTransaction.commit();
        }

    }



    @Override
    protected void defineContentView() {
        setContentView(R.layout.activity_resultados);
    }


}
