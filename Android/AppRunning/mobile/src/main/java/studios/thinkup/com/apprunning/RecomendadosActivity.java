package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import studios.thinkup.com.apprunning.adapter.RecomendadosPagerAdapter;


public class RecomendadosActivity extends DrawerPagerActivity {
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState == null) {
            initView();
        }
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new RecomendadosPagerAdapter(getSupportFragmentManager()));
        viewPager.setBackgroundResource(R.drawable.path);
        viewPager.getBackground().setAlpha(130);
        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(this.viewPager == null){
            initView();
        }
        this.viewPager.getAdapter().notifyDataSetChanged();

    }


}
