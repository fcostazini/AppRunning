package studios.thinkup.com.apprunning.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;

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

    private DefaultSettings defaultSettings;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        final FiltrosProvider filtrosProvider = new FiltrosProvider(this.getActivity());

        defaultSettings = getDefaultSettings(savedInstanceState);
        View rootView = inflater.inflate(R.layout.preferencias_fragment, container, false);


        Spinner spGenero = (Spinner) rootView.findViewById(R.id.sp_genero);

        ArrayAdapter<Modalidad> adapterGenero = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, Modalidad.values());
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenero.setAdapter(adapterGenero);
        spGenero.setOnItemSelectedListener(new GeneroSpinnerItemSelectedListener(defaultSettings));
        spGenero.setSelection(adapterGenero.getPosition(defaultSettings.getModalidad()));

        final Spinner spCiudad = (Spinner) rootView.findViewById(R.id.sp_ciudad);
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
                defaultSettings.setProvincia(provincia);
                saveDefaultSettings(defaultSettings);
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
                            defaultSettings.setCiudad(parent.getItemAtPosition(position).toString());
                            saveDefaultSettings(defaultSettings);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            defaultSettings.setCiudad(FiltrosProvider.TODAS_LAS_CIUDADES);
                            saveDefaultSettings(defaultSettings);
                        }
                    });
                    spCiudad.setSelection(adapterZona.getPosition(defaultSettings.getCiudad()));
                } else {
                    spCiudad.setVisibility(View.GONE);
                    defaultSettings.setCiudad(FiltrosProvider.TODAS_LAS_CIUDADES);
                    saveDefaultSettings(defaultSettings);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                defaultSettings.setProvincia(FiltrosProvider.TODAS_LAS_PROVINCIAS);
                spCiudad.setVisibility(View.GONE);
                saveDefaultSettings(defaultSettings);
            }
        });

        spProvincia.setSelection(adapterProvincia.getPosition(defaultSettings.getProvincia()));
        RangeBar sb_distancia = (RangeBar) rootView.findViewById(R.id.sb_distancia);

        TextView left = (TextView) rootView.findViewById(R.id.lbl_dist_desde);
        left.setText(String.valueOf(defaultSettings.getDistanciaMin()) + "Km");
        TextView right = (TextView) rootView.findViewById(R.id.lbl_dist_hasta);
        right.setText(String.valueOf(defaultSettings.getDistanciaMax()) + "Km");
        sb_distancia.setOnRangeBarChangeListener(new DistanciaSeekBarChangeListener(defaultSettings, left, right));

        sb_distancia.setThumbIndices(defaultSettings.getDistanciaMin() / 10, defaultSettings.getDistanciaMax() / 10);

        SeekBar sbMeses = (SeekBar) rootView.findViewById(R.id.sb_meses);
        TextView txtMeses = (TextView) rootView.findViewById(R.id.txt_meses);
        txtMeses.setText(defaultSettings.getMesesBusqueda().toString());
        sbMeses.setProgress(defaultSettings.getMesesBusqueda());
        sbMeses.setMax(12);
        sbMeses.setOnSeekBarChangeListener(new MesesSeekBarChangeListener(defaultSettings, txtMeses));
        sbMeses.setProgress(defaultSettings.getMesesBusqueda());
        return rootView;

    }

    private DefaultSettings getDefaultSettings(Bundle savedInstance) {
        DefaultSettings defaultSettings = null;
        if (savedInstance != null && savedInstance.containsKey("default")) {
            defaultSettings = (DefaultSettings) savedInstance.getSerializable("default");
        }
        if(defaultSettings == null){
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
        }
        return defaultSettings;
    }


    private class GeneroSpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private DefaultSettings defaultSettings;

        GeneroSpinnerItemSelectedListener(DefaultSettings defaultSettings) {
            this.defaultSettings = defaultSettings;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            defaultSettings.setModalidad((Modalidad) parent.getItemAtPosition(position));
            saveDefaultSettings(defaultSettings);

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            defaultSettings.setModalidad(Modalidad.TODOS);
            saveDefaultSettings(defaultSettings);
        }
    }

    private class MesesSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {
        int progress = 0;
        DefaultSettings defaultSettings;
        TextView toUpdate;

        private MesesSeekBarChangeListener(DefaultSettings defaultSettings, TextView toUpdate) {
            this.defaultSettings = defaultSettings;
            this.toUpdate = toUpdate;
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            this.progress = progress;
            if (seekBar.getId() == R.id.sb_meses) {
                this.toUpdate.setText(String.valueOf(progress));
                defaultSettings.setMesesBusqueda(progress);
                saveDefaultSettings(defaultSettings);
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
                    case R.id.sb_meses:
                        defaultSettings.setMesesBusqueda(progress);
                        saveDefaultSettings(defaultSettings);
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
            if(i >= FiltrosProvider.MIN_DISTANCIA && i <= FiltrosProvider.MAX_DISTANCIA){
                this.left.setText(String.valueOf(i * 10) + " Km");
                filtro.setDistanciaMin(i * 10);
            }else{
                this.left.setText(String.valueOf(FiltrosProvider.MIN_DISTANCIA) + " Km");
                filtro.setDistanciaMin(FiltrosProvider.MIN_DISTANCIA);
            }
            if(i1 >= FiltrosProvider.MIN_DISTANCIA && i1 <= FiltrosProvider.MAX_DISTANCIA){
                this.right.setText(String.valueOf(i1 * 10) + " Km");
                filtro.setDistanciaMax(i1 * 10);
            }else{
                this.right.setText(String.valueOf(FiltrosProvider.MAX_DISTANCIA) + " Km");
                filtro.setDistanciaMax(FiltrosProvider.MAX_DISTANCIA);
            }


        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.defaultSettings != null && outState != null) {
            outState.putSerializable("default", this.defaultSettings);
        }
    }

    private void saveDefaultSettings(DefaultSettings defaultSettings) {
        ConfigProvider cp = new ConfigProvider(this.getActivity());
        if (defaultSettings != null) {
            try {
                if (defaultSettings.getId() != null) {
                    cp.update(defaultSettings);
                } else {
                    cp.grabar(defaultSettings);
                }
            } catch (EntidadNoGuardadaException e) {
                e.printStackTrace();
            }

        }
    }
}
