package studios.thinkup.com.apprunning.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Locale;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;

/**
 * Created by fcostazini on 21/05/2015.
 * Detalle de la carrera
 */
public class DetalleCarreraFragment extends Fragment {
    private UsuarioCarrera carrera;

    public static DetalleCarreraFragment newInstance(int idCarrera) {
        DetalleCarreraFragment fragment = new DetalleCarreraFragment();
        Bundle args = new Bundle();
        args.putInt(UsuarioCarrera.class.getSimpleName(), idCarrera);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalle_carrera, container, false);
        IUsuarioCarreraProvider cp = new UsuarioCarreraProvider(this.getActivity(),((RunningApplication)this.getActivity().getApplication()).getUsuario().getId());
        if (getArguments() != null) {
            int id = getArguments().getInt(UsuarioCarrera.class.getSimpleName());
            this.carrera = cp.getByIdCarrera(id);
        }
        TextView txtNombre = (TextView) rootView.findViewById(R.id.txt_nombre_carrera);
        if (this.carrera == null) {
            //Sin RESULTADO
            txtNombre.setText(this.getActivity().getResources().getString(R.string.sin_resultados));
            return rootView;
        }

        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.getDefault());
        if (this.carrera.getUrlImage() != null && !this.carrera.getUrlImage().isEmpty()) {
            ImageView logo = (ImageView) rootView.findViewById(R.id.img_logo);
            Picasso.with(this.getActivity()).load(this.carrera.getUrlImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(logo);
        }

        txtNombre.setText(this.carrera.getNombre());
        TextView fecha = (TextView) rootView.findViewById(R.id.txt_fecha_largada);
        fecha.setText(sf.format(this.carrera.getFechaInicio()));

        TextView descripcion = (TextView) rootView.findViewById(R.id.txt_descripcion);
        descripcion.setText(this.carrera.getDescripcion());

        TextView distancia = (TextView) rootView.findViewById(R.id.txt_distancia);
        distancia.setText(this.carrera.getDistancia() + " Km");


        TextView genero = (TextView) rootView.findViewById(R.id.txt_modalidad);
        genero.setText(this.carrera.getModalidad());

        TextView direccion = (TextView) rootView.findViewById(R.id.txt_direccion);

        TextView masInfo = (TextView) rootView.findViewById(R.id.lbl_mas_info);
        masInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DetalleCarreraFragment.this.carrera.getUrlWeb()!=null &&
                        !DetalleCarreraFragment.this.carrera.getUrlWeb().isEmpty()){
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(DetalleCarreraFragment.this.carrera.getUrlWeb()));
                    startActivity(i);
                }


            }
        });
        direccion.setText(this.carrera.getFullDireccion());
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


}
