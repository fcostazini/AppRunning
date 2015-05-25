package studios.thinkup.com.apprunning;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.Genero;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.provider.ZonaProvider;


public class FiltrosPorDefectoActivity extends FragmentActivity{

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filtros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtros_activity);
        ZonaProvider zonaProvider = new ZonaProvider();
        DefaultSettings defaultSettings = ((RunningApplication)getApplication()).getDefaultSettings();


        Spinner spGenero = (Spinner) findViewById(R.id.sp_genero);
        ArrayAdapter<Genero> adapterGenero = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, Genero.values());
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenero.setAdapter(adapterGenero);
        spGenero.setOnItemSelectedListener(new GeneroSpinnerItemSelectedListener(defaultSettings));
        spGenero.setSelection(adapterGenero.getPosition(defaultSettings.getGenero()));

        Spinner spZona = (Spinner) findViewById(R.id.sp_zona);
        ArrayAdapter<String> adapterZona = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, zonaProvider.getZonas());
        adapterZona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spZona.setAdapter(adapterZona);
        spZona.setOnItemSelectedListener(new ZonaSpinnerItemSelectedListener(defaultSettings));
        spZona.setSelection(adapterZona.getPosition(defaultSettings.getZona()));


        SeekBar sbMinDistancia = (SeekBar) findViewById(R.id.sb_min_distancia);
        TextView txtMinDistancia = (TextView) findViewById(R.id.lbl_dist_desde);
        txtMinDistancia.setText(defaultSettings.getDistanciaMin().toString());
        sbMinDistancia.setProgress(defaultSettings.getDistanciaMin());
        sbMinDistancia.setOnSeekBarChangeListener(new DistanciaSeekBarChangeListener(defaultSettings,txtMinDistancia));


        SeekBar sbMaxDistancia = (SeekBar) findViewById(R.id.sb_max_distancia);
        TextView txtMaxDistancia = (TextView) findViewById(R.id.lbl_dist_hasta);
        txtMaxDistancia.setText(defaultSettings.getDistanciaMax().toString());
        sbMaxDistancia.setProgress(defaultSettings.getDistanciaMax());
        sbMaxDistancia.setOnSeekBarChangeListener(new DistanciaSeekBarChangeListener(defaultSettings, txtMaxDistancia));

        SeekBar sbDias = (SeekBar) findViewById(R.id.sb_dias);
        TextView txtDias = (TextView) findViewById(R.id.txt_dias);
        txtDias.setText(defaultSettings.getDiasBusqueda().toString());
        sbDias.setProgress(defaultSettings.getDiasBusqueda());
        sbDias.setMax(120);
        sbDias.setOnSeekBarChangeListener(new DistanciaSeekBarChangeListener(defaultSettings, txtDias));

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

            defaultSettings.setGenero((Genero)parent.getItemAtPosition(position));

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            defaultSettings.setGenero(Genero.TODOS);
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
                switch (seekBar.getId()){
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
