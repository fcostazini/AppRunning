package studios.thinkup.com.apprunning.broadcast.handler;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;

import com.google.android.gms.gcm.GcmListenerService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import studios.thinkup.com.apprunning.DetalleCarreraActivity;
import studios.thinkup.com.apprunning.MisCarrerasActivity;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.RecomendadosActivity;
import studios.thinkup.com.apprunning.SeleccionarUsuarioActivity;
import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.provider.CarreraLocalProvider;

/**
 * Created by Facundo on 31/01/2016.
 */
public class NotificationListener extends GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        String message = data.getString("message");
        if (from.startsWith("/topics/noticias")) {
            this.notificarNoticia(from,data);
        } else {
            int idCarrera = Integer.valueOf(data.getString(DetalleCarreraActivity.ID_CARRERA));
            String urlImagen = data.getString(DetalleCarreraActivity.URL_IMAGEN);
            new NotificationBuilderURL(from, data, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, urlImagen);
        }




    }

    private void notificarNoticia(String from,Bundle data) {

        String p = from;
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notificacion)
                        .setContentTitle(data.getString("title"))
                        .setContentText(data.getString("message"))
                        .setAutoCancel(true)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        Intent resultIntent = new Intent(this, SeleccionarUsuarioActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
// Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MisCarrerasActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0, NotificationCompat.FLAG_SHOW_LIGHTS |
                                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT
                );
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(data.getString("title").hashCode(), mBuilder.build());
    }

    private class NotificationBuilderURL extends AsyncTask<String, Integer, Bitmap> {

        private Bundle data;
        private Context context;
        private String from;

        public NotificationBuilderURL(String from, Bundle data, Context c) {
            this.data = data;
            this.from = from;
            this.context = c;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            InputStream in;

            try {

                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                in = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            Bundle b = (Bundle) data.get("notification");
            String p = from;
            android.support.v4.app.NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(this.context)
                            .setSmallIcon(R.drawable.ic_notificacion)
                            .setContentTitle(b.getString("title"))
                            .setContentText(b.getString("body"))
                            .setLargeIcon(bitmap);

            Intent resultIntent = new Intent(this.context, DetalleCarreraActivity.class);

            Bundle carrera = new Bundle();
            int idCarrera = Integer.valueOf(data.getString(DetalleCarreraActivity.ID_CARRERA));
            carrera.putInt(DetalleCarreraActivity.ID_CARRERA, idCarrera);
            resultIntent.putExtras(carrera);
// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this.context);
// Adds the back stack for the Intent (but not the Intent itself)
            stackBuilder.addParentStack(MisCarrerasActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0, NotificationCompat.FLAG_SHOW_LIGHTS |
                            PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.

            mNotificationManager.notify(idCarrera * 1000, mBuilder.build());
        }
    }
}
