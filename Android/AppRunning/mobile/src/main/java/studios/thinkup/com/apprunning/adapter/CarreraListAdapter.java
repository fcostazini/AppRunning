package studios.thinkup.com.apprunning.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;

/**
 * Created by fcostazini on 22/05/2015.
 * <p/>
 * Adaptador para mostrar los item Categoria del ListView SubcategoriaActivity
 */
public class CarreraListAdapter extends BaseAdapter {
    private List<CarreraCabecera> carreras;
    private Context context;
    private LayoutInflater inflater;

    public CarreraListAdapter(Context context, List<CarreraCabecera> carreras) {
        this.carreras = carreras;
        this.context = context;
        this.inflater = (LayoutInflater) this.getContext()
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return this.carreras.size();
    }

    @Override
    public Object getItem(int i) {
        return this.carreras.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.carreras.get(i).getNombre().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.carrera_item, null);
            viewHolder.nombre = (TextView) convertView.findViewById(R.id.txt_nombre_carrera);
            viewHolder.zona = (TextView) convertView.findViewById(R.id.txt_zona);
            viewHolder.fecha = (TextView) convertView.findViewById(R.id.txt_fecha);
            viewHolder.corrida = (ImageView) convertView.findViewById(R.id.img_corrida);
            viewHolder.meGusta = (ImageView) convertView.findViewById(R.id.img_favorito);
            viewHolder.anotado = (ImageView) convertView.findViewById(R.id.img_anotado_carrera);
            viewHolder.logo = (ImageView) convertView.findViewById(R.id.img_logo_carrera);
            viewHolder.distancia = (TextView) convertView.findViewById(R.id.txt_distancia);
           // viewHolder.descripcion = (TextView) convertView.findViewById(R.id.txt_descripcion);
            viewHolder.corrida.setImageResource(R.drawable.ic_no_corrida);
            viewHolder.meGusta.setImageResource(R.drawable.ic_no_me_gusta);
            viewHolder.anotado.setImageResource(R.drawable.ic_no_anotado);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.corrida.setImageResource(R.drawable.ic_no_corrida);
            viewHolder.meGusta.setImageResource(R.drawable.ic_no_me_gusta);
            viewHolder.anotado.setImageResource(R.drawable.ic_no_anotado);

        }

        CarreraCabecera p = (CarreraCabecera) getItem(position);

        if (p != null) {

            if (p.getNombre() != null) {
                viewHolder.nombre.setText(p.getNombre());
            }
            if(p.getZona()!= null ){
                viewHolder.zona.setText(p.getZona());
            }
           /* if (p.getDescripcion() != null) {
                viewHolder.descripcion.setText(p.getDescripcion());
            }*/
            if (p.getDistancia() != null) {
                viewHolder.distancia.setText(p.getDistancia() + " Km");
            }
            if (p.getFechaInicio() != null) {
                SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                viewHolder.fecha.setText(sf.format(p.getFechaInicio()));
            }

            if (p.isEstoyInscripto()) {
                viewHolder.anotado.setImageResource(R.drawable.ic_anotado);
            }else{
                viewHolder.anotado.setImageResource(R.drawable.ic_no_anotado);
            }

            if (p.isFueCorrida()) {
                viewHolder.corrida.setImageResource(R.drawable.ic_corrida);
            }else{
                viewHolder.corrida.setImageResource(R.drawable.ic_no_corrida);
            }

            if (p.isMeGusta()) {
                viewHolder.meGusta.setImageResource(R.drawable.ic_me_gusta);
            }else{
                viewHolder.meGusta.setImageResource(R.drawable.ic_no_me_gusta);
            }
            if (p.getUrlImage() != null) {
                Picasso.with(context).load(p.getUrlImage())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher).into(viewHolder.logo);

            }
        }


        return convertView;
    }

    private static class ViewHolder {
        TextView nombre;
        TextView zona;
        TextView fecha;
        ImageView corrida;
        ImageView meGusta;
        ImageView anotado;
        ImageView logo;
        TextView distancia;
        TextView descripcion;
    }
}
