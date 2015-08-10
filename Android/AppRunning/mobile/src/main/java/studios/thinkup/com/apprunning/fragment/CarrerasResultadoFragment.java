package studios.thinkup.com.apprunning.fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
    private CarreraListAdapter adapter;
    private static ProgressDialog pd;
    public String getIdFragment() {
        return "TODOS";
    }

    protected static void showProgress(Context context,String message) {
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
    }

    private void getData() {
        CarreraCabeceraService cb = new CarreraCabeceraService(this, this.getActivity(), this.getUsuario());
        cb.execute(this.filtro);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        CarreraCabecera c = (CarreraCabecera) l.getItemAtPosition(position);
        showProgress(this.getActivity(),"Buscando Carrera...");
        UsuarioCarreraService uc = new UsuarioCarreraService(this, this.getActivity(), getUsuario());
        uc.execute(c.getCodigoCarrera());


    }

    @Override
    public void actualizarResultados(List<CarreraCabecera> resultados) {
        if (isAdded()) {
            this.adapter = new CarreraListAdapter(CarrerasResultadoFragment.this.getActivity(),
                    resultados);
            setListAdapter(this.adapter);
        }
    }

    @Override
    public void actualizarResultado(UsuarioCarrera resultado) {
        hideProgress();
        if (this.getActivity() != null) {
            Intent intent = new Intent(this.getActivity(), DetalleCarreraActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("carrera", resultado);
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (this.adapter != null) {
            this.adapter.notifyDataSetInvalidated();
        }
        this.getData();
    }
}
