package studios.thinkup.com.apprunning;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Activity para Acerca De
 */
public class AcecaDeNosotrosActivity extends MainNavigationActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void defineContentView() {
        setContentView(R.layout.activity_aceca_de_nosotros);
    }
}
