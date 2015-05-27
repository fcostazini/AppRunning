package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import studios.thinkup.com.apprunning.adapter.DetalleCarreraPagerAdapter;
import studios.thinkup.com.apprunning.model.Carrera;
import studios.thinkup.com.apprunning.provider.CarrerasProvider;

/**
 * Created by fcostazini on 21/05/2015.
 * Detalle de Carrera
 */
public class DetalleCarreraActivity extends DrawerPagerActivity {
    private Carrera carrera;

    @Override
    protected ViewPager setPagerAdapter() {
        CarrerasProvider provider = new CarrerasProvider(this);
        Bundle b = getIntent().getExtras();
        int codigo;
        if(b!=null){
            codigo = b.getInt(Carrera.ID);
            this.carrera = provider.getCarreraByCodigo(codigo);
        }else{

        }
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(new DetalleCarreraPagerAdapter(getSupportFragmentManager(), this.carrera));
        return viewPager;
    }
}

