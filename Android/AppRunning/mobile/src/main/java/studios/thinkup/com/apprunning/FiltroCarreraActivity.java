package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

import studios.thinkup.com.apprunning.dialogs.DatePicker;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.CamposOrdenEnum;
import studios.thinkup.com.apprunning.model.entity.Modalidad;
import studios.thinkup.com.apprunning.provider.FiltrosProvider;


public class FiltroCarreraActivity extends ResultadosFiltrablesActivity implements View.OnClickListener {
    private Class<? extends Activity> caller;

    public void updateVisibilidadCiudad() {
        TextView provincia = (TextView) this.findViewById(R.id.txt_provincia);
        RelativeLayout ciudad = (RelativeLayout) findViewById(R.id.ly_ciudad);
        if (!provincia.getText().toString().equals(FiltrosProvider.TODAS_LAS_PROVINCIAS) &&
                !provincia.getText().toString().equals(FiltrosProvider.CAPITAL)) {
            ciudad.setVisibility(View.VISIBLE);
        } else {
            ciudad.setVisibility(View.GONE);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initCaller();
            Button btn = (Button) findViewById(R.id.btn_filtrar);
            btn.setOnClickListener(this);
            ChangeManager cm = new ChangeManager(this.filtro);

            RelativeLayout campoOrden = (RelativeLayout) findViewById(R.id.campo_click);
            TextView txtCampo = (TextView) campoOrden.findViewById(R.id.txt_campo);
            txtCampo.setText(this.filtro.getOrdenarPor());
            campoOrden.setOnClickListener(new DropDownFilter(this, txtCampo, CamposOrdenEnum.getLabels(), cm));

            RelativeLayout sentidoOrden = (RelativeLayout) findViewById(R.id.sentido_click);
            TextView txtSentido = (TextView) sentidoOrden.findViewById(R.id.txt_sentido);
            txtSentido.setText(this.filtro.getSentido());
            sentidoOrden.setOnClickListener(new DropDownFilter(this, txtSentido, Filtro.SENTIDO_ORDEN, cm));

            RelativeLayout modalidad = (RelativeLayout) findViewById(R.id.modalidad_click);
            TextView txtModalidad = (TextView) modalidad.findViewById(R.id.txt_modalidad);
            txtModalidad.setText(this.filtro.getModalidad().getNombre());
            modalidad.setOnClickListener(new DropDownFilter(this, txtModalidad, Modalidad.getNombres(), cm));

            FiltrosProvider fp = new FiltrosProvider(this);

            RelativeLayout provincia = (RelativeLayout) findViewById(R.id.provincia_click);
            TextView txtProvincia = (TextView) provincia.findViewById(R.id.txt_provincia);
            txtProvincia.setText(this.filtro.getProvincia());
            DropDownFilter provinciaFilter = new DropDownFilter(this, txtProvincia, fp.getProvincias(), cm);
            provincia.setOnClickListener(provinciaFilter);

            RelativeLayout ciudad = (RelativeLayout) findViewById(R.id.ciudad_click);
            TextView txtCiudad = (TextView) ciudad.findViewById(R.id.txt_ciudad);
            txtCiudad.setText(this.filtro.getCiudad());
            CiudadDropDownFilter ciudadFilter = new CiudadDropDownFilter(this, txtCiudad, fp.getCiudades(this.filtro.getProvincia()), cm);
            provinciaFilter.registrar(ciudadFilter);
            ciudad.setOnClickListener(ciudadFilter);
            updateVisibilidadCiudad();

            RelativeLayout distancia = (RelativeLayout) findViewById(R.id.distancia_clickable);
            TextView txtDistancia = (TextView) distancia.findViewById(R.id.txt_distancia);
            txtDistancia.setText(getDistanciaString(this.filtro));
            distancia.setOnClickListener(new DistanciaFiltro(this, txtDistancia, cm, filtro));

            RelativeLayout fechas = (RelativeLayout) findViewById(R.id.fechas_clickable);
            TextView txtFechas = (TextView) fechas.findViewById(R.id.txt_fechas);
            txtFechas.setText(getFechasString(filtro));
            FechasFiltro fechasFiltro = new FechasFiltro(this, txtFechas, cm, filtro);
            fechas.setOnClickListener(fechasFiltro);


    }

    private String getDistanciaString(Filtro filtro) {
        return filtro.getMinDistancia() + " a " + filtro.getMaxDistancia() + " Km";
    }

    private void initCaller() {

        if (this.getIntent().getExtras().containsKey("caller")) {
            String strCaller = this.getIntent().getExtras().getString("caller");
            try {
                this.caller = (Class<? extends Activity>) Class.forName(strCaller);
            } catch (Exception e) {
                e.printStackTrace();
                this.caller = MainActivity.class;
            }
        } else {
            this.caller = MainActivity.class;
        }
    }

    @Override
    protected void defineContentView() {
        setContentView(R.layout.activity_filtro_carrera);
    }

    @Override
    public void onClick(View v) {
        Bundle b = new Bundle();
        b.putSerializable(Filtro.FILTRO_ID, this.filtro);
        Intent intent = new Intent(this, this.caller);
        intent.putExtras(b);
        startActivity(intent);
        overridePendingTransition(R.anim.aparece_desde_la_derecha, R.anim.desaparece_hacia_la_derecha);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private class DropDownFilter implements View.OnClickListener {

        protected TextView txtToUpdate;
        protected String[] valores;
        protected Context context;
        protected ChangeManager cm;
        private List<DropDownFilter> observadores = new Vector<>();

        public DropDownFilter(Context context, TextView txtToUpdate, List<String> valores, ChangeManager cm) {
            this.txtToUpdate = txtToUpdate;
            this.context = context;
            this.cm = cm;
            this.observadores = new Vector<>();
            String[] valArray = new String[valores.size()];
            this.valores = valores.toArray(valArray);

        }

        public DropDownFilter(Context context, TextView txtToUpdate, String[] valores, ChangeManager cm) {

            this.txtToUpdate = txtToUpdate;
            this.valores = valores;
            this.context = context;
            this.cm = cm;
            this.observadores = new Vector<>();
        }

        public void registrar(DropDownFilter filtro) {
            this.observadores.add(filtro);
        }

        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(getString(R.string.title_elija_opcion))
                    .setItems(valores, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            txtToUpdate.setText(valores[which]);
                            cm.updateChange(txtToUpdate);
                            for (DropDownFilter obs : observadores) {
                                obs.actualizarValores(valores[which]);
                            }
                        }
                    });
            builder.create().show();
        }

        public void actualizarValores(String valor) {

        }
    }

    private class CiudadDropDownFilter extends DropDownFilter {

        public CiudadDropDownFilter(Context context, TextView txtToUpdate, List<String> valores, ChangeManager cm) {
            super(context, txtToUpdate, valores, cm);

        }

        @Override
        public void actualizarValores(String valor) {
            FiltrosProvider fp = new FiltrosProvider(context);
            List<String> ciudades = fp.getCiudades(valor);
            this.valores = new String[ciudades.size()];
            this.valores = ciudades.toArray(this.valores);
            this.txtToUpdate.setText(FiltrosProvider.TODAS_LAS_CIUDADES);
            updateVisibilidadCiudad();
        }
    }

    private class ChangeManager {
        private Filtro filtro;

        public ChangeManager(Filtro filtro) {
            this.filtro = filtro;
        }

        public void updateChange(TextView textView) {
            switch (textView.getId()) {
                case R.id.txt_campo:
                    this.filtro.setOrdenarPor(textView.getText().toString());
                    break;
                case R.id.txt_sentido:
                    this.filtro.setSentido(textView.getText().toString());
                    break;
                case R.id.txt_modalidad:
                    this.filtro.setModalidad(Modalidad.getByName(textView.getText().toString()));
                    break;

                case R.id.txt_provincia:
                    this.filtro.setProvincia(textView.getText().toString());
                    this.filtro.setCiudad(FiltrosProvider.TODAS_LAS_CIUDADES);
                    break;
                case R.id.txt_ciudad:
                    this.filtro.setCiudad(textView.getText().toString());
                    break;
                case R.id.lbl_dist_desde:
                    this.filtro.setMinDistancia(Integer.valueOf(textView.getText().toString().replace(" Km", "").trim()));
                    break;
                case R.id.lbl_dist_hasta:
                    this.filtro.setMaxDistancia(Integer.valueOf(textView.getText().toString().replace(" Km", "").trim()));
                    break;
                case R.id.txt_fecha_desde:
                    if(!textView.getText().toString().isEmpty()) {
                        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        try {
                            this.filtro.setFechaDesde(sf.parse(textView.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            this.filtro.setFechaDesde(null);
                        }
                    }else{
                        this.filtro.setFechaDesde(null);
                    }
                    break;
                case R.id.txt_fecha_hasta:
                    if(!textView.getText().toString().isEmpty()) {
                        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                        try {
                            this.filtro.setFechaHasta(sf.parse(textView.getText().toString()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            this.filtro.setFechaHasta(null);
                        }
                    }else{
                        this.filtro.setFechaHasta(null);
                    }
                    break;

            }
        }

    }


    private class DistanciaFiltro implements View.OnClickListener {
        private Activity context;
        private TextView txtToUpdate;
        private TextView left;
        private TextView right;

        private ChangeManager cm;
        private Filtro filtro;
        private Dialog d;

        public DistanciaFiltro(Activity context, TextView txtToUpdate, ChangeManager cm, Filtro filtro) {
            this.context = context;
            this.txtToUpdate = txtToUpdate;
            this.cm = cm;
            this.filtro = filtro;
        }

        @Override
        public void onClick(View v) {


            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            // Get the layout inflater
            LayoutInflater inflater = context.getLayoutInflater();
            final View rootView = inflater.inflate(R.layout.seleccion_rango_distancia, null);
            left = (TextView) rootView.findViewById(R.id.lbl_dist_desde);
            left.setText(String.valueOf(filtro.getMinDistancia()) + " Km");
            right = (TextView) rootView.findViewById(R.id.lbl_dist_hasta);
            right.setText(String.valueOf(filtro.getMaxDistancia()) + " Km");
            RangeBar rb = (RangeBar) rootView.findViewById(R.id.sb_distancia);
            rb.setThumbIndices(filtro.getMinDistancia()/10,filtro.getMaxDistancia()/10);
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(rootView).setTitle(getString(R.string.distancia))
                    // Add action buttons
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            cm.updateChange(left);
                            cm.updateChange(right);
                            txtToUpdate.setText(getDistanciaString(filtro));
                        }
                    })
                    .setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            this.d = builder.create();
            rb.setOnRangeBarChangeListener(
                    new DistanciaSeekBarChangeListener((TextView) rootView.findViewById(R.id.lbl_dist_desde),
                            (TextView) rootView.findViewById(R.id.lbl_dist_hasta)));

            this.d.show();
        }
    }


    private class DistanciaSeekBarChangeListener implements RangeBar.OnRangeBarChangeListener {
        TextView left;
        TextView right;

        private DistanciaSeekBarChangeListener(TextView left, TextView right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public void onIndexChangeListener(RangeBar rangeBar, int i, int i1) {
            this.left.setText(String.valueOf(i * 10) + " Km");
            this.right.setText(String.valueOf(i1 * 10) + " Km");
        }
    }







    private class FechasFiltro implements View.OnClickListener {
        private FiltroCarreraActivity context;
        private TextView txtToUpdate;
        private TextView left;
        private TextView right;

        private ChangeManager cm;
        private Filtro filtro;
        private Dialog d;
        private TextView txtDesde;
        private TextView txtHasta;

        public FechasFiltro(FiltroCarreraActivity context, TextView txtToUpdate, ChangeManager cm, Filtro filtro) {
            this.context = context;
            this.txtToUpdate = txtToUpdate;
            this.cm = cm;
            this.filtro = filtro;
        }

        @Override
        public void onClick(View v) {


            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            // Get the layout inflater
            LayoutInflater inflater = context.getLayoutInflater();
            final View rootView = inflater.inflate(R.layout.seleccion_rango_fechas, null);
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
            
            txtDesde = (TextView) rootView.findViewById(R.id.txt_fecha_desde);
            txtDesde.setOnClickListener(new DatePickerListener(txtDesde, this.filtro, cm, context));
            if (filtro.getFechaDesde() != null)
                txtDesde.setText(sf.format(filtro.getFechaDesde()));
            rootView.findViewById(R.id.img_fecha_desde).setOnClickListener(new DatePickerListener(txtDesde,this.filtro,cm,context));
            rootView.findViewById(R.id.img_close_desde).setOnClickListener(new Cleaner(txtDesde, this.filtro));

            txtHasta = (TextView) rootView.findViewById(R.id.txt_fecha_hasta);
            txtHasta.setOnClickListener(new DatePickerListener(txtHasta, this.filtro, cm, context));
            if (filtro.getFechaHasta() != null)
                txtHasta.setText(sf.format(filtro.getFechaHasta()));
            rootView.findViewById(R.id.img_fecha_hasta).setOnClickListener(new DatePickerListener(txtHasta,this.filtro,cm,context));
            rootView.findViewById(R.id.img_close_hasta).setOnClickListener(new Cleaner(txtHasta, this.filtro));
            
            builder.setView(rootView).setTitle(getString(R.string.distancia))
                    // Add action buttons
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            cm.updateChange(txtDesde);
                            cm.updateChange(txtHasta);
                            txtToUpdate.setText(getFechasString(filtro));
                        }
                    })
                    .setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            this.d = builder.create();
           this.d.show();
        }
    }

    private String getFechasString(Filtro filtro) {
        String s = "";
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yy", Locale.getDefault());
        if(filtro.getFechaDesde()!= null){
            s += sf.format(filtro.getFechaDesde());
        }

        if(filtro.getFechaHasta()!=null){
            s += " a " + sf.format(filtro.getFechaHasta());
        }
        if(s.isEmpty()){
            s = "Sin Filtro";
        }
        return s;
    }


    private class DatePickerListener implements View.OnClickListener {
        private TextView fechaToUpdate;
        private Filtro filtro;
        private ChangeManager cm;
        private FiltroCarreraActivity context;
        DatePickerListener(TextView fecha, Filtro filtro, ChangeManager cm,FiltroCarreraActivity context) {
            this.fechaToUpdate = fecha;
            this.filtro = filtro;
            this.cm = cm;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            DatePicker newFragment = new DatePicker();
            if (v.getId() == R.id.txt_fecha_desde || v.getId() == R.id.img_fecha_desde) {

                newFragment.setMaxDate(filtro.getFechaHasta());
                newFragment.setListener(new FechaDesdeListener(filtro, fechaToUpdate));
            } else {
                newFragment.setMinDate(filtro.getFechaDesde());
                newFragment.setListener(new FechaHastaListener(filtro, fechaToUpdate));

            }
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                newFragment.setInitialDate(sf.parse(fechaToUpdate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            newFragment.show(context.getSupportFragmentManager(), "datePicker");

        }
    }

    private class FechaDesdeListener implements DatePickerDialog.OnDateSetListener {
        private Filtro filtro;
        private TextView text;

        public FechaDesdeListener(Filtro filtro, TextView text) {
            this.filtro = filtro;
            this.text = text;
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DATE, dayOfMonth);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            this.text.setText(sf.format(c.getTime()));
            this.filtro.setFechaDesde(c.getTime());
        }
    }

    private class FechaHastaListener implements DatePickerDialog.OnDateSetListener {
        private Filtro filtro;
        private TextView text;

        public FechaHastaListener(Filtro filtro, TextView text) {
            this.filtro = filtro;
            this.text = text;
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, monthOfYear);
            c.set(Calendar.DATE, dayOfMonth);
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.MINUTE, 59);
            c.set(Calendar.SECOND, 59);
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            this.text.setText(sf.format(c.getTime()));
            this.filtro.setFechaHasta(c.getTime());

        }
    }

    private class Cleaner implements View.OnClickListener {
        private TextView toClean;
        private Filtro filter;

        public Cleaner(TextView txt, Filtro filter) {
            this.toClean = txt;
            this.filter = filter;
        }


        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.img_close_desde) {
                this.filter.setFechaDesde(null);
            } else {
                this.filter.setFechaHasta(null);
            }
            this.toClean.setText("");
        }
    }

}
