package studios.thinkup.com.apprunning.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
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
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * interface.
 */
public class CarrerasResultadoFragment extends FilteredFragment {
    public String getIdFragment() {
        return "TODOS";
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
        ICarreraCabeceraProvider carrerasProvider = new CarreraCabeceraProvider(this.getActivity());
        List<CarreraCabecera> resultados = carrerasProvider.getCarrerasByFiltro(this.getFiltro());
        setListAdapter(new CarreraListAdapter(this.getActivity(),
                resultados));


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

}
