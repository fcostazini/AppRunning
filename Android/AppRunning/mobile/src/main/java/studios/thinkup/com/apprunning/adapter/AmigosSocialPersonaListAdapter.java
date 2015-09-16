package studios.thinkup.com.apprunning.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.gorbin.asne.core.persons.SocialPerson;
import com.squareup.picasso.Picasso;

import java.util.List;

import studios.thinkup.com.apprunning.R;

/**
 * Created by fcostazini on 16/09/2015.
 * Adapter para amigos
 */
public class AmigosSocialPersonaListAdapter extends BaseAdapter {
    private List<SocialPerson> amigos;
    private Context context;
    private LayoutInflater inflater;


    public AmigosSocialPersonaListAdapter(Activity context, List<SocialPerson> amigos) {
        this.amigos = amigos;
        this.context = context;
        this.inflater = LayoutInflater.from(context);


    }

    @Override
    public int getCount() {
        return amigos.size();
    }

    @Override
    public Object getItem(int i) {
        return amigos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return Long.valueOf(amigos.get(i).id);
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder viewHolder = new ViewHolder();
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.amigo_item, null);
            viewHolder.nombre = (TextView) convertView.findViewById(R.id.txt_nombre_usuario);
            viewHolder.foto = (ImageView) convertView.findViewById(R.id.img_usuario);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();


        }
        SocialPerson p = this.amigos.get(i);
        viewHolder.nombre.setText(p.name);
        Picasso.with(context).load(p.avatarURL)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).into(viewHolder.foto);

        return convertView;
    }

    private static class ViewHolder {
        TextView nombre;
        ImageView foto;

    }
}
