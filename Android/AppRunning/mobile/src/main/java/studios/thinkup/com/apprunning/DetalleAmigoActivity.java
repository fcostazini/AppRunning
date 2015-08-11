package studios.thinkup.com.apprunning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import studios.thinkup.com.apprunning.adapter.AmigosPagerAdapter;
import studios.thinkup.com.apprunning.model.entity.Amigo;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.ActualizarAmigoService;

/**
 * Created by Facundo on 11/08/2015.
 * Detalle de un amigo seleccionado
 */
public class DetalleAmigoActivity extends DrawerPagerActivity implements ActualizarAmigoService.IServicioActualizacionAmigoHandler {
    private Amigo amigo;
    private Menu menu;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.amigo = (Amigo)restoreField(savedInstanceState,Amigo.FIELD_ID);
        if(this.amigo== null){
            this.amigo = new Amigo();
        }
    }
    private Object restoreField(Bundle savedInstanceState, String name) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(name)) {
                return savedInstanceState.getSerializable(name);
            }
        } else {
            if (this.getIntent().getExtras() != null) {
                if (this.getIntent().getExtras().containsKey(name)) {
                    return this.getIntent().getExtras().getSerializable(name);
                }
            }
        }
        return null;
    }
    @Override
    protected PagerAdapter getAdapter() {
        return new AmigosPagerAdapter(this.getSupportFragmentManager());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle_amigo, menu);
        this.menu = menu;
        if(amigo != null){
            actualizarEsAmigo(menu);
            /*
            if(amigo.isEsBloqueado()){

                return true;
            }
            if(amigo.isSolicitudEnviada()){

                return true;
            }*/
        }
        return true;
    }

    private void actualizarEsAmigo(Menu menu) {
        if(amigo.isEsAmigo()){
            menu.getItem(0).setIcon(R.drawable.ic_eliminar_amigo);
        }else{
            menu.getItem(0).setIcon(R.drawable.ic_agregar_amigo);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        UsuarioApp ua = this.getUsuario();
        ActualizarAmigoService amigoService = new ActualizarAmigoService(this, this, ua);
        this.amigo.setEsAmigo(!amigo.isEsAmigo());
        amigoService.execute(this.amigo);
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public void onOk() {
        actualizarEsAmigo(menu);
        Toast.makeText(this,"Estado guardado",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Integer estado) {
        Toast.makeText(this,"No guardado" + estado,Toast.LENGTH_SHORT).show();
    }
}
