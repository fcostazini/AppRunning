package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;

import studios.thinkup.com.apprunning.adapter.ResultadoCarrerasPagerAdapter;
import studios.thinkup.com.apprunning.model.TutorialesPaginaEnum;


public class MisCarrerasActivity extends DrawerPagerActivity {
    @Override
    protected void initFiltro(Bundle savedInstance) {
        super.initFiltro(savedInstance);
        filtro.clean();
        filtro.setIdUsuario(this.getIdUsuario());

    }

    @Override
    protected PagerAdapter getAdapter() {
        return new ResultadoCarrerasPagerAdapter(getSupportFragmentManager(), filtro);
    }
    @Override
    public int getTutorialPage() {
        return TutorialesPaginaEnum.MIS_CARRERAS.getId();
    }

}
