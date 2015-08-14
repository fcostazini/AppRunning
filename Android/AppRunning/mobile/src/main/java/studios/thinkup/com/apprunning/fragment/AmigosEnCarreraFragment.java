package studios.thinkup.com.apprunning.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ListView;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.AmigosListAdapter;
import studios.thinkup.com.apprunning.DetalleAmigoActivity;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.AmigosEnCarreraService;

/**
 * Created by Facundo on 11/08/2015.
 * Fragment que muestra los amigos
 */
public class AmigosEnCarreraFragment extends ListFragment implements AmigosEnCarreraService.IServiceAmigosHandler {

    private AmigosListAdapter adapter;
    private IUsuarioCarreraObservable usuarioObservable;
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void init() {
        if (this.usuarioObservable == null) {
            this.usuarioObservable = (IUsuarioCarreraObservable) this.getActivity();
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        getData();


    }

    private void getData() {
        AmigosEnCarreraService as = new AmigosEnCarreraService(this.getActivity(), this,this.getUsuario());
        as.execute(this.usuarioObservable.getUsuarioCarrera().getCodigoCarrera());
    }

    private UsuarioApp getUsuario() {
        return ((RunningApplication) this.getActivity().getApplication()).getUsuario();
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        AmigosDTO u = (AmigosDTO) this.adapter.getItem(position);
        Intent i = new Intent(this.getActivity(), DetalleAmigoActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(AmigosDTO.FIELD_ID, u);
        i.putExtras(b);
        startActivity(i);


    }

    @Override
    public void onResume() {
        super.onResume();
        this.getData();
    }

    /**
     * Called when the Fragment is visible to the user.  This is generally
     * Activity's lifecycle.
     */
    @Override
    public void onStart() {
        super.onStart();
        setEmptyText("No hay amigos inscriptos");
    }

    @Override
    public void onDataRetrived(List<AmigosDTO> amigos) {
        this.adapter = new AmigosListAdapter(this.getActivity(), amigos);
        setListAdapter(adapter);
    }

    @Override
    public void onError(String error) {
        this.onDataRetrived(new Vector<AmigosDTO>());
    }
}
