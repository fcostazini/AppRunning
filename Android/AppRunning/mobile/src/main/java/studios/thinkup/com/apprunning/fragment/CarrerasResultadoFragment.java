package studios.thinkup.com.apprunning.fragment;

import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import studios.thinkup.com.apprunning.DetalleCarreraActivity;

import studios.thinkup.com.apprunning.adapter.CarreraListAdapter;
import studios.thinkup.com.apprunning.model.Carrera;
import studios.thinkup.com.apprunning.model.CarreraCabecera;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.provider.CarrerasProvider;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * interface.
 */
public class CarrerasResultadoFragment extends ListFragment {


    private Filtro filtro;
    private CarrerasProvider carrerasProvider;


    // TODO: Rename and change types of parameters
    public static CarrerasResultadoFragment newInstance(Filtro filtro) {
        CarrerasResultadoFragment fragment = new CarrerasResultadoFragment();
        Bundle args = new Bundle();
        args.putSerializable(Filtro.class.getSimpleName(), filtro);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CarrerasResultadoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.carrerasProvider = new CarrerasProvider(this.getActivity());

        if (getArguments() != null) {
            filtro = (Filtro) getArguments().getSerializable(Filtro.class.getSimpleName());
        }
        if(this.filtro == null){
            filtro = new Filtro(((RunningApplication) this.getActivity().getApplication()).getDefaultSettings());
        }
        List<CarreraCabecera> resultados = carrerasProvider.getCarreras(this.filtro);
        // TODO: Change Adapter to display your content
        setListAdapter(new CarreraListAdapter(this.getActivity(),
                android.R.layout.simple_list_item_1,resultados));


    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        CarreraCabecera c = (CarreraCabecera)l.getItemAtPosition(position);
        Intent intent = new Intent(this.getActivity(), DetalleCarreraActivity.class);
        Bundle b = new Bundle();
        b.putInt(Carrera.ID, c.getCodigoCarrera()); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);

    }

}
