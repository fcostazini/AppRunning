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
    public int getTutorialPage() {
        return TutorialesPaginaEnum.BUSQUEDA.getId();
    }
}
