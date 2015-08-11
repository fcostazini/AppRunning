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
import studios.thinkup.com.apprunning.model.entity.Amigo;
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
        if (s.length() > 4 && s.length() > previousLenght && adapter != null) {
            this.adapter.getFilter().filter(s.toString());
        } else {
            if (s.length() >= 4) {
                buscarAmigos(s);
            }
        }
        previousLenght = s.length();


    }

    private void buscarAmigos(CharSequence s) {
        BuscarNuevosAmigosService up = new BuscarNuevosAmigosService(this.getActivity(), this);
        up.execute(s.toString());
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
    public void onDataRetrived(List<Amigo> amigos) {
        this.adapter = new AmigosListAdapter(this.getActivity(), amigos);
        if (this.getView() != null) {
            ListView lv = (ListView) this.getView().findViewById(R.id.lv_resultados);
            lv.setOnItemClickListener(this);
            lv.setAdapter(this.adapter);
        }

    }

    @Override
    public void onError(String error) {
        this.onDataRetrived(new Vector<Amigo>());
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Amigo u = (Amigo) this.adapter.getItem(position);
        Intent i = new Intent(this.getActivity(), DetalleAmigoActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(Amigo.FIELD_ID, u);
        i.putExtras(b);
        startActivity(i);
    }
}
