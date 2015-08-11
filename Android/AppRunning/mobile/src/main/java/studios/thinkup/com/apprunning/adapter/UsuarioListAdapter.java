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

import java.util.List;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;

/**
 * Created by fcostazini on 22/05/2015.
 * <p/>
 * Adaptador para mostrar los item Categoria del ListView SubcategoriaActivity
 */
public class UsuarioListAdapter extends BaseAdapter {
    private List<UsuarioApp> carreras;
    private Context context;
    private LayoutInflater inflater;

    public UsuarioListAdapter(Activity context, List<UsuarioApp> carreras) {
        this.carreras = carreras;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

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
            convertView = inflater.inflate(R.layout.usuario_item, null);
            viewHolder.nombre = (TextView) convertView.findViewById(R.id.txt_nombre_usuario);
            viewHolder.email = (TextView) convertView.findViewById(R.id.lbl_email);
            viewHolder.perfil = (ImageView) convertView.findViewById(R.id.img_usuario);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();


        }
        UsuarioApp u = (UsuarioApp) getItem(position);
        if (u != null) {

            viewHolder.nombre.setText(u.getNick());
            viewHolder.email.setText(u.getEmail());
            Picasso.with(context).load(u.getFotoPerfil())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(viewHolder.perfil);
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView nombre;
        TextView email;
        ImageView perfil;

    }
}
