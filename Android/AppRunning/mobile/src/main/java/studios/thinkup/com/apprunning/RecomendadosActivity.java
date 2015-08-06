package studios.thinkup.com.apprunning;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import studios.thinkup.com.apprunning.fragment.RecomendadosFragment;


public class RecomendadosActivity extends ResultadosFiltrablesActivity {
    @Override
    protected Fragment getFragment() {
        return new RecomendadosFragment();
    }

}
