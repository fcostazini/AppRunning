package studios.thinkup.com.apprunning.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import studios.thinkup.com.apprunning.DetalleCarreraActivity;
import studios.thinkup.com.apprunning.adapter.CarreraListAdapter;
import studios.thinkup.com.apprunning.adapter.TiempoCarreraListAdapter;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.CarreraCabecera;
import studios.thinkup.com.apprunning.model.entity.UsuarioCarrera;
import studios.thinkup.com.apprunning.provider.CarreraCabeceraProvider;
import studios.thinkup.com.apprunning.provider.ICarreraCabeceraProvider;
import studios.thinkup.com.apprunning.provider.IUsuarioCarreraProvider;
import studios.thinkup.com.apprunning.provider.UsuarioCarreraProvider;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * interface.
 */
public class TiemposCarrerasFragment extends ListFragment {


    private Filtro filtro;
    public String getIdFragment(){
        return "TODOS";
    }

    public static TiemposCarrerasFragment newInstance(Filtro filtro) {
        TiemposCarrerasFragment fragment = new TiemposCarrerasFragment();
        Bundle args = new Bundle();
        args.putSerializable(Filtro.class.getSimpleName() + fragment.getIdFragment(),  filtro);
        fragment.setArguments(args);
        return fragment;
    }


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TiemposCarrerasFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            filtro = (Filtro) getArguments().getSerializable(Filtro.class.getSimpleName() +this.getIdFragment());
        }
        if(this.filtro == null){
            filtro = new Filtro(((RunningApplication) this.getActivity().getApplication()).getDefaultSettings());
        }
        IUsuarioCarreraProvider carrerasProvider = new UsuarioCarreraProvider(getActivity(),(int)filtro.getIdUsuario());
        List<UsuarioCarrera> resultados = carrerasProvider.findTiemposByFiltro(this.filtro);
        // TODO: Change Adapter to display your content

        setListAdapter(new TiempoCarreraListAdapter(this.getActivity(),
                resultados));



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
        UsuarioCarrera c = (UsuarioCarrera)l.getItemAtPosition(position);
        Intent intent = new Intent(this.getActivity(), DetalleCarreraActivity.class);
        Bundle b = new Bundle();
        b.putInt(UsuarioCarrera.class.getSimpleName(), c.getCodigoCarrera()); //Your id
        intent.putExtras(b); //Put your id to your next Intent
        startActivity(intent);

    }
    @Override
    public void onResume() {
        super.onResume();
        if(this.filtro != null){

            IUsuarioCarreraProvider carrerasProvider = new UsuarioCarreraProvider(getActivity(),(int)filtro.getIdUsuario());
            List<UsuarioCarrera> resultados = carrerasProvider.findTiemposByFiltro(this.filtro);
            // TODO: Change Adapter to display your content

            setListAdapter(new TiempoCarreraListAdapter(this.getActivity(),
                    resultados));
            this.getListView().invalidateViews();

        }
    }
   }
