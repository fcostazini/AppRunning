package studios.thinkup.com.apprunning;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

import studios.thinkup.com.apprunning.adapter.BusquedaCarreraActivity;
import studios.thinkup.com.apprunning.adapter.DrawerItem;
import studios.thinkup.com.apprunning.adapter.DrawerListAdapter;
import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.TiemposDrawerItem;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.ConfigProvider;
import studios.thinkup.com.apprunning.provider.UsuarioProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;


public abstract class MainNavigationActivity extends FragmentActivity {

    ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private List<DrawerItem> items;
    private int tutorialPage;

    protected Integer getIdUsuario() {
        UsuarioApp ua = ((RunningApplication) this.getApplication()).getUsuario();
        if (ua != null) {
            return ua.getId();
        } else {
            Intent i = new Intent(this,SeleccionarUsuarioActivity.class);
            startActivity(i);
            finish();
            return 0;
        }

    }

    protected UsuarioApp getUsuario() {
        UsuarioApp ua = ((RunningApplication) this.getApplication()).getUsuario();
        if (ua != null) {
            return ua;
        } else {
            Intent i = new Intent(this,SeleccionarUsuarioActivity.class);
            startActivity(i);
            finish();
            return null;
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        defineContentView();
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerList = (ListView) findViewById(R.id.left_drawer);
        items = new ArrayList<>();
        items.add(new DrawerItem(getString(R.string.nav_menu_mis_datos), R.drawable.ic_mis_datos, DatosUsuarioActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_mis_carreras), R.drawable.ic_mis_carreras, MisCarrerasActivity.class));
        items.add(new TiemposDrawerItem(getString(R.string.nav_menu_mis_tiempos), R.drawable.ic_cronometro));
        items.add(new DrawerItem(getString(R.string.nav_menu_mis_amigos), R.drawable.ic_amigos, AmigosActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_buscar), R.drawable.ic_buscar_carrera, BusquedaCarreraActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_recomendados), R.drawable.ic_recomendadas, RecomendadosActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_preferencias), R.drawable.ic_preferencias, PreferenciasActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_ayuda), R.drawable.ic_ayuda, TutorialActivity.class));
        items.add(new DrawerItem(getString(R.string.nav_menu_acerca_de), R.drawable.ic_acerca_de, AcecaDeNosotrosActivity.class));


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
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().setHomeButtonEnabled(true);
        }
        recuperarUsuario(savedInstanceState);

    }

    private void recuperarUsuario(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("usuario")) {

            ((RunningApplication) this.getApplication())
                    .setUsuario((UsuarioApp) savedInstanceState.getSerializable("usuario"));

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return mDrawerToggle.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (mDrawerToggle != null) {
            mDrawerToggle.syncState();
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    protected abstract void defineContentView();


    protected void selectItem(int position) {
        drawerList.setItemChecked(position, true);
        drawerLayout.closeDrawer(drawerList);
        items.get(position).navigate(this);

    }

    public DefaultSettings getDefaultSettings() {
        ConfigProvider cp = new ConfigProvider(this);
        UsuarioApp ua = ((RunningApplication) this.getApplication()).getUsuario();

        DefaultSettings defaultSettings = cp.getByUsuario(ua.getId());
        if (defaultSettings == null) {
            defaultSettings = new DefaultSettings(ua.getId());
            try {
                cp.grabar(defaultSettings);
            } catch (EntidadNoGuardadaException e) {
                e.printStackTrace();
            }

        }
        return defaultSettings;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (((RunningApplication) this.getApplication()).getUsuario() != null) {
            outState.putSerializable("usuario", ((RunningApplication) this.getApplication()).getUsuario());
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.LEFT))
            drawerLayout.closeDrawer(Gravity.LEFT);
        else
            super.onBackPressed();
    }

    public int getTutorialPage() {
        return 0;
    }

    /* La escucha del ListView en el Drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }


    }


}
