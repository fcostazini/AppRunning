package studios.thinkup.com.apprunning.fragment;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.List;

import studios.thinkup.com.apprunning.MainActivity;
import studios.thinkup.com.apprunning.StartUpActivity;
import studios.thinkup.com.apprunning.adapter.UsuarioListAdapter;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.UsuarioProvider;

/**
 * A fragment representing a list of Items.
 * <p/>
 * <p/>
 * interface.
 */
public class SelectorUsuarioFragment extends ListFragment {


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        UsuarioProvider up = new UsuarioProvider(this.getActivity());
        List<UsuarioApp> usuarios = up.findAll(UsuarioApp.class);

        if (usuarios.size() == 0) {
            Intent i = new Intent(this.getActivity(), MainActivity.class);
            startActivity(i);

        } else if (usuarios.size() == 1) {
            this.singleUserLogin(usuarios.get(0));
        } else {
            setListAdapter(new UsuarioListAdapter(this.getActivity(), usuarios));
        }

    }

    private void singleUserLogin(UsuarioApp usuarioApp) {
        Intent i = new Intent(this.getActivity(), StartUpActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("usuario", usuarioApp);
        i.putExtras(b);
        startActivity(i);

    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        UsuarioApp u = (UsuarioApp) l.getItemAtPosition(position);
        singleUserLogin(u);


    }

}
