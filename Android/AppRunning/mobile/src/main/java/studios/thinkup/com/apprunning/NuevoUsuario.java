package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.PasswordEncoder;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.GrupoRunning;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.GrupoRunningService;
import studios.thinkup.com.apprunning.provider.IUsuarioProvider;
import studios.thinkup.com.apprunning.provider.UsuarioProvider;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioProviderRemote;


public class NuevoUsuario extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener, GrupoRunningService.IServiceGruposHandler {
    public static final String NUEVO_USUARIO = "nuevoUsuario";
    private static ProgressDialog pd;
    private UsuarioApp ua;
    private boolean nuevoUsuario;
    private ArrayAdapter<String> autoCompleteAdapter;

    final TextWatcher textChecker = new TextWatcher() {
        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() >2) {

                GrupoRunningService gp = new GrupoRunningService(NuevoUsuario.this, NuevoUsuario.this);
                gp.execute(s.toString());
            }else{
                autoCompleteAdapter.clear();
            }

        }
    };

    protected static void showProgress(Context context, String message) {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    protected static void hideProgress() {
        if (pd != null) {
            pd.dismiss();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_datos_usuario);
        this.nuevoUsuario = false;
        initActivity(savedInstanceState);
        initView();
    }

    private void initActivity(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(UsuarioApp.FIELD_ID)) {
                this.ua = (UsuarioApp) savedInstanceState.getSerializable(UsuarioApp.FIELD_ID);
            }
            if (savedInstanceState.containsKey(NUEVO_USUARIO)) {
                this.nuevoUsuario = savedInstanceState.getBoolean(NUEVO_USUARIO);
            }

        } else {
            if (this.getIntent().getExtras() != null) {
                if (this.getIntent().getExtras().containsKey(UsuarioApp.FIELD_ID)) {
                    this.ua = (UsuarioApp) this.getIntent().getExtras().getSerializable(UsuarioApp.FIELD_ID);
                }
                if (this.getIntent().getExtras().containsKey(NUEVO_USUARIO)) {
                    this.nuevoUsuario = this.getIntent().getExtras().getBoolean(NUEVO_USUARIO);
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
                completarUsuario();
                Bundle b = new Bundle();
                b.putSerializable(UsuarioApp.FIELD_ID, ua);
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

        AutoCompleteTextView actv = (AutoCompleteTextView) findViewById(R.id.txt_auto_grupo);
        actv.setVisibility(View.VISIBLE);
        autoCompleteAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line);
        autoCompleteAdapter.setNotifyOnChange(true);
        actv.setAdapter(autoCompleteAdapter);
        actv.addTextChangedListener(textChecker);

        if (nuevoUsuario) {
            TextView pass = (TextView) findViewById(R.id.txt_pass);
            TextView confirmPass = (TextView) findViewById(R.id.txt_confirm_pass);
            pass.setVisibility(View.VISIBLE);
            confirmPass.setVisibility(View.VISIBLE);
        }
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
            outState.putSerializable(UsuarioApp.FIELD_ID, this.ua);
        }
        outState.putBoolean(NUEVO_USUARIO, this.nuevoUsuario);

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
            if (verificarCamposObligatorios()) {
                if (this.ua == null) {
                    this.ua = new UsuarioApp();
                }
                completarUsuario();
                UsuarioProviderTask usuarioProviderTask = new UsuarioProviderTask();
                if (NetworkUtils.isConnected(this)) {
                    showProgress(this, "Guardando Usuario...");
                    usuarioProviderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.ua);
                } else {
                    Toast.makeText(this, "Sin Conexión a internet", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(this, "Verifique los datos Ingresados", Toast.LENGTH_LONG).show();
            }
        }

    }

    private boolean verificarPassword() {
        TextView txtPass = (TextView) findViewById(R.id.txt_pass);
        TextView confPass = (TextView) findViewById(R.id.txt_confirm_pass);
        if (txtPass.getText().length() < 8) {
            Toast.makeText(this, "Longitud minima de password 8 caracteres", Toast.LENGTH_LONG).show();
            return false;

        } else if (!txtPass.getText().toString().equals(confPass.getText().toString())) {
            Toast.makeText(this, "El password no coincide", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void completarUsuario() {
        TextView txtNickname = (TextView) findViewById(R.id.txt_nick);
        this.ua.setNick(txtNickname.getText().toString());
        TextView txtNombre = (TextView) findViewById(R.id.txt_nombre);
        this.ua.setNombre(txtNombre.getText().toString());
        TextView txtApellido = (TextView) findViewById(R.id.txt_apellido);
        this.ua.setApellido(txtApellido.getText().toString());
        TextView txtEmail = (TextView) findViewById(R.id.txt_email);
        this.ua.setEmail(txtEmail.getText().toString());
        TextView txtFechaNac = (TextView) findViewById(R.id.txt_fecha_nac);

        AutoCompleteTextView grupo =(AutoCompleteTextView)findViewById(R.id.txt_auto_grupo);
        this.ua.setGrupoId(grupo.getText().toString());
        if (nuevoUsuario) {
            TextView txtPass = (TextView) findViewById(R.id.txt_pass);
            this.ua.setPassword(PasswordEncoder.encodePass(txtPass.getText().toString()));
        }
        this.ua.setFechaNacimiento(txtFechaNac.getText().toString());

    }

    private boolean verificarCamposObligatorios() {
        TextView txtNickname = (TextView) findViewById(R.id.txt_nick);
        TextView txtEmail = (TextView) findViewById(R.id.txt_email);
        TextView txtFechaNac = (TextView) findViewById(R.id.txt_fecha_nac);
        if (nuevoUsuario) {
            TextView txtPass = (TextView) findViewById(R.id.txt_pass);
            TextView confPass = (TextView) findViewById(R.id.txt_confirm_pass);

            return notEmptyText(txtEmail) & notEmptyText(txtNickname) & notEmptyText(txtFechaNac) & notEmptyText(txtPass) & notEmptyText(confPass) && verificarPassword();

        } else {
            return notEmptyText(txtEmail) &
                    notEmptyText(txtNickname) &
                    notEmptyText(txtFechaNac);
        }


    }

    private boolean notEmptyText(TextView txt) {
        if (txt.getText().length() <= 0) {
            txt.setBackgroundColor(Color.parseColor("#22FF0000"));
            return false;
        } else {
            EditText et = new EditText(this);
            txt.setBackground(et.getBackground());
            return true;
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

    private class UsuarioProviderTask extends AsyncTask<UsuarioApp, Integer, Resultado> {

        @Override
        protected void onPostExecute(Resultado r) {
            super.onPostExecute(r);
            hideProgress();
            if (r.getU() == null) {
                Toast.makeText(NuevoUsuario.this, r.getMensajeError(), Toast.LENGTH_LONG).show();
            } else {
                ((RunningApplication) NuevoUsuario.this.getApplication()).setUsuario(r.getU());
                Intent intent = new Intent(NuevoUsuario.this, StartUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Bundle b = new Bundle();
                b.putSerializable(UsuarioApp.FIELD_ID, r.getU());
                intent.putExtras(b);
                NuevoUsuario.this.startActivity(intent);
            }

        }


        @Override
        protected Resultado doInBackground(UsuarioApp... params) {

            IUsuarioProvider up = null;
            up = new UsuarioProviderRemote(NuevoUsuario.this);
            UsuarioApp u;
            try {
                if (params[0].getId() == null) {
                    u = up.grabar(params[0]);
                    if (u == null) {
                        return new Resultado(null,"No se puede guardar el usuario");
                    } else {
                        return new Resultado(getUsuarioAppLocale(u, params[0]),"No se puede guardar el usuario");
                    }
                } else {
                    return new Resultado(null,"No se puede guardar el usuario");

                }
            } catch (Exception e) {
                return new Resultado(null,e.getMessage());
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

    @Override
    public void onDataRetrived(List<GrupoRunning> grupos) {
        List<String> gruposStr = new Vector<>();
        for (GrupoRunning g : grupos) {
            gruposStr.add(g.getNombre());
        }
        this.autoCompleteAdapter.clear();
        this.autoCompleteAdapter.addAll(gruposStr);
        this.autoCompleteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError(String error) {
        this.autoCompleteAdapter.addAll(new Vector<String>());
        this.autoCompleteAdapter.notifyDataSetChanged();
    }

    private class Resultado{
        private UsuarioApp u;
        private String mensajeError;

        public Resultado(UsuarioApp u, String mensajeError) {
            this.u = u;
            this.mensajeError = mensajeError;
        }

        public UsuarioApp getU() {
            return u;
        }

        public void setU(UsuarioApp u) {
            this.u = u;
        }

        public String getMensajeError() {
            return mensajeError;
        }

        public void setMensajeError(String mensajeError) {
            this.mensajeError = mensajeError;
        }
    }
}
