package studios.thinkup.com.apprunning.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.SubcategoriaActivity;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.Subcategoria;

/**
 * Created by FaQ on 23/05/2015.
 *
 * Busqueda por categoría
 */
public class BusquedaCategoria extends Fragment implements View.OnClickListener{

    public static BusquedaCategoria newInstance() {
        BusquedaCategoria fragment = new BusquedaCategoria();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_buscar_carrera_categorias_iniciales, container, false);

        LinearLayout categoria = (LinearLayout)rootView.findViewById(R.id.btn_zona);
        categoria.setOnClickListener(this);
        categoria = (LinearLayout)rootView.findViewById(R.id.btn_distancia);
        categoria.setOnClickListener(this);
        categoria = (LinearLayout)rootView.findViewById(R.id.btn_genero);
        categoria.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent(this.getActivity(), SubcategoriaActivity.class);
        Filtro filtro = new Filtro(((RunningApplication)this.getActivity().getApplication()).getDefaultSettings());

        switch (view.getId()){

            case R.id.btn_zona:{
                filtro.setSubcategoria(Subcategoria.ZONA);
                break;

            }
            case R.id.btn_distancia:{
                filtro.setSubcategoria(Subcategoria.DISTANCIA);
                break;

            }

            default:{
                filtro.setSubcategoria(Subcategoria.GENERO);
                break;
            }
        }
        i.putExtra(Filtro.class.getSimpleName(),filtro);
        startActivity(i);
    }

}
