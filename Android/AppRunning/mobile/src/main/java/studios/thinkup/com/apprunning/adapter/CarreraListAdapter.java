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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

    public CarreraListAdapter(Context context, int textViewResourceId, List<CarreraCabecera> carreras) {
        this.carreras = carreras;
        this.context = context;
    }

    public void sortData(Comparator<CarreraCabecera> comparator) {
        Collections.sort(carreras, comparator);
        this.notifyDataSetChanged();
    }

    public List<CarreraCabecera> getCategorias() {
        return carreras;
    }

    public void setCategorias(List<CarreraCabecera> carreras) {
        this.carreras = carreras;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
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

        if (v == null) {
            LayoutInflater mInflater = (LayoutInflater) this.getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            v = mInflater.inflate(R.layout.carrera_item, parent,false);
        }

        CarreraCabecera p = this.carreras.get(position);

        if (p != null) {
            TextView nombre = (TextView) v.findViewById( R.id.txt_nombre_carrera);
            TextView fecha = (TextView) v.findViewById(R.id.txt_fecha);
            ImageView estado = (ImageView) v.findViewById(R.id.img_estado_carrera);
            ImageView logo = (ImageView) v.findViewById(R.id.img_logo_carrera);
            TextView distancia = (TextView) v.findViewById(R.id.txt_distancia);
            TextView descripcion = (TextView) v.findViewById(R.id.txt_descripcion);

            if(p.getNombre()!=null){
                nombre.setText(p.getNombre());
            }
            if(p.getDescripcion() !=null){
                descripcion.setText(p.getDescripcion());
            }
            if(p.getDistancia() !=null){
                distancia.setText(p.getDistancia());
            }
            if(p.getFechaInicio()!=null){

                SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy");
                fecha.setText(sf.format(p.getFechaInicio()));
            }

            if(p.getEstadoCarrera()!=null){
                switch (p.getEstadoCarrera()){
                    case CORRIDA:
                        estado.setImageResource(android.R.drawable.btn_star_big_on);
                        break;
                    case INSCRIPTO:
                        estado.setImageResource(android.R.drawable.btn_star_big_off);
                        break;
                    case ME_GUSTA:
                        estado.setImageResource(android.R.drawable.btn_plus);
                        break;
                }
            }

            if(p.getUrlImage()!=null){
                Ion.with(logo)
                        .placeholder(R.drawable.common_ic_googleplayservices)
                        .error(R.drawable.common_ic_googleplayservices)
                        .load(p.getUrlImage());
            }

            //nombre.setText(p.getNombre() + " (" + p.getCantidad().toString() + ")");
        }

        return v;
    }

}
