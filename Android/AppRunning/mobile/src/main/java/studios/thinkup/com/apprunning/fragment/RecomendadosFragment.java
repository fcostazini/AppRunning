package studios.thinkup.com.apprunning.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.DetalleCarreraActivity;
import studios.thinkup.com.apprunning.adapter.CarreraListAdapter;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.restProviders.CarreraCabeceraService;
import studios.thinkup.com.apprunning.provider.restProviders.OnSingleResultHandler;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioCarreraService;

/**
 * Created by fcostazini on 26/05/2015.
 * Fragmento Recomendados
 */
public class RecomendadosFragment extends FilteredFragment implements CarreraCabeceraService.OnResultsHandler, OnSingleResultHandler<UsuarioCarrera> {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getFiltro().setRecomendadas(true);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        CarreraCabeceraService cp = new CarreraCabeceraService(this, RecomendadosFragment.this.getActivity(), this.getUsuario());
        if(NetworkUtils.NETWORK_STATUS_NOT_CONNECTED == NetworkUtils.getConnectivityStatus(this.getActivity())) {
            this.actualizarResultados(new Vector<CarreraCabecera>());
        }else{
            cp.execute(getFiltro());

        }


    }
    @Override
    public String getIdFragment() {
        return "recomendados";
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        CarreraCabecera c = (CarreraCabecera) l.getItemAtPosition(position);
        UsuarioCarreraService uc = new UsuarioCarreraService(this, this.getActivity(), getUsuario());
        uc.execute(c.getCodigoCarrera());

    }


    @Override
    public void actualizarResultados(List<CarreraCabecera> resultados) {
        if (isAdded()) {
            ListAdapter adapter = new CarreraListAdapter(this.getActivity(), resultados);
            setListAdapter(adapter);
        }
    }

    @Override
    public void actualizarResultado(UsuarioCarrera resultado) {
        Intent intent = new Intent(this.getActivity(), DetalleCarreraActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("carrera", resultado);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }
}
