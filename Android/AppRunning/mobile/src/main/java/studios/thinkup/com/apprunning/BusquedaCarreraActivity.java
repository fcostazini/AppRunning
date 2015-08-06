package studios.thinkup.com.apprunning;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import studios.thinkup.com.apprunning.model.TutorialesPaginaEnum;

public class BusquedaCarreraActivity extends MainNavigationActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void defineContentView() {
        setContentView(R.layout.busquedas_pager_fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_buscar_carrera, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == R.id.mnu_ayuda) {
            Intent i = new Intent(this, TutorialActivity.class);
            Bundle b = new Bundle();
            b.putInt(TutorialActivity.PAGINA_TUTORIAL, TutorialesPaginaEnum.BUSQUEDA.getId());
            i.putExtras(b);
            startActivity(i);
            return false;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }
}
