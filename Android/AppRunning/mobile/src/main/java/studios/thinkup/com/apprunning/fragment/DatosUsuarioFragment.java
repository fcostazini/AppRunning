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
import android.support.v7.internal.app.ToolbarActionBar;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import studios.thinkup.com.apprunning.DatosUsuarioActivity;
import studios.thinkup.com.apprunning.MainActivity;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.GrupoRunning;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.GrupoRunningProvider;
import studios.thinkup.com.apprunning.provider.IGrupoRunningProvider;
import studios.thinkup.com.apprunning.provider.IUsuarioProvider;
import studios.thinkup.com.apprunning.provider.UsuarioProvider;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioProviderRemote;

/**
 * Fragment de datos de usuario
 */
public class DatosUsuarioFragment extends Fragment implements View.OnClickListener {
    private static ProgressDialog pd;
    private Boolean displayOnly;
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
                        if(up.deleteUsuario(ua)) {
                            getActivity().startActivity(i);
                            getActivity().finish();
                        }else{
                            Toast.makeText(getActivity(),"No se pudo desvincular la cuenta",Toast.LENGTH_SHORT).show();
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
    private UsuarioApp ua;

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


        Spinner spinner = (Spinner) rootView.findViewById(R.id.sp_grupo);
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
            txtGrupo.setText(ua.getGrupoId());
            txtGrupo.setVisibility(View.VISIBLE);
            spinner.setVisibility(View.GONE);
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item) {
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

            IGrupoRunningProvider gp = new GrupoRunningProvider(this.getActivity());
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

            spinner.setAdapter(adapter);
            if (selection >= 0) {
                spinner.setSelection(selection); //display hint
            } else {
                spinner.setSelection(0); //display hint
            }

            guardar.setOnClickListener(this);
            calendario.setOnClickListener(dl);
            txtFechaNac.setOnClickListener(dl);

            if (!ua.getTipoCuenta().equals(String.valueOf(GooglePlusSocialNetwork.ID))) {
                logout.setBackgroundColor(this.getActivity().getResources().getColor(R.color.com_facebook_blue));
                logout.setTextColor(this.getActivity().getResources().getColor(R.color.common_signin_btn_text_dark));
            } else {
                logout.setBackgroundColor(this.getActivity().getResources().getColor(R.color.rojo_google));
                logout.setTextColor(this.getActivity().getResources().getColor(R.color.common_signin_btn_text_dark));
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
            Spinner grupo = (Spinner) getView().findViewById(R.id.sp_grupo);
            if (!grupo.getSelectedItem().equals(getString(R.string.corres_grupo))) {
                this.ua.setGrupoId((String) grupo.getSelectedItem());
            }
            if (NetworkUtils.isConnected(this.getActivity())) {
                UsuarioProviderTask usuarioProviderTask = new UsuarioProviderTask();
                showProgress(this.getActivity(), this.getActivity().getString(R.string.guardando_usuario));
                usuarioProviderTask.execute(this.ua);
            } else {
                Toast.makeText(this.getActivity(), this.getString(R.string.sin_conexion), Toast.LENGTH_LONG).show();
            }
        }
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

    private class UsuarioProviderTask extends AsyncTask<UsuarioApp, Integer, UsuarioApp> {

        public UsuarioProviderTask() {
        }

        @Override
        protected void onPostExecute(UsuarioApp usuarioApp) {
            super.onPostExecute(usuarioApp);
            hideProgress();
            if (usuarioApp == null) {
                Toast.makeText(DatosUsuarioFragment.this.getActivity(), "No se puede guardar el usuario", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(DatosUsuarioFragment.this.getActivity(), "Usuario Guardado", Toast.LENGTH_LONG).show();
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
            if (NetworkUtils.getConnectivityStatus(DatosUsuarioFragment.this.getActivity()) == NetworkUtils.NETWORK_STATUS_NOT_CONNECTED) {
                return null;
            }
            IUsuarioProvider up = new UsuarioProviderRemote(DatosUsuarioFragment.this.getActivity());
            try {
                up.update(params[0]);
                up = new UsuarioProvider(DatosUsuarioFragment.this.getActivity());
                return up.update(params[0]);

            } catch (Exception e) {

                return null;
            }
        }

    }


}
