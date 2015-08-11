package studios.thinkup.com.apprunning;


import android.os.Bundle;
import android.support.v4.app.Fragment;

import studios.thinkup.com.apprunning.fragment.TiemposCarrerasFragment;


public class TiemposCarrerasActivity extends ResultadosFiltrablesActivity {
    @Override
    protected void initFiltro(Bundle savedInstance) {
        super.initFiltro(savedInstance);

    }

    @Override
    protected Fragment getFragment() {
        return new TiemposCarrerasFragment();
    }


}
