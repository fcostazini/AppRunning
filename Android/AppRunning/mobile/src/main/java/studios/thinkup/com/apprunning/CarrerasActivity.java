package studios.thinkup.com.apprunning;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;

import com.astuetz.PagerSlidingTabStrip;

import studios.thinkup.com.apprunning.adapter.ResultadoCarrerasPagerAdapter;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.provider.CarrerasProvider;


public class CarrerasActivity extends DrawerPagerActivity implements AdapterView.OnItemClickListener {

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
    }

    @Override
    protected ViewPager setPagerAdapter() {
        ViewPager v = ((ViewPager) findViewById(R.id.viewpager));
        v.setAdapter(new ResultadoCarrerasPagerAdapter(getSupportFragmentManager(), this.filtro));
        return v;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent = new Intent(this, DetalleCarreraActivity.class);
        startActivity(intent);
    }


}
