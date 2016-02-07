package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import studios.thinkup.com.apprunning.broadcast.handler.RegistrationIntentService;

/**
 * Created by fcostazini on 07/08/2015.
 */
public class SeleccionarUsuarioActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selecccionar_usuario);

    }
}
