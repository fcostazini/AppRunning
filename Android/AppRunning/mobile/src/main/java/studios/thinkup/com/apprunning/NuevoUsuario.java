package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.GrupoRunning;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.GrupoRunningProvider;
import studios.thinkup.com.apprunning.provider.IGrupoRunningProvider;
import studios.thinkup.com.apprunning.provider.IUsuarioProvider;
import studios.thinkup.com.apprunning.provider.UsuarioProvider;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioProviderRemote;


public class NuevoUsuario extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {
    private UsuarioApp ua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_datos_usuario);

        initActivity(savedInstanceState);
        initView();
    }

    private void initActivity(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("usuario")) {
                this.ua = (UsuarioApp) savedInstanceState.getSerializable("usuario");
            }
        } else {
            if (this.getIntent().getExtras() != null) {
                if (this.getIntent().getExtras().containsKey("usuario")) {
                    this.ua = (UsuarioApp) this.getIntent().getExtras().getSerializable("usuario");
                }
            }
        }
        if (this.ua == null) {
            if (((RunningApplication) this.getApplication()).getUsuario() != null) {
                this.ua = ((RunningApplication) this.getApplication()).getUsuario();
            } else {
                this.ua = new UsuarioApp();
            }
        }
    }

    private void initView() {

        CheckBox cbAcepto = (CheckBox) findViewById(R.id.acepto_terminos);
        if (this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey("aceptado")) {
            cbAcepto.setChecked(true);
        }
        RelativeLayout terminos = (RelativeLayout) findViewById(R.id.ly_terminos);
        TextView lblTerminos = (TextView) findViewById(R.id.lbl_terminos);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(NuevoUsuario.this, TerminosYCondicionesActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("usuario", ua);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
        };
        lblTerminos.setOnClickListener(listener);
        terminos.setVisibility(View.VISIBLE);
        Button logout = (Button) findViewById(R.id.btn_logout);
        Button guardar = (Button) findViewById(R.id.btn_guardar);
        guardar.setOnClickListener(this);
        logout.setVisibility(View.GONE);
        TextView txtNickname = (TextView) findViewById(R.id.txt_nick);
        txtNickname.setText(this.ua.getNick());
        TextView txtNombre = (TextView) findViewById(R.id.txt_nombre);
        txtNombre.setText(this.ua.getNombre());
        TextView txtApellido = (TextView) findViewById(R.id.txt_apellido);
        txtApellido.setText(this.ua.getApellido());
        TextView txtEmail = (TextView) findViewById(R.id.txt_email);
        txtEmail.setText(this.ua.getEmail());
        TextView txtFechaNac = (TextView) findViewById(R.id.txt_fecha_nac);

        txtFechaNac.setText(this.ua.getFechaNacimiento());
        txtFechaNac.setInputType(InputType.TYPE_NULL);
        ImageView perfil = (ImageView) findViewById(R.id.img_profile);
        if (this.ua.getFotoPerfil() != null) {
            Picasso.with(this).load(this.ua.getFotoPerfil())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(perfil);

        }
        perfil.requestFocus();
        ImageView calendario = (ImageView) findViewById(R.id.img_calendario);
        DatePickerListener dl = new DatePickerListener(txtFechaNac);
        calendario.setOnClickListener(dl);
        txtFechaNac.setOnClickListener(dl);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {


                return super.getView(position, convertView, parent);
            }

            @Override
            public int getCount() {
                return super.getCount(); // you dont display last item. It is used as hint.
            }

        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        IGrupoRunningProvider gp = new GrupoRunningProvider(this);
        List<GrupoRunning> grupos = gp.findAll(GrupoRunning.class);
        int selection = -1;
        int count = 0;
        for (GrupoRunning g : grupos) {
            adapter.add(g.getNombre());
            if (this.ua.getGrupoId() != null && !this.ua.getGrupoId().isEmpty()) {
                if (g.getNombre().equals(this.ua.getGrupoId())) {
                    selection = count;
                }
            }
            count++;
        }

        Spinner spinner = (Spinner) findViewById(R.id.sp_grupo);
        spinner.setAdapter(adapter);
        if (selection >= 0) {
            spinner.setSelection(selection); //display hint
        } else {
            spinner.setSelection(0); //display hint
        }
        spinner.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nuevo_usuario, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.ua != null) {
            outState.putSerializable("usuario", this.ua);
        }

    }


    @Override
    public void onClick(View v) {

        CheckBox cbAcepto = (CheckBox) findViewById(R.id.acepto_terminos);
        if (!cbAcepto.isChecked()) {
            final TextView txCondiciones = (TextView) findViewById(R.id.lbl_terminos);
            final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
            txCondiciones.requestFocus();
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    scrollView.scrollTo(0, txCondiciones.getBottom());
                    // load animation XML resource under res/anim
                    Animation animation = AnimationUtils.loadAnimation(NuevoUsuario.this, R.anim.scale);
                    if (animation == null) {
                        return; // here, we don't care
                    }
                    // reset initialization state
                    animation.reset();
                    txCondiciones.startAnimation(animation);
                }
            });
        } else {
            if (this.ua == null) {
                this.ua = new UsuarioApp();
            }
            TextView txtNickname = (TextView) findViewById(R.id.txt_nick);
            this.ua.setNick(txtNickname.getText().toString());
            TextView txtNombre = (TextView) findViewById(R.id.txt_nombre);
            this.ua.setNombre(txtNombre.getText().toString());
            TextView txtApellido = (TextView) findViewById(R.id.txt_apellido);
            this.ua.setApellido(txtApellido.getText().toString());
            TextView txtEmail = (TextView) findViewById(R.id.txt_email);
            this.ua.setEmail(txtEmail.getText().toString());
            TextView txtFechaNac = (TextView) findViewById(R.id.txt_fecha_nac);

            this.ua.setFechaNacimiento(txtFechaNac.getText().toString());
            Spinner grupo = (Spinner) findViewById(R.id.sp_grupo);
            if (!grupo.getSelectedItem().equals(getString(R.string.corres_grupo))) {
                this.ua.setGrupoId((String) grupo.getSelectedItem());
            }
            UsuarioProviderTask usuarioProviderTask = new UsuarioProviderTask();
            usuarioProviderTask.execute(this.ua);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        this.ua.setGrupoId(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        this.ua.setGrupoId("Ninguno");
    }

    private class DatePickerListener implements View.OnClickListener {
        private TextView fechaToUpdate;

        DatePickerListener(TextView fecha) {
            this.fechaToUpdate = fecha;
        }

        @Override
        public void onClick(View v) {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            if (fechaToUpdate.getText() != null && !fechaToUpdate.getText().toString().isEmpty()) {
                try {
                    c.setTime(sf.parse(ua.getFechaNacimiento()));
                } catch (ParseException e) {
                    c.set(c.get(Calendar.YEAR) - 20, 1, 1);
                }
            } else {
                c.set(c.get(Calendar.YEAR) - 20, 1, 1);
            }

            DatePickerDialog newFragment = new DatePickerDialog(NuevoUsuario.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar c = Calendar.getInstance();
                    c.set(year, monthOfYear, dayOfMonth);
                    SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    fechaToUpdate.setText(sf.format(c.getTime()));
                }
            }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
            newFragment.show();

        }
    }

    private class UsuarioProviderTask extends AsyncTask<UsuarioApp, Integer, UsuarioApp> {

        @Override
        protected void onPostExecute(UsuarioApp usuarioApp) {
            super.onPostExecute(usuarioApp);
            if (usuarioApp == null) {
                Toast.makeText(NuevoUsuario.this, "No se puede crear el usuario", Toast.LENGTH_LONG).show();
            } else {
                ((RunningApplication) NuevoUsuario.this.getApplication()).setUsuario(usuarioApp);
                Intent intent = new Intent(NuevoUsuario.this, RecomendadosActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                NuevoUsuario.this.startActivity(intent);
            }

        }

        @Override
        protected void onCancelled(UsuarioApp usuarioApp) {
            super.onCancelled(usuarioApp);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected UsuarioApp doInBackground(UsuarioApp... params) {

            IUsuarioProvider up = null;
            up = new UsuarioProviderRemote(NuevoUsuario.this);
            UsuarioApp u;
            try {
                if (params[0].getId() == null) {
                    u = up.grabar(params[0]);
                    return getUsuarioAppLocale(u, params[0]);
                } else {
                    return null;

                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }


        }

        private UsuarioApp getUsuarioAppLocale(UsuarioApp u, UsuarioApp param) throws studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException {
            IUsuarioProvider up;
            up = new UsuarioProvider(NuevoUsuario.this);
            if (up.getUsuarioByEmail(param.getEmail()) != null) {
                return null;
            } else {
                return up.grabar(u);
            }
        }
    }


}
