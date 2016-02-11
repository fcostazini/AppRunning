package studios.thinkup.com.apprunning.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.google.common.base.Utf8;
import com.squareup.picasso.Picasso;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.EstadoCarrera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;

/**
 * Created by fcostazini on 21/05/2015.
 * Detalle de la carrera
 */
public class DetalleCarreraFragment extends Fragment implements FacebookService.IPostHandler,IUsuarioCarreraObserver{

    private IUsuarioCarreraObservable usuarioObservable;
    private FacebookService fService;

    public void setUsuarioObsercable(IUsuarioCarreraObservable usuarioObservable) {
        this.usuarioObservable = usuarioObservable;

    }


    private void publicar() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        Bundle b = new Bundle();
 //                       b.putString(SocialNetwork.BUNDLE_NAME,usuarioObservable.getUsuarioCarrera().getCarrera().getNombre());
                        b.putString(SocialNetwork.BUNDLE_PICTURE, usuarioObservable.getUsuarioCarrera().getCarrera().getUrlImagen());
                        b.putString(SocialNetwork.BUNDLE_LINK, "http://play.google.com/store/apps/details?id=studios.thinkup.com.apprunning");
                        fService.setPostHandler(DetalleCarreraFragment.this);
                        fService.post(b);
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:

                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setMessage("Querés compartir en Facebook?").setPositiveButton("Si", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).setTitle("Compartir").setIcon(R.drawable.ic_amigos).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalle_carrera, container, false);
        setHasOptionsMenu(true);
        if (this.usuarioObservable == null) {
            this.usuarioObservable = (IUsuarioCarreraObservable) this.getActivity();
        }
        updateView(rootView);
        return rootView;

    }

    private boolean updateView(View rootView) {
        TextView txtNombre = (TextView) rootView.findViewById(R.id.txt_nombre_usuario);
        if (this.usuarioObservable.getUsuarioCarrera() == null) {
            //Sin RESULTADO
            txtNombre.setText(this.getActivity().getResources().getString(R.string.sin_resultados));
            return true;
        }

        //SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        if (this.usuarioObservable.getUsuarioCarrera().getUrlImage() != null && !this.usuarioObservable.getUsuarioCarrera().getUrlImage().isEmpty()) {
            ImageView logo = (ImageView) rootView.findViewById(R.id.img_logo);
            Picasso.with(this.getActivity()).load(this.usuarioObservable.getUsuarioCarrera().getUrlImage())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(logo);
        }

        txtNombre.setText(this.usuarioObservable.getUsuarioCarrera().getNombre());
        TextView fecha = (TextView) rootView.findViewById(R.id.txt_fecha_largada);
        if (this.usuarioObservable.getUsuarioCarrera().getFechaInicio() != null) {
            Date d = this.usuarioObservable.getUsuarioCarrera().getFechaInicio();
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            if (this.usuarioObservable.getUsuarioCarrera().getHora() != null && !this.usuarioObservable.getUsuarioCarrera().getHora().isEmpty()) {


                fecha.setText(sf.format(d) + "   " + this.usuarioObservable.getUsuarioCarrera().getHora().substring(0,5) + " hs.");
            } else {
                fecha.setText(sf.format(d));
            }
        }

        TextView descripcion = (TextView) rootView.findViewById(R.id.txt_descripcion);
        descripcion.setText(this.usuarioObservable.getUsuarioCarrera().getDescripcion());

        TextView distancia = (TextView) rootView.findViewById(R.id.txt_distancia);
        distancia.setText(this.usuarioObservable.getUsuarioCarrera().getDistancias() + " Km");


        TextView genero = (TextView) rootView.findViewById(R.id.txt_modalidad);
        genero.setText(this.usuarioObservable.getUsuarioCarrera().getCarrera().getModalidades());

        TextView direccion = (TextView) rootView.findViewById(R.id.txt_direccion);

        TextView masInfo = (TextView) rootView.findViewById(R.id.lbl_mas_info);
        masInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DetalleCarreraFragment.this.usuarioObservable.getUsuarioCarrera().getUrlWeb() != null &&
                        !DetalleCarreraFragment.this.usuarioObservable.getUsuarioCarrera().getUrlWeb().isEmpty()) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(DetalleCarreraFragment.this.usuarioObservable.getUsuarioCarrera().getUrlWeb()));
                    startActivity(i);
                }


            }
        });


        direccion.setText(this.usuarioObservable.getUsuarioCarrera().getFullDireccion());
        TextView lblInscripto = (TextView)rootView.findViewById(R.id.lbl_inscripto_en);
        TextView txtInscripto = (TextView)rootView.findViewById(R.id.txt_inscripto_en);
        if(this.usuarioObservable.getUsuarioCarrera().getDistancias().contains("/") &&
                this.usuarioObservable.getUsuarioCarrera().isAnotado()){

            lblInscripto.setVisibility(View.VISIBLE);

            txtInscripto.setVisibility(View.VISIBLE);
            txtInscripto.setText(usuarioObservable.getUsuarioCarrera().getDistancia() + " Km");
        }else{
            lblInscripto.setVisibility(View.GONE);

            txtInscripto.setVisibility(View.GONE);
            txtInscripto.setText("");
        }
        fService = new FacebookService(this);
        return false;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(int i, String s, String s1, Object o) {

    }

    /**
     * Actualiza un usuario en base a un cambio de estado
     *
     * @param usuario .
     * @param estado  .
     */
    @Override
    public void actuliazarUsuarioCarrera(UsuarioCarrera usuario, EstadoCarrera estado) {
        if(estado == null){
            if(this.getView()!=null)
                this.updateView(this.getView());
        }else if(estado.equals(EstadoCarrera.ANOTADO)||estado.equals(EstadoCarrera.CORRIDA)){
            publicar();
        }
    }
}
