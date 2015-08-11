package studios.thinkup.com.apprunning;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by Facundo on 11/08/2015.
 * Activity para manejar los amigos
 */
public class AmigosActivity extends MainNavigationActivity {
    @Override
    protected void defineContentView() {
        setContentView(R.layout.activity_amigos);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_amigos, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        if (item.getItemId() == R.id.mnu_agregar_amigo) {
            Intent i = new Intent(this, BuscarAmigosActivity.class);
            startActivity(i);
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
}
