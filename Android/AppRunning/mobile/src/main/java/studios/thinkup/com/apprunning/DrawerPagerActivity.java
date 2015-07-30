package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by fcostazini on 21/05/2015.
 * Activity con Drawer y Pager
 */
public abstract class DrawerPagerActivity extends ActivityConFiltro {

    @Override
    protected void defineContentView() {
        setContentView(R.layout.fragment_pager_drawer);
    }

    protected abstract PagerAdapter getAdapter();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView(savedInstanceState);
    }


    protected void initView(Bundle savedInstance) {
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        initFiltro(savedInstance);
        viewPager.setAdapter(this.getAdapter());
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabsStrip.setViewPager(viewPager);
    }

}

