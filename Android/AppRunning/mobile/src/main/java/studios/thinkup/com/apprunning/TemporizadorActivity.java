package studios.thinkup.com.apprunning;


import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.TypefaceProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;
import studios.thinkup.com.apprunning.view.IconTextView;

/**
 * Created by fcostazini on 21/05/2015.
 * Temporizador de la carrera
 */
public class TemporizadorActivity extends Activity implements View.OnClickListener {
    long init, now, time, paused;
    private TextView horas;
    private TextView minutos;
    private TextView segundos;
    private TextView millisec;
    private ImageButton pause;
    private ImageButton play;
    private IconTextView save;
    private boolean isRunning;
    private UsuarioCarrera uc;
    private Runnable updater;

    private Handler handler;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.uc != null) {
            outState.putSerializable("carrera", this.uc);
        }
        if (this.time >= 0) {
            outState.putLong("tiempo", this.time);
        }
        outState.putBoolean("isRunning", this.isRunning);


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startUp(savedInstanceState);
        if (this.uc == null) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.fragment_temporizador);
        this.isRunning = false;
        save = (IconTextView) this.findViewById(R.id.icon_fin_carrera);
        play = (ImageButton) this.findViewById(R.id.ib_start);
        pause = (ImageButton) this.findViewById(R.id.ib_pause);
        this.horas = (TextView) this.findViewById(R.id.txt_hora);
        this.minutos = (TextView) this.findViewById(R.id.txt_minutos);
        this.segundos = (TextView) this.findViewById(R.id.txt_segundos);
        this.millisec = (TextView) this.findViewById(R.id.txt_millisec);

        Typeface type = TypefaceProvider.getInstance(this).getTypeface(TypefaceProvider.DIGIT);
        this.horas.setTypeface(type);
        this.minutos.setTypeface(type);
        this.segundos.setTypeface(type);
        this.millisec.setTypeface(type);


        this.save.getBackground().setColorFilter(0xFF6B87B7, PorterDuff.Mode.SRC_ATOP);
        this.pause.getBackground().setColorFilter(0xFF6B87B7, PorterDuff.Mode.SRC_ATOP);
        this.save.setEnabled(false);
        this.pause.setEnabled(false);

        horas.setText("00");
        minutos.setText("00");
        segundos.setText("00");
        millisec.setText("00");

        this.handler = new Handler();
        this.updater = new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    now = SystemClock.elapsedRealtime();
                    time = paused + now - init;
                    int h = (int) (time / 3600000);
                    int m = (int) (time - h * 3600000) / 60000;
                    int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                    int ms = (int) (time - h * 3600000 - m * 60000 - s * 1000) / 10;

                    horas.setText(h < 10 ? "0" + h : h + "");
                    minutos.setText(m < 10 ? "0" + m : m + "");
                    segundos.setText(s < 10 ? "0" + s : s + "");
                    millisec.setText(ms < 10 ? "0" + ms : ms + "");

                    handler.postDelayed(this, 30);
                }
            }
        };
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        save.setOnClickListener(this);
    }

    private void startUp(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("carrera")) {
                this.uc = (UsuarioCarrera) savedInstanceState.getSerializable("carrera");
            }
            if (savedInstanceState.containsKey("tiempo")) {
                this.time = savedInstanceState.getLong("tiempo");
            } else {
                this.time = 0l;
            }
            this.isRunning = savedInstanceState.containsKey("isRunning") && savedInstanceState.getBoolean("isRunning");
        }
        if (this.uc == null && this.getIntent().getExtras() != null && this.getIntent().getExtras().containsKey("carrera")) {
            this.uc = (UsuarioCarrera) this.getIntent().getExtras().getSerializable("carrera");
        }


    }

    public void updateUsuarioCarrera() {
        try {
            Integer idUsuario = ((RunningApplication) this.getApplication()).getUsuario().getId();
            IUsuarioCarreraProvider up = new UsuarioCarreraProvider(this, ((RunningApplication) this.getApplication()).getUsuario());
            up.actualizarCarrera(this.uc);
        } catch (EntidadNoGuardadaException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ib_start: {
                this.isRunning = true;
                init = SystemClock.elapsedRealtime();
                this.play.getBackground().setColorFilter(0xFF6B87B7, PorterDuff.Mode.SRC_ATOP);
                this.play.setEnabled(false);
                this.pause.getBackground().clearColorFilter();
                this.save.getBackground().clearColorFilter();
                this.save.setEnabled(true);
                this.pause.setEnabled(true);
                handler.post(updater);
                break;
            }
            case R.id.ib_pause: {
                this.isRunning = false;
                paused = time;
                this.pause.getBackground().setColorFilter(0xFF6B87B7, PorterDuff.Mode.SRC_ATOP);
                this.pause.setEnabled(false);
                this.save.getBackground().clearColorFilter();
                this.play.getBackground().clearColorFilter();
                this.save.setEnabled(true);
                this.play.setEnabled(true);
                break;
            }
            case R.id.icon_fin_carrera: {

                this.isRunning = false;
                paused = time;

                uc.setTiempo(time);
                updateUsuarioCarrera();
                Intent i = new Intent(this, DetalleCarreraActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("carrera", this.uc);
                i.putExtras(b);
                startActivity(i);
                finish();

                break;
            }
        }
    }
}
