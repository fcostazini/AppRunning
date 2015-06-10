package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import studios.thinkup.com.apprunning.adapter.DetalleCarreraPagerAdapter;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.CarrerasProvider;
import studios.thinkup.com.apprunning.provider.ICarrerasProvider;

/**
 * Created by fcostazini on 21/05/2015.
 * Detalle de Carrera
 */
public class DetalleCarreraActivity extends DrawerPagerActivity {
    private long idCarrera;
    private UsuarioCarrera carrera;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ICarrerasProvider provider = new CarrerasProvider();
        Bundle b = getIntent().getExtras();
        int codigo;
        if (b != null) {
            if (b.containsKey(UsuarioCarrera.class.getSimpleName())) {
                this.idCarrera = b.getLong(UsuarioCarrera.class.getSimpleName());
                this.carrera = provider.getByIdCarrera(this.idCarrera);
                ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
                viewPager.setAdapter(new DetalleCarreraPagerAdapter(getSupportFragmentManager(), this.idCarrera));
                // Give the PagerSlidingTabStrip the ViewPager
                PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
                // Attach the view pager to the tab strip
                tabsStrip.setViewPager(viewPager);
            }else{
                setContentView(R.layout.sin_resultados);
            }

        } else {
            setContentView(R.layout.sin_resultados);

        }

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_detalle_carrera, menu);
        if (this.carrera != null) {
            if (this.carrera.isMeGusta()) {
                menu.getItem(0).setIcon(R.drawable.ic_me_gusta);
            } else {
                menu.getItem(0).setIcon(R.drawable.ic_no_me_gusta);
            }
            if (this.carrera.isAnotado()) {
                menu.getItem(1).setIcon(R.drawable.ic_anotado);
            } else {
                menu.getItem(1).setIcon(R.drawable.ic_no_anotado);
            }
            if (this.carrera.isCorrida()) {
                menu.getItem(2).setIcon(R.drawable.ic_corrida);
            } else {
                menu.getItem(2).setIcon(R.drawable.ic_no_corrida);
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.carrera == null) {
            return super.onOptionsItemSelected(item);
        }

        long usuarioId = ((RunningApplication) this
                .getApplication()).getUsuario().getId();
        switch (item.getItemId()) {
            case R.id.mnu_me_gusta:
                if (!this.carrera.isMeGusta()) {

                    item.setIcon(R.drawable.ic_me_gusta);
                    this.carrera.setMeGusta(true);
                } else {

                    item.setIcon(R.drawable.ic_no_me_gusta);
                    this.carrera.setMeGusta(false);
                }
                return true;
            case R.id.mnu_inscripto:
                if (!this.carrera.isCorrida()) {
                    if (!this.carrera.isAnotado()) {

                        item.setIcon(R.drawable.ic_anotado);
                        this.carrera.setAnotado(true);
                    } else {

                        item.setIcon(R.drawable.ic_no_anotado);
                        this.carrera.setAnotado(false);
                    }
                }
                return true;
            case R.id.mnu_corrida:
                if (!this.carrera.isCorrida()) {

                    item.setIcon(R.drawable.ic_corrida);
                    this.carrera.setCorrida(true);
                } else {

                    item.setIcon(R.drawable.ic_no_corrida);
                    this.carrera.setCorrida(false);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

