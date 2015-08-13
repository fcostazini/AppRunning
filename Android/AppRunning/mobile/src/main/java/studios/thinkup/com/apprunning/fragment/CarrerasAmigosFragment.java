package studios.thinkup.com.apprunning.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.DetalleCarreraActivity;
import studios.thinkup.com.apprunning.adapter.CarrerasAmigoListAdapter;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;
import studios.thinkup.com.apprunning.model.entity.CarreraAmigoDTO;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.CarrerasAmigosService;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * interface.
 */
public class CarrerasAmigosFragment extends ListFragment implements CarrerasAmigosService.IServicioActualizacionAmigoHandler {

    private AmigosDTO amigo;

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
            ts.execute(this.amigo.getIdAmigo());
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
        CarreraAmigoDTO a = (CarreraAmigoDTO) l.getItemAtPosition(position);
        UsuarioCarreraProvider up = new UsuarioCarreraProvider(this.getActivity(), getUsuario());
        UsuarioCarrera c = up.getByIdCarrera(a.getIdCarrera());
        Intent intent = new Intent(this.getActivity(), DetalleCarreraActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("carrera", c);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);

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
}
