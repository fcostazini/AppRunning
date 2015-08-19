package studios.thinkup.com.apprunning.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;

import java.util.HashMap;
import java.util.Map;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.fragment.AmigosEnCarreraFragment;
import studios.thinkup.com.apprunning.fragment.DetalleCarreraFragment;
import studios.thinkup.com.apprunning.fragment.EstadisticaCarreraFragment;
import studios.thinkup.com.apprunning.fragment.IUsuarioCarreraObservable;

/**
 * Created by fcostazini on 21/05/2015.
 * Pager de Detalle de carrera
 */
public class DetalleCarreraPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    private Map<Integer, Fragment> fragmentMap;


    public DetalleCarreraPagerAdapter(FragmentManager fm, IUsuarioCarreraObservable observable) {
        super(fm);

        fragmentMap = new HashMap<>();
        DetalleCarreraFragment df = new DetalleCarreraFragment();
        fragmentMap.put(0, df);

        if (observable != null) {
            df.setUsuarioObsercable(observable);

        }
        EstadisticaCarreraFragment ef = new EstadisticaCarreraFragment();

        if (observable != null) {
            observable.registrarObservadorUsuario(ef);
            ef.setObservable(observable);

        }
        fragmentMap.put(1, ef);

        AmigosEnCarreraFragment af = new AmigosEnCarreraFragment();
        fragmentMap.put(2,af);

    }

    @Override
    public int getCount() {
        return fragmentMap.size();

    }

    @Override
    public int getPageIconResId(int position) {
        switch (position) {
            case 0:
                return R.drawable.ic_detalle;
            case 1:
                return R.drawable.ic_cronometro;
            case 2:
                return R.drawable.ic_amigos;
        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            default:
                return "";
        }

    }

    @Override
    public Fragment getItem(int i) {
        return this.fragmentMap.get(i);


    }

}
