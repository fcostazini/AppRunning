package studios.thinkup.com.apprunning;


import android.content.Intent;
import android.media.Image;
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
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;
import com.github.gorbin.asne.googleplus.GooglePlusSocialNetwork;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.IUsuarioProvider;

import studios.thinkup.com.apprunning.provider.UsuarioProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;

public class MainFragment extends Fragment implements OnRequestSocialPersonCompleteListener, SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener {
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
        ImageView i = (ImageView)rootView.findViewById(R.id.background);
        i.setImageResource(R.drawable.loggin_bg);
        i.setAlpha(0.35f);
        //Chose permissions
        ArrayList<String> fbScope = new ArrayList<String>();
        fbScope.addAll(Arrays.asList("public_profile, email, user_friends"));

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
                if(getView()!=null){
                    getView().findViewById(R.id.login_buttons).setVisibility(View.GONE);
                    ImageView i = (ImageView) getView().findViewById(R.id.background);
                    i.setImageResource(R.drawable.app_logo);
                    i.setAlpha(1f);
                }
                startProfile(socialNetwork.getID());
            }

    }

    @Override
    public void onSocialNetworkManagerInitialized() {
        //when init SocialNetworks - get and setup login only for initialized SocialNetworks
        for (SocialNetwork socialNetwork : mSocialNetworkManager.getInitializedSocialNetworks()) {
            socialNetwork.setOnLoginCompleteListener(this);
            initSocialNetwork(socialNetwork);
        }
    }

    //Login listener

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
            socialNetwork.setOnRequestCurrentPersonCompleteListener(this);
            socialNetwork.requestCurrentPerson();
    }

    @Override
    public void onRequestSocialPersonSuccess(int i, SocialPerson socialPerson) {
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
                IUsuarioProvider usuarioPovider = new UsuarioProvider(this.getActivity());
                UsuarioApp u = usuarioPovider.getUsuarioByEmail(socialPerson.email);
                if (u == null) {
                    u = new UsuarioApp();
                    u.setNick(socialPerson.name);
                    u.setEmail(socialPerson.email);
                    u.setFotoPerfilUrl(socialPerson.avatarURL);
                    u.setTipoCuenta(String.valueOf(socialNetwork.getID()));

                    Bundle extras = new Bundle();
                    extras.putSerializable("usuario", u);
                    extras.putBoolean("nuevoUsuario",true);
                    Intent intent = new Intent(this.getActivity(), NuevoUsuario.class);
                    intent.putExtras(extras);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(this.getActivity(), RecomendadosActivity.class);
                    ((RunningApplication) this.getActivity().getApplication()).setUsuario(u);
                    startActivity(intent);
                }

            }
        }
    }
}
