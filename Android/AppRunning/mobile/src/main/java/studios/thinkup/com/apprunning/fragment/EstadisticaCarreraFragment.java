package studios.thinkup.com.apprunning.fragment;


import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.TemporizadorActivity;
import studios.thinkup.com.apprunning.model.Carrera;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstadisticaCarreraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstadisticaCarreraFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {
private LinearLayout aCorrer;
private Carrera carrera;
    public static EstadisticaCarreraFragment newInstance(Carrera carrera) {
        EstadisticaCarreraFragment fragment = new EstadisticaCarreraFragment();
        Bundle args = new Bundle();
        args.putSerializable(Carrera.class.getSimpleName(), carrera);
        fragment.setArguments(args);
        return fragment;
    }

    public EstadisticaCarreraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.carrera = (Carrera)getArguments().getSerializable(Carrera.class.getSimpleName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_estadistica_carrera, container, false);

        this.aCorrer = (LinearLayout) rootView.findViewById(R.id.lb_a_correr);
        if(this.carrera.isEstoyInscripto() && !this.carrera.isFueCorrida()){
            aCorrer.setVisibility(View.VISIBLE);

        }else{
            aCorrer.setVisibility(View.GONE);
        }
        Typeface type = Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/digit.ttf");
        ((TextView) rootView.findViewById(R.id.txt_hora)).setTypeface(type);
        ((TextView) rootView.findViewById(R.id.txt_minutos)).setTypeface(type);
        ((TextView) rootView.findViewById(R.id.txt_segundos)).setTypeface(type);
        ((TextView) rootView.findViewById(R.id.txt_millisec)).setTypeface(type);


        aCorrer.setOnClickListener(this);
        aCorrer.setOnTouchListener(this);

        return rootView;
    }


    @Override
    public void onClick(View v) {
       Intent i = new Intent(this.getActivity(), TemporizadorActivity.class);
        i.putExtra(Carrera.class.getSimpleName(), this.carrera);
        startActivity(i);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                this.aCorrer.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                return true;
            }
            case MotionEvent.ACTION_UP: {
                this.aCorrer.getBackground().clearColorFilter();
                return true;
            }
            case MotionEvent.ACTION_CANCEL:{
                this.aCorrer.getBackground().clearColorFilter();
                return true;
            }
        }
        return true;
    }
}
