package studios.thinkup.com.apprunning.provider;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import com.github.gorbin.asne.core.AccessToken;
import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.SocialNetworkException;
import com.github.gorbin.asne.core.listener.OnCheckIsFriendCompleteListener;
import com.github.gorbin.asne.core.listener.OnLoginCompleteListener;
import com.github.gorbin.asne.core.listener.OnPostingCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestAccessTokenCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestAddFriendCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestDetailedSocialPersonCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestGetFriendsCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestRemoveFriendCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonsCompleteListener;
import com.github.gorbin.asne.core.listener.base.SocialNetworkListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.github.gorbin.asne.googleplus.GooglePlusPerson;
import com.github.gorbin.asne.googleplus.MomentUtil;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.xml.transform.Result;

/**
 * Created by FaQ on 13/06/2015.
 */
public class LoginGoogleProvider extends SocialNetwork implements GooglePlayServicesClient.ConnectionCallbacks, GooglePlayServicesClient.OnConnectionFailedListener, com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks, com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener {
    public static final int ID = 3;
    private static final String TAG = LoginGoogleProvider.class.getSimpleName();
    private static final int REQUEST_AUTH = UUID.randomUUID().hashCode() & '\uffff';
    private static final String SAVE_STATE_KEY_IS_CONNECTED = "LoginGoogleProvider.SAVE_STATE_KEY_OAUTH_TOKEN";
    private static boolean LOGOUT = false;
    private static Activity mActivity;
    private GoogleApiClient googleApiClient;
    private ConnectionResult mConnectionResult;
    private boolean mConnectRequested;
    private Handler mHandler = new Handler();

    public GoogleApiClient getGoogleApiClient() {
        return googleApiClient;
    }

    public LoginGoogleProvider(Fragment fragment) {
        super(fragment);
    }

    public boolean isConnected() {
        return this.mSharedPreferences.getBoolean("LoginGoogleProvider.SAVE_STATE_KEY_OAUTH_TOKEN", false);
    }

    public void requestLogin(OnLoginCompleteListener onLoginCompleteListener) {
        super.requestLogin(onLoginCompleteListener);
        this.mConnectRequested = true;

        try {
            this.mConnectionResult.startResolutionForResult(mActivity, REQUEST_AUTH);
        } catch (Exception var3) {
            if (!this.googleApiClient.isConnecting()) {
                this.googleApiClient.connect();
            }
        }

    }

    public void logout() {
        this.mConnectRequested = false;
        LoginGoogleProvider.LOGOUT = true;
        if (this.googleApiClient.isConnected()) {
            this.mSharedPreferences.edit().remove("LoginGoogleProvider.SAVE_STATE_KEY_OAUTH_TOKEN").commit();
            Plus.AccountApi.revokeAccessAndDisconnect(this.googleApiClient);
            this.googleApiClient.disconnect();
            this.googleApiClient.connect();
        }


    }

    public int getID() {
        return 3;
    }

    public AccessToken getAccessToken() {
        throw new SocialNetworkException("Not supported for LoginGoogleProvider");
    }

    public void requestAccessToken(OnRequestAccessTokenCompleteListener onRequestAccessTokenCompleteListener) {
        super.requestAccessToken(onRequestAccessTokenCompleteListener);
        AsyncTask task = new AsyncTask<Activity, String, String>() {
            protected String doInBackground(Activity... params) {
                String scope = "oauth2:https://www.googleapis.com/auth/plus.login";

                try {
                    String token = GoogleAuthUtil.getToken(params[0], Plus.AccountApi.getAccountName(LoginGoogleProvider.this.googleApiClient), scope);
                    return token;
                } catch (Exception var5) {
                    var5.printStackTrace();
                    return var5.getMessage();
                }
            }

            protected void onPostExecute(String token) {
                if (token != null) {
                    ((OnRequestAccessTokenCompleteListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_ACCESS_TOKEN")).onRequestAccessTokenComplete(LoginGoogleProvider.this.getID(), new AccessToken(token, (String) null));
                } else {
                    ((SocialNetworkListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_ACCESS_TOKEN")).onError(LoginGoogleProvider.this.getID(), "SocialNetwork.REQUEST_ACCESS_TOKEN", token, (Object) null);
                }

            }
        };
        task.execute(new Activity[]{mActivity});
    }

    public void requestCurrentPerson(OnRequestSocialPersonCompleteListener onRequestSocialPersonCompleteListener) {
        super.requestCurrentPerson(onRequestSocialPersonCompleteListener);
        this.requestPerson("me", onRequestSocialPersonCompleteListener);
    }

    public void requestSocialPerson(String userID, OnRequestSocialPersonCompleteListener onRequestSocialPersonCompleteListener) {
        super.requestSocialPerson(userID, onRequestSocialPersonCompleteListener);
        this.requestPerson(userID, onRequestSocialPersonCompleteListener);
    }

    public void requestSocialPersons(final String[] userID, OnRequestSocialPersonsCompleteListener onRequestSocialPersonsCompleteListener) {
        super.requestSocialPersons(userID, onRequestSocialPersonsCompleteListener);
        Plus.PeopleApi.load(this.googleApiClient, userID).setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
            public void onResult(People.LoadPeopleResult loadPeopleResult) {
                if (loadPeopleResult.getStatus().getStatusCode() == 0) {
                    PersonBuffer personBuffer = loadPeopleResult.getPersonBuffer();

                    try {
                        int count = personBuffer.getCount();
                        SocialPerson socialPerson = new SocialPerson();
                        ArrayList socialPersons = new ArrayList();

                        for (int i = 0; i < count; ++i) {
                            LoginGoogleProvider.this.getSocialPerson(socialPerson, personBuffer.get(i), userID[i]);
                            socialPersons.add(socialPerson);
                            socialPerson = new SocialPerson();
                        }

                        if (LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_PERSONS") != null) {
                            ((OnRequestSocialPersonsCompleteListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_PERSONS")).onRequestSocialPersonsSuccess(LoginGoogleProvider.this.getID(), socialPersons);
                            LoginGoogleProvider.this.mLocalListeners.remove("SocialNetwork.REQUEST_GET_PERSONS");
                        }
                    } finally {
                        personBuffer.close();
                    }
                } else if (LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_PERSONS") != null) {
                    ((SocialNetworkListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_PERSONS")).onError(LoginGoogleProvider.this.getID(), "SocialNetwork.REQUEST_GET_PERSONS", "Can\'t get persons" + loadPeopleResult.getStatus(), (Object) null);
                    LoginGoogleProvider.this.mLocalListeners.remove("SocialNetwork.REQUEST_GET_PERSONS");
                }

            }
        });
    }

    public void requestDetailedSocialPerson(String userId, OnRequestDetailedSocialPersonCompleteListener onRequestDetailedSocialPersonCompleteListener) {
        super.requestDetailedSocialPerson(userId, onRequestDetailedSocialPersonCompleteListener);
        final String user = userId == null ? "me" : userId;
        Plus.PeopleApi.load(this.googleApiClient, new String[]{user}).setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
            public void onResult(final People.LoadPeopleResult loadPeopleResult) {
                if (loadPeopleResult.getStatus().getStatusCode() == 0) {
                    PersonBuffer personBuffer = loadPeopleResult.getPersonBuffer();

                    try {
                        int count = personBuffer.getCount();
                        GooglePlusPerson googlePlusPerson = new GooglePlusPerson();

                        for (int i = 0; i < count; ++i) {
                            LoginGoogleProvider.this.getDetailedSocialPerson(googlePlusPerson, personBuffer.get(i), user);
                        }

                        if (LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_DETAIL_PERSON") != null) {
                            ((OnRequestDetailedSocialPersonCompleteListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_DETAIL_PERSON")).onRequestDetailedSocialPersonSuccess(LoginGoogleProvider.this.getID(), googlePlusPerson);
                            LoginGoogleProvider.this.mLocalListeners.remove("SocialNetwork.REQUEST_GET_DETAIL_PERSON");
                        }
                    } finally {
                        personBuffer.close();
                    }
                } else if (LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_DETAIL_PERSON") != null) {
                    LoginGoogleProvider.this.mHandler.post(new Runnable() {
                        public void run() {
                            ((SocialNetworkListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_DETAIL_PERSON")).onError(LoginGoogleProvider.this.getID(), "SocialNetwork.REQUEST_GET_DETAIL_PERSON", "Can\'t get person" + loadPeopleResult.getStatus(), (Object) null);
                            LoginGoogleProvider.this.mLocalListeners.remove("SocialNetwork.REQUEST_GET_DETAIL_PERSON");
                        }
                    });
                }

            }
        });
    }

    private void requestPerson(final String userID, OnRequestSocialPersonCompleteListener onRequestSocialPersonCompleteListener) {
        Plus.PeopleApi.load(this.googleApiClient, new String[]{userID}).setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
            public void onResult(People.LoadPeopleResult loadPeopleResult) {
                if (loadPeopleResult.getStatus().getStatusCode() == 0) {
                    PersonBuffer personBuffer = loadPeopleResult.getPersonBuffer();

                    try {
                        int count = personBuffer.getCount();
                        SocialPerson socialPerson = new SocialPerson();

                        for (int i = 0; i < count; ++i) {
                            LoginGoogleProvider.this.getSocialPerson(socialPerson, personBuffer.get(i), userID);
                        }

                        if (LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_PERSON") != null) {
                            ((OnRequestSocialPersonCompleteListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_PERSON")).onRequestSocialPersonSuccess(LoginGoogleProvider.this.getID(), socialPerson);
                            LoginGoogleProvider.this.mLocalListeners.remove("SocialNetwork.REQUEST_GET_PERSON");
                        } else if (LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_CURRENT_PERSON") != null) {
                            ((OnRequestSocialPersonCompleteListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_CURRENT_PERSON")).onRequestSocialPersonSuccess(LoginGoogleProvider.this.getID(), socialPerson);
                            LoginGoogleProvider.this.mLocalListeners.remove("SocialNetwork.REQUEST_GET_CURRENT_PERSON");
                        }
                    } finally {
                        personBuffer.close();
                    }
                } else if (LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_PERSON") != null) {
                    ((SocialNetworkListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_PERSON")).onError(LoginGoogleProvider.this.getID(), "SocialNetwork.REQUEST_GET_PERSON", "Can\'t get person" + loadPeopleResult.getStatus(), (Object) null);
                    LoginGoogleProvider.this.mLocalListeners.remove("SocialNetwork.REQUEST_GET_PERSON");
                } else if (LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_CURRENT_PERSON") != null) {
                    ((SocialNetworkListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_CURRENT_PERSON")).onError(LoginGoogleProvider.this.getID(), "SocialNetwork.REQUEST_GET_CURRENT_PERSON", "Can\'t get person" + loadPeopleResult.getStatus(), (Object) null);
                    LoginGoogleProvider.this.mLocalListeners.remove("SocialNetwork.REQUEST_GET_CURRENT_PERSON");
                }

            }
        });
    }

    private SocialPerson getSocialPerson(SocialPerson socialPerson, Person person, String userId) {
        socialPerson.id = person.getId();
        socialPerson.name = person.getDisplayName();
        if (person.hasImage() && person.getImage().hasUrl()) {
            socialPerson.avatarURL = person.getImage().getUrl().replace("?sz=50", "?sz=200");
        }

        socialPerson.profileURL = person.getUrl();
        if (userId.equals("me")) {
            socialPerson.email = Plus.AccountApi.getAccountName(this.googleApiClient);
        }

        return socialPerson;
    }

    private GooglePlusPerson getDetailedSocialPerson(GooglePlusPerson googlePlusPerson, Person person, String userId) {
        this.getSocialPerson(googlePlusPerson, person, userId);
        googlePlusPerson.aboutMe = person.getAboutMe();
        googlePlusPerson.birthday = person.getBirthday();
        googlePlusPerson.braggingRights = person.getBraggingRights();
        Person.Cover cover = person.getCover();
        String placesLived;
        if (cover != null) {
            Person.Cover.CoverPhoto organizations = cover.getCoverPhoto();
            if (organizations != null) {
                placesLived = organizations.getUrl();
                if (placesLived != null) {
                    googlePlusPerson.coverURL = placesLived;
                }
            }
        }

        googlePlusPerson.currentLocation = person.getCurrentLocation();
        googlePlusPerson.gender = person.getGender();
        googlePlusPerson.lang = person.getLanguage();
        googlePlusPerson.nickname = person.getNickname();
        googlePlusPerson.objectType = person.getObjectType();
        List organizations1 = person.getOrganizations();
        String placeLivedValue;
        if (organizations1 != null && organizations1.size() > 0) {
            placesLived = ((Person.Organizations) organizations1.get(organizations1.size() - 1)).getName();
            if (placesLived != null) {
                googlePlusPerson.company = placesLived;
            }

            placeLivedValue = ((Person.Organizations) organizations1.get(organizations1.size() - 1)).getTitle();
            if (placeLivedValue != null) {
                googlePlusPerson.position = placeLivedValue;
            }
        }

        List placesLived1 = person.getPlacesLived();
        if (placesLived1 != null && placesLived1.size() > 0) {
            placeLivedValue = ((Person.PlacesLived) placesLived1.get(placesLived1.size() - 1)).getValue();
            if (placeLivedValue != null) {
                googlePlusPerson.placeLivedValue = placeLivedValue;
            }
        }

        googlePlusPerson.relationshipStatus = person.getRelationshipStatus();
        googlePlusPerson.tagline = person.getTagline();
        return googlePlusPerson;
    }

    public void requestPostMessage(String message, OnPostingCompleteListener onPostingCompleteListener) {
        throw new SocialNetworkException("requestPostMessage isn\'t allowed for LoginGoogleProvider");
    }

    public void requestPostPhoto(File photo, String message, OnPostingCompleteListener onPostingCompleteListener) {
        throw new SocialNetworkException("requestPostPhoto isn\'t allowed for LoginGoogleProvider");
    }

    public void requestPostLink(Bundle bundle, String message, OnPostingCompleteListener onPostingCompleteListener) {
        throw new SocialNetworkException("requestPostLink isn\'t allowed for LoginGoogleProvider");
    }

    public void requestPostDialog(Bundle bundle, OnPostingCompleteListener onPostingCompleteListener) {
        super.requestPostDialog(bundle, onPostingCompleteListener);
        PlusShare.Builder plusShare = (new PlusShare.Builder(mActivity)).setType("text/plain");
        if (bundle != null) {
            if (bundle.containsKey("message")) {
                plusShare.setText(bundle.getString("message"));
            }

            if (bundle.containsKey("link")) {
                plusShare.setContentUrl(Uri.parse(bundle.getString("link")));
            }
        }

        Intent shareIntent = plusShare.getIntent();
        mActivity.startActivityForResult(shareIntent, 0);
    }

    public void requestCheckIsFriend(String userID, OnCheckIsFriendCompleteListener onCheckIsFriendCompleteListener) {
        throw new SocialNetworkException("requestCheckIsFriend isn\'t allowed for LoginGoogleProvider");
    }

    public void requestGetFriends(OnRequestGetFriendsCompleteListener onRequestGetFriendsCompleteListener) {
        super.requestGetFriends(onRequestGetFriendsCompleteListener);
        ArrayList socialPersons = new ArrayList();
        ArrayList ids = new ArrayList();
        this.getAllFriends((String) null, socialPersons, ids);
    }

    private void getAllFriends(String pageToken, final ArrayList<SocialPerson> socialPersons, final ArrayList<String> ids) {
        Plus.PeopleApi.loadVisible(this.googleApiClient, pageToken).setResultCallback(new ResultCallback<People.LoadPeopleResult>() {
            public void onResult(People.LoadPeopleResult loadPeopleResult) {
                if (loadPeopleResult.getStatus().getStatusCode() == 0) {
                    PersonBuffer personBuffer = loadPeopleResult.getPersonBuffer();

                    try {
                        SocialPerson socialPerson = new SocialPerson();

                        for (Iterator var4 = personBuffer.iterator(); var4.hasNext(); socialPerson = new SocialPerson()) {
                            Person person = (Person) var4.next();
                            LoginGoogleProvider.this.getSocialPerson(socialPerson, person, "not me");
                            ids.add(person.getId());
                            socialPersons.add(socialPerson);
                        }

                        if (loadPeopleResult.getNextPageToken() != null) {
                            LoginGoogleProvider.this.getAllFriends(loadPeopleResult.getNextPageToken(), socialPersons, ids);
                        } else if (LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_FRIENDS") != null) {
                            ((OnRequestGetFriendsCompleteListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_FRIENDS")).OnGetFriendsIdComplete(LoginGoogleProvider.this.getID(), (String[]) ids.toArray(new String[ids.size()]));
                            ((OnRequestGetFriendsCompleteListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_FRIENDS")).OnGetFriendsComplete(LoginGoogleProvider.this.getID(), socialPersons);
                            LoginGoogleProvider.this.mLocalListeners.remove("SocialNetwork.REQUEST_GET_FRIENDS");
                        }
                    } finally {
                        personBuffer.close();
                    }
                } else if (LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_FRIENDS") != null) {
                    ((SocialNetworkListener) LoginGoogleProvider.this.mLocalListeners.get("SocialNetwork.REQUEST_GET_FRIENDS")).onError(LoginGoogleProvider.this.getID(), "SocialNetwork.REQUEST_GET_DETAIL_PERSON", "Can\'t get person" + loadPeopleResult.getStatus(), (Object) null);
                }

            }
        });
    }

    public void requestAddFriend(String userID, OnRequestAddFriendCompleteListener onRequestAddFriendCompleteListener) {
        throw new SocialNetworkException("requestAddFriend isn\'t allowed for LoginGoogleProvider");
    }

    public void requestRemoveFriend(String userID, OnRequestRemoveFriendCompleteListener onRequestRemoveFriendCompleteListener) {
        throw new SocialNetworkException("requestRemoveFriend isn\'t allowed for LoginGoogleProvider");
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this.mSocialNetworkManager.getActivity();
        Plus.PlusOptions plusOptions = (new com.google.android.gms.plus.Plus.PlusOptions.Builder()).addActivityTypes(MomentUtil.ACTIONS).build();
        this.googleApiClient = (new com.google.android.gms.common.api.GoogleApiClient.Builder(mActivity)).addApi(Plus.API, plusOptions).addScope(Plus.SCOPE_PLUS_LOGIN).addScope(Plus.SCOPE_PLUS_PROFILE).addConnectionCallbacks(this).addOnConnectionFailedListener(this).build();
    }

    public void onStart() {
        this.googleApiClient.connect();
    }

    public void onStop() {
        if (this.googleApiClient.isConnected()) {
            this.googleApiClient.disconnect();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int sanitizedRequestCode = requestCode % 65536;
        if (sanitizedRequestCode == REQUEST_AUTH) {
            if (resultCode == -1 && !this.googleApiClient.isConnected() && !this.googleApiClient.isConnecting()) {
                this.googleApiClient.connect();
            } else if (resultCode == 0 && this.mLocalListeners.get("SocialNetwork.REQUEST_LOGIN") != null) {
                ((SocialNetworkListener) this.mLocalListeners.get("SocialNetwork.REQUEST_LOGIN")).onError(this.getID(), "SocialNetwork.REQUEST_LOGIN", "canceled", (Object) null);
            }
        }

    }

    public void onConnected(Bundle bundle) {
        if (this.mConnectRequested) {
            if (this.mLocalListeners.get("SocialNetwork.REQUEST_LOGIN") != null) {
                this.mSharedPreferences.edit().putBoolean("LoginGoogleProvider.SAVE_STATE_KEY_OAUTH_TOKEN", true).commit();
                ((OnLoginCompleteListener) this.mLocalListeners.get("SocialNetwork.REQUEST_LOGIN")).onLoginSuccess(this.getID());
                return;
            }

            if (this.mLocalListeners.get("SocialNetwork.REQUEST_LOGIN") != null) {
                ((SocialNetworkListener) this.mLocalListeners.get("SocialNetwork.REQUEST_LOGIN")).onError(this.getID(), "SocialNetwork.REQUEST_LOGIN", "get person == null", (Object) null);
            }

        } else {
            if (LoginGoogleProvider.LOGOUT) {
                Plus.AccountApi.clearDefaultAccount(this.googleApiClient);
                this.googleApiClient.disconnect();
                LoginGoogleProvider.LOGOUT = false;

            }
        }

        this.mConnectRequested = false;
    }

    public void onConnectionSuspended(int i) {
        if (this.mLocalListeners.get("SocialNetwork.REQUEST_LOGIN") != null) {
            ((SocialNetworkListener) this.mLocalListeners.get("SocialNetwork.REQUEST_LOGIN")).onError(this.getID(), "SocialNetwork.REQUEST_LOGIN", "get person == null", (Object) null);
        }

        this.mConnectRequested = false;
    }

    public void onDisconnected() {
        this.mConnectRequested = false;
    }

    public void onConnectionFailed(ConnectionResult connectionResult) {
        this.mConnectionResult = connectionResult;
        if (this.mConnectRequested && this.mLocalListeners.get("SocialNetwork.REQUEST_LOGIN") != null) {
            ((SocialNetworkListener) this.mLocalListeners.get("SocialNetwork.REQUEST_LOGIN")).onError(this.getID(), "SocialNetwork.REQUEST_LOGIN", "error: " + connectionResult.getErrorCode(), (Object) null);
        }

        this.mConnectRequested = false;
    }
}

