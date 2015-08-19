package studios.thinkup.com.apprunning.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.adapter.AmigosPagerAdapter;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;

/**
 * Created by Facundo on 11/08/2015.
 * Detalle de un amigo
 */

public class DetalleAmigoFragment extends Fragment implements AmigosPagerAdapter.IAmigoActualizableFragment {

    private AmigosDTO ua;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_mostar_usuario, container, false);
        initActivity(savedInstanceState);
        initView(rootView);
        return rootView;
    }

    private void initActivity(Bundle savedInstanceState) {
        this.ua = (AmigosDTO) restoreField(savedInstanceState, AmigosDTO.FIELD_ID);
        if (this.ua == null) {
            this.ua = new AmigosDTO();
        }

    }

    private Object restoreField(Bundle savedInstanceState, String name) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(name)) {
                return savedInstanceState.getSerializable(name);
            }
        } else {
            if (this.getActivity().getIntent().getExtras() != null) {
                if (this.getActivity().getIntent().getExtras().containsKey(name)) {
                    return this.getActivity().getIntent().getExtras().getSerializable(name);
                }
            }
        }
        return null;
    }

    private void initView(View rootView) {
        TextView txtNickname = (TextView) rootView.findViewById(R.id.txt_nick);
        txtNickname.setText(this.ua.getNick());
        ImageView perfil = (ImageView) rootView.findViewById(R.id.img_profile);
        if (this.ua.getUrlFoto() != null) {
            Picasso.with(this.getActivity()).load(this.ua.getUrlFoto())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher).into(perfil);

        }
        TextView txtGrupo = (TextView) rootView.findViewById(R.id.txt_grupo);
        txtGrupo.setText(ua.getGrupo());
        txtGrupo.setVisibility(View.VISIBLE);
        perfil.requestFocus();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.ua != null) {
            outState.putSerializable(AmigosDTO.FIELD_ID, this.ua);
        }
    }


    @Override
    public void actualizarAmigo(AmigosDTO amigo) {

    }
}
