package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.entity.AmigosDTO;

/**
 * Created by fcostazini on 22/05/2015.
 * <p/>
 * Adaptador para mostrar los item Categoria del ListView SubcategoriaActivity
 */
public class AmigosListAdapter extends BaseAdapter implements Filterable {
    private List<AmigosDTO> amigos;
    private Context context;
    private LayoutInflater inflater;

    public AmigosListAdapter(Activity context, List<AmigosDTO> amigos) {
        this.amigos = amigos;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    public Context getContext() {
        return context;
    }

    @Override
    public int getCount() {
        return this.amigos.size();
    }

    @Override
    public Object getItem(int i) {
        return this.amigos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.amigos.get(i).getNick().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.amigo_item, null);
            viewHolder.nombre = (TextView) convertView.findViewById(R.id.txt_nombre_usuario);
            viewHolder.perfil = (ImageView) convertView.findViewById(R.id.img_usuario);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();


        }
        AmigosDTO u = (AmigosDTO) getItem(position);
        if (u != null) {

            viewHolder.nombre.setText(u.getNick());

            Picasso.with(context).load(u.getUrlFoto())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(viewHolder.perfil);
        }

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {


                List<AmigosDTO> filteredResults = getFilteredResults(constraint);

                FilterResults results = new FilterResults();
                results.values = filteredResults;

                return results;


            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                amigos = (List<AmigosDTO>) results.values;
                AmigosListAdapter.this.notifyDataSetChanged();
            }
        };

    }

    private List<AmigosDTO> getFilteredResults(CharSequence constraint) {
        List<AmigosDTO> resultados = new Vector<>();
        for(AmigosDTO u : amigos){
            if(u.getNick().startsWith(constraint.toString())){
                resultados.add(u);
            }
        }



        return resultados;
    }

    private static class ViewHolder {
        TextView nombre;
        ImageView perfil;

    }
}
