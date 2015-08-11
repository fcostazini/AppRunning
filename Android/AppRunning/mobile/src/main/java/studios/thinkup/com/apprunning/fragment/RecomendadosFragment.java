package studios.thinkup.com.apprunning.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.DetalleCarreraActivity;
import studios.thinkup.com.apprunning.adapter.CarreraListAdapter;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.CarreraLocalProvider;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.restProviders.CarreraCabeceraService;
import studios.thinkup.com.apprunning.provider.restProviders.OnSingleResultHandler;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioCarreraService;

/**
 * Created by fcostazini on 26/05/2015.
 * Fragmento Recomendados
 */
public class RecomendadosFragment extends FilteredFragment implements CarreraCabeceraService.OnResultsHandler, OnSingleResultHandler<UsuarioCarrera> {

    private CarreraListAdapter adapter;
    private static ProgressDialog pd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getFiltro().setRecomendadas(true);

    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();


    }

    private void getData() {
        if (NetworkUtils.isConnected(this.getActivity())) {
            CarreraCabeceraService cp = new CarreraCabeceraService(this, RecomendadosFragment.this.getActivity(), this.getUsuario());
            cp.execute(getFiltro());
        } else {
            Toast.makeText(this.getActivity(), "Sin Conexión a internet", Toast.LENGTH_SHORT).show();
            this.actualizarResultados(new Vector<CarreraCabecera>());

        }
    }

    protected static void showProgress(Context context, String message) {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    protected static void hideProgress() {
        if (pd != null) {
            pd.dismiss();
        }
    }

    @Override
    public String getIdFragment() {
        return "recomendados";
    }

    private UsuarioCarrera getUsuarioCarreraLocal(Integer id) {
        Carrera c = null;
        IUsuarioCarreraProvider ucp = new UsuarioCarreraProvider(this.getActivity(), this.getUsuario());
        UsuarioCarrera uc = ucp.getByIdCarrera(id);
        CarreraLocalProvider local = new CarreraLocalProvider(this.getActivity());
        c = local.getById(id);
        if (c == null) {
            return null;
        }
        if (uc == null) {
            uc = new UsuarioCarrera(c);
        } else {
            uc.setCarrera(c);
        }
        uc.setUsuario(this.getUsuario().getId());
        return uc;

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        CarreraCabecera c = (CarreraCabecera) l.getItemAtPosition(position);
        showProgress(this.getActivity(), "Cargando Carrera...");
        if (NetworkUtils.isConnected(this.getActivity())) {
            UsuarioCarreraService uc = new UsuarioCarreraService(this, this.getActivity(), getUsuario());
            uc.execute(c.getCodigoCarrera());
        } else {
            UsuarioCarrera uc = this.getUsuarioCarreraLocal(c.getCodigoCarrera());
            if (uc == null) {
                Toast.makeText(this.getActivity(), "Sin Conexión a internet", Toast.LENGTH_SHORT).show();
                hideProgress();
            } else {
                actualizarResultado(uc);
            }

        }

    }


    @Override
    public void actualizarResultados(List<CarreraCabecera> resultados) {
        if (isAdded()) {
            this.adapter = new CarreraListAdapter(this.getActivity(), resultados);
            setListAdapter(adapter);
        }
    }

    @Override
    public void actualizarResultado(UsuarioCarrera resultado) {
        hideProgress();
        Intent intent = new Intent(this.getActivity(), DetalleCarreraActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("carrera", resultado);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        this.getData();
    }
}
