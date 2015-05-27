package studios.thinkup.com.apprunning;

import android.os.Bundle;
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


public class FiltrosPorDefectoActivity extends MainNavigationActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void defineContentView() {
        setContentView(R.layout.preferencias_layout);
    }

}
