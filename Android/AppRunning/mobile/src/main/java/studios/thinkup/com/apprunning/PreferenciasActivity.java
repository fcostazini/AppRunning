package studios.thinkup.com.apprunning;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.edmodo.rangebar.RangeBar;

import java.util.List;
import java.util.Vector;

import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.entity.CamposOrdenEnum;
import studios.thinkup.com.apprunning.model.entity.Modalidad;
import studios.thinkup.com.apprunning.provider.ConfigProvider;
import studios.thinkup.com.apprunning.provider.FiltrosProvider;
import studios.thinkup.com.apprunning.provider.IFiltrosProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;


public class PreferenciasActivity extends MainNavigationActivity implements View.OnClickListener {
    private static ProgressDialog pd;
    private DefaultSettings defaultSettings;

    protected static void showProgress(Context context, String message) {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        pd.setMessage(message);
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
    }

    protected static void hideProgress() {
        if (pd != null) {
            pd.dismiss();
        }
    }

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
    protected void onResume() {
        super.onResume();
        if (this.defaultSettings == null) {
            initDefaultSettings();
        }
    }

    private void initDefaultSettings() {
        ConfigProvider cp = new ConfigProvider(this);
        this.defaultSettings = cp.getByUsuario(getIdUsuario());
        if (this.defaultSettings == null) {
            this.defaultSettings = new DefaultSettings(getIdUsuario());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDefaultSettings();

        Button btn = (Button) findViewById(R.id.btn_filtrar);
        btn.setOnClickListener(this);
        ChangeManager cm = new ChangeManager(this.defaultSettings);

        RelativeLayout campoOrden = (RelativeLayout) findViewById(R.id.campo_click);
        TextView txtCampo = (TextView) campoOrden.findViewById(R.id.txt_campo);
        txtCampo.setText(this.defaultSettings.getOrdenarPor());
        campoOrden.setOnClickListener(new DropDownFilter(this, txtCampo, CamposOrdenEnum.getLabels(), cm));

        RelativeLayout sentidoOrden = (RelativeLayout) findViewById(R.id.sentido_click);
        TextView txtSentido = (TextView) sentidoOrden.findViewById(R.id.txt_sentido);
        txtSentido.setText(this.defaultSettings.getSentido());
        sentidoOrden.setOnClickListener(new DropDownFilter(this, txtSentido, Filtro.SENTIDO_ORDEN, cm));

        RelativeLayout modalidad = (RelativeLayout) findViewById(R.id.modalidad_click);
        TextView txtModalidad = (TextView) modalidad.findViewById(R.id.txt_modalidad);
        txtModalidad.setText(this.defaultSettings.getModalidad().getNombre());
        modalidad.setOnClickListener(new DropDownFilter(this, txtModalidad, Modalidad.getNombres(), cm));

        IFiltrosProvider fp = new FiltrosProvider(this);

        RelativeLayout provincia = (RelativeLayout) findViewById(R.id.provincia_click);
        TextView txtProvincia = (TextView) provincia.findViewById(R.id.txt_provincia);
        txtProvincia.setText(this.defaultSettings.getProvincia());
        DropDownFilter provinciaFilter = new DropDownFilter(this, txtProvincia, fp.getProvincias(), cm);
        provincia.setOnClickListener(provinciaFilter);

        RelativeLayout ciudad = (RelativeLayout) findViewById(R.id.ciudad_click);
        TextView txtCiudad = (TextView) ciudad.findViewById(R.id.txt_ciudad);
        txtCiudad.setText(this.defaultSettings.getCiudad());
        CiudadDropDownFilter ciudadFilter = new CiudadDropDownFilter(this, txtCiudad, fp.getCiudades(this.defaultSettings.getProvincia()), cm);
        provinciaFilter.registrar(ciudadFilter);
        ciudad.setOnClickListener(ciudadFilter);
        updateVisibilidadCiudad();

        RelativeLayout distancia = (RelativeLayout) findViewById(R.id.distancia_clickable);
        TextView txtDistancia = (TextView) distancia.findViewById(R.id.txt_distancia);
        txtDistancia.setText(getDistanciaString(this.defaultSettings));
        distancia.setOnClickListener(new DistanciaFiltro(this, txtDistancia, cm, defaultSettings));

        RelativeLayout meses = (RelativeLayout) findViewById(R.id.meses_clickeable);
        TextView txtMeses = (TextView) meses.findViewById(R.id.txt_meses);
        txtMeses.setText(getMesesString(this.defaultSettings));
        MesesFiltro fechasFiltro = new MesesFiltro(this, txtMeses, cm, defaultSettings);
        meses.setOnClickListener(fechasFiltro);


    }

    private String getMesesString(DefaultSettings settings) {
        if (settings.getMesesBusqueda() == 1) {
            return "hasta 1 mes";
        } else {
            return "hasta " + settings.getMesesBusqueda() + " meses";
        }
    }

    private String getDistanciaString(DefaultSettings defaultSettings) {
        return defaultSettings.getDistanciaMin() + " a " + defaultSettings.getDistanciaMax() + " Km";
    }


    @Override
    protected void defineContentView() {
        setContentView(R.layout.activity_preferencias);
    }

    @Override
    public void onClick(View v) {
        ConfigProvider cp = new ConfigProvider(this);
        try {
            showProgress(this, "Guardando...");
            cp.update(this.defaultSettings);
            hideProgress();
            Toast.makeText(this, "Preferencias Guardadas", Toast.LENGTH_LONG).show();
        } catch (EntidadNoGuardadaException e) {
            e.printStackTrace();
            hideProgress();
        }

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

        public void registrar(DropDownFilter defaultSettings) {
            this.observadores.add(defaultSettings);
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
        private DefaultSettings defaultSettings;

        public ChangeManager(DefaultSettings defaultSettings) {
            this.defaultSettings = defaultSettings;
        }

        public void updateChange(TextView textView) {
            switch (textView.getId()) {
                case R.id.txt_campo:
                    this.defaultSettings.setOrdenarPor(textView.getText().toString());
                    break;
                case R.id.txt_sentido:
                    this.defaultSettings.setSentido(textView.getText().toString());
                    break;
                case R.id.txt_modalidad:
                    this.defaultSettings.setModalidad(Modalidad.getByName(textView.getText().toString()));
                    break;

                case R.id.txt_provincia:
                    this.defaultSettings.setProvincia(textView.getText().toString());
                    this.defaultSettings.setCiudad(FiltrosProvider.TODAS_LAS_CIUDADES);
                    break;
                case R.id.txt_ciudad:
                    this.defaultSettings.setCiudad(textView.getText().toString());
                    break;
                case R.id.lbl_dist_desde:
                    this.defaultSettings.setDistanciaMin(Integer.valueOf(textView.getText().toString().replace(" Km", "").trim()));
                    break;
                case R.id.lbl_dist_hasta:
                    this.defaultSettings.setDistanciaMax(Integer.valueOf(textView.getText().toString().replace(" Km", "").trim()));
                    break;
                case R.id.txt_meses:
                    if (!textView.getText().toString().isEmpty()) {
                        try {
                            String val = textView.getText().toString()
                                    .replace("hasta ", "")
                                    .replace(" meses", "").replace(" mes", "");
                            this.defaultSettings.setMesesBusqueda(Integer.valueOf(val.trim()));
                        } catch (Exception e) {
                            e.printStackTrace();
                            this.defaultSettings.setMesesBusqueda(1);
                        }
                    } else {
                        this.defaultSettings.setMesesBusqueda(1);
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
        private DefaultSettings defaultSettings;
        private Dialog d;

        public DistanciaFiltro(Activity context, TextView txtToUpdate, ChangeManager cm, DefaultSettings defaultSettings) {
            this.context = context;
            this.txtToUpdate = txtToUpdate;
            this.cm = cm;
            this.defaultSettings = defaultSettings;
        }

        @Override
        public void onClick(View v) {


            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            // Get the layout inflater
            LayoutInflater inflater = context.getLayoutInflater();
            final View rootView = inflater.inflate(R.layout.seleccion_rango_distancia, null);
            left = (TextView) rootView.findViewById(R.id.lbl_dist_desde);
            left.setText(String.valueOf(defaultSettings.getDistanciaMin()) + " Km");
            right = (TextView) rootView.findViewById(R.id.lbl_dist_hasta);
            right.setText(String.valueOf(defaultSettings.getDistanciaMax()) + " Km");
            RangeBar rb = (RangeBar) rootView.findViewById(R.id.sb_distancia);
            rb.setThumbIndices(defaultSettings.getDistanciaMin() / 10, defaultSettings.getDistanciaMax() / 10);
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(rootView).setTitle(getString(R.string.distancia))
                    // Add action buttons
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            cm.updateChange(left);
                            cm.updateChange(right);
                            txtToUpdate.setText(getDistanciaString(defaultSettings));
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
            if (i >= FiltrosProvider.MIN_DISTANCIA && i <= FiltrosProvider.MAX_DISTANCIA) {
                this.left.setText(String.valueOf(i * 10) + " Km");
            } else {
                this.left.setText(String.valueOf(FiltrosProvider.MIN_DISTANCIA) + " Km");
            }
            if (i1 >= FiltrosProvider.MIN_DISTANCIA && i1 <= FiltrosProvider.MAX_DISTANCIA) {
                this.right.setText(String.valueOf(i1 * 10) + " Km");
            } else {
                this.right.setText(String.valueOf(FiltrosProvider.MAX_DISTANCIA) + " Km");
            }
        }
    }


    private class MesesFiltro implements View.OnClickListener {
        private Activity context;
        private TextView txtToUpdate;
        private TextView valor;


        private ChangeManager cm;
        private DefaultSettings defaultSettings;
        private Dialog d;

        public MesesFiltro(Activity context, TextView txtToUpdate, ChangeManager cm, DefaultSettings defaultSettings) {
            this.context = context;
            this.txtToUpdate = txtToUpdate;
            this.cm = cm;
            this.defaultSettings = defaultSettings;
        }

        @Override
        public void onClick(View v) {


            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            // Get the layout inflater
            LayoutInflater inflater = context.getLayoutInflater();
            final View rootView = inflater.inflate(R.layout.seleccion_meses, null);
            valor = (TextView) rootView.findViewById(R.id.txt_meses);
            valor.setText(String.valueOf(getMesesString(defaultSettings)));
            SeekBar sb = (SeekBar) rootView.findViewById(R.id.sb_meses);
            sb.setProgress(defaultSettings.getMesesBusqueda() - 1);// el progreso 0 es el mes 1
            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(rootView).setTitle(getString(R.string.title_meses_busqueda))
                    // Add action buttons
                    .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            cm.updateChange(valor);
                            txtToUpdate.setText(getMesesString(defaultSettings));
                        }
                    })
                    .setNegativeButton(getString(R.string.cancelar), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            this.d = builder.create();
            sb.setOnSeekBarChangeListener(
                    new MesesChangeListener(valor));

            this.d.show();
        }
    }


    private class MesesChangeListener implements SeekBar.OnSeekBarChangeListener {
        TextView valor;


        private MesesChangeListener(TextView valor) {
            this.valor = valor;

        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (progress == 0) {
                this.valor.setText("hasta 1 mes");
            } else {
                int val = progress + 1;
                this.valor.setText("hasta " + val + " meses");
            }

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }


}
