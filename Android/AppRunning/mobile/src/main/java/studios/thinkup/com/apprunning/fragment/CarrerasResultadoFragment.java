package studios.thinkup.com.apprunning.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import studios.thinkup.com.apprunning.DetalleCarreraActivity;
import studios.thinkup.com.apprunning.adapter.CarreraListAdapter;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.restProviders.CarreraCabeceraService;
import studios.thinkup.com.apprunning.provider.restProviders.OnSingleResultHandler;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioCarreraService;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * interface.
 */
public class CarrerasResultadoFragment extends FilteredFragment implements CarreraCabeceraService.OnResultsHandler, OnSingleResultHandler<UsuarioCarrera> {
    public String getIdFragment() {
        return "TODOS";
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CarreraCabeceraService cb = new CarreraCabeceraService(this, this.getActivity());
        cb.execute(this.filtro);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        CarreraCabecera c = (CarreraCabecera) l.getItemAtPosition(position);
        UsuarioCarreraService uc = new UsuarioCarreraService(this, this.getActivity(),getIdUsuario());
        uc.execute(c.getCodigoCarrera());



    }

    @Override
    public void actualizarResultados(List<CarreraCabecera> resultados) {
        if (isAdded()) {
            setListAdapter(new CarreraListAdapter(CarrerasResultadoFragment.this.getActivity(),
                    resultados));
        }
    }

    @Override
    public void actualizarResultado(UsuarioCarrera resultado) {
        Intent intent = new Intent(this.getActivity(), DetalleCarreraActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("carrera",resultado);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
}
