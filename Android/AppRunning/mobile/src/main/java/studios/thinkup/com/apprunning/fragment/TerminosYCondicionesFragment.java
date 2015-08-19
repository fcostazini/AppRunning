package studios.thinkup.com.apprunning.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import studios.thinkup.com.apprunning.R;

/**
 */
public class TerminosYCondicionesFragment extends Fragment {

    public TerminosYCondicionesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_terminos_y_condiciones, container, false);
        Button button = (Button) rootView.findViewById(R.id.btn_aceptar);
        button.setVisibility(View.GONE);
        return rootView;
    }


}
