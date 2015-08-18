package studios.thinkup.com.apprunning.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;

import java.util.HashMap;
import java.util.Map;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.fragment.CarrerasAmigosFragment;
import studios.thinkup.com.apprunning.fragment.DetalleAmigoFragment;
import studios.thinkup.com.apprunning.fragment.TiemposCarrerasFragment;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;
import studios.thinkup.com.apprunning.model.entity.CamposOrdenEnum;

/**
 * Created by fcostazini on 21/05/2015.
 * Pager de Detalle de carrera
 */
public class AmigosPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    private Map<Integer, Fragment> fragmentMap;
    private AmigosDTO amigo;

    public AmigosPagerAdapter(FragmentManager fm, AmigosDTO amigo) {
        super(fm);
        this.amigo = amigo;
        fragmentMap = new HashMap<>();
        fragmentMap.put(0, new DetalleAmigoFragment());
        fragmentMap.put(1, new CarrerasAmigosFragment());


    }

    @Override
    public int getCount() {
        return 2;

    }

    @Override
    public int getPageIconResId(int position) {
        switch (position) {
            case 0:
                return R.drawable.ic_detalle;
            case 1:
                return R.drawable.ic_recomendadas;

        }
        return 0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "";

            default:
                return "";
        }

    }

    @Override
    public Fragment getItem(int i) {
        Bundle b = new Bundle();

        if (amigo != null) {

            b.putSerializable(AmigosDTO.FIELD_ID, amigo);
        }
        Fragment frag = this.fragmentMap.get(i);
        frag.setArguments(b);
        return frag;


    }

    public interface IAmigoActualizableFragment {

        void actualizarAmigo(AmigosDTO amigo);
    }

}
