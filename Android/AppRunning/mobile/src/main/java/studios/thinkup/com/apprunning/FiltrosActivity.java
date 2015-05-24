package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SeekBar;
import android.widget.Spinner;

import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.Genero;
import studios.thinkup.com.apprunning.model.RunningApplication;


public class FiltrosActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filtros_activity);
        Spinner spZona = (Spinner)findViewById(R.id.sp_zona);
        spZona.setOnItemSelectedListener(new ZonaSpinnerItemSelectedListener());
        Spinner spGenero = (Spinner)findViewById(R.id.sp_genero);
        spGenero.setOnItemSelectedListener(new GeneroSpinnerItemSelectedListener());
        SeekBar sbDias = (SeekBar)findViewById(R.id.sb_dias_max);
        sbDias.setOnSeekBarChangeListener(new DiasSeekBarChangeListener());
        SeekBar sbDistancia = (SeekBar)findViewById(R.id.sb_distancia_maxima);
        sbDistancia.setOnSeekBarChangeListener(new DistanciaSeekBarChangeListener());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filtros, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

  private class ZonaSpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener{

      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            DefaultSettings settings = ((RunningApplication)FiltrosActivity.this.getApplication()).getDefaultSettings();
            if(view.getId() == R.id.sp_zona) {
                Spinner spZona = (Spinner) view;
                settings.setZona(spZona.getSelectedItem().toString());
            }
             
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {
          DefaultSettings settings = ((RunningApplication)FiltrosActivity.this.getApplication()).getDefaultSettings();
          settings.setZona("");
      }
  }

    private class GeneroSpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            DefaultSettings settings = ((RunningApplication)FiltrosActivity.this.getApplication()).getDefaultSettings();
            if(view.getId() == R.id.sp_genero) {
                Spinner spGenero = (Spinner) view;
                settings.setGenero((Genero)spGenero.getSelectedItem());
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            DefaultSettings settings = ((RunningApplication)FiltrosActivity.this.getApplication()).getDefaultSettings();
            settings.setGenero(Genero.TODOS);
        }
    }

    private class DiasSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{
        int progress = 0;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            this.progress = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            DefaultSettings settings = ((RunningApplication)FiltrosActivity.this.getApplication()).getDefaultSettings();
            settings.setMaxDias(progress);
        }
    }
    private class DistanciaSeekBarChangeListener implements SeekBar.OnSeekBarChangeListener{
        int progress = 0;
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            this.progress = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            DefaultSettings settings = ((RunningApplication)FiltrosActivity.this.getApplication()).getDefaultSettings();
            settings.setMaxDistancia(progress);
        }
    }

}
