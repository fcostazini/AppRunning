package studios.thinkup.com.apprunning.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

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
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "TODAS";
            case 1:
                return "MIS CARRERAS";
            case 2:
                return "CORRIDAS";
            case 3:
                return "FAVORITAS";
            default:
                return "";
        }
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment;
        Filtro filtroNuevo = null;
        try {
            filtroNuevo = (Filtro) this.filtro.clone();
            filtroNuevo.setFechaDesde(null);
            filtroNuevo.setFechaHasta(null);
        } catch (CloneNotSupportedException e) {
            Log.println(1, "ERROR", e.getMessage());
        }
        switch (i) {
            case 0: {
                filtroNuevo.setMeGusta(null);
                filtroNuevo.setInscripto(null);
                filtroNuevo.setCorrida(null);
                break;
            }
            case 1: {
                filtroNuevo.setMeGusta(null);
                filtroNuevo.setInscripto(Boolean.TRUE);
                filtroNuevo.setCorrida(Boolean.FALSE);
                break;
            }

            case 2: {
                filtroNuevo.setMeGusta(null);
                filtroNuevo.setInscripto(null);
                filtroNuevo.setCorrida(Boolean.TRUE);
                break;
            }
            case 3: {
                filtroNuevo.setMeGusta(Boolean.TRUE);
                filtroNuevo.setInscripto(null);
                filtroNuevo.setCorrida(null);
                break;
            }
        }
        Bundle b = new Bundle();
        b.putSerializable(Filtro.FILTRO_ID, filtroNuevo);
        fragment = new CarrerasResultadoFragment();
        fragment.setArguments(b);

        return fragment;


    }

}
