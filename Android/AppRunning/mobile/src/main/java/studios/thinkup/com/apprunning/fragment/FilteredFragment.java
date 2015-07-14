package studios.thinkup.com.apprunning.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;

import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;

/**
 * Created by fcostazini on 14/07/2015.
 */
public abstract class FilteredFragment extends ListFragment {


    private Filtro filtro;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            this.filtro = (Filtro) savedInstanceState.getSerializable("filtro");
        } else {
            if (getArguments() != null) {
                this.filtro = (Filtro) getArguments().getSerializable(Filtro.class.getSimpleName() + this.getIdFragment());
            }
            if (this.filtro == null) {
                this.filtro = new Filtro(((RunningApplication) this.getActivity().getApplication()).getDefaultSettings());
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putSerializable("filtro", this.filtro);

    }

    public Filtro getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }

    public abstract String getIdFragment();
}
