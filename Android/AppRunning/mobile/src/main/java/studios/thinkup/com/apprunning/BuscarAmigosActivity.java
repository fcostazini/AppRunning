package studios.thinkup.com.apprunning;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import studios.thinkup.com.apprunning.model.TutorialesPaginaEnum;

/**
 * Created by Facundo on 11/08/2015.
 * Activity de Busqueda de Amigos
 */
public class BuscarAmigosActivity extends MainNavigationActivity {
    @Override
    protected void defineContentView() {
        setContentView(R.layout.activity_buscar_amigos);
    }

    @Override
    public int getTutorialPage() {
        return TutorialesPaginaEnum.BUSCAR_AMIGO.getId();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_buscar_amigos, menu);
        return true;
    }


}
