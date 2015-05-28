package studios.thinkup.com.apprunning.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Locale;

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

    public static DetalleCarreraFragment newInstance(Carrera carrera) {
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
        TextView txtNombre = (TextView) rootView.findViewById(R.id.txt_nombre_carrera);
        if (this.carrera == null) {
            //Sin RESULTADO
            txtNombre.setText(this.getActivity().getResources().getString(R.string.sin_resultados));
            return rootView;
        }

        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyyy", Locale.getDefault());
        if (this.carrera.getUrlImage() != null && !this.carrera.getUrlImage().isEmpty()) {
            ImageView logo = (ImageView) rootView.findViewById(R.id.img_logo);
            Ion.with(logo)
                    .placeholder(R.drawable.common_ic_googleplayservices)
                    .error(R.drawable.common_ic_googleplayservices)
                    .load(this.carrera.getUrlImage());
        }

        txtNombre.setText(this.carrera.getNombre());
        TextView fecha = (TextView)rootView.findViewById(R.id.txt_fecha_largada);
        fecha.setText(sf.format(this.carrera.getFechaInicio()));

        TextView descripcion = (TextView)rootView.findViewById(R.id.txt_descripcion);
        descripcion.setText(this.carrera.getDescripcion());

        TextView genero = (TextView)rootView.findViewById(R.id.txt_genero);
        genero.setText(this.carrera.getGenero().getNombre());

        TextView direccion = (TextView)rootView.findViewById(R.id.txt_direccion);
        direccion.setText(this.carrera.getDireccion());

        return rootView;
    }
}
