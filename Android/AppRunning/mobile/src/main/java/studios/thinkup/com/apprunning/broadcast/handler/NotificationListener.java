package studios.thinkup.com.apprunning.broadcast.handler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import studios.thinkup.com.apprunning.DetalleCarreraActivity;
import studios.thinkup.com.apprunning.MisCarrerasActivity;
import studios.thinkup.com.apprunning.R;

/**
 * Created by Facundo on 31/01/2016.
 */
public class NotificationListener extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        Bundle b = (Bundle) data.get("notification");
        String p = from;
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notificacion)
                        .setContentTitle(b.getString("title"))
                        .setContentText(b.getString("body"));
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, DetalleCarreraActivity.class);

        Bundle carrera = new Bundle();
        int idCarrera = Integer.valueOf(data.getString(DetalleCarreraActivity.ID_CARRERA));
        carrera.putInt(DetalleCarreraActivity.ID_CARRERA, idCarrera);
        resultIntent.putExtras(carrera);
// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MisCarrerasActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.

        mNotificationManager.notify(idCarrera * 1000, mBuilder.build());

    }
}
