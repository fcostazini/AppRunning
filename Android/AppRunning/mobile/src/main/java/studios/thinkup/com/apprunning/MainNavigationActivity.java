package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import studios.thinkup.com.apprunning.adapter.DrawerItem;
import studios.thinkup.com.apprunning.adapter.DrawerListAdapter;
import studios.thinkup.com.apprunning.model.LogOutItem;


public abstract class MainNavigationActivity extends FragmentActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private List<DrawerItem> items;
    ActionBarDrawerToggle mDrawerToggle;


    public DrawerLayout getDrawerLayout() {
        return drawerLayout;
    }

    public void setDrawerLayout(DrawerLayout drawerLayout) {
        this.drawerLayout = drawerLayout;
    }

    public ListView getDrawerList() {
        return drawerList;
    }

    public void setDrawerList(ListView drawerList) {
        this.drawerList = drawerList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        defineContentView();
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerList = (ListView) findViewById(R.id.left_drawer);
        items = new ArrayList<>();
        items.add(new DrawerItem(getString(R.string.nav_menu_mis_datos), R.drawable.ic_zapatillas, MisDatosActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_mis_carreras), R.drawable.ic_mis_carreras, MisDatosActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_buscar), R.drawable.ic_search, BusquedaCarreraActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_recomendados), R.drawable.ic_action_ic_pasos, RecomendadosActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_preferencias), R.drawable.ic_preferencias, FiltrosPorDefectoActivity.class));
        items.add(new LogOutItem(getString(R.string.nav_menu_logout), R.drawable.ic_logout));

        drawerList.setAdapter(new DrawerListAdapter(this, items));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        this.mDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    protected abstract void defineContentView();


    protected void selectItem(int position) {
        drawerList.setItemChecked(position, true);
        if (items.get(position).navigate(this)) {

            drawerLayout.closeDrawer(drawerList);
        }


    }

    /* La escucha del ListView en el Drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }


    }

}
