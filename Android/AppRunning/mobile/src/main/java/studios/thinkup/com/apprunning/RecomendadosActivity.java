package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import studios.thinkup.com.apprunning.adapter.BusquedaPagerAdapter;
import studios.thinkup.com.apprunning.adapter.RecomendadosPagerAdapter;


public class RecomendadosActivity extends DrawerPagerActivity {
    @Override
    protected ViewPager setPagerAdapter() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new RecomendadosPagerAdapter(getSupportFragmentManager()));
        return viewPager;
    }

}
