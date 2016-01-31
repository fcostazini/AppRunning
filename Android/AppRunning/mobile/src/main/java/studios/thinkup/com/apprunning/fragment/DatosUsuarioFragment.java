package studios.thinkup.com.apprunning.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import studios.thinkup.com.apprunning.DatosUsuarioActivity;
import studios.thinkup.com.apprunning.MainActivity;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.GrupoRunning;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.GrupoRunningService;
import studios.thinkup.com.apprunning.provider.IUsuarioProvider;
import studios.thinkup.com.apprunning.provider.UsuarioProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioProviderRemote;

/**
 * Fragment de datos de usuario
 */
public class DatosUsuarioFragment extends Fragment implements View.OnClickListener, GrupoRunningService.IServiceGruposHandler {
    private static ProgressDialog pd;
    private Boolean displayOnly;
    private ArrayAdapter<String> autoCompleteAdapter;

    final TextWatcher textChecker = new TextWatcher() {
        public void afterTextChanged(Editable s) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 2) {

                GrupoRunningService gp = new GrupoRunningService(DatosUsuarioFragment.this.getActivity(), DatosUsuarioFragment.this);
                gp.execute(s.toString());
            } else {
                autoCompleteAdapter.clear();
            }

        }
    };
    private UsuarioApp ua;
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    if (getActivity() != null) {
                        Intent i = new Intent(getActivity(), MainActivity.class);
                        i.putExtra("LOGOUT", true);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        UsuarioProvider up = new UsuarioProvider(getActivity());
                        if (up.deleteUsuario(ua)) {
                            getActivity().startActivity(i);
                            getActivity().finish();
                        } else {
                            Toast.makeText(getActivity(), "No se pudo desvincular la cuenta", Toast.LENGTH_SHORT).show();
                        }
                        //TODO: BORRAR EL USUARIO LOCAL
                    }
                    dialog.dismiss();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.cancel();
                    break;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_datos_usuario, container, false);
        initActivity(savedInstanceState);
        initView(rootView);
        return rootView;
    }

    private void initActivity(Bundle savedInstanceState) {
        this.ua = (UsuarioApp) restoreField(savedInstanceState, UsuarioApp.FIELD_ID);
        this.displayOnly = (Boolean) restoreField(savedInstanceState, DatosUsuarioActivity.DISPLAY_ONLY);
        if (this.ua == null) {
            if (((RunningApplication) this.getActivity().getApplication()).getUsuario() != null) {
                this.ua = ((RunningApplication) this.getActivity().getApplication()).getUsuario();
            } else {
                this.ua = new UsuarioApp();
            }
        }
        if (this.displayOnly == null) {
            this.displayOnly = false;
        }

    }

    private Object restoreField(Bundle savedInstanceState, String name) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(name)) {
                return savedInstanceState.getSerializable(name);
            }
        } else {
            if (this.getActivity().getIntent().getExtras() != null) {
                if (this.getActivity().getIntent().getExtras().containsKey(name)) {
                    return this.getActivity().getIntent().getExtras().getSerializable(name);
                }
            }
        }
        return null;
    }

    private void initView(View rootView) {
        RelativeLayout terminos = (RelativeLayout) rootView.findViewById(R.id.ly_terminos);
        terminos.setVisibility(View.GONE);

        TextView txtNickname = (TextView) rootView.findViewById(R.id.txt_nick);
        txtNickname.setText(this.ua.getNick());
        TextView txtNombre = (TextView) rootView.findViewById(R.id.txt_nombre);
        txtNombre.setText(this.ua.getNombre());
        TextView txtApellido = (TextView) rootView.findViewById(R.id.txt_apellido);
        txtApellido.setText(this.ua.getApellido());
        TextView txtEmail = (TextView) rootView.findViewById(R.id.txt_email);
        txtEmail.setText(this.ua.getEmail());
        TextView txtFechaNac = (TextView) rootView.findViewById(R.id.txt_fecha_nac);
        AutoCompleteTextView actv = (AutoCompleteTextView) rootView.findViewById(R.id.txt_auto_grupo);
        actv.setText(this.ua.getGrupoId());
        txtFechaNac.setText(this.ua.getFechaNacimiento());
        txtFechaNac.setInputType(InputType.TYPE_NULL);
        ImageView perfil = (ImageView) rootView.findViewById(R.id.img_profile);
        if (this.ua.getFotoPerfil() != null) {
            Picasso.with(this.getActivity()).load(this.ua.getFotoPerfil())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(perfil);

        }
        perfil.requestFocus();
        ImageView calendario = (ImageView) rootView.findViewById(R.id.img_calendario);
        DatePickerListener dl = new DatePickerListener(txtFechaNac);


        Button guardar = (Button) rootView.findViewById(R.id.btn_guardar);
        Button logout = (Button) rootView.findViewById(R.id.btn_logout);

        if (this.displayOnly) {
            guardar.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
            txtApellido.setInputType(InputType.TYPE_NULL);
            txtApellido.setFocusable(false);
            txtEmail.setInputType(InputType.TYPE_NULL);
            txtEmail.setFocusable(false);
            txtFechaNac.setFocusable(false);
            txtFechaNac.setInputType(InputType.TYPE_NULL);
            txtNickname.setInputType(InputType.TYPE_NULL);
            txtNickname.setFocusable(false);
            txtNombre.setInputType(InputType.TYPE_NULL);
            txtNombre.setFocusable(false);
            TextView txtGrupo = (TextView) rootView.findViewById(R.id.txt_grupo);
            if(ua.getGrupoId().equals("")){
                txtGrupo.setText("Ninguno");
            }else{
                txtGrupo.setText(ua.getGrupoId());
            }
            txtGrupo.setVisibility(View.VISIBLE);
            actv.setInputType(InputType.TYPE_NULL);
            actv.setFocusable(false);


        } else {


            autoCompleteAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_dropdown_item_1line);
            autoCompleteAdapter.setNotifyOnChange(true);
            actv.setAdapter(autoCompleteAdapter);
            actv.addTextChangedListener(textChecker);


            guardar.setOnClickListener(this);
            calendario.setOnClickListener(dl);
            txtFechaNac.setOnClickListener(dl);

            if (!ua.getTipoCuenta().equals(String.valueOf(GooglePlusSocialNetwork.ID))) {
                logout.setBackgroundColor(this.getActivity().getResources().getColor(R.color.com_facebook_blue));
                logout.setTextColor(this.getActivity().getResources().getColor(R.color.common_google_signin_btn_text_dark));
            } else {
                logout.setBackgroundColor(this.getActivity().getResources().getColor(R.color.rojo_google));
                logout.setTextColor(this.getActivity().getResources().getColor(R.color.common_google_signin_btn_text_dark));
            }
            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(getString(R.string.confirmacion_desvinculacion_cuenta))
                            .setTitle(getString(R.string.desvincular_cuenta))
                            .setIcon(R.drawable.ic_warning)
                            .setPositiveButton(getString(R.string.ok), dialogClickListener)
                            .setNegativeButton(getString(R.string.cancelar), dialogClickListener).show();

                }
            });

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.ua != null) {
            outState.putSerializable(UsuarioApp.FIELD_ID, this.ua);
        }
        if (this.displayOnly != null) {
            outState.putBoolean(DatosUsuarioActivity.DISPLAY_ONLY, this.displayOnly);
        }

    }

    @Override
    public void onClick(View v) {
        if (this.ua == null) {
            this.ua = new UsuarioApp();
        }
        if (getView() != null) {
            TextView txtNickname = (TextView) getView().findViewById(R.id.txt_nick);
            this.ua.setNick(txtNickname.getText().toString());
            TextView txtNombre = (TextView) getView().findViewById(R.id.txt_nombre);
            this.ua.setNombre(txtNombre.getText().toString());
            TextView txtApellido = (TextView) getView().findViewById(R.id.txt_apellido);
            this.ua.setApellido(txtApellido.getText().toString());
            TextView txtEmail = (TextView) getView().findViewById(R.id.txt_email);
            this.ua.setEmail(txtEmail.getText().toString());
            TextView txtFechaNac = (TextView) getView().findViewById(R.id.txt_fecha_nac);

            this.ua.setFechaNacimiento(txtFechaNac.getText().toString());
            AutoCompleteTextView grupo = (AutoCompleteTextView) getView().findViewById(R.id.txt_auto_grupo);
            if(grupo.getText().toString().equals("")){
                this.ua.setGrupoId("Ninguno");
                grupo.setText("Ninguno");
            }else{
                this.ua.setGrupoId(grupo.getText().toString());
            }
            if (NetworkUtils.isConnected(this.getActivity())) {
                UsuarioProviderTask usuarioProviderTask = new UsuarioProviderTask();
                showProgress(this.getActivity(), this.getActivity().getString(R.string.guardando_usuario));
                usuarioProviderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.ua);
            } else {
                Toast.makeText(this.getActivity(), this.getString(R.string.sin_conexion), Toast.LENGTH_LONG).show();
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

            DatePickerDialog newFragment = new DatePickerDialog(DatosUsuarioFragment.this.getActivity(),
                    new DatePickerDialog.OnDateSetListener() {
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

        public UsuarioProviderTask() {
        }

        @Override
        protected void onPostExecute(Resultado r) {
            super.onPostExecute(r);
            if (isAdded()) {
                hideProgress();
                if (r.getU() == null) {
                    Toast.makeText(DatosUsuarioFragment.this.getActivity(), r.getMensajeError(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(DatosUsuarioFragment.this.getActivity(), "Usuario Guardado", Toast.LENGTH_LONG).show();
                }
            }

        }


        @Override
        protected Resultado doInBackground(UsuarioApp... params) {
            if (NetworkUtils.getConnectivityStatus(DatosUsuarioFragment.this.getActivity()) == NetworkUtils.NETWORK_STATUS_NOT_CONNECTED) {
                return null;
            }
            IUsuarioProvider up = new UsuarioProviderRemote(DatosUsuarioFragment.this.getActivity());
            try {
                up.update(params[0]);
                up = new UsuarioProvider(DatosUsuarioFragment.this.getActivity());
                return new Resultado(up.update(params[0]),"");

            } catch (EntidadNoGuardadaException e) {

                return  new Resultado(null,e.getMessage());
            }
        }

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
