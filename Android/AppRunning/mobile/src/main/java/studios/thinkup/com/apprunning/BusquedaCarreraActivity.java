package studios.thinkup.com.apprunning;

import android.os.Bundle;

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
