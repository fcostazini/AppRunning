package studios.thinkup.com.apprunning.provider.restProviders;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import studios.thinkup.com.apprunning.R;

/**
 * Created by fcostazini on 30/07/2015.
 * Main de Servicios Remotos
 */
public abstract class RemoteService {
    protected Context context;

    public RemoteService(Context context) {
        this.context = context;
    }

    protected String getBaseURL() {
        return this.context.getString(R.string.host_url) + this.context.getString(R.string.base_service_url) + this.getModule();
    }

   /* protected HttpURLConnection getGETConnection(URL url){
        if(isInternetConnected()){

        }else{
            return null;
        }
    }*/

    protected boolean isInternetConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    protected abstract String getModule();
}
