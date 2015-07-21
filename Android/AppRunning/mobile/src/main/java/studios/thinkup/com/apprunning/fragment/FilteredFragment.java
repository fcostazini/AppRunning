package studios.thinkup.com.apprunning.fragment;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;

import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.ConfigProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;

/**
 * Created by fcostazini on 14/07/2015.
 */
public abstract class FilteredFragment extends ListFragment {


    private Filtro filtro;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.setEmptyText(getString(R.string.sin_carreras));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (savedInstanceState != null) {
            this.filtro = (Filtro) savedInstanceState.getSerializable(Filtro.FILTRO_ID);
        } else {
            if (getArguments() != null) {
                this.filtro = (Filtro) getArguments().getSerializable(Filtro.FILTRO_ID);
            }

        }
        if (this.filtro == null) {
            this.filtro = new Filtro(getDefaultSettings());
        }
    }

    protected DefaultSettings getDefaultSettings() {
        ConfigProvider cp = new ConfigProvider(this.getActivity().getApplication());
        UsuarioApp ua = ((RunningApplication) this.getActivity().getApplication()).getUsuario();

        DefaultSettings defaultSettings = cp.getByUsuario(ua.getId());
        if (defaultSettings == null) {
            defaultSettings = new DefaultSettings(ua.getId());
            try {
                cp.grabar(defaultSettings);
            } catch (EntidadNoGuardadaException e) {
                e.printStackTrace();
            }

        }
        return defaultSettings;
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putSerializable(Filtro.FILTRO_ID, this.filtro);

    }

    public Filtro getFiltro() {
        return filtro;
    }

    public void setFiltro(Filtro filtro) {
        this.filtro = filtro;
    }

    public abstract String getIdFragment();
}
