package studios.thinkup.com.apprunning;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestDetailedSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.facebook.FacebookPerson;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.github.gorbin.asne.googleplus.GooglePlusPerson;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.IUsuarioProvider;
import studios.thinkup.com.apprunning.provider.UsuarioProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;
import studios.thinkup.com.apprunning.provider.restProviders.UsuarioProviderRemote;

public class MainFragment extends Fragment implements OnRequestDetailedSocialPersonCompleteListener, SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener {
    public static SocialNetworkManager mSocialNetworkManager;
    private SocialNetwork socialNetwork;
    private View.OnClickListener loginClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int networkId = 0;
            switch (view.getId()) {
                case R.id.facebook:
                    networkId = FacebookSocialNetwork.ID;
                    break;
                case R.id.googleplus:
                    networkId = GooglePlusSocialNetwork.ID;
                    break;
            }

            SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(networkId);
            if (!socialNetwork.isConnected()) {
                if (networkId != 0) {
                    socialNetwork.requestLogin();
                    if (getView() != null) {
                        getView().findViewById(R.id.login_buttons).setVisibility(View.GONE);
                        ImageView i = (ImageView) getView().findViewById(R.id.background);
                        i.setImageResource(R.drawable.app_logo);
                        i.setAlpha(1f);
                    }
                } else {
                    Toast.makeText(getActivity(), "Wrong networkId", Toast.LENGTH_LONG).show();
                }
            } else {

                startProfile(socialNetwork.getID());
            }
        }

    };

    public MainFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        // init buttons and set Listener
        /*
      SocialNetwork Ids in ASNE:
      1 - Twitter
      2 - LinkedIn
      3 - Google Plus
      4 - Facebook
      5 - Vkontakte
      6 - Odnoklassniki
      7 - Instagram
     */
        Button facebook = (Button) rootView.findViewById(R.id.facebook);
        facebook.setOnClickListener(loginClick);
        Button googleplus = (Button) rootView.findViewById(R.id.googleplus);
        googleplus.setOnClickListener(loginClick);

        Button sinRedSocial = (Button) rootView.findViewById(R.id.bt_sin_red_social);
        sinRedSocial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginSinRedSocialActivity.class);
                startActivity(i);
            }
        });

        Button nuevoUsuario = (Button) rootView.findViewById(R.id.nuevoUsuario);
        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioApp u = new UsuarioApp();
                u.setTipoCuenta("P");
                nuevoUsuario(u, true);
            }
        });
        rootView.findViewById(R.id.login_buttons).setVisibility(View.VISIBLE);
        ImageView i = (ImageView) rootView.findViewById(R.id.background);
        i.setImageResource(R.drawable.loggin_bg);
        i.setAlpha(0.35f);
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

            //Init and add to manager LinkedInSocialNetwork
            GooglePlusSocialNetwork gpNetwork = new GooglePlusSocialNetwork(this);
            mSocialNetworkManager.addSocialNetwork(gpNetwork);

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


    //Login listener

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
        MainActivity.hideProgress();
        startProfile(networkId);
    }

    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {
        MainActivity.hideProgress();
        Toast.makeText(getActivity(), "ERROR: No se encontró conexión a internet", Toast.LENGTH_LONG).show();


    }

    private void startProfile(int networkId) {
        socialNetwork = MainFragment.mSocialNetworkManager.getSocialNetwork(networkId);
        socialNetwork.setOnRequestDetailedSocialPersonCompleteListener(this);
        socialNetwork.requestDetailedCurrentPerson();
    }

    @Override
    public void onRequestDetailedSocialPersonSuccess(int i, SocialPerson socialPerson) {
        if (this.getActivity() != null &&
                this.getActivity().getIntent() != null &&
                this.getActivity().getIntent().getExtras() != null &&
                this.getActivity().getIntent().getExtras().containsKey("LOGOUT") &&
                socialNetwork.isConnected()) {
            MainActivity.hideProgress();
            this.getActivity().getIntent().getExtras().remove("LOGOUT");
            socialNetwork.logout();
            Intent intent = new Intent(this.getActivity(), MainActivity.class);
            UsuarioApp u = ((RunningApplication) this.getActivity().getApplication()).getUsuario();
            UsuarioProvider up = new UsuarioProvider(this.getActivity());
            up.deleteUsuario(u);
            this.getActivity().startActivity(intent);
            this.getActivity().finish();

        } else {

            MainActivity.hideProgress();
            if (socialPerson == null) {

            } else {

                MainActivity.showProgress("Buscando Usuario");
                UsuarioProviderTask task = new UsuarioProviderTask();
                task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, socialPerson);
            }
        }
    }


    private class UsuarioProviderTask extends AsyncTask<SocialPerson, Integer, UsuarioApp> {

        @Override
        protected void onPostExecute(UsuarioApp usuarioApp) {
            super.onPostExecute(usuarioApp);
            MainActivity.hideProgress();
            if (usuarioApp.getId() == null) {
                nuevoUsuario(usuarioApp, false);
            } else {
                usuarioRegistrado(usuarioApp);
            }


        }

        @Override
        protected void onCancelled(UsuarioApp usuarioApp) {
            super.onCancelled(usuarioApp);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected UsuarioApp doInBackground(SocialPerson... params) {

            UsuarioApp u = null;


            IUsuarioProvider up = new UsuarioProviderRemote(MainFragment.this.getActivity());
            u = up.getUsuarioByEmail(params[0].email);
            if (u != null) {
                if(u.getTipoCuenta().equals("4") &&
                        (u.getSocialId()== null || u.getSocialId().equals("")|| u.getSocialId().equals("F" + params[0].id))){
                    u.setSocialId("F" + params[0].id);
                    try {
                        up.update(u);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                return u;
            } else {
                return this.getUsuarioApp(socialNetwork, params[0]);
            }


        }


        private UsuarioApp getUsuarioApp(SocialNetwork socialNetwork, SocialPerson socialPerson) {
            UsuarioApp u;

            u = new UsuarioApp();

            u.setEmail(socialPerson.email);
            u.setFotoPerfil(socialPerson.avatarURL);
            u.setTipoCuenta(String.valueOf(socialNetwork.getID()));

            if (socialNetwork.getID() == GooglePlusSocialNetwork.ID) {
                GooglePlusPerson gp = (GooglePlusPerson) socialPerson;
                if (gp.birthday != null && gp.birthday.length() == 10) {
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    Date bd;
                    try {
                        bd = sf.parse(gp.birthday);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        bd = new Date();
                    }
                    sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    u.setFechaNacimiento(sf.format(bd));
                }

                u.setNombre(gp.name);
                u.setNick(gp.nickname);
                u.setSocialId("G" + gp.id);

            }
            if (socialNetwork.getID() == FacebookSocialNetwork.ID) {
                FacebookPerson fp = (FacebookPerson) socialPerson;
                u.setNombre(fp.firstName);
                u.setApellido(fp.lastName);
                u.setNick(fp.name);
                if (fp.birthday != null && fp.birthday.length() == 10) {
                    SimpleDateFormat sf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
                    Date bd;
                    try {
                        bd = sf.parse(fp.birthday);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        bd = new Date();
                    }
                    sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    u.setFechaNacimiento(sf.format(bd));
                    u.setSocialId("F" + fp.id);
                }


            }
            return u;
        }
    }

    private void usuarioRegistrado(UsuarioApp usuarioApp) {
        Intent intent = new Intent(MainFragment.this.getActivity(), StartUpActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(UsuarioApp.FIELD_ID, usuarioApp);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void nuevoUsuario(UsuarioApp usuarioApp, boolean nuevoUsuario) {
        Bundle extras = new Bundle();
        extras.putSerializable(UsuarioApp.FIELD_ID, usuarioApp);
        extras.putBoolean("nuevoUsuario", nuevoUsuario);
        Intent intent = new Intent(MainFragment.this.getActivity(), NuevoUsuario.class);
        intent.putExtras(extras);
        startActivity(intent);

    }

}
