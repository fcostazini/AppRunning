package studios.thinkup.com.apprunning.fragment;


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

/**
 * Created by fcostazini on 21/05/2015.
 */
public class TemporizadorFragment extends Fragment implements  View.OnClickListener {
    private TextView horas;
    private TextView minutos;
    private TextView segundos;
    private TextView millisec;

    private ImageButton pause;
    private ImageButton play;
    private ImageButton save;
    private boolean isRunning;
    long init,now,time,paused;

    private Runnable updater;

    private Handler handler;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_temporizador, container, false);
        this.isRunning = false;
        save = (ImageButton) rootView.findViewById(R.id.ib_save);
        play = (ImageButton) rootView.findViewById(R.id.ib_start);
        pause = (ImageButton) rootView.findViewById(R.id.ib_pause);
        this.horas= (TextView) rootView.findViewById(R.id.txt_hora);
        this.minutos=(TextView) rootView.findViewById(R.id.txt_minutos);
        this.segundos =(TextView) rootView.findViewById(R.id.txt_segundos);
        this.millisec =(TextView) rootView.findViewById(R.id.txt_millisec);
        this.pause.setVisibility(View.GONE);
        this.save.setVisibility(View.GONE);
        Typeface type = Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/digit.ttf");
        this.horas.setTypeface(type);
        this.minutos.setTypeface(type);
        this.segundos.setTypeface(type);
        this.millisec.setTypeface(type);

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
        return rootView;
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
        }
    }
}
