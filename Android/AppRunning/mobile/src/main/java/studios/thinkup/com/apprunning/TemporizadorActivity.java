package studios.thinkup.com.apprunning;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.Carrera;

/**
 * Created by fcostazini on 21/05/2015.
 */
public class TemporizadorActivity extends Activity implements View.OnClickListener {
    private TextView horas;
    private TextView minutos;
    private TextView segundos;
    private TextView millisec;
    private boolean editando;
    private Carrera carrera;

    private ImageButton pause;
    private ImageButton play;
    private ImageButton save;
    private boolean isRunning;
    long init,now,time,paused;

    private Runnable updater;

    private Handler handler;



    @Override
    public  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.carrera = (Carrera) this.getIntent().getExtras().getSerializable(Carrera.class.getSimpleName());
        setContentView(R.layout.fragment_temporizador);
        this.isRunning = false;
        save = (ImageButton) this.findViewById(R.id.ib_save);
        play = (ImageButton) this.findViewById(R.id.ib_start);
        pause = (ImageButton) this.findViewById(R.id.ib_pause);
        this.horas= (TextView) this.findViewById(R.id.txt_hora);
        this.minutos=(TextView) this.findViewById(R.id.txt_minutos);
        this.segundos =(TextView) this.findViewById(R.id.txt_segundos);
        this.millisec =(TextView) this.findViewById(R.id.txt_millisec);
        this.pause.setVisibility(View.GONE);
        this.save.setVisibility(View.GONE);
        Typeface type = Typeface.createFromAsset(this.getAssets(), "fonts/digit.ttf");
        this.horas.setTypeface(type);
        this.minutos.setTypeface(type);
        this.segundos.setTypeface(type);
        this.millisec.setTypeface(type);

        if(this.carrera.getTiempo() == 0){
            this.editando = false;
            this.pause.setVisibility(View.GONE);
            this.save.setVisibility(View.VISIBLE);
            this.play.setVisibility(View.VISIBLE);
        }else{
            this.editando = true;
            this.pause.setVisibility(View.GONE);
            this.save.setVisibility(View.VISIBLE);
            this.save.setImageResource(android.R.drawable.ic_menu_edit);
            this.play.setVisibility(View.GONE);
            time = this.carrera.getTiempo();
            int h   = (int)(time /3600000);
            int m = (int)(time - h*3600000)/60000;
            int s= (int)(time - h*3600000- m*60000)/1000 ;
            int ms= (int)(time - h*3600000- m*60000 -s*1000)/10 ;
            horas.setText( h < 10 ? "0"+h: h+"");
            minutos.setText( m < 10 ? "0"+m: m+"");
            segundos.setText( s < 10 ? "0"+s : s+"");
            millisec.setText( ms < 10 ? "0"+ms : ms+"");
        }

        this.handler = new Handler();
        this.updater = new Runnable() {
            @Override
            public void run() {
                if (isRunning) {
                    now=SystemClock.elapsedRealtime();
                    time =  paused + now-init;
                    int h   = (int)(time /3600000);
                    int m = (int)(time - h*3600000)/60000;
                    int s= (int)(time - h*3600000- m*60000)/1000 ;
                    int ms= (int)(time - h*3600000- m*60000 -s*1000)/10 ;

                    horas.setText( h < 10 ? "0"+h: h+"");
                    minutos.setText( m < 10 ? "0"+m: m+"");
                    segundos.setText( s < 10 ? "0"+s : s+"");
                    millisec.setText( ms < 10 ? "0"+ms : ms+"");

                    handler.postDelayed(this, 30);
                }
            }
        };
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        save.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.ib_start: {
                this.isRunning = true;
                     init = SystemClock.elapsedRealtime();
                if(time > 0){

                }
                     handler.post(updater);
                     this.play.setVisibility(View.GONE);
                     this.pause.setVisibility(View.VISIBLE);
                     this.save.setVisibility(View.GONE);
                break;
                   }
            case R.id.ib_pause:{
                this.isRunning = false;
                paused = time;
                this.play.setVisibility(View.VISIBLE);
                this.pause.setVisibility(View.GONE);
                this.save.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.ib_save:{
                this.play.setVisibility(View.GONE);
                this.pause.setVisibility(View.GONE);
                this.save.setVisibility(View.VISIBLE);

                this.isRunning = false;
                paused = time;

                this.save.setImageResource(android.R.drawable.ic_menu_edit);
                this.carrera.setTiempo(this.time);

                Intent i = new Intent(this,DetalleCarreraActivity.class);
                i.getExtras().putSerializable(Carrera.class.getSimpleName(),this.carrera);
                startActivity(i);






                break;
            }
        }
    }
}
