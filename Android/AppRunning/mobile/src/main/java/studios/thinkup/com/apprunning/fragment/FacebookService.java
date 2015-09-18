package studios.thinkup.com.apprunning.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkManager;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestDetailedSocialPersonCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestGetFriendsCompleteListener;
import com.github.gorbin.asne.core.listener.base.SocialNetworkListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.facebook.FacebookSocialNetwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import studios.thinkup.com.apprunning.MainActivity;

/**
 * Created by fcostazini on 18/09/2015.
 * <p/>
 * Fragmente para manejar login con facebook
 */
public class FacebookService implements SocialNetworkManager.OnInitializationCompleteListener, OnLoginCompleteListener, OnRequestDetailedSocialPersonCompleteListener {

    public static SocialNetworkManager mSocialNetworkManager;

    public IFriendHandler frindHandler;
    public ILoginHandler loginHandler;
    public Fragment context;


    public FacebookService(Fragment context) {

        this.context = context;
        //Chose permissions
        ArrayList<String> fbScope = new ArrayList<>();
        fbScope.addAll(Collections.singletonList("public_profile, email, user_friends,user_birthday"));

        //Use manager to manage SocialNetworks
        mSocialNetworkManager = (SocialNetworkManager) context.getFragmentManager().findFragmentByTag(MainActivity.SOCIAL_NETWORK_TAG);

        //Check if manager exist
        if (mSocialNetworkManager == null || mSocialNetworkManager.getInitializedSocialNetworks().isEmpty()) {
            mSocialNetworkManager = new SocialNetworkManager();

            //Init and add to manager FacebookSocialNetwork
            FacebookSocialNetwork fbNetwork = new FacebookSocialNetwork(context, fbScope);
            mSocialNetworkManager.addSocialNetwork(fbNetwork);
            //Initiate every network from mSocialNetworkManager
            context.getFragmentManager().beginTransaction().add(mSocialNetworkManager, MainActivity.SOCIAL_NETWORK_TAG).commit();
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


    }

    public boolean isConnected() {
        SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(FacebookSocialNetwork.ID);

        return socialNetwork.isConnected();
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
    public void onLoginSuccess(int i) {

        SocialNetwork socialNetwork = mSocialNetworkManager.getSocialNetwork(i);

        socialNetwork.setOnRequestDetailedSocialPersonCompleteListener(this);
        socialNetwork.requestDetailedCurrentPerson();
    }


    @Override
    public void onError(int networkId, String requestID, String errorMessage, Object data) {

        Toast.makeText(this.context.getActivity(), "ERROR: No se encontró conexión a internet", Toast.LENGTH_LONG).show();


    }


    public void obtenerAmigosFacebook() {

        if (mSocialNetworkManager.getSocialNetwork(FacebookSocialNetwork.ID).isConnected()) {
            try {
                mSocialNetworkManager.getSocialNetwork(FacebookSocialNetwork.ID).requestGetFriends(new OnRequestGetFriendsCompleteListener() {
                    @Override
                    public void OnGetFriendsIdComplete(int i, String[] strings) {

                    }

                    @Override
                    public void OnGetFriendsComplete(int i, ArrayList<SocialPerson> arrayList) {

                        if (frindHandler != null) {
                            frindHandler.onSuccess(arrayList);
                        }


                    }

                    @Override
                    public void onError(int i, String s, String s1, Object o) {

                        if (frindHandler != null) {
                            frindHandler.onError(i, s, s1, o);
                        }
                    }
                });
            } catch (Exception e) {
                Toast.makeText(this.context.getActivity(), "Error de conexión", Toast.LENGTH_LONG).show();
            }
        } else {
            mSocialNetworkManager.getSocialNetwork(FacebookSocialNetwork.ID).requestLogin();
        }

    }


    @Override
    public void onRequestDetailedSocialPersonSuccess(int i, SocialPerson socialPerson) {

        if (loginHandler != null) {
            this.loginHandler.onSuccess(socialPerson);
        }
    }

    public IFriendHandler getFrindHandler() {
        return frindHandler;
    }

    public void setFrindHandler(IFriendHandler frindHandler) {
        this.frindHandler = frindHandler;
    }

    public ILoginHandler getLoginHandler() {
        return loginHandler;
    }

    public void setLoginHandler(ILoginHandler loginHandler) {
        this.loginHandler = loginHandler;
    }

    public interface IFriendHandler extends SocialNetworkListener {

        void onSuccess(List<SocialPerson> amigos);


    }

    public interface ILoginHandler extends SocialNetworkListener {

        void onSuccess(SocialPerson usuario);


    }
}
