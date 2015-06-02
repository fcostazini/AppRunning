package studios.thinkup.com.apprunning.fragment;

import android.os.Bundle;

import studios.thinkup.com.apprunning.model.Filtro;

/**
 * Created by FaQ on 01/06/2015.
 */
public class CarrerasResultadosFavoritos extends  CarrerasResultadoFragment{
    @Override
    public String getIdFragment() {
        return "FAVORITO";
    }

    public static CarrerasResultadosFavoritos newInstance(Filtro filtro) {
        CarrerasResultadosFavoritos fragment = new CarrerasResultadosFavoritos();
        Bundle args = new Bundle();
        args.putSerializable(Filtro.class.getSimpleName() + fragment.getIdFragment(),  filtro);
        fragment.setArguments(args);
        return fragment;
    }
}
