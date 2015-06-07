package studios.thinkup.com.apprunning.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import studios.thinkup.com.apprunning.fragment.DetalleCarreraFragment;
import studios.thinkup.com.apprunning.TemporizadorActivity;
import studios.thinkup.com.apprunning.fragment.EstadisticaCarreraFragment;
import studios.thinkup.com.apprunning.fragment.EstadisticasFragment;
import studios.thinkup.com.apprunning.model.Carrera;

/**
 * Created by fcostazini on 21/05/2015.
 */
public class DetalleCarreraPagerAdapter extends FragmentPagerAdapter {
    private Carrera carrera;
    private boolean conEstadisticas;

    public DetalleCarreraPagerAdapter(FragmentManager fm, Carrera carrera) {
        super(fm);
        this.carrera = carrera;
        this.conEstadisticas =this.carrera.isEstoyInscripto();
    }

    @Override
    public int getCount() {
        if (this.conEstadisticas) {
            return 2;

        } else {
            return 1;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (this.conEstadisticas) {
            switch (position) {
                case 0:
                    return "DETALLE";
                case 1:
                    return "ESTADISTICAS";
                default:
                    return "";
            }
        } else {
            switch (position) {
                case 0:
                    return "DETALLE";
                default:
                    return "";
            }

        }
    }
        @Override
        public Fragment getItem ( int i){
            Fragment fragment;
            // Crear un FoodFragment con el nombre como argumento
            if (i == 0) {
                fragment = DetalleCarreraFragment.newInstance(this.carrera);
                //Bundle args = new Bundle();

            } else {
                fragment = EstadisticaCarreraFragment.newInstance(this.carrera);
            }
            return fragment;


        }
    }
