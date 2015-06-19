package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.adapter.DetalleCarreraPagerAdapter;
import studios.thinkup.com.apprunning.fragment.IStatusCarreraObservable;
import studios.thinkup.com.apprunning.fragment.IStatusCarreraObserver;
import studios.thinkup.com.apprunning.model.EstadoCarrera;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;

/**
 * Created by fcostazini on 21/05/2015.
 * Detalle de Carrera
 */
public class DetalleCarreraActivity extends DrawerPagerActivity implements IStatusCarreraObservable{
    private int idCarrera;
    private UsuarioCarrera carrera;
    private Menu menu;
    private List<IStatusCarreraObserver> observadoresStatus;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IUsuarioCarreraProvider provider = new UsuarioCarreraProvider(this, ((RunningApplication)this.getApplication()).getUsuario().getId());
        this.observadoresStatus = new Vector<>();
        Bundle b = getIntent().getExtras();
        int codigo;
        if (b != null) {
            if (b.containsKey(UsuarioCarrera.class.getSimpleName())) {
                this.idCarrera = b.getInt(UsuarioCarrera.class.getSimpleName());
                this.carrera = provider.getByIdCarrera(this.idCarrera);
                ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
                viewPager.setAdapter(new DetalleCarreraPagerAdapter(getSupportFragmentManager(), this.idCarrera, this));
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
        this.menu = menu;
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
                    this.actualizarObservadores(EstadoCarrera.ME_GUSTA);
                } else {

                    item.setIcon(R.drawable.ic_no_me_gusta);
                    this.carrera.setMeGusta(false);
                    this.actualizarObservadores(EstadoCarrera.NO_ME_GUSTA);
                }

                return true;
            case R.id.mnu_inscripto:
               if(this.carrera.isAnotado()){
                    if(!this.carrera.isCorrida()){
                        item.setIcon(R.drawable.ic_no_anotado);
                        this.carrera.setAnotado(false);
                        this.actualizarObservadores(EstadoCarrera.NO_ANOTADO);
                    }
               }else{
                       item.setIcon(R.drawable.ic_anotado);
                       this.carrera.setAnotado(true);
                   this.actualizarObservadores(EstadoCarrera.ANOTADO);
               }


                return true;
            case R.id.mnu_corrida:
                if(this.carrera.isCorrida()){
                    item.setIcon(R.drawable.ic_no_corrida);
                    this.carrera.setCorrida(false);
                    this.actualizarObservadores(EstadoCarrera.NO_CORRIDA);
                }else{
                    if(this.carrera.getFechaInicio().compareTo(new Date())<=0){

                        if(this.menu!= null){
                            menu.getItem(1).setIcon(R.drawable.ic_anotado);
                            this.carrera.setAnotado(true);
                        }
                        this.actualizarObservadores(EstadoCarrera.CORRIDA);
                        item.setIcon(R.drawable.ic_corrida);
                        this.carrera.setCorrida(true);
                    }
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
        private void actualizarObservadores(EstadoCarrera estado){

            for(IStatusCarreraObserver ob : this.observadoresStatus){
                ob.actualizarCambioEstadoCarrera(estado);
            }

        }
    @Override
    public void registrarObservador(IStatusCarreraObserver observador) {
        this.observadoresStatus.add(observador);
    }
}

