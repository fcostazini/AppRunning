package studios.thinkup.com.apprunning.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.DetalleCarreraActivity;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.adapter.CarrerasAmigoListAdapter;
import studios.thinkup.com.apprunning.broadcast.handler.NetworkUtils;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;
import studios.thinkup.com.apprunning.model.entity.Carrera;
import studios.thinkup.com.apprunning.model.entity.CarreraAmigoDTO;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.CarreraLocalProvider;
import studios.thinkup.com.apprunning.provider.CarrerasAmigosService;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.restProviders.OnSingleResultHandler;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioCarreraService;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * interface.
 */
public class CarrerasAmigosFragment extends ListFragment implements CarrerasAmigosService.IServicioActualizacionAmigoHandler, OnSingleResultHandler<UsuarioCarrera> {

    private AmigosDTO amigo;
    private static ProgressDialog pd;

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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CarrerasAmigosFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.getArguments() != null && this.getArguments().containsKey(AmigosDTO.FIELD_ID)) {
            this.amigo = (AmigosDTO) this.getArguments().getSerializable(AmigosDTO.FIELD_ID);
        }
    }


    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null && savedInstanceState.containsKey(AmigosDTO.FIELD_ID)) {
            this.amigo = (AmigosDTO) savedInstanceState.getSerializable(AmigosDTO.FIELD_ID);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (this.amigo != null) {
            CarrerasAmigosService ts = new CarrerasAmigosService(this.getActivity(), this);
            ts.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, this.amigo.getIdAmigo());
        } else {
            onOk(new Vector<CarreraAmigoDTO>());
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.amigo != null) {
            outState.putSerializable(AmigosDTO.FIELD_ID, amigo);
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        CarreraAmigoDTO c = (CarreraAmigoDTO) getListAdapter().getItem(position);
        showProgress(this.getActivity(), getString(R.string.cargando_carrera));
        if (NetworkUtils.isConnected(this.getActivity())) {
            UsuarioCarreraService uc = new UsuarioCarreraService(this, this.getActivity(), getUsuario());
            uc.execute(c.getIdCarrera());
        } else {
            UsuarioCarrera uc = this.getUsuarioCarreraLocal(c.getIdCarrera());
            if (uc == null) {
                Toast.makeText(this.getActivity(), getString(R.string.sin_conexion), Toast.LENGTH_SHORT).show();
                hideProgress();
            } else {
                actualizarResultado(uc);
            }

        }

    }

    private UsuarioApp getUsuario() {
        return ((RunningApplication) this.getActivity().getApplication()).getUsuario();
    }

    @Override
    public void onStart() {
        super.onStart();
        setEmptyText("No se encuentra inscripto en ninguna carrera");
    }

    @Override
    public void onOk(List<CarreraAmigoDTO> amigos) {
        if (isAdded()) {
            CarrerasAmigoListAdapter adapter = new CarrerasAmigoListAdapter(this.getActivity(), amigos);
            setListAdapter(adapter);
        }
    }

    @Override
    public void onError(Integer estado) {
        this.onOk(new Vector<CarreraAmigoDTO>());
    }

    @Override
    public void actualizarResultado(UsuarioCarrera resultado) {
        hideProgress();
        if (resultado != null) {
            Intent intent = new Intent(this.getActivity(), DetalleCarreraActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("carrera", resultado);
            intent.putExtras(b); //Put your id to your next Intent
            startActivity(intent);
        } else {
            Toast.makeText(this.getActivity(), "No se cargó la carrera", Toast.LENGTH_SHORT).show();
        }

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
}
