package studios.thinkup.com.apprunning.fragment;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.DetalleCarreraActivity;
import studios.thinkup.com.apprunning.adapter.CarreraListAdapter;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.CarreraCabeceraProvider;
import studios.thinkup.com.apprunning.provider.restProviders.CarreraCabeceraService;

/**
 * Created by fcostazini on 26/05/2015.
 * Fragmento Recomendados
 */
public class RecomendadosFragment extends FilteredFragment implements CarreraCabeceraService.OnResultsHandler {


    public static RecomendadosFragment newInstance() {
        RecomendadosFragment fragment = new RecomendadosFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public RecomendadosFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getFiltro().setRecomendadas(true);
        CarreraCabeceraService cp = new CarreraCabeceraService(this, this.getActivity());
        cp.execute(this.getFiltro());

    }

    @Override
    public String getIdFragment() {
        return "recomendados";
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        CarreraCabecera c = (CarreraCabecera) l.getItemAtPosition(position);
        Intent intent = new Intent(this.getActivity(), DetalleCarreraActivity.class);
        Bundle b = new Bundle();
        b.putInt(UsuarioCarrera.class.getSimpleName(), c.getCodigoCarrera()); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);

    }


    @Override
    public void actualizarResultados(List<CarreraCabecera> resultados) {
        ListAdapter adapter = new CarreraListAdapter(this.getActivity(), resultados);
        setListAdapter(adapter);
    }

}
