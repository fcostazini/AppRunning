package studios.thinkup.com.apprunning.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import studios.thinkup.com.apprunning.DetalleCarreraActivity;
import studios.thinkup.com.apprunning.adapter.CarreraListAdapter;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.CarreraCabeceraProvider;
import studios.thinkup.com.apprunning.provider.ICarreraCabeceraProvider;

/**
 * Created by fcostazini on 26/05/2015.
 * Fragmento Recomendados
 */
public class RecomendadosFragment extends ListFragment {

    private ICarreraCabeceraProvider carrerasProvider;
    private Filtro filtro;
    private ListAdapter adapter;



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
        this.carrerasProvider = new CarreraCabeceraProvider(this.getActivity());
        filtro = new Filtro(((RunningApplication)this.getActivity().getApplication()).getDefaultSettings());
        List<CarreraCabecera> resultados = carrerasProvider.getCarrerasByFiltro(filtro);
        adapter =new CarreraListAdapter(this.getActivity(),resultados);

        setListAdapter(adapter);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setDividerHeight(0);
        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Intent i = new Intent(this.getActivity(), DetalleCarreraActivity.class);
        CarreraCabecera c = (CarreraCabecera)l.getItemAtPosition(position);
        Intent intent = new Intent(this.getActivity(), DetalleCarreraActivity.class);
        Bundle b = new Bundle();
        b.putLong(UsuarioCarrera.class.getSimpleName(), c.getCodigoCarrera()); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(this.filtro != null){
            List<CarreraCabecera> resultados = this.carrerasProvider.getCarrerasByFiltro(this.filtro);
            this.adapter = new CarreraListAdapter(this.getActivity(),resultados);
            this.setListAdapter(this.adapter);
            this.getListView().invalidateViews();

        }
    }
}
