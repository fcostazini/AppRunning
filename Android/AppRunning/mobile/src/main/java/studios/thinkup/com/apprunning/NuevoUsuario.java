package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.IUsuarioProvider;
import studios.thinkup.com.apprunning.provider.UsuarioProvider;


public class NuevoUsuario extends Activity implements View.OnClickListener {
private UsuarioApp ua;
    private boolean esNuevoUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);

        initActivity(savedInstanceState);
        initView();
    }

    private void initActivity(Bundle savedInstanceState) {
        if(savedInstanceState!= null){
            if(savedInstanceState.containsKey("usuario")) {
                this.ua = (UsuarioApp) savedInstanceState.getSerializable("usuario");
            }
            if(savedInstanceState.containsKey("nuevoUsuario")){
                this.esNuevoUsuario = true;
            }else{
                this.esNuevoUsuario = false;
            }
        }else {
            if (this.getIntent().getExtras() != null) {
                if (this.getIntent().getExtras().containsKey("usuario")) {
                    this.ua = (UsuarioApp) this.getIntent().getExtras().getSerializable("usuario");
                }

                if (this.getIntent().getExtras().containsKey("nuevoUsuario")) {
                    this.esNuevoUsuario = true;
                } else {
                    this.esNuevoUsuario = false;
                }

            }
        }
        if(this.ua == null){
            this.ua = new UsuarioApp();
        }
    }

    private void initView() {
        TextView txtNickname = (TextView)findViewById(R.id.txt_nick);
        txtNickname.setText(this.ua.getNick());
        TextView txtNombre = (TextView)findViewById(R.id.txt_nombre);
        txtNombre.setText(this.ua.getNombre());
        TextView txtApellido = (TextView)findViewById(R.id.txt_apellido);
        txtApellido.setText(this.ua.getApellido());
        TextView txtEmail = (TextView)findViewById(R.id.txt_email);
        txtEmail.setText(this.ua.getEmail());
        TextView txtFechaNac = (TextView)findViewById(R.id.txt_fecha_nac);
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        txtFechaNac.setText(sf.format(this.ua.getFechaNacimiento()));
        ImageView perfil = (ImageView)findViewById(R.id.img_profile);
        if (this.ua.getFotoPerfilUrl() != null) {
            Picasso.with(this).load(this.ua.getFotoPerfilUrl())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(perfil);

        }
        Button guardar = (Button)findViewById(R.id.btn_guardar);
        guardar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nuevo_usuario, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(this.ua != null){
            outState.putSerializable("usuario",this.ua);
        }
        if(this.esNuevoUsuario){
            outState.putBoolean("nuevoUsuario",true);
        }else {
            if (outState.containsKey("nuevoUsuario")) {
                outState.remove("nuevoUsuario");

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
    if(this.ua == null){
        this.ua = new UsuarioApp();
    }
        TextView txtNickname = (TextView)findViewById(R.id.txt_nick);
        this.ua.setNick(txtNickname.getText().toString());
        TextView txtNombre = (TextView)findViewById(R.id.txt_nombre);
        this.ua.setNombre(txtNombre.getText().toString());
        TextView txtApellido = (TextView)findViewById(R.id.txt_apellido);
        this.ua.setApellido(txtApellido.getText().toString());
        TextView txtEmail = (TextView)findViewById(R.id.txt_email);
        this.ua.setEmail(txtEmail.getText().toString());
        TextView txtFechaNac = (TextView)findViewById(R.id.txt_fecha_nac);
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            this.ua.setFechaNacimiento(sf.parse(txtFechaNac.getText().toString()));
        } catch (ParseException e) {
            this.ua.setFechaNacimiento(new Date());
        }
        IUsuarioProvider up = new UsuarioProvider(this);
        try {
            if (this.ua.getId() == null) {
                up.grabar(this.ua);
            } else {
                up.update(this.ua);
            }

        }catch (Exception e){
            e.printStackTrace();
            Toast.makeText(this, "No se puede guardar el usuario", Toast.LENGTH_LONG);
        }

    }


}
