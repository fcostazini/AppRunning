package studios.thinkup.com.apprunning;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import java.util.Date;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.adapter.DetalleCarreraPagerAdapter;
import studios.thinkup.com.apprunning.fragment.IUsuarioCarreraObservable;
import studios.thinkup.com.apprunning.fragment.IUsuarioCarreraObserver;
import studios.thinkup.com.apprunning.model.EstadoCarrera;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;

/**
 * Created by fcostazini on 21/05/2015.
 * Detalle de Carrera
 */
public class DetalleCarreraActivity extends DrawerPagerActivity implements  IUsuarioCarreraObservable {
    private int idCarrera;
    private UsuarioCarrera carrera;
    private Menu menu;
    AlertDialog distanciaDialog;
    private List<IUsuarioCarreraObserver> observadoresUsuario;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(((RunningApplication) this.getApplication()).getUsuario()==null){
            Intent intent = new Intent(this,MainActivity.class);
            this.startActivity(intent);
            this.finish();
        }else {
            IUsuarioCarreraProvider provider = new UsuarioCarreraProvider(this, ((RunningApplication) this.getApplication()).getUsuario().getId());
            this.observadoresUsuario = new Vector<>();
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
                } else {
                    setContentView(R.layout.sin_resultados);
                }

            } else {
                setContentView(R.layout.sin_resultados);

            }
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
    public boolean onOptionsItemSelected(final MenuItem item) {
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
                    this.actualizarUsuarioCarrera(this.carrera, EstadoCarrera.ME_GUSTA);
                } else {


                    item.setIcon(R.drawable.ic_no_me_gusta);
                    this.carrera.setMeGusta(false);
                    this.actualizarUsuarioCarrera(this.carrera, EstadoCarrera.NO_ME_GUSTA);
                }

                return true;
            case R.id.mnu_inscripto:
                if (this.carrera.isAnotado()) {
                    desanotarCarrera(item);
                } else {
                    Integer distancia = 0;
                    if(this.carrera.getDistancias().contains("/")){
                        this.seleccionarCarrera(item);
                    }else {

                        this.carrera.setDistancia(Integer.valueOf(this.carrera.getDistancias().replace("Km","").trim()));
                        item.setIcon(R.drawable.ic_anotado);
                        this.carrera.setAnotado(true);
                        this.actualizarUsuarioCarrera(this.carrera, EstadoCarrera.ANOTADO);
                    }

                }


                return true;
            case R.id.mnu_corrida:
                if (this.carrera.isCorrida()) {
                    confirmarNoCorrida(item);

                } else {
                    if (this.carrera.getFechaInicio().compareTo(new Date()) <= 0) {

                        if (this.menu != null) {
                            menu.getItem(1).setIcon(R.drawable.ic_anotado);
                            this.carrera.setAnotado(true);
                        }
                        this.actualizarUsuarioCarrera(this.carrera, EstadoCarrera.CORRIDA);
                        item.setIcon(R.drawable.ic_corrida);
                        this.carrera.setCorrida(true);
                    }
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void confirmarNoCorrida(final MenuItem item) {

        AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setTitle("Desmarcar Corrida");
        dialog.setMessage(getString(R.string.confirmar_no_corrida));
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {
                item.setIcon(R.drawable.ic_no_corrida);
                carrera.setCorrida(false);
                carrera.setTiempo(0l);
                carrera.setVelocidad(0);
                actualizarUsuarioCarrera(carrera, EstadoCarrera.NO_CORRIDA);

            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int buttonId) {

            }
        });
        dialog.setIcon(R.drawable.ic_corrida);
        dialog.show();


    }

    private void desanotarCarrera(final MenuItem item) {
        if (!this.carrera.isCorrida()) {
            AlertDialog dialog = new AlertDialog.Builder(this).create();
            dialog.setTitle("Borrar Inscripción");
            dialog.setMessage(getString(R.string.confirmar_Desanotar));
            dialog.setCancelable(false);
            dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Si", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int buttonId) {
                    item.setIcon(R.drawable.ic_no_anotado);
                    carrera.setAnotado(false);
                    carrera.setTiempo(0l);
                    actualizarUsuarioCarrera(carrera, EstadoCarrera.NO_ANOTADO);
                }
            });
            dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int buttonId) {

                }
            });
            dialog.setIcon(R.drawable.ic_anotado);
            dialog.show();

        }
    }

    @Override
    public void registrarObservadorUsuario(IUsuarioCarreraObserver ob) {
        this.observadoresUsuario.add(ob);
    }

    private void actualizarUsuarioCarrera(UsuarioCarrera usuarioCarrera, EstadoCarrera estado) {

        for (IUsuarioCarreraObserver ob : this.observadoresUsuario) {
            ob.actuliazarUsuarioCarrera(usuarioCarrera, estado);
        }
    }

    @Override
    public UsuarioCarrera getUsuarioCarrera() {
    return this.carrera;
    }
    private void seleccionarCarrera(final MenuItem menuItem){


        // Strings to Show In Dialog with Radio Buttons


        // Creating and Building the Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.que_distancia_recorres));
        String[] distancias = this.carrera.getDistancias().replace("Km","").split("/");
        int i = 0;
        for(String s : distancias){
            s = s.trim();
            if(Integer.valueOf(s.trim()) < 10){

                s = " " + s;
            }
            distancias[i] = s + " Km";
            i++;
        }
        builder.setSingleChoiceItems(distancias, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                if( item >= 0){
                    String distSeleccionada =carrera.getDistancias().split("/")[item];
                    Integer distancia = Integer.valueOf(distSeleccionada.replace("Km", "").trim());
                    carrera.setDistancia(distancia);
                    menuItem.setIcon(R.drawable.ic_anotado);
                    carrera.setAnotado(true);
                    actualizarUsuarioCarrera(carrera, EstadoCarrera.ANOTADO);
                }
                distanciaDialog.dismiss();
            }
        });

        distanciaDialog= builder.create();
        distanciaDialog.setIcon(R.drawable.ic_anotado);
        distanciaDialog.show();
    }

}

