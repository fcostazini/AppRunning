package studios.thinkup.com.apprunning.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import studios.thinkup.com.apprunning.MainActivity;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.Modalidad;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.FiltrosProvider;
import studios.thinkup.com.apprunning.provider.LoginGoogleProvider;

/**
 * Created by fcostazini on 27/05/2015.
 * Preferencias de busqueda
 */
public class Preferencias extends Fragment {


private Spinner spProvincia;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FiltrosProvider filtrosProvider = new FiltrosProvider(this.getActivity());
        DefaultSettings defaultSettings = ((RunningApplication) this.getActivity().getApplication()).getDefaultSettings();

        View rootView = inflater.inflate(R.layout.filtros_activity, container, false);
        Button logout = (Button) rootView.findViewById(R.id.btn_logout);
        UsuarioApp ua = ((RunningApplication) this.getActivity().getApplication()).getUsuario();
        if (!ua.getTipoCuenta().equals(String.valueOf(LoginGoogleProvider.ID))) {
            logout.setBackgroundColor(this.getActivity().getResources().getColor(R.color.com_facebook_blue));
            logout.setTextColor(this.getActivity().getResources().getColor(R.color.common_signin_btn_text_dark));
        } else {
            logout.setBackgroundColor(this.getActivity().getResources().getColor(R.color.rojo_google));
            logout.setTextColor(this.getActivity().getResources().getColor(R.color.common_signin_btn_text_dark));
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Preferencias.this.getActivity() != null) {

                    Intent i = new Intent(Preferencias.this.getActivity(), MainActivity.class);
                    i.putExtra("LOGOUT", true);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    Preferencias.this.getActivity().startActivity(i);
                    Preferencias.this.getActivity().finish();

                }
            }
        });
        Spinner spGenero = (Spinner) rootView.findViewById(R.id.sp_genero);
        ArrayAdapter<Modalidad> adapterGenero = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, Modalidad.values());
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenero.setAdapter(adapterGenero);
        spGenero.setOnItemSelectedListener(new GeneroSpinnerItemSelectedListener(defaultSettings));
        spGenero.setSelection(adapterGenero.getPosition(defaultSettings.getModalidad()));

        Spinner spZona = (Spinner) rootView.findViewById(R.id.sp_zona);
        ArrayAdapter<String> adapterZona = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, filtrosProvider.getProvincias());
        adapterZona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spZona.setAdapter(adapterZona);
        spZona.setOnItemSelectedListener(new ZonaSpinnerItemSelectedListener(defaultSettings));
        spZona.setSelection(adapterZona.getPosition(defaultSettings.getZona()));


        SeekBar sbMinDistancia = (SeekBar) rootView.findViewById(R.id.sb_min_distancia);
        TextView txtMinDistancia = (TextView) rootView.findViewById(R.id.lbl_dist_desde);
        sbMinDistancia.setProgress(defaultSettings.getDistanciaMin());
        //txtMinDistancia.setText(Filtro.DISTANCIAS[defaultSettings.getDistanciaMin()]);
        sbMinDistancia.setOnSeekBarChangeListener(new DistanciaSeekBarChangeListener(defaultSettings, txtMinDistancia));

        SeekBar sbDias = (SeekBar) rootView.findViewById(R.id.sb_dias);
        TextView txtDias = (TextView) rootView.findViewById(R.id.txt_dias);
        txtDias.setText(defaultSettings.getDiasBusqueda().toString());
        sbDias.setProgress(defaultSettings.getDiasBusqueda());
        sbDias.setMax(120);
        sbDias.setOnSeekBarChangeListener(new DistanciaSeekBarChangeListener(defaultSettings, txtDias));
        return rootView;

    }


    private class ZonaSpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private DefaultSettings defaultSettings;

        ZonaSpinnerItemSelectedListener(DefaultSettings defaultSettings) {
            this.defaultSettings = defaultSettings;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            defaultSettings.setZona(parent.getItemAtPosition(position).toString());


        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            defaultSettings.setZona("");
        }
    }

    private class GeneroSpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private DefaultSettings defaultSettings;

        GeneroSpinnerItemSelectedListener(DefaultSettings defaultSettings) {
            this.defaultSettings = defaultSettings;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            defaultSettings.setModalidad((Modalidad) parent.getItemAtPosition(position));

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            defaultSettings.setModalidad(Modalidad.TODOS);
        }
    }

    private class DistanciaSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        int progress = 0;
        DefaultSettings defaultSettings;
        TextView toUpdate;

        private DistanciaSeekBarChangeListener(DefaultSettings defaultSettings, TextView toUpdate) {
            this.defaultSettings = defaultSettings;
            this.toUpdate = toUpdate;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            this.progress = progress;
            if(seekBar.getId()== R.id.sb_dias){
                this.toUpdate.setText(String.valueOf(progress));
            }else{
                //this.toUpdate.setText(Filtro.DISTANCIAS[progress]);
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (seekBar != null) {
                switch (seekBar.getId()) {
                    case R.id.sb_min_distancia:
                        defaultSettings.setDistanciaMin(progress);
                        break;

                    case R.id.sb_dias:
                        defaultSettings.setDiasBusqueda(progress);
                        break;

                }
            }

        }
    }


}
