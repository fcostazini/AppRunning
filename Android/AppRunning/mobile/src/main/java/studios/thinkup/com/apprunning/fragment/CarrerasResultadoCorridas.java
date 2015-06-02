package studios.thinkup.com.apprunning.fragment;

import android.os.Bundle;

import studios.thinkup.com.apprunning.model.Filtro;

/**
 * Created by FaQ on 01/06/2015.
 */
public class CarrerasResultadoCorridas extends CarrerasResultadoFragment {
    @Override
    public String getIdFragment() {
        return "CORRIDAS";
    }

    public static CarrerasResultadoCorridas newInstance(Filtro filtro) {
        CarrerasResultadoCorridas fragment = new CarrerasResultadoCorridas();
        Bundle args = new Bundle();
        args.putSerializable(Filtro.class.getSimpleName() + fragment.getIdFragment(),  filtro);
        fragment.setArguments(args);
        return fragment;
    }
}
