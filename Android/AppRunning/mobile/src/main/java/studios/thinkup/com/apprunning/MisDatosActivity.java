package studios.thinkup.com.apprunning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.astuetz.PagerSlidingTabStrip;

import studios.thinkup.com.apprunning.adapter.ResultadoCarrerasPagerAdapter;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;


public class MisDatosActivity extends DrawerPagerActivity implements AdapterView.OnItemClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        Filtro filtro = new Filtro();
        Integer id = ((RunningApplication)this.getApplication()).getUsuario().getId();
        filtro.setIdUsuario(id);
        viewPager.setAdapter(new ResultadoCarrerasPagerAdapter(getSupportFragmentManager(),filtro));
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

        switch (item.getItemId()) {

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
