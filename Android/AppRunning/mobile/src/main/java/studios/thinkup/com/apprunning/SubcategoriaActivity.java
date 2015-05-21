package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class SubcategoriaActivity extends Activity implements AdapterView.OnItemClickListener{
    String[] categorias = {"Categoria1","Categoria2"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_subcategoria_busqueda);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, categorias);

        ListView list = (ListView)findViewById(R.id.list_subcategoria);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buscar_carrera, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_filtros:
                Intent i = new Intent(this, FiltrosActivity.class);
                startActivity(i);
                return true;

            case R.id.mnu_mis_datos:
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
    if(adapterView.getItemAtPosition(i).equals("Categoria2")){
        Intent intent = new Intent(this, CarrerasActivity.class);
        startActivity(intent);

    }else{
        Intent intent = new Intent(this, SubcategoriaActivity.class);
        startActivity(intent);
    }
    }
}
