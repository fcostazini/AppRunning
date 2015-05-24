package studios.thinkup.com.apprunning.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import studios.thinkup.com.apprunning.R;

/**
 * Created by fcostazini on 21/05/2015.
 */
public class DetalleCarreraFragment extends Fragment {
    public static final String ARG_SECTION_NAME = "section_name";
    public static final String ARG_SECTION_IMAGE = "section_image";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detalle_carrera, container, false);


        return rootView;
    }
}
