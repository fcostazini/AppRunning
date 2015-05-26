package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import studios.thinkup.com.apprunning.adapter.CategoriaListAdapter;
import studios.thinkup.com.apprunning.model.Categoria;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.Genero;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.provider.CarrerasProvider;
import studios.thinkup.com.apprunning.provider.CategoriaProvider;


public class SubcategoriaActivity extends Activity implements AdapterView.OnItemClickListener {
    private CategoriaProvider categoriaProvider;
    private CarrerasProvider carrerasProvider;
    private Filtro filtro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (categoriaProvider == null) {
            categoriaProvider = new CategoriaProvider();
        }
        if (this.carrerasProvider == null)
            this.carrerasProvider = new CarrerasProvider();


        if (getIntent().getExtras() != null && getIntent().getSerializableExtra(Filtro.class.getSimpleName()) != null) {
            this.filtro = (Filtro) getIntent().getExtras().getSerializable(Filtro.class.getSimpleName());
        } else {
            this.filtro = new Filtro(((RunningApplication) getApplication()).getDefaultSettings());
        }
        if (this.carrerasProvider.getCantidadCarreras(filtro) > 20) {
            List<Categoria> resultados = categoriaProvider.getCategorias(this.filtro);
            if (resultados.size() > 1) {
                setContentView(R.layout.fragment_subcategoria_busqueda);
                CategoriaListAdapter adapter = new CategoriaListAdapter(this,
                        android.R.layout.simple_list_item_1, categoriaProvider.getCategorias(this.filtro));
                ListView list = (ListView) findViewById(R.id.list_subcategoria);
                list.setAdapter(adapter);
                list.setOnItemClickListener(this);
            } else {
                setContentView(R.layout.sin_resultados);
            }
        } else {
            Intent intent = new Intent(this, CarrerasActivity.class);
            intent.putExtra(Filtro.class.getSimpleName(), this.filtro);
            startActivity(intent);

        }

    }

    private void goToNextCategory(Categoria categoria) {
        Intent intent = new Intent(this, SubcategoriaActivity.class);

        switch (this.filtro.getSubcategoria()) {
            case ZONA:
                this.filtro.setZona(categoria.getNombre());
                break;
            case GENERO:
                this.filtro.setGenero(Genero.getByName(categoria.getNombre()));
                break;
            case DISTANCIA:
                String s = categoria.getNombre().replaceAll("[^\\d.]", "");
                this.filtro.setDistanciaMin(Integer.valueOf(s));
                this.filtro.setDistanciaMax(Integer.valueOf(s));
                break;
        }
        this.filtro.setSubcategoria(filtro.nextCategoria(categoria.getTipo()));
        intent.putExtra(Filtro.class.getSimpleName(), this.filtro);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buscar_carrera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.mnu_filtros:
                i = new Intent(this, FiltrosPorDefectoActivity.class);
                startActivity(i);
                return true;

            case R.id.mnu_mis_datos:
                i = new Intent(this, MisDatosActivity.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Categoria c = (Categoria) adapterView.getItemAtPosition(i);


        if (c.getCantidad() > 10) {
            this.goToNextCategory(c);
        } else {
            Intent intent = new Intent(this, CarrerasActivity.class);

            intent.putExtra(Filtro.class.getSimpleName(), this.filtro);
            startActivity(intent);
        }


    }
}
