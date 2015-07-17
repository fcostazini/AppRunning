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
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.UsuarioProvider;


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
        items.add(new DrawerItem(getString(R.string.nav_menu_mis_datos), R.drawable.ic_mis_datos, MisDatosActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_mis_carreras), R.drawable.ic_mis_carreras, MisDatosActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_mis_tiempos), R.drawable.ic_cronometro, TiemposCarrerasActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_buscar), R.drawable.ic_buscar_carrera, BusquedaCarreraActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_recomendados), R.drawable.ic_recomendadas, RecomendadosActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_preferencias), R.drawable.ic_preferencias, FiltrosPorDefectoActivity.class));


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
        recuperarUsuario(savedInstanceState);

    }

    private void recuperarUsuario(Bundle savedInstanceState) {
        if (savedInstanceState!= null && savedInstanceState.containsKey("usuario")) {
            UsuarioProvider up = new UsuarioProvider(this);
            ((RunningApplication) this.getApplication())
                    .setUsuario(up.findById(UsuarioApp.class, savedInstanceState.getInt("usuario")));
            savedInstanceState.remove("usuario");
        }
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
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }

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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (((RunningApplication) this.getApplication()).getUsuario() != null) {
            outState.putInt("usuario", ((RunningApplication) this.getApplication()).getUsuario().getId());
        }
    }

}
