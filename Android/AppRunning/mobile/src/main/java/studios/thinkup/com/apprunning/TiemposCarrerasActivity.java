package studios.thinkup.com.apprunning;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import studios.thinkup.com.apprunning.fragment.TiemposCarrerasFragment;
import studios.thinkup.com.apprunning.model.Filtro;


public class TiemposCarrerasActivity extends ResultadosFiltrablesActivity {
    @Override
    protected void initFiltro(Bundle savedInstance) {
        if (savedInstance != null) {
            if (savedInstance.containsKey(Filtro.FILTRO_ID)) {
                this.filtro = (Filtro) savedInstance.getSerializable(Filtro.FILTRO_ID);
            }
        }
        if (this.filtro == null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(Filtro.FILTRO_ID)) {
            this.filtro = (Filtro) getIntent().getExtras().getSerializable(Filtro.FILTRO_ID);
        } else {
            this.filtro = new Filtro(getDefaultSettings());
            this.filtro.clean();
            this.filtro.setInscripto(true);
            this.filtro.setIdUsuario(getIdUsuario());
        }
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            TiemposCarrerasFragment fragment = new TiemposCarrerasFragment();
            Bundle b = new Bundle();
            b.putSerializable(Filtro.FILTRO_ID,this.filtro);
            fragment.setArguments(b);
            fragmentTransaction.replace(R.id.content_fragment, fragment);
            fragmentTransaction.commit();

    }



    @Override
    protected void defineContentView() {
        setContentView(R.layout.activity_resultados);
    }


}
