package studios.thinkup.com.apprunning.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import studios.thinkup.com.apprunning.fragment.CarrerasResultadoFragment;

import studios.thinkup.com.apprunning.fragment.EstadisticasFragment;

/**
 * Created by fcostazini on 21/05/2015.
 *
 * Datos Propios Pager
 */
public class MisDatosPagerAdapter extends FragmentPagerAdapter {
    public MisDatosPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "MIS CARRERAS";
            case 1: return "ESTADISTICAS";
            default: return "";
        }
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        switch (i){
            case 0:
                fragment = new CarrerasResultadoFragment();
                break;
            case 1:
                fragment = new EstadisticasFragment();
                break;
            default:
                fragment = new EstadisticasFragment();
                break;
        }
        return fragment;

    }
}
