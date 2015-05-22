package studios.thinkup.com.apprunning.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import studios.thinkup.com.apprunning.fragment.CarrerasResultadoFragment;
import studios.thinkup.com.apprunning.model.Filtro;

/**
 * Created by fcostazini on 21/05/2015.
 */
public class ResultadoCarrerasPagerAdapter extends FragmentPagerAdapter {

    private Filtro filtro;
    public ResultadoCarrerasPagerAdapter(FragmentManager fm, Filtro filtro) {
        super(fm);
        this.filtro = filtro;
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

            fragment = CarrerasResultadoFragment.newInstance(this.filtro);

        return fragment;



    }

}
