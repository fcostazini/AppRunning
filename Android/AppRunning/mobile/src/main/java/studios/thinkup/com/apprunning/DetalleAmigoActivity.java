package studios.thinkup.com.apprunning;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.adapter.AmigosPagerAdapter;
import studios.thinkup.com.apprunning.model.entity.AmigoRequest;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;
import studios.thinkup.com.apprunning.model.entity.TipoRequestEnum;
import studios.thinkup.com.apprunning.provider.ActualizarAmigoService;

/**
 * Created by Facundo on 11/08/2015.
 * Detalle de un amigo seleccionado
 */
public class DetalleAmigoActivity extends DrawerPagerActivity implements ActualizarAmigoService.IServicioActualizacionAmigoHandler {
    private AmigosDTO amigo;
    private Menu menu;
    private static ProgressDialog pd;
    protected static void showProgress(Context context, String message) {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    protected static void hideProgress() {
        if (pd != null) {
            pd.dismiss();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        this.amigo = (AmigosDTO) restoreField(savedInstanceState, AmigosDTO.FIELD_ID);
        if (this.amigo == null) {
            this.amigo = new AmigosDTO();
        }
        super.onCreate(savedInstanceState);
    }

    private Object restoreField(Bundle savedInstanceState, String name) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(name)) {
                return savedInstanceState.getSerializable(name);
            }
        } else {
            if (this.getIntent().getExtras() != null) {
                if (this.getIntent().getExtras().containsKey(name)) {
                    return this.getIntent().getExtras().getSerializable(name);
                }
            }
        }
        return null;
    }

    @Override
    protected PagerAdapter getAdapter() {
        return new AmigosPagerAdapter(this.getSupportFragmentManager(),this.amigo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detalle_amigo, menu);
        this.menu = menu;

        actualizarBotonera(menu);


        return true;
    }

    private void actualizarBotonera(Menu menu) {
        if (amigo != null) {
            menu.findItem(R.id.mnu_agregar_amigo).setVisible(!amigo.getEsAmigo() && !amigo.getEsBloqueado());
            menu.findItem(R.id.mnu_agregar_amigo).setEnabled(!amigo.getEsAmigo());
            menu.findItem(R.id.mnu_borrar_amigo).setVisible(amigo.getEsAmigo());
            menu.findItem(R.id.mnu_bloquear_amigo).setVisible(!amigo.getEsBloqueado());
            menu.findItem(R.id.mnu_desbloquear_amigo).setVisible(amigo.getEsBloqueado());


        }
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        AmigoRequest request = new AmigoRequest();
        request.setIdAmigo(amigo.getIdAmigo());
        request.setIdOwner(amigo.getIdOwner());

        switch (item.getItemId()) {
            case R.id.mnu_agregar_amigo:
                showProgress(this,"Procesando...");
                request.setTipoRequest(TipoRequestEnum.SOLICITUD_AMIGO);
                break;
            case R.id.mnu_bloquear_amigo:
                showProgress(this,"Procesando...");
                request.setTipoRequest(TipoRequestEnum.BLOQUEAR_AMIGO);
                break;
            case R.id.mnu_borrar_amigo:
                showProgress(this,"Procesando...");
                request.setTipoRequest(TipoRequestEnum.QUITA_AMIGO);
                break;
            case R.id.mnu_desbloquear_amigo:
                showProgress(this,"Procesando...");
                request.setTipoRequest(TipoRequestEnum.DESBLOQUEAR_AMIGO);
                break;
            default:
                return super.onMenuItemSelected(featureId, item);

        }
        List<AmigoRequest> ar = new Vector<>();
        ar.add(request);
        new ActualizarAmigoService(this, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ar);
        return true;
    }

    @Override
    public void onOk(List<AmigosDTO> amigo) {
        if (amigo != null && amigo.size()==1) {
            this.amigo = amigo.get(0);
        }
        hideProgress();
        actualizarBotonera(menu);
        Toast.makeText(this, "Estado guardado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onError(Integer estado) {
        hideProgress();
        Toast.makeText(this, "No guardado" + estado, Toast.LENGTH_SHORT).show();
    }

    private void setBackgroundGlow(Drawable imgview, int imageicon,int r,int g,int b)
    {
// An added margin to the initial image
        int margin = 24;
        int halfMargin = margin / 2;
        // the glow radius
        int glowRadius = 40;

        // the glow color
        int glowColor = Color.rgb(r, g, b);

        // The original image to use
        Bitmap src = BitmapFactory.decodeResource(getResources(), imageicon);

        // extract the alpha from the source image
        Bitmap alpha = src.extractAlpha();

        // The output bitmap (with the icon + glow)
        Bitmap bmp =  Bitmap.createBitmap(src.getWidth() + margin, src.getHeight() + margin, Bitmap.Config.ARGB_8888);

        // The canvas to paint on the image
        Canvas canvas = new Canvas(bmp);

        Paint paint = new Paint();
        paint.setColor(glowColor);

        // outer glow
        paint.setMaskFilter(new BlurMaskFilter(glowRadius, BlurMaskFilter.Blur.OUTER));//For Inner glow set Blur.INNER
        canvas.drawBitmap(alpha, halfMargin, halfMargin, paint);

        // original icon
        canvas.drawBitmap(src, halfMargin, halfMargin, null);

        imgview.draw(canvas);


    }

}
