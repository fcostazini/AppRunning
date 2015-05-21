package studios.thinkup.com.apprunning.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import studios.thinkup.com.apprunning.fragment.CarrerasResultadoFragment;
import studios.thinkup.com.apprunning.fragment.DetalleCarreraFragment;
import studios.thinkup.com.apprunning.fragment.TemporizadorFragment;

/**
 * Created by fcostazini on 21/05/2015.
 */
public class ResultadoCarrerasPagerAdapter extends FragmentPagerAdapter {
    public ResultadoCarrerasPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "TODAS";
            case 1: return "INSCRIPTO";
            case 2: return "CORRIDAS";
            default: return "";
        }
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        // Crear un FoodFragment con el nombre como argumento

            fragment = new CarrerasResultadoFragment();

        return fragment;



    }

}
