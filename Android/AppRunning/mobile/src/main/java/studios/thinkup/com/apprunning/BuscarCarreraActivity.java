package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class BuscarCarreraActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_buscar_carrera_categorias_iniciales);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buscar_carrera, menu);
        LinearLayout categoria = (LinearLayout)findViewById(R.id.btn_zona);
        categoria.setOnClickListener(this);
        categoria = (LinearLayout)findViewById(R.id.btn_distancia);
        categoria.setOnClickListener(this);
        categoria = (LinearLayout)findViewById(R.id.btn_genero);
        categoria.setOnClickListener(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mnu_filtros:
                Intent intentFiltros = new Intent(this, FiltrosActivity.class);
                startActivity(intentFiltros);
                return true;

            case R.id.mnu_mis_datos:
                Intent intentDatos = new Intent(this, MisDatosActivity.class);
                startActivity(intentDatos);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_zona:{

                break;

            }
            case R.id.btn_distancia:{

                break;

            }
            case R.id.btn_genero:{

                break;

            }
        }
        Intent intent = new Intent(this, SubcategoriaActivity.class);
        startActivity(intent);
    }
}
