package studios.thinkup.com.apprunning.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import studios.thinkup.com.apprunning.fragment.BusquedaCategoria;
import studios.thinkup.com.apprunning.fragment.BusquedaFormulario;

/**
 * Created by fcostazini on 21/05/2015.
 * Adapter de Fragmentos para Busqueda
 */
public class BusquedaPagerAdapter extends FragmentPagerAdapter {
    public BusquedaPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "CATEGORIA";
            case 1: return "FORMULARIO";
            default: return "";
        }
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        // Crear un FoodFragment con el nombre como argumento
        if(i==0){
            fragment = BusquedaCategoria.newInstance();
        }else{
            fragment = BusquedaFormulario.newInstance();
        }


        return fragment;



    }
}
