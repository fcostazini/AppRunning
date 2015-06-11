package studios.thinkup.com.apprunning.fragment;


import android.app.Dialog;
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

import studios.thinkup.com.apprunning.IconTextView;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.TemporizadorActivity;
import studios.thinkup.com.apprunning.components.CustomNumberPickerView;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.CarrerasProvider;
import studios.thinkup.com.apprunning.provider.ICarrerasProvider;
import studios.thinkup.com.apprunning.provider.TypefaceProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstadisticaCarreraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstadisticaCarreraFragment extends Fragment implements View.OnClickListener {
    private LinearLayout aCorrer;
    private UsuarioCarrera carrera;
    private Dialog dialog;
    private TextView tiempo;
    private IconTextView editar;

    public static EstadisticaCarreraFragment newInstance(long idCarrera) {
        EstadisticaCarreraFragment fragment = new EstadisticaCarreraFragment();
        Bundle args = new Bundle();
        args.putLong(UsuarioCarrera.class.getSimpleName(), idCarrera);
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
            long id =  getArguments().getLong(UsuarioCarrera.class.getSimpleName());
            ICarrerasProvider cp = new CarrerasProvider();
            this.carrera = cp.getByIdCarrera(id);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_estadistica_carrera, container, false);
        initView(rootView);



        //aCorrer.setOnClickListener(this);
        aCorrer.setOnTouchListener(new View.OnTouchListener() {
            private static final int MAX_CLICK_DURATION = 200;
            private long startClickTime;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN: {
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        EstadisticaCarreraFragment.this.aCorrer.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                        return true;
                    }
                    case MotionEvent.ACTION_UP: {
                        EstadisticaCarreraFragment.this.aCorrer.getBackground().clearColorFilter();
                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                        if (clickDuration < MAX_CLICK_DURATION) {
                            EstadisticaCarreraFragment.this.onClickACorrer();
                        }
                        return true;
                    }
                    case MotionEvent.ACTION_CANCEL: {
                        EstadisticaCarreraFragment.this.aCorrer.getBackground().clearColorFilter();
                        return true;
                    }

                }
                return true;
            }

        });

        return rootView;
    }

    private void initView(View rootView) {
        this.editar = (IconTextView) rootView.findViewById(R.id.icon_edit_time);

        this.aCorrer = (LinearLayout) rootView.findViewById(R.id.lb_a_correr);
        editar.setOnClickListener(this);
        if (this.carrera.isAnotado() && !this.carrera.isCorrida()) {
            aCorrer.setVisibility(View.VISIBLE);

        } else {
            aCorrer.setVisibility(View.GONE);
        }
        Typeface type = TypefaceProvider.getInstance(this.getActivity()).getTypeface(TypefaceProvider.DIGIT);
        this.tiempo = (TextView) rootView.findViewById(R.id.txt_tiempo);

        actualizarValores();
        tiempo.setTypeface(type);
    }


    protected void onClickACorrer() {
        Intent i = new Intent(this.getActivity(), TemporizadorActivity.class);
        i.putExtra(UsuarioCarrera.class.getSimpleName(), this.carrera.getId());
        this.getActivity().startActivity(i);
        this.getActivity().finish();

    }


    @Override
    public void onClick(View v) {
// custom dialog
        this.dialog = new Dialog(this.getActivity());
        dialog.setContentView(R.layout.time_picker_fragment);
        dialog.setTitle("Ingrese su tiempo");

        // set the custom dialog components - text, image and button
        CustomNumberPickerView hr = (CustomNumberPickerView) dialog.findViewById(R.id.np_hr);

        CustomNumberPickerView min = (CustomNumberPickerView) dialog.findViewById(R.id.np_min);
        CustomNumberPickerView sec = (CustomNumberPickerView) dialog.findViewById(R.id.np_sec);
        CustomNumberPickerView ms = (CustomNumberPickerView) dialog.findViewById(R.id.np_ms);

        if (this.carrera.getTiempo() > 0) {
            hr.setNumeroVal((int) (this.carrera.getTiempo() / 3600000));
            min.setNumeroVal((int) (this.carrera.getTiempo() - hr.getNumeroVal() * 3600000) / 60000);
            sec.setNumeroVal((int) (this.carrera.getTiempo() - hr.getNumeroVal() * 3600000 - min.getNumeroVal() * 60000) / 1000);
            ms.setNumeroVal((int) (this.carrera.getTiempo() - hr.getNumeroVal() * 3600000 - min.getNumeroVal() * 60000 - sec.getNumeroVal() * 1000) / 10);

        }


        IconTextView dialogOk = (IconTextView) dialog.findViewById(R.id.ic_save);
        IconTextView dialogCancel = (IconTextView) dialog.findViewById(R.id.ic_cancel);
        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // if button is clicked, close the custom dialog
        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int h = ((CustomNumberPickerView)dialog.findViewById(R.id.np_hr)).getNumeroVal();
                int m = ((CustomNumberPickerView)dialog.findViewById(R.id.np_min)).getNumeroVal();
                int s = ((CustomNumberPickerView)dialog.findViewById(R.id.np_sec)).getNumeroVal();
                int ms = ((CustomNumberPickerView)dialog.findViewById(R.id.np_ms)).getNumeroVal();
                long tiempo = ms + (s*1000) + (m*60000) + (h*3600000);
                EstadisticaCarreraFragment.this.carrera.setTiempo(tiempo);
                actualizarValores();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void actualizarValores() {
        if (this.carrera.getTiempo() > 0) {
            int h = (int) (this.carrera.getTiempo() / 3600000);
            int m = (int) (this.carrera.getTiempo() - h * 3600000) / 60000;
            int s = (int) (this.carrera.getTiempo() - h * 3600000 - m * 60000) / 1000;
            int ms = (int) (this.carrera.getTiempo() - h * 3600000 - m * 60000 - s * 1000) / 10;
            String tiempoStr = (h < 10 ? "0" + h : h + "");
            tiempoStr += ":" + (m < 10 ? "0" + m : m + "");
            tiempoStr += ":" + (s < 10 ? "0" + s : s + "");
            tiempoStr += ":" + (ms < 10 ? "0" + ms : ms + "");
            tiempo.setText(tiempoStr);
            aCorrer.setVisibility(View.GONE);
            editar.setVisibility(View.VISIBLE);
        }
    }


}
