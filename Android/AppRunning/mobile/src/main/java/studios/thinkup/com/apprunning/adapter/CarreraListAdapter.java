package studios.thinkup.com.apprunning.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.CarreraCabecera;

/**
 * Created by fcostazini on 22/05/2015.
 * <p/>
 * Adaptador para mostrar los item Categoria del ListView SubcategoriaActivity
 */
public class CarreraListAdapter extends BaseAdapter {
    private List<CarreraCabecera> carreras;
    private Context context;

    public CarreraListAdapter(Context context, List<CarreraCabecera> carreras) {
        this.carreras = carreras;
        this.context = context;
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

        View v = convertView;
        ViewHolder viewHolder = new ViewHolder();
        if (v == null) {
            LayoutInflater mInflater = (LayoutInflater) this.getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            v = mInflater.inflate(R.layout.carrera_item, null);
            viewHolder.nombre = (TextView) v.findViewById(R.id.txt_nombre_carrera);
            viewHolder.fecha = (TextView) v.findViewById(R.id.txt_fecha);
            viewHolder.corrida = (ImageView) v.findViewById(R.id.img_corrida);
            viewHolder.meGusta = (ImageView) v.findViewById(R.id.img_favorito);
            viewHolder.anotado = (ImageView) v.findViewById(R.id.img_anotado_carrera);
            viewHolder.logo = (ImageView) v.findViewById(R.id.img_logo_carrera);
            viewHolder.distancia = (TextView) v.findViewById(R.id.txt_distancia);
            viewHolder.descripcion = (TextView) v.findViewById(R.id.txt_descripcion);
            v.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) v.getTag();
        }

        CarreraCabecera p = (CarreraCabecera)getItem(position);

        if (p != null) {


            if(p.getNombre()!=null){
                viewHolder.nombre.setText(p.getNombre());
            }
            if(p.getDescripcion() !=null){
                viewHolder.descripcion.setText(p.getDescripcion());
            }
            if(p.getDistancia() !=null){
                viewHolder.distancia.setText(p.getDistancia() + " Km");
            }
            if(p.getFechaInicio()!=null){

                SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                viewHolder.fecha.setText(sf.format(p.getFechaInicio()));
            }

            if(p.isEstoyInscripto()){
                viewHolder.anotado.getDrawable().setAlpha(255);
            }else{
                viewHolder.anotado.getDrawable().setAlpha(45);

            }

            if(p.isFueCorrida()){
                viewHolder.corrida.getDrawable().setAlpha(255);
            }else{
                viewHolder.corrida.getDrawable().setAlpha(45);
            }
            if(p.isMeGusta()){
                viewHolder.meGusta.getDrawable().setAlpha(255);
            }else{
                viewHolder.meGusta.getDrawable().setAlpha(45);
            }
            if(p.getUrlImage()!=null){
                Ion.with( viewHolder.logo)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .load(p.getUrlImage());
            }
        }
            //nombre.setText(p.getNombre() + " (" + p.getCantidad().toString() + ")");


        return v;
    }

    private class ViewHolder{
        TextView nombre;
        TextView fecha;
        ImageView corrida;
        ImageView meGusta;
        ImageView anotado;
        ImageView logo;
        TextView distancia;
        TextView descripcion;
    }
}
