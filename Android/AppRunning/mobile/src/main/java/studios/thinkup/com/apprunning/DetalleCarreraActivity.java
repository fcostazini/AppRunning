package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import studios.thinkup.com.apprunning.adapter.DetalleCarreraPagerAdapter;
import studios.thinkup.com.apprunning.model.Carrera;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.provider.CarrerasProvider;

/**
 * Created by fcostazini on 21/05/2015.
 * Detalle de Carrera
 */
public class DetalleCarreraActivity extends DrawerPagerActivity {
    private Carrera carrera;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CarrerasProvider provider = new CarrerasProvider(this);
        Bundle b = getIntent().getExtras();
        int codigo;
        if(b!=null){
            if(b.getSerializable(Carrera.class.getSimpleName())!=null){
                this.carrera = (Carrera)b.getSerializable(Carrera.class.getSimpleName());
            }else{
                codigo = b.getInt(Carrera.ID);
                this.carrera = provider.getCarreraByCodigo(codigo);
            }

        }else{

        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new DetalleCarreraPagerAdapter(getSupportFragmentManager(), this.carrera));
        // Give the PagerSlidingTabStrip the ViewPager
        PagerSlidingTabStrip tabsStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the view pager to the tab strip
        tabsStrip.setViewPager(viewPager);
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
            if (this.carrera.isEstoyInscripto()) {
                menu.getItem(1).setIcon(R.drawable.ic_anotado);
            } else {
                menu.getItem(1).setIcon(R.drawable.ic_no_anotado);
            }
            if (this.carrera.isFueCorrida()) {
                menu.getItem(2).setIcon(R.drawable.ic_corrida);
            } else {
                menu.getItem(2).setIcon(R.drawable.ic_no_corrida);
            }

            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (this.carrera == null) {
            return super.onOptionsItemSelected(item);
        }
        CarrerasProvider cp = new CarrerasProvider(this);
        Integer usuarioId = ((RunningApplication) this
                .getApplication()).getUsuario().getId();
        switch (item.getItemId()) {
            case R.id.mnu_me_gusta:
                if (!this.carrera.isMeGusta()) {
                    cp.meGusta(this.carrera.getCodigoCarrera(), usuarioId);
                    item.setIcon(R.drawable.ic_me_gusta);
                    this.carrera.setMeGusta(true);
                } else {
                    cp.noMegusta(this.carrera.getCodigoCarrera(), usuarioId);
                    item.setIcon(R.drawable.ic_no_me_gusta);
                    this.carrera.setMeGusta(false);
                }
                return true;
            case R.id.mnu_inscripto:
                if (!this.carrera.isFueCorrida()) {
                    if (!this.carrera.isEstoyInscripto()) {
                        cp.anotarEnCarrera(this.carrera.getCodigoCarrera(), usuarioId);
                        item.setIcon(R.drawable.ic_anotado);
                        this.carrera.setInscripto(true);
                    } else {
                        cp.desanotarEnCarrera(this.carrera.getCodigoCarrera(), usuarioId);
                        item.setIcon(R.drawable.ic_no_anotado);
                        this.carrera.setInscripto(false);
                    }
                }
                return true;
            case R.id.mnu_corrida:
                if (!this.carrera.isFueCorrida()) {
                    cp.carreraCorrida(this.carrera.getCodigoCarrera(), usuarioId);
                    item.setIcon(R.drawable.ic_corrida);
                    this.carrera.setCorrida(true);
                } else {
                    cp.carreraNoCorrida(this.carrera.getCodigoCarrera(), usuarioId);
                    item.setIcon(R.drawable.ic_no_corrida);
                    this.carrera.setCorrida(false);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

