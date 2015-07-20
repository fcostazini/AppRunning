package studios.thinkup.com.apprunning;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import studios.thinkup.com.apprunning.fragment.CarrerasResultadoFragment;
import studios.thinkup.com.apprunning.fragment.TiemposCarrerasFragment;
import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.ConfigProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;


public class TiemposCarrerasActivity extends MainNavigationActivity {

    private Filtro filtro;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            ConfigProvider cp = new ConfigProvider(this);
            UsuarioApp ua = ((RunningApplication) this.getApplication()).getUsuario();

            DefaultSettings defaultSettings = cp.getByUsuario(ua.getId());
            if (defaultSettings == null) {
                defaultSettings = new DefaultSettings(ua.getId());
                try {
                    cp.grabar(defaultSettings);
                } catch (EntidadNoGuardadaException e) {
                    e.printStackTrace();
                }

            }

            // Get the ViewPager and set it's PagerAdapter so that it can display items
            if (getIntent().getExtras() != null && getIntent().getSerializableExtra(Filtro.class.getSimpleName()) != null) {
                this.filtro = (Filtro) getIntent().getExtras().getSerializable(Filtro.class.getSimpleName());
            } else {
                this.filtro = new Filtro(defaultSettings);
            }
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            this.filtro.setIdUsuario(((RunningApplication) getApplication()).getUsuario().getId());
            TiemposCarrerasFragment fragment = TiemposCarrerasFragment.newInstance(this.filtro);
            fragmentTransaction.add(R.id.content_fragment, fragment);
            fragmentTransaction.commit();
        }

    }

    @Override
    protected void defineContentView() {
        setContentView(R.layout.activity_resultados);
    }


}
