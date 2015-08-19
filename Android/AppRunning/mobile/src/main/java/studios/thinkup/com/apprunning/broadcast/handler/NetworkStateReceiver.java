package studios.thinkup.com.apprunning.broadcast.handler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Facundo on 01/08/2015.
 * Network Updater
 */
public class NetworkStateReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int status = NetworkUtils.getConnectivityStatusString(context);

        if (!"android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            if (status == NetworkUtils.NETWORK_STAUS_WIFI) {

                if (UpdateBuffer.getInstance().hasPendingUpdates() && !UpdateBuffer.getInstance().isUpdating()) {
                    UpdateBuffer.getInstance().publishBuffer(context);
                }
            } else if (status == NetworkUtils.NETWORK_STATUS_MOBILE && !UpdateBuffer.getInstance().isUpdating()) {
                UpdateBuffer.getInstance().publishBuffer(context);
            }

        }
    }
}
