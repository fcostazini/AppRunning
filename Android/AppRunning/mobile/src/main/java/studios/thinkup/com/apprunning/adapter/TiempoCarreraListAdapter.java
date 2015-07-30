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

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;

/**
 * Created by fcostazini on 22/05/2015.
 * <p/>
 * Adaptador para mostrar los item Categoria del ListView SubcategoriaActivity
 */
public class TiempoCarreraListAdapter extends BaseAdapter {
    private List<UsuarioCarrera> carreras;
    private Context context;
    private LayoutInflater inflater;
    private SimpleDateFormat sf;

    public TiempoCarreraListAdapter(Activity context, List<UsuarioCarrera> carreras) {
        this.carreras = carreras;
        this.context = context;
        this.inflater = context.getLayoutInflater();
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

            convertView = inflater.inflate(R.layout.carrera_tiempos, null);
            viewHolder.nombre = (TextView) convertView.findViewById(R.id.txt_nombre_carrera);
            viewHolder.distancia = (TextView) convertView.findViewById(R.id.txt_distancia);
            viewHolder.tiempo = (TextView) convertView.findViewById(R.id.txt_tiempo);
            viewHolder.tiempoPorDistancia = (TextView) convertView.findViewById(R.id.txt_tiempo_km);
            viewHolder.logo = (ImageView) convertView.findViewById(R.id.img_logo_carrera);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        UsuarioCarrera p = (UsuarioCarrera) getItem(position);

        if (p != null) {

            if (p.getNombre() != null) {
                viewHolder.nombre.setText(p.getNombre());
            }
            if (p.getDistancia() != null) {
                viewHolder.distancia.setText(p.getDistancia() + " Km");
            }
            if (p.getTiempo() != null) {

                viewHolder.tiempo.setText(getTimeString(p.getTiempo()));
            }
            if (p.getTiempo() != null && p.getTiempo() > 0 && p.getDistancia() != null && p.getDistancia() > 0) {

                viewHolder.tiempoPorDistancia.setText(getTimeString(p.getTiempo() / p.getDistancia()));
            } else {
                viewHolder.tiempoPorDistancia.setText(" - ");
            }

            if (p.getUrlImage() != null) {
                Picasso.with(context).load(p.getUrlImage())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher).into(viewHolder.logo);

            }
        }


        return convertView;
    }

    private String getTimeString(long tiempo) {
        long second = (tiempo / 1000) % 60;
        long minute = (tiempo / (1000 * 60)) % 60;
        long hour = (tiempo / (1000 * 60 * 60)) % 24;

        String str = "";
        if (hour > 0) {
            str += String.format("%02d", hour) + "h ";
        }
        if (minute > 0) {
            str += String.format("%02d", minute) + "m ";
        }
        if (second > 0) {
            str += String.format("%02d", second) + "s ";
        }

        return str;
    }

    private static class ViewHolder {
        TextView nombre;
        TextView tiempo;
        TextView distancia;
        TextView tiempoPorDistancia;
        ImageView logo;

    }
}
