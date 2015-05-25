package studios.thinkup.com.apprunning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import studios.thinkup.com.apprunning.adapter.BusquedaPagerAdapter;


public class BusquedaCarreraActivity extends FragmentActivity{
    private FragmentPagerAdapter adapter;
    private ViewPager viewpager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busquedas_pager_fragment);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new BusquedaPagerAdapter(getSupportFragmentManager()));

        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buscar_carrera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()){
            case R.id.mnu_filtros:
                Intent intentFiltros = new Intent(this, FiltrosPorDefectoActivity.class);
                startActivity(intentFiltros);
                return true;

            case R.id.mnu_mis_datos:
                Intent intentDatos = new Intent(this, MisDatosActivity.class);
                startActivity(intentDatos);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }



}
