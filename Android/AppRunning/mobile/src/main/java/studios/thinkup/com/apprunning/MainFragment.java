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
    /**
     * SocialNetwork Ids in ASNE:
     * 1 - Twitter
     * 2 - LinkedIn
     * 3 - Google Plus
     * 4 - Facebook
     * 5 - Vkontakte
     * 6 - Odnoklassniki
     * 7 - Instagram
     */
    private Button facebook;
    private Button googleplus;
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
        facebook = (Button) rootView.findViewById(R.id.facebook);
        facebook.setOnClickListener(loginClick);
        googleplus = (Button) rootView.findViewById(R.id.googleplus);
        googleplus.setOnClickListener(loginClick);

        rootView.findViewById(R.id.login_buttons).setVisibility(View.VISIBLE);
        ImageView i = (ImageView) rootView.findViewById(R.id.background);
        i.setImageResource(R.drawable.loggin_bg);
        i.setAlpha(0.35f);
        //Chose permissions
        ArrayList<String> fbScope = new ArrayList<String>();
        fbScope.addAll(Arrays.asList("public_profile, email, user_friends,user_birthday"));

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
                    initSocialNetwork(socialNetwork);
                }
            }
        }
        return rootView;
    }

    private void initSocialNetwork(SocialNetwork socialNetwork) {

        if (socialNetwork.isConnected()) {
            if (getView() != null) {
                getView().findViewById(R.id.login_buttons).setVisibility(View.GONE);
                ImageView i = (ImageView) getView().findViewById(R.id.background);
                i.setImageResource(R.drawable.app_logo);
                i.setAlpha(1f);
            }
            startProfile(socialNetwork.getID());
        }

    }

    //Login listener

    @Override
    public void onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            initSocialNetwork(socialNetwork);
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
        Toast.makeText(getActivity(), "ERROR: " + errorMessage, Toast.LENGTH_LONG).show();


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
            this.getActivity().getIntent().getExtras().remove("LOGOUT");
            MainActivity.hideProgress();
            socialNetwork.logout();
            Intent intent = new Intent(this.getActivity(), MainActivity.class);
            this.getActivity().startActivity(intent);
            this.getActivity().finish();

        } else {

            MainActivity.hideProgress();
            if (socialPerson == null) {

            } else {

                MainActivity.showProgress("Buscando Usuario");
                UsuarioProviderTask task = new UsuarioProviderTask();
                task.execute(socialPerson);
            }
        }
    }


    private class UsuarioProviderTask extends AsyncTask<SocialPerson, Integer, UsuarioApp> {

        @Override
        protected void onPostExecute(UsuarioApp usuarioApp) {
            super.onPostExecute(usuarioApp);
            MainActivity.hideProgress();
            if (usuarioApp.getId() == null) {
                Bundle extras = new Bundle();
                extras.putSerializable("usuario", usuarioApp);
                extras.putBoolean("nuevoUsuario", true);
                Intent intent = new Intent(MainFragment.this.getActivity(), NuevoUsuario.class);
                intent.putExtras(extras);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainFragment.this.getActivity(), RecomendadosActivity.class);
                ((RunningApplication) MainFragment.this.getActivity().getApplication()).setUsuario(usuarioApp);
                startActivity(intent);
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

            IUsuarioProvider up = new UsuarioProvider(MainFragment.this.getActivity());
            u = up.getUsuarioByEmail(params[0].email);
            if (u != null) {
                up = new UsuarioProviderRemote(MainFragment.this.getActivity());
                if (up.getUsuarioByEmail(params[0].email) == null) {
                    try {
                        up.grabar(u);
                        return u;
                    } catch (EntidadNoGuardadaException e) {
                        e.printStackTrace();
                        return getUsuarioApp(socialNetwork, params[0]);
                    }
                }
            } else {
                if (up.getUsuarioByEmail(params[0].email) != null) {
                    try {
                        up = new UsuarioProvider(MainFragment.this.getActivity());
                        up.grabar(u);
                        return u;
                    } catch (EntidadNoGuardadaException e) {
                        e.printStackTrace();
                        return getUsuarioApp(socialNetwork, params[0]);
                    }
                }
            }


            if (u == null) {
                return this.getUsuarioApp(socialNetwork, params[0]);
            } else {
                return u;
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
                }


            }
            return u;
        }
    }

}
