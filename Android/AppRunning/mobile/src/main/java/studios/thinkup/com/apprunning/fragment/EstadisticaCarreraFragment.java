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

import java.util.Calendar;

import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;
import studios.thinkup.com.apprunning.view.IconTextView;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.TemporizadorActivity;
import studios.thinkup.com.apprunning.model.EstadoCarrera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.TypefaceProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstadisticaCarreraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstadisticaCarreraFragment extends Fragment implements View.OnClickListener, IUsuarioCarreraObserver {
    private IUsuarioCarreraObservable usuarioObservable;
    public EstadisticaCarreraFragment() {
        // Required empty public constructor
    }

    public void setObservable(IUsuarioCarreraObservable usuarioObservable) {
        this.usuarioObservable = usuarioObservable;
    }

    public static EstadisticaCarreraFragment newInstance(int idCarrera) {
        EstadisticaCarreraFragment fragment = new EstadisticaCarreraFragment();
        Bundle args = new Bundle();
        args.putInt(UsuarioCarrera.class.getSimpleName(), idCarrera);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_estadistica_carrera, container, false);


        initView(rootView);
             final LinearLayout aCorrer = (LinearLayout)rootView.findViewById(R.id.lb_a_correr);
        
        aCorrer.setOnTouchListener(new View.OnTouchListener() {
            private static final int MAX_CLICK_DURATION = 200;
            private long startClickTime;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN: {
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        aCorrer.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        aCorrer.getBackground().clearColorFilter();
                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                        if (clickDuration < MAX_CLICK_DURATION) {
                            EstadisticaCarreraFragment.this.onClickACorrer();
                        }
                        return true;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        aCorrer.getBackground().clearColorFilter();
                        return true;
                    }

                }
                return true;
            }

        });

        return rootView;
    }

    private void initView(View rootView) {
        if (this.usuarioObservable == null) {
            this.usuarioObservable = (IUsuarioCarreraObservable) this.getActivity();
        }
        IconTextView editar = (IconTextView)rootView.findViewById(R.id.icon_edit_time);

        editar.setOnClickListener(this);
        IconTextView cancelar = (IconTextView)rootView.findViewById(R.id.icon_cancel);
        cancelar.setOnClickListener(this);
        updateEstadoEdicionTiempo(rootView);
        actualizarValores(rootView);
        Typeface type = TypefaceProvider.getInstance(this.getActivity()).getTypeface(TypefaceProvider.DIGIT);
        TextView hsText = (TextView) rootView.findViewById(R.id.txt_hs);
        TextView puntos = (TextView) rootView.findViewById(R.id.txt_1);
        puntos.setTypeface(type);
        puntos = (TextView) rootView.findViewById(R.id.txt_2);
        puntos.setTypeface(type);
        TextView secText = (TextView) rootView.findViewById(R.id.txt_sec);
        TextView minText = (TextView) rootView.findViewById(R.id.txt_min);

        IconTextView plusHsText = (IconTextView) rootView.findViewById(R.id.icon_plus_hs);
        IconTextView plusMinText = (IconTextView) rootView.findViewById(R.id.icon_plus_min);
        IconTextView plusSecText = (IconTextView) rootView.findViewById(R.id.icon_plus_sec);
        IconTextView minusHsText = (IconTextView) rootView.findViewById(R.id.icon_minus_hs);
        IconTextView minusMinText = (IconTextView) rootView.findViewById(R.id.icon_minus_min);
        IconTextView minusSecText = (IconTextView) rootView.findViewById(R.id.icon_minus_sec);

        plusHsText.setOnTouchListener(new AddTimeListener(hsText, 99, false));
        plusMinText.setOnTouchListener(new AddTimeListener(minText));
        plusSecText.setOnTouchListener(new AddTimeListener(secText));
        minusHsText.setOnTouchListener(new AddTimeListener(hsText, 99, true));
        minusMinText.setOnTouchListener(new AddTimeListener(minText, 60, true));
        minusSecText.setOnTouchListener(new AddTimeListener(secText, 60, true));

        hsText.setTypeface(type);
        secText.setTypeface(type);
        minText.setTypeface(type);


    }

    private void updateEstadoEdicionTiempo(View view) {
        if (this.usuarioObservable == null) {
            this.usuarioObservable = (IUsuarioCarreraObservable) this.getActivity();
        }
        if(view!=null) {
            IconTextView editar = (IconTextView) view.findViewById(R.id.icon_edit_time);
            LinearLayout aCorrer = (LinearLayout) view.findViewById(R.id.lb_a_correr);

            editar.setVisibility(View.INVISIBLE);
            aCorrer.setVisibility(View.GONE);
            if (this.usuarioObservable.getUsuarioCarrera() != null) {
                if (this.usuarioObservable.getUsuarioCarrera().isAnotado()) {
                    if (!this.usuarioObservable.getUsuarioCarrera().isCorrida() &&
                            this.usuarioObservable.getUsuarioCarrera().getTiempo() <= 0) {
                        aCorrer.setVisibility(View.VISIBLE);
                    }
                    editar.setVisibility(View.VISIBLE);
                }
            }
        }
    }

    private void updateEstadoEdicionTiempo() {
       if(this.getView()!=null){
           this.updateEstadoEdicionTiempo(this.getView());
       }
    }


    protected void onClickACorrer() {
        Intent i = new Intent(this.getActivity().getApplicationContext(), TemporizadorActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("carrera",this.usuarioObservable.getUsuarioCarrera());
        i.putExtras(b);
        this.getActivity().startActivity(i);
        this.getActivity().finish();

    }


    @Override
    public void onClick(View v) {

        IconTextView plusHsText = (IconTextView) getView().findViewById(R.id.icon_plus_hs);
        IconTextView plusMinText = (IconTextView) getView().findViewById(R.id.icon_plus_min);
        IconTextView plusSecText = (IconTextView) getView().findViewById(R.id.icon_plus_sec);
        IconTextView minusHsText = (IconTextView) getView().findViewById(R.id.icon_minus_hs);
        IconTextView minusMinText = (IconTextView) getView().findViewById(R.id.icon_minus_min);
        IconTextView minusSecText = (IconTextView) getView().findViewById(R.id.icon_minus_sec);
        IconTextView editIcon = (IconTextView)getView().findViewById(R.id.icon_edit_time);
        IconTextView cancelIcon = (IconTextView)getView().findViewById(R.id.icon_cancel);
        LinearLayout aCorrer = (LinearLayout)getView().findViewById(R.id.lb_a_correr);

        if(v.getId()== R.id.icon_cancel){
            editIcon.setPressed(false);
            cancelIcon.setPressed(true);
            actualizarValores(this.getView());
            updateEstadoEdicionTiempo();

        }else{
            if(plusHsText.getVisibility() == View.VISIBLE){
                TextView hs = (TextView) getView().findViewById(R.id.txt_hs);
                TextView min = (TextView) getView().findViewById(R.id.txt_min);
                TextView sec = (TextView) getView().findViewById(R.id.txt_sec);

                long tiempo = Integer.valueOf(hs.getText().toString()) * 3600000;
                tiempo += Integer.valueOf(min.getText().toString()) * 60000;
                tiempo += Integer.valueOf(sec.getText().toString()) * 1000;
                this.usuarioObservable.getUsuarioCarrera().setTiempo(tiempo);
                this.usuarioObservable.updateUsuarioCarrera();
                updateEstadoEdicionTiempo();
                editIcon.setPressed(false);
            }
        }
        if(!editIcon.isPressed()){
            plusHsText.setVisibility(View.INVISIBLE);
            plusMinText.setVisibility(View.INVISIBLE);
            plusSecText.setVisibility(View.INVISIBLE);
            minusHsText.setVisibility(View.INVISIBLE);
            minusMinText.setVisibility(View.INVISIBLE);
            minusSecText.setVisibility(View.INVISIBLE);
            editIcon.setText(getString(R.string.icon_edit));
            cancelIcon.setVisibility(View.GONE);
            editIcon.setPressed(false);
            aCorrer.setVisibility(View.GONE);

        }else{
            plusHsText.setVisibility(View.VISIBLE);
            plusMinText.setVisibility(View.VISIBLE);
            plusSecText.setVisibility(View.VISIBLE);
            minusHsText.setVisibility(View.VISIBLE);
            minusMinText.setVisibility(View.VISIBLE);
            minusSecText.setVisibility(View.VISIBLE);
            editIcon.setText(getString(R.string.icon_save));
            cancelIcon.setVisibility(View.VISIBLE);
            editIcon.setPressed(true);

        }


    }

    private void actualizarValores(View view) {
        if (this.usuarioObservable.getUsuarioCarrera().getTiempo() >= 0) {
            int h = (int) (this.usuarioObservable.getUsuarioCarrera().getTiempo() / 3600000);
            int m = (int) (this.usuarioObservable.getUsuarioCarrera().getTiempo() - h * 3600000) / 60000;
            int s = (int) (this.usuarioObservable.getUsuarioCarrera().getTiempo() - h * 3600000 - m * 60000) / 1000;

            TextView hsText = (TextView) view.findViewById(R.id.txt_hs);
            TextView secText = (TextView) view.findViewById(R.id.txt_sec);
            TextView minText = (TextView) view.findViewById(R.id.txt_min);
            toStringNum(m, minText);
            toStringNum(h, hsText);
            toStringNum(s, secText);
            updateEstadoEdicionTiempo();
        }
    }

    private void toStringNum(int val, TextView minText) {
        if(val <10){
            minText.setText("0" + String.valueOf(val));
        }else{
            minText.setText(String.valueOf(val));
        }
    }


    @Override
    public void actuliazarUsuarioCarrera(UsuarioCarrera usuario, EstadoCarrera estado) {
        updateEstadoEdicionTiempo();
        if (getView() != null) {
            actualizarValores(getView());
        }

    }

    private class AddTimeListener implements View.OnTouchListener {
        private TextView textToUpdate;
        private Integer topLimit = 60;
        private Integer laps = 1;
        private Integer initLaps = 1;
        private Integer count = 0;
        private Integer val;

        public AddTimeListener(TextView txtToUpdate) {
            init(txtToUpdate, 60, false);

        }

        public AddTimeListener(TextView txtToUpdate, Integer topLimit) {
            init(txtToUpdate, topLimit, false);
        }

        public AddTimeListener(TextView txtToUpdate, Integer topLimit, Boolean isNegative) {
            init(txtToUpdate, topLimit, isNegative);
        }

        private void init(TextView txtToUpdate, Integer topLimit, Boolean isNegative) {
            this.textToUpdate = txtToUpdate;
            this.val = Integer.valueOf(txtToUpdate.getText().toString());
            this.topLimit = topLimit;
            this.laps = isNegative ? -1 : 1;
            this.initLaps = this.laps;
            this.count = 0;
        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            this.val = Integer.valueOf(this.textToUpdate.getText().toString());
            switch (event.getAction() & MotionEvent.ACTION_MASK) {

                case MotionEvent.ACTION_DOWN:
                    v.setPressed(true);
                    if (this.val + laps <= topLimit && this.val + laps >= 0) {
                        this.val += laps;
                        toStringNum(this.val,this.textToUpdate);
                        count++;
                    }
                    if (count > 7) {
                        laps = laps * 2;
                        count = 0;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_OUTSIDE:
                case MotionEvent.ACTION_CANCEL:
                    v.setPressed(false);
                    count = 0;
                    laps = initLaps;
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
            }

            return true;
        }
    }


}
