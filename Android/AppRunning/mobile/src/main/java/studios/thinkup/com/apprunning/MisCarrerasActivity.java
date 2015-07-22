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


public class MisCarrerasActivity extends DrawerPagerActivity implements AdapterView.OnItemClickListener {
    private ViewPager viewPager;
    @Override
    protected void initFiltro(Bundle savedInstance) {
        if (savedInstance != null) {
            if (savedInstance.containsKey(Filtro.FILTRO_ID)) {
                this.filtro = (Filtro) savedInstance.getSerializable(Filtro.FILTRO_ID);
            }
        }
        if (this.filtro == null && getIntent().getExtras() != null && getIntent().getExtras().containsKey(Filtro.FILTRO_ID)) {
            this.filtro = (Filtro) getIntent().getExtras().getSerializable(Filtro.FILTRO_ID);
        } else {
            this.filtro = new Filtro(getDefaultSettings());
            this.filtro.clean();
            this.filtro.setIdUsuario(getIdUsuario());
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            initView(savedInstanceState);
    }

    private void initView(Bundle savedInstance) {
        this.viewPager = (ViewPager) findViewById(R.id.viewpager);
        initFiltro(savedInstance);
        long id = ((RunningApplication) this.getApplication()).getUsuario().getId();
        filtro.setIdUsuario(id);
        viewPager.setAdapter(new ResultadoCarrerasPagerAdapter(getSupportFragmentManager(), filtro));
        viewPager.setBackgroundResource(R.drawable.path);
        viewPager.getBackground().setAlpha(130);
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

    @Override
    protected void onResume() {
        super.onResume();
        if(this.viewPager == null){
            initView(null);
        }
        this.viewPager.getAdapter().notifyDataSetChanged();

    }

}
