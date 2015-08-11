package studios.thinkup.com.apprunning;


import android.support.v4.app.Fragment;

import studios.thinkup.com.apprunning.fragment.CarrerasResultadoFragment;


public class CarrerasActivity extends ResultadosFiltrablesActivity {

    @Override
    protected Fragment getFragment() {
        return new CarrerasResultadoFragment();
    }
}
