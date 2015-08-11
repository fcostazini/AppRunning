package studios.thinkup.com.apprunning.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.astuetz.PagerSlidingTabStrip;

import java.util.HashMap;
import java.util.Map;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.fragment.AgregarAmigosFragment;
import studios.thinkup.com.apprunning.fragment.AmigosFragment;
import studios.thinkup.com.apprunning.fragment.DetalleAmigoFragment;

/**
 * Created by fcostazini on 21/05/2015.
 * Pager de Detalle de carrera
 */
public class AmigosPagerAdapter extends FragmentPagerAdapter implements PagerSlidingTabStrip.IconTabProvider {

    private Map<Integer, Fragment> fragmentMap;

    public AmigosPagerAdapter(FragmentManager fm) {
        super(fm);

        fragmentMap = new HashMap<>();
        fragmentMap.put(0, new DetalleAmigoFragment());


   }

    @Override
    public int getCount() {
        return 1;

    }

    @Override
    public int getPageIconResId(int position) {
        switch (position) {
            case 0:
                return R.drawable.ic_detalle;

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
        return this.fragmentMap.get(i);


    }

}
