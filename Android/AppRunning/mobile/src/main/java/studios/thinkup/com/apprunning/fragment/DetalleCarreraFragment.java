package studios.thinkup.com.apprunning.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.Carrera;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;

/**
 * Created by fcostazini on 21/05/2015.
 */
public class DetalleCarreraFragment extends Fragment {
    public static final String ARG_SECTION_NAME = "section_name";
    public static final String ARG_SECTION_IMAGE = "section_image";
private Carrera carrera;
    public static DetalleCarreraFragment newInstance(Carrera carrera){
        DetalleCarreraFragment fragment = new DetalleCarreraFragment();
        Bundle args = new Bundle();
        args.putSerializable(Carrera.class.getSimpleName(), carrera);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalle_carrera, container, false);
        if (getArguments() != null) {
            this.carrera = (Carrera) getArguments().getSerializable(Carrera.class.getSimpleName());
        }
        if(this.carrera == null){
            //Sin RESULTADO
           return rootView;
        }
        TextView txtNombre = (TextView) rootView.findViewById(R.id.txt_nombre_carrera);
        txtNombre.setText(this.carrera.getNombre());

        return rootView;
    }
}
