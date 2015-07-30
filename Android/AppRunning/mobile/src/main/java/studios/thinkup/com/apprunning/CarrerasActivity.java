package studios.thinkup.com.apprunning;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import studios.thinkup.com.apprunning.fragment.CarrerasResultadoFragment;
import studios.thinkup.com.apprunning.model.Filtro;


public class CarrerasActivity extends ResultadosFiltrablesActivity {

    @Override
    protected Fragment getFragment() {
        return new CarrerasResultadoFragment();
    }
}
