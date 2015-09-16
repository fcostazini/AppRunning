package studios.thinkup.com.apprunning.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestGetFriendsCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.AmigosListAdapter;
import studios.thinkup.com.apprunning.DetalleAmigoActivity;
import studios.thinkup.com.apprunning.MainActivity;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.adapter.AmigosSocialPersonaListAdapter;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.AmigoRequest;
import studios.thinkup.com.apprunning.model.entity.AmigosDTO;
import studios.thinkup.com.apprunning.model.entity.TipoRequestEnum;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.ActualizarAmigoService;
import studios.thinkup.com.apprunning.provider.BuscarNuevosAmigosService;
import studios.thinkup.com.apprunning.provider.MisAmigosService;

/**
 * Created by Facundo on 11/08/2015.
 * Fragmento para agregar amigos
 */
public class AgregarAmigosFragment extends Fragment implements TextWatcher, BuscarNuevosAmigosService.IServiceAmigosHandler, AdapterView.OnItemClickListener, SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener {
    private static ProgressDialog pd;
    private AmigosListAdapter adapter;
    private Integer previousLenght = 0;
    private String aBuscar;

    public static SocialNetworkManager mSocialNetworkManager;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        previousLenght = 0;
        init(savedInstanceState);
        setHasOptionsMenu(true);
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
        //Chose permissions
        ArrayList<String> fbScope = new ArrayList<>();
        fbScope.addAll(Collections.singletonList("public_profile, email, user_friends,user_birthday"));

        //Use manager to manage SocialNetworks
        mSocialNetworkManager = (SocialNetworkManager) getFragmentManager().findFragmentByTag(MainActivity.SOCIAL_NETWORK_TAG);

        //Check if manager exist
        if (mSocialNetworkManager == null || mSocialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
            mSocialNetworkManager = new SocialNetworkManager();

            //Init and add to manager FacebookSocialNetwork
            FacebookSocialNetwork fbNetwork = new FacebookSocialNetwork(this, fbScope);
            mSocialNetworkManager.addSocialNetwork(fbNetwork);
            //Initiate every network from mSocialNetworkManager
            getFragmentManager().beginTransaction().add(mSocialNetworkManager, MainActivity.SOCIAL_NETWORK_TAG).commit();
            mSocialNetworkManager.setOnInitializationCompleteListener(this);
        } else {
            //if manager exist - get and setup login only for initialized SocialNetworks
            if (!mSocialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
                List<SocialNetwork> socialNetworks = mSocialNetworkManager.getInitializedSocialNetworks();
                for (SocialNetwork socialNetwork : socialNetworks) {
                    socialNetwork.setOnLoginCompleteListener(this);
                    //initSocialNetwork(socialNetwork);
                }
            }
        }
        return rootView;
    }

    @Override
    public void onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            //initSocialNetwork(socialNetwork);
        }
    }

    @Override
    public void onLoginSuccess(int networkId) {
    }

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        hideProgress();
        Toast.makeText(getActivity(), "ERROR: No se encontró conexión a internet", Toast.LENGTH_LONG).show();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnu_amigos_facebook) {
            this.getAmigos();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAmigos() {
        MisAmigosService as = new MisAmigosService(this.getActivity(), new MisAmigosService.IServiceAmigosHandler() {
            @Override
            public void onDataRetrived(List<AmigosDTO> amigos) {
                if (isAdded()) {
                    if (getUsuario().getTipoCuenta().equals(String.valueOf((FacebookSocialNetwork.ID)))) {
                        obtenerAmigosFacebook(FacebookSocialNetwork.ID, amigos);
                    }
                }
            }

            @Override
            public void onError(String error) {
                this.onDataRetrived(new Vector<AmigosDTO>());
            }
        });
        as.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, getUsuario().getId());
    }


    private void obtenerAmigosFacebook(int networkId, final List<AmigosDTO> amigos) {
        showProgress(this.getActivity(), "Buscando tus amigos...");
        final List<AmigosDTO> amigosEncontrados = amigos;
        final List<SocialPerson> amigosAAgregar = new Vector<>();
        mSocialNetworkManager.getSocialNetwork(networkId).requestGetFriends(new OnRequestGetFriendsCompleteListener() {
            @Override
            public void OnGetFriendsIdComplete(int i, String[] strings) {
                hideProgress();
            }

            @Override
            public void OnGetFriendsComplete(int i, ArrayList<SocialPerson> arrayList) {
                hideProgress();

                List<SocialPerson> filtrado = new Vector<>();
                for (SocialPerson s : arrayList) {
                    if (!amigosEncontrados.contains(s)) {
                        filtrado.add(s);
                    }
                }

                AlertDialog.Builder builderSingle = new AlertDialog.Builder(
                        AgregarAmigosFragment.this.getActivity());
                builderSingle.setIcon(R.drawable.ic_amigos);
                builderSingle.setTitle("Agregar amigos");
                final AmigosSocialPersonaListAdapter adapter = new AmigosSocialPersonaListAdapter(
                        AgregarAmigosFragment.this.getActivity(), filtrado);
                builderSingle.setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builderSingle.setAdapter(adapter, null);
                builderSingle.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        showProgress(AgregarAmigosFragment.this.getActivity(),"Agregando Amigos...");


                        ActualizarAmigoService ms = new ActualizarAmigoService(AgregarAmigosFragment.this.getActivity(),
                                new ActualizarAmigoService.IServicioActualizacionAmigoHandler() {
                                    @Override
                                    public void onOk(List<AmigosDTO> amigosDTO) {
                                        hideProgress();
                                        onDataRetrived(amigosDTO);

                                    }

                                    @Override
                                    public void onError(Integer estado) {
                                        hideProgress();
                                        onDataRetrived(new Vector<AmigosDTO>());
                                    }
                                });
                        ArrayList<AmigoRequest> ar = new ArrayList<>();
                        AmigoRequest request;

                        for(SocialPerson a : amigosAAgregar){
                            request = new AmigoRequest();
                            request.setIdAmigo(null);
                            request.setEmailAmigo(a.email);
                            request.setIdOwner(getUsuario().getId());
                            request.setTipoRequest(TipoRequestEnum.SOLICITUD_AMIGO);
                            ar.add(request);
                        }
                        ms.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, ar);
                        dialog.dismiss();

                    }
                });
                final AlertDialog alertDialog = builderSingle.create();
                alertDialog.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
                alertDialog.getListView().setDivider(null);
                alertDialog.getListView().setItemsCanFocus(false);
                alertDialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.setSelected(!view.isSelected());
                        if (view.isSelected()) {
                            amigosAAgregar.add((SocialPerson) parent.getItemAtPosition(position));
                        } else {
                            amigosAAgregar.remove((SocialPerson) parent.getItemAtPosition(position));
                        }
                    }
                });
                alertDialog.show();
            }

            @Override
            public void onError(int i, String s, String s1, Object o) {
                hideProgress();
                Toast.makeText(AgregarAmigosFragment.this.getActivity(), "No se pudo obtener tus amigos", Toast.LENGTH_SHORT).show();
            }
        });
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
            up.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, s.toString());
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
