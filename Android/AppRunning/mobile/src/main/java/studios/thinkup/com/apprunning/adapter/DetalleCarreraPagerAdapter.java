package studios.thinkup.com.apprunning.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import studios.thinkup.com.apprunning.fragment.DetalleCarreraFragment;
import studios.thinkup.com.apprunning.fragment.TemporizadorFragment;
import studios.thinkup.com.apprunning.model.Carrera;

/**
 * Created by fcostazini on 21/05/2015.
 */
public class DetalleCarreraPagerAdapter extends FragmentPagerAdapter{
private Carrera carrera;
    public DetalleCarreraPagerAdapter(FragmentManager fm, Carrera carrera) {
        super(fm);
        this.carrera = carrera;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0: return "DETALLE";
            case 1: return "TEMPORIZADOR";
            default: return "";
        }
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        // Crear un FoodFragment con el nombre como argumento
        if(i==0){
            fragment = DetalleCarreraFragment.newInstance(this.carrera);
            //Bundle args = new Bundle();

        }else{
            fragment = new TemporizadorFragment();
        }
        return fragment;



    }
}
