package studios.thinkup.com.apprunning.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import studios.thinkup.com.apprunning.AcecaDeNosotrosActivity;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;

/**
 * Fragment de datos de usuario
 */
public class AcercaDeNosotrosFragment extends Fragment implements View.OnClickListener {
    private UsuarioApp ua;
    private static final String FACEBOOK_URL = "https://www.facebook.com/167591430932";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_acerca_de_nosotros, container, false);
        initActivity(savedInstanceState);
        initView(rootView);
        return rootView;
    }

    private void initActivity(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("usuario")) {
                this.ua = (UsuarioApp) savedInstanceState.getSerializable("usuario");
            }

        } else {
            if (this.getActivity().getIntent().getExtras() != null) {
                if (this.getActivity().getIntent().getExtras().containsKey("usuario")) {
                    this.ua = (UsuarioApp) this.getActivity().getIntent()
                            .getExtras().getSerializable("usuario");
                }


            }
        }
        if (this.ua == null) {
            if (((RunningApplication) this.getActivity().getApplication()).getUsuario() != null) {
                this.ua = ((RunningApplication) this.getActivity().getApplication()).getUsuario();
            } else {
                this.ua = new UsuarioApp();
            }
        }
    }

    private void initView(View rootView) {
        RelativeLayout seguinos = (RelativeLayout) rootView.findViewById(R.id.seguinos);
        seguinos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = null;

                Uri uri;
                try {
                    AcercaDeNosotrosFragment.this.getActivity().getPackageManager().getPackageInfo("com.facebook.katana", 0);
                    // http://stackoverflow.com/a/24547437/1048340
                    uri = Uri.parse("fb://facewebmodal/f?href=" + "https://www.facebook.com/fusionbarclub");
                } catch (PackageManager.NameNotFoundException e) {
                    uri = Uri.parse("https://www.facebook.com/fusionbarclub");
                }
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }

        });

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (this.ua != null) {
            outState.putSerializable("usuario", this.ua);
        }

    }

    @Override
    public void onClick(View v) {

    }

}
