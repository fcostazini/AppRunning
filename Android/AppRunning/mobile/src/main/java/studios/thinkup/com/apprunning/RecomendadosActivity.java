package studios.thinkup.com.apprunning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import studios.thinkup.com.apprunning.broadcast.handler.RegistrationIntentService;
import studios.thinkup.com.apprunning.fragment.RecomendadosFragment;


public class RecomendadosActivity extends ResultadosFiltrablesActivity {
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    @Override
    protected Fragment getFragment() {
        return new RecomendadosFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPlayServices()) {
            // Start IntentService to register this application with GCM.
            Intent intent = new Intent(this, RegistrationIntentService.class);
            Bundle b = new Bundle();
            b.putInt(Constants.ID_USUARIO, this.getIdUsuario());
            intent.putExtras(b);
            startService(intent);
        }

    }

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i("gcm", "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }
}
