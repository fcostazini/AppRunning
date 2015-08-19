package studios.thinkup.com.apprunning;

import android.os.Bundle;

import studios.thinkup.com.apprunning.model.Filtro;

/**
 * Created by fcostazini on 30/07/2015.
 * Activity con un parametro de filtro y el menu de filtro
 */
public abstract class ActivityConFiltro extends MainNavigationActivity {
    protected Filtro filtro;

    protected void initFiltro(Bundle savedInstance) {
        if (savedInstance != null && savedInstance.containsKey(Filtro.FILTRO_ID)) {
            this.filtro = (Filtro) savedInstance.getSerializable(Filtro.FILTRO_ID);

        }
        if (this.filtro == null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(Filtro.FILTRO_ID)) {
            this.filtro = (Filtro) getIntent().getExtras().getSerializable(Filtro.FILTRO_ID);
        }
        if (this.filtro == null) {
            this.filtro = new Filtro(getDefaultSettings());
        }


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFiltro(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.filtro != null) {
            outState.putSerializable(Filtro.FILTRO_ID, this.filtro);
        }
    }

}
