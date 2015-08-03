package studios.thinkup.com.apprunning.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import studios.thinkup.com.apprunning.MainActivity;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.RecomendadosActivity;
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
    private UsuarioApp ua;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_datos_usuario, container, false);
        initActivity(savedInstanceState);
        initView(rootView);
        return rootView;
    }

    private void initActivity(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("usuario")) {
                this.ua = (UsuarioApp) savedInstanceState.getSerializable("usuario");
            }

        } else {
            if (this.getActivity().getIntent().getExtras() != null) {
                if (this.getActivity().getIntent().getExtras().containsKey("usuario")) {
                    this.ua = (UsuarioApp) this.getActivity().getIntent()
                            .getExtras().getSerializable("usuario");
                }


            }
        }
        if (this.ua == null) {
            if (((RunningApplication) this.getActivity().getApplication()).getUsuario() != null) {
                this.ua = ((RunningApplication) this.getActivity().getApplication()).getUsuario();
            } else {
                this.ua = new UsuarioApp();
            }
        }
    }

    private void initView(View rootView) {
        Button logout = (Button) rootView.findViewById(R.id.btn_logout);
        RelativeLayout terminos = (RelativeLayout)rootView.findViewById(R.id.ly_terminos);
        terminos.setVisibility(View.GONE);
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
        calendario.setOnClickListener(dl);
        txtFechaNac.setOnClickListener(dl);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item) {

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);
                return v;
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

        Spinner spinner = (Spinner) rootView.findViewById(R.id.sp_grupo);
        spinner.setAdapter(adapter);
        if (selection >= 0) {
            spinner.setSelection(selection); //display hint
        } else {
            spinner.setSelection(0); //display hint
        }


        Button guardar = (Button) rootView.findViewById(R.id.btn_guardar);
        guardar.setOnClickListener(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.ua != null) {
            outState.putSerializable("usuario", this.ua);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (this.ua == null) {
            this.ua = new UsuarioApp();
        }
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
        UsuarioProviderTask usuarioProviderTask = new UsuarioProviderTask(this.getActivity());
        if(NetworkUtils.NETWORK_STATUS_NOT_CONNECTED == NetworkUtils.getConnectivityStatus(this.getActivity())){
            Toast.makeText(DatosUsuarioFragment.this.getActivity(), "No se puede guardar el usuario sin Conexión", Toast.LENGTH_LONG).show();
        }else {
            usuarioProviderTask.execute(this.ua);
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
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
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
                            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
                            fechaToUpdate.setText(sf.format(c.getTime()));
                        }
                    }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
            newFragment.show();

        }
    }
    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    if (getActivity() != null) {

                        Intent i = new Intent(getActivity(), MainActivity.class);
                        i.putExtra("LOGOUT", true);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().startActivity(i);
                        getActivity().finish();

                    }
                    dialog.dismiss();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    dialog.cancel();
                    break;
            }
        }
    };

    private class UsuarioProviderTask extends AsyncTask<UsuarioApp, Integer, UsuarioApp> {
        private Context context;

        public UsuarioProviderTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPostExecute(UsuarioApp usuarioApp) {
            super.onPostExecute(usuarioApp);
            if(usuarioApp == null) {
                Toast.makeText(DatosUsuarioFragment.this.getActivity(), "No se puede guardar el usuario", Toast.LENGTH_LONG).show();
            }else {
                ((RunningApplication) DatosUsuarioFragment.this.getActivity().getApplication()).setUsuario(usuarioApp);
                Intent intent = new Intent(DatosUsuarioFragment.this.getActivity(), RecomendadosActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                DatosUsuarioFragment.this.getActivity().startActivity(intent);
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
            IUsuarioProvider up = new UsuarioProviderRemote(DatosUsuarioFragment.this.getActivity());
            try {
                if (params[0].getId() == null) {
                    return up.grabar(params[0]);
                } else {
                    return up.update(params[0]);
                }
            }catch (Exception e){
                return null;
            }

        }
    }



}
