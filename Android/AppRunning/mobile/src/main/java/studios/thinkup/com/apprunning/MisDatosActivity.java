package studios.thinkup.com.apprunning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.astuetz.PagerSlidingTabStrip;

import studios.thinkup.com.apprunning.adapter.MisDatosPagerAdapter;


public class MisDatosActivity extends FragmentActivity implements AdapterView.OnItemClickListener {
    private FragmentPagerAdapter adapter;
    private ViewPager viewpager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_carrera_fragment_slider);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new MisDatosPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mis_datos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mnu_filtros:
                Intent i = new Intent(this, FiltrosPorDefectoActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, DetalleCarreraActivity.class);
        startActivity(intent);
    }


}
