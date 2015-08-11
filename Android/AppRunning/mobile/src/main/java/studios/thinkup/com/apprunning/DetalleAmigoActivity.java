package studios.thinkup.com.apprunning;

import android.support.v4.view.PagerAdapter;

import studios.thinkup.com.apprunning.adapter.AmigosPagerAdapter;

/**
 * Created by Facundo on 11/08/2015.
 */
public class DetalleAmigoActivity extends DrawerPagerActivity {
    @Override
    protected PagerAdapter getAdapter() {
        return new AmigosPagerAdapter(this.getSupportFragmentManager());
    }
}
