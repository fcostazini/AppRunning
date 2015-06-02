package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import studios.thinkup.com.apprunning.adapter.BusquedaPagerAdapter;
import studios.thinkup.com.apprunning.adapter.DetalleCarreraPagerAdapter;
import studios.thinkup.com.apprunning.adapter.RecomendadosPagerAdapter;
import studios.thinkup.com.apprunning.model.Carrera;
import studios.thinkup.com.apprunning.provider.CarrerasProvider;


public class RecomendadosActivity extends DrawerPagerActivity {
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new RecomendadosPagerAdapter(getSupportFragmentManager()));
        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.viewPager.getAdapter().notifyDataSetChanged();

    }


}
