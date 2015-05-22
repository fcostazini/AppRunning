package studios.thinkup.com.apprunning.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.Categoria;

/**
 * Created by fcostazini on 22/05/2015.
 * <p/>
 * Adaptador para mostrar los item Categoria del ListView SubcategoriaActivity
 */
public class CategoriaListAdapter extends BaseAdapter {
    private List<Categoria> categorias;
    private Context context;

    public CategoriaListAdapter(Context context, int textViewResourceId, List<Categoria> categorias) {
        this.categorias = categorias;
        this.context = context;
    }

    public void sortData(Comparator<Categoria> comparator) {
        Collections.sort(categorias, comparator);
        this.notifyDataSetChanged();
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.categorias.size();
    }

    @Override
    public Object getItem(int i) {
       return this.categorias.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.categorias.get(i).getNombre().hashCode();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater mInflater = (LayoutInflater) this.getContext()
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            v = mInflater.inflate(R.layout.categoria_item, parent,false);
        }

        Categoria p = this.categorias.get(position);

        if (p != null) {
            TextView nombre = (TextView) v.findViewById(R.id.txt_categoria);
            nombre.setText(p.getNombre() + " (" + p.getCantidad().toString() + ")");
        }

        return v;
    }
}
