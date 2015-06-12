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

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.entity.Modalidad;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.provider.ZonaProvider;

/**
 * Created by fcostazini on 27/05/2015.
 * Preferencias de busqueda
 *
 */
public class Preferencias extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ZonaProvider zonaProvider = new ZonaProvider(this.getActivity());
        DefaultSettings defaultSettings = ((RunningApplication) this.getActivity().getApplication()).getDefaultSettings();

        View rootView = inflater.inflate(R.layout.filtros_activity, container, false);

        Spinner spGenero = (Spinner) rootView.findViewById(R.id.sp_genero);
        ArrayAdapter<Modalidad> adapterGenero = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, Modalidad.values());
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenero.setAdapter(adapterGenero);
        spGenero.setOnItemSelectedListener(new GeneroSpinnerItemSelectedListener(defaultSettings));
        spGenero.setSelection(adapterGenero.getPosition(defaultSettings.getModalidad()));

        Spinner spZona = (Spinner) rootView.findViewById(R.id.sp_zona);
        ArrayAdapter<String> adapterZona = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, zonaProvider.getZonas());
        adapterZona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spZona.setAdapter(adapterZona);
        spZona.setOnItemSelectedListener(new ZonaSpinnerItemSelectedListener(defaultSettings));
        spZona.setSelection(adapterZona.getPosition(defaultSettings.getZona()));


        SeekBar sbMinDistancia = (SeekBar) rootView.findViewById(R.id.sb_min_distancia);
        TextView txtMinDistancia = (TextView) rootView.findViewById(R.id.lbl_dist_desde);
        txtMinDistancia.setText(defaultSettings.getDistanciaMin().toString());
        sbMinDistancia.setProgress(defaultSettings.getDistanciaMin());
        sbMinDistancia.setOnSeekBarChangeListener(new DistanciaSeekBarChangeListener(defaultSettings, txtMinDistancia));


        SeekBar sbMaxDistancia = (SeekBar) rootView.findViewById(R.id.sb_max_distancia);
        TextView txtMaxDistancia = (TextView) rootView.findViewById(R.id.lbl_dist_hasta);
        txtMaxDistancia.setText(defaultSettings.getDistanciaMax().toString());
        sbMaxDistancia.setProgress(defaultSettings.getDistanciaMax());
        sbMaxDistancia.setOnSeekBarChangeListener(new DistanciaSeekBarChangeListener(defaultSettings, txtMaxDistancia));

        SeekBar sbDias = (SeekBar) rootView.findViewById(R.id.sb_dias);
        TextView txtDias = (TextView) rootView.findViewById(R.id.txt_dias);
        txtDias.setText(defaultSettings.getDiasBusqueda().toString());
        sbDias.setProgress(defaultSettings.getDiasBusqueda());
        sbDias.setMax(120);
        sbDias.setOnSeekBarChangeListener(new DistanciaSeekBarChangeListener(defaultSettings, txtDias));
        return  rootView;

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
            this.toUpdate.setText(String.valueOf(progress));
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
                    case R.id.sb_max_distancia:
                        defaultSettings.setDistanciaMax(progress);
                        break;
                    case R.id.sb_dias:
                        defaultSettings.setDiasBusqueda(progress);
                        break;

                }
            }

        }
    }


}
