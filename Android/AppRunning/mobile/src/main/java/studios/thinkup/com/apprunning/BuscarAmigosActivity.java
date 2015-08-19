package studios.thinkup.com.apprunning;

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
}
