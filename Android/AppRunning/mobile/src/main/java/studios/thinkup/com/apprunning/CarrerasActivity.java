package studios.thinkup.com.apprunning;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import studios.thinkup.com.apprunning.fragment.CarrerasResultadoFragment;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;


public class CarrerasActivity extends MainNavigationActivity {

    private Filtro filtro;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        if( getIntent().getExtras() != null && getIntent().getSerializableExtra(Filtro.class.getSimpleName()) != null) {
            this.filtro = (Filtro) getIntent().getExtras().getSerializable(Filtro.class.getSimpleName());
        }else{
            this.filtro = new Filtro(((RunningApplication)this.getApplication()).getDefaultSettings());
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CarrerasResultadoFragment fragment = CarrerasResultadoFragment.newInstance(this.filtro);
        fragmentTransaction.add(R.id.content_fragment, fragment);
        fragmentTransaction.commit();

    }

    @Override
    protected void defineContentView() {
        setContentView(R.layout.activity_resultados);
    }


}
