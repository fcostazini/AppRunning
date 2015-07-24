package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import studios.thinkup.com.apprunning.model.entity.UsuarioApp;


public class TerminosYCondicionesActivity extends Activity implements View.OnClickListener {

    private UsuarioApp ua;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_terminos_y_condiciones);
        if(savedInstanceState!= null && savedInstanceState.containsKey("usuario")){
            this.ua = (UsuarioApp) savedInstanceState.getSerializable("usuario");
        }
        if(this.ua == null && this.getIntent().getExtras()!= null && this.getIntent().getExtras().containsKey("usuario")){
            this.ua = (UsuarioApp)this.getIntent().getExtras().getSerializable("usuario");
        }
        Button aceptar = (Button)findViewById(R.id.btn_aceptar);
        aceptar.setOnClickListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(this.ua != null){
            outState.putSerializable("usuario",ua);
        }
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this,NuevoUsuario.class);
        Bundle b = new Bundle();

        b.putBoolean("aceptado",true);
        if(this.ua != null){
            b.putSerializable("usuario",ua);
        }
        i.putExtras(b);
        startActivity(i);
    }
}
