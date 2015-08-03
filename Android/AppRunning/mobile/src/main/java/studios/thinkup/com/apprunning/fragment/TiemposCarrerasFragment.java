package studios.thinkup.com.apprunning.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import studios.thinkup.com.apprunning.DetalleCarreraActivity;
import studios.thinkup.com.apprunning.adapter.TiempoCarreraListAdapter;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.restProviders.OnResultHandler;
import studios.thinkup.com.apprunning.provider.restProviders.TiemposService;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * interface.
 */
public class TiemposCarrerasFragment extends FilteredFragment implements OnResultHandler<UsuarioCarrera> {


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TiemposCarrerasFragment() {
    }

    @Override
    public String getIdFragment() {
        return "TODOS";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

            TiemposService ts = new TiemposService(this,this.getActivity());
            ts.execute(this.getFiltro());


    }

    @Override
    public Filtro getFiltro() {
        Filtro f = super.getFiltro();
        f.clean();
        f.setIdUsuario(this.getIdUsuario());
        return f;

    }

    @Override
    public void actualizarResultados(List<UsuarioCarrera> resultados) {
        if (isAdded()) {
            TiempoCarreraListAdapter adapter = new TiempoCarreraListAdapter(this.getActivity(), resultados);
            setListAdapter(adapter);
        }
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        UsuarioCarrera c = (UsuarioCarrera) l.getItemAtPosition(position);
        Intent intent = new Intent(this.getActivity(), DetalleCarreraActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("carrera",c);
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);

    }



}
