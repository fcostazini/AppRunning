package studios.thinkup.com.apprunning.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.AmigosListAdapter;
import studios.thinkup.com.apprunning.DetalleAmigoActivity;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.BuscarNuevosAmigosService;

/**
 * Created by Facundo on 11/08/2015.
 * Fragmento para agregar amigos
 */
public class AgregarAmigosFragment extends Fragment implements TextWatcher, BuscarNuevosAmigosService.IServiceAmigosHandler, AdapterView.OnItemClickListener {
    private AmigosListAdapter adapter;
    private Integer previousLenght = 0;
    private String aBuscar;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        previousLenght = 0;
        init(savedInstanceState);
    }

    private void init(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey("aBuscar")) {
            this.aBuscar = savedInstanceState.getString("aBuscar");
        } else {
            this.aBuscar = "";
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.buscar_amigo_fragment, container, false);

        TextView txtABuscar = (TextView) rootView.findViewById(R.id.txt_dato_amigo);
        txtABuscar.addTextChangedListener(this);
        previousLenght = txtABuscar.length();
        ListView resultados = (ListView) rootView.findViewById(R.id.lv_resultados);
        resultados.setAdapter(this.adapter);
        return rootView;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() >= 3) {
            buscarAmigos(s);
            aBuscar = s.toString();
        }
    }

    private UsuarioApp getUsuario() {
        return ((RunningApplication) this.getActivity().getApplication()).getUsuario();
    }

    private void buscarAmigos(CharSequence s) {
        if (s.length() > 3) {
            BuscarNuevosAmigosService up = new BuscarNuevosAmigosService(this.getActivity(), this.getUsuario(), this);
            up.execute(s.toString());
        } else {
            onDataRetrived(new Vector<AmigosDTO>());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.aBuscar != null && !this.aBuscar.isEmpty()) {
            outState.putString("aBuscar", aBuscar);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onDataRetrived(List<AmigosDTO> amigos) {
        if (isAdded()) {
            this.adapter = new AmigosListAdapter(this.getActivity(), amigos);
            if (this.getView() != null) {
                ListView lv = (ListView) this.getView().findViewById(R.id.lv_resultados);
                lv.setOnItemClickListener(this);
                lv.setEmptyView(getView().findViewById(android.R.id.empty));
                lv.setAdapter(this.adapter);

            }
        }
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * tied to  of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onError(String error) {
        this.onDataRetrived(new Vector<AmigosDTO>());
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        AmigosDTO u = (AmigosDTO) this.adapter.getItem(position);
        Intent i = new Intent(this.getActivity(), DetalleAmigoActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(AmigosDTO.FIELD_ID, u);
        i.putExtras(b);
        startActivity(i);
    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * Activity's lifecycle.
     */
    @Override
    public void onResume() {
        super.onResume();
        buscarAmigos("");

    }
}
