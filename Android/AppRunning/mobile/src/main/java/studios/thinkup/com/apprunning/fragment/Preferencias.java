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

import com.edmodo.rangebar.RangeBar;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;

import studios.thinkup.com.apprunning.MainActivity;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.Modalidad;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.ConfigProvider;
import studios.thinkup.com.apprunning.provider.FiltrosProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;


/**
 * Created by fcostazini on 27/05/2015.
 * Preferencias de busqueda
 */
public class Preferencias extends Fragment {


    private Spinner spCiudad;
    private DefaultSettings defaultSettings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final FiltrosProvider filtrosProvider = new FiltrosProvider(this.getActivity());
        ConfigProvider cp = new ConfigProvider(this.getActivity());
        UsuarioApp ua = ((RunningApplication) this.getActivity().getApplication()).getUsuario();

        defaultSettings = cp.getByUsuario(ua.getId());
        if (defaultSettings == null) {
            defaultSettings = new DefaultSettings(ua.getId());
            try {
                cp.grabar(defaultSettings);
            } catch (EntidadNoGuardadaException e) {
                e.printStackTrace();
            }

        }
        View rootView = inflater.inflate(R.layout.filtros_activity, container, false);
        Button logout = (Button) rootView.findViewById(R.id.btn_logout);

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
        spGenero.setOnItemSelectedListener(new GeneroSpinnerItemSelectedListener(this.defaultSettings));
        spGenero.setSelection(adapterGenero.getPosition(defaultSettings.getModalidad()));

        spCiudad = (Spinner) rootView.findViewById(R.id.sp_ciudad);
        spCiudad.setVisibility(View.GONE);
        Spinner spProvincia = (Spinner) rootView.findViewById(R.id.sp_provincia);
        ArrayAdapter<String> adapterProvincia = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, filtrosProvider.getProvincias());
        adapterProvincia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProvincia.setAdapter(adapterProvincia);

        spProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String provincia = parent.getItemAtPosition(position).toString();
                Preferencias.this.defaultSettings.setProvincia(provincia);
                if (!provincia.equals(FiltrosProvider.TODAS_LAS_PROVINCIAS)
                        && !provincia.equals(FiltrosProvider.CAPITAL)) {
                    spCiudad.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapterZona = new ArrayAdapter<>(Preferencias.this.getActivity(),
                            android.R.layout.simple_spinner_item, filtrosProvider.getCiudades(provincia));
                    adapterZona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCiudad.setAdapter(adapterZona);
                    spCiudad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            Preferencias.this.defaultSettings.setCiudad(parent.getItemAtPosition(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            Preferencias.this.defaultSettings.setCiudad(FiltrosProvider.TODAS_LAS_CIUDADES);
                        }
                    });
                    spCiudad.setSelection(adapterZona.getPosition(defaultSettings.getCiudad()));
                } else {
                    spCiudad.setVisibility(View.GONE);
                    Preferencias.this.defaultSettings.setCiudad(FiltrosProvider.TODAS_LAS_CIUDADES);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Preferencias.this.defaultSettings.setProvincia(FiltrosProvider.TODAS_LAS_PROVINCIAS);
                spCiudad.setVisibility(View.GONE);
            }
        });

        spProvincia.setSelection(adapterProvincia.getPosition(defaultSettings.getProvincia()));
        RangeBar sb_distancia = (RangeBar) rootView.findViewById(R.id.sb_distancia);

        TextView left = (TextView) rootView.findViewById(R.id.lbl_dist_desde);
        left.setText(String.valueOf(defaultSettings.getDistanciaMin()) + "Km");
        TextView right = (TextView) rootView.findViewById(R.id.lbl_dist_hasta);
        right.setText(String.valueOf(defaultSettings.getDistanciaMax()) + "Km");
        sb_distancia.setOnRangeBarChangeListener(new DistanciaSeekBarChangeListener(this.defaultSettings, left, right));

        sb_distancia.setThumbIndices(defaultSettings.getDistanciaMin() /10, defaultSettings.getDistanciaMax() / 10);

        SeekBar sbDias = (SeekBar) rootView.findViewById(R.id.sb_dias);
        TextView txtDias = (TextView) rootView.findViewById(R.id.txt_dias);
        txtDias.setText(defaultSettings.getDiasBusqueda().toString());
        sbDias.setProgress(defaultSettings.getDiasBusqueda());
        sbDias.setMax(120);
        sbDias.setOnSeekBarChangeListener(new DiasSeekBarChangeListener(defaultSettings, txtDias));
        sbDias.setProgress(defaultSettings.getDiasBusqueda());
        return rootView;

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

    private class DiasSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        int progress = 0;
        DefaultSettings defaultSettings;
        TextView toUpdate;

        private DiasSeekBarChangeListener(DefaultSettings defaultSettings, TextView toUpdate) {
            this.defaultSettings = defaultSettings;
            this.toUpdate = toUpdate;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            this.progress = progress;
            if (seekBar.getId() == R.id.sb_dias) {
                this.toUpdate.setText(String.valueOf(progress));
            } else {
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
                    case R.id.sb_dias:
                        defaultSettings.setDiasBusqueda(progress);
                        break;

                }
            }

        }
    }


    private class DistanciaSeekBarChangeListener implements RangeBar.OnRangeBarChangeListener {
        DefaultSettings filtro;
        TextView left;
        TextView right;

        private DistanciaSeekBarChangeListener(DefaultSettings filtro, TextView left, TextView right) {
            this.filtro = filtro;
            this.left = left;
            this.right = right;
        }

        @Override
        public void onIndexChangeListener(RangeBar rangeBar, int i, int i1) {
            this.left.setText(String.valueOf(i * 10) + " Km");
            this.right.setText(String.valueOf(i1 * 10) + " Km");
            filtro.setDistanciaMin(i * 10);
            filtro.setDistanciaMax(i1 * 10);

        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveDefaultSettings();
    }

    @Override
    public void onDestroy() {
        saveDefaultSettings();
        super.onDestroy();
    }

    private void saveDefaultSettings() {
        ConfigProvider cp = new ConfigProvider(this.getActivity());
        if (this.defaultSettings != null) {
            try {
                if (this.defaultSettings.getId() != null) {
                    cp.update(this.defaultSettings);
                } else {
                    cp.grabar(this.defaultSettings);
                }
            } catch (EntidadNoGuardadaException e) {
                e.printStackTrace();
            }

        }
    }
}
