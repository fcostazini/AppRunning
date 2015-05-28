package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

/**
 * Created by fcostazini on 21/05/2015.
 * Activity con Drawer y Pager
 */
public abstract class DrawerPagerActivity extends MainNavigationActivity {

    @Override
    protected void defineContentView() {
        setContentView(R.layout.fragment_pager_drawer);
    }


}

