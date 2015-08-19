package studios.thinkup.com.apprunning.fragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.edmodo.rangebar.RangeBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import studios.thinkup.com.apprunning.CarrerasActivity;
import studios.thinkup.com.apprunning.R;
import studios.thinkup.com.apprunning.dialogs.DatePicker;
import studios.thinkup.com.apprunning.model.DefaultSettings;
import studios.thinkup.com.apprunning.model.Filtro;
import studios.thinkup.com.apprunning.model.RunningApplication;
import studios.thinkup.com.apprunning.model.entity.Modalidad;
import studios.thinkup.com.apprunning.model.entity.UsuarioApp;
import studios.thinkup.com.apprunning.provider.ConfigProvider;
import studios.thinkup.com.apprunning.provider.FiltrosProvider;
import studios.thinkup.com.apprunning.provider.exceptions.EntidadNoGuardadaException;

/**
 * Created by FaQ on 23/05/2015.
 * Formulario de Busqueda de Carreras
 */
public class BusquedaFormulario extends Fragment implements View.OnClickListener, TextWatcher {

    protected Filtro filtro;
    private Spinner spCiudad;

    public static BusquedaFormulario newInstance() {
        return new BusquedaFormulario();
    }

    private void initFilter(Bundle savedInstanceState) {

        if (savedInstanceState != null) {
            this.filtro = (Filtro) savedInstanceState.getSerializable(Filtro.FILTRO_ID);
        } else {
            if (getArguments() != null) {
                this.filtro = (Filtro) getArguments().getSerializable(Filtro.FILTRO_ID);
            }

        }
        if (this.filtro == null) {
            this.filtro = new Filtro(getDefaultSettings());
        }
    }

    protected DefaultSettings getDefaultSettings() {
        ConfigProvider cp = new ConfigProvider(this.getActivity().getApplication());
        UsuarioApp ua = ((RunningApplication) this.getActivity().getApplication()).getUsuario();

        DefaultSettings defaultSettings = cp.getByUsuario(ua.getId());
        if (defaultSettings == null) {
            defaultSettings = new DefaultSettings(ua.getId());
            try {
                cp.grabar(defaultSettings);
            } catch (EntidadNoGuardadaException e) {
                e.printStackTrace();
            }

        }
        return defaultSettings;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final FiltrosProvider filtrosProvider = new FiltrosProvider(this.getActivity());
        initFilter(savedInstanceState);
        SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        View rootView = inflater.inflate(R.layout.fragment_formulario_busqueda, container, false);

        EditText nombre = (EditText) rootView.findViewById(R.id.txt_nombre);
        nombre.addTextChangedListener(this);

        Button buscar = (Button) rootView.findViewById(R.id.btn_buscar);
        buscar.setOnClickListener(this);

        Spinner spGenero = (Spinner) rootView.findViewById(R.id.sp_genero);

        ArrayAdapter<Modalidad> adapterGenero = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, Modalidad.values());
        adapterGenero.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGenero.setAdapter(adapterGenero);
        spGenero.setOnItemSelectedListener(new GeneroSpinnerItemSelectedListener(this.filtro));
        spGenero.setSelection(adapterGenero.getPosition(filtro.getModalidad()));
        RangeBar sb_distancia = (RangeBar) rootView.findViewById(R.id.sb_distancia);

        TextView left = (TextView) rootView.findViewById(R.id.lbl_dist_desde);
        left.setText(String.valueOf(filtro.getMinDistancia()) + "Km");
        TextView right = (TextView) rootView.findViewById(R.id.lbl_dist_hasta);
        right.setText(String.valueOf(filtro.getMaxDistancia()) + "Km");

        sb_distancia.setOnRangeBarChangeListener(new DistanciaSeekBarChangeListener(this.filtro, left, right));
        sb_distancia.setThumbIndices(filtro.getMinDistancia() / 10, filtro.getMaxDistancia() / 10);

        spCiudad = (Spinner) rootView.findViewById(R.id.sp_ciudad);
        spCiudad.setVisibility(View.GONE);
        Spinner spProvincia = (Spinner) rootView.findViewById(R.id.sp_provincia);
        ArrayAdapter<String> adapterProvincia = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_item, filtrosProvider.getProvincias());
        adapterProvincia.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProvincia.setAdapter(adapterProvincia);
        spProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String provincia = parent.getItemAtPosition(position).toString();
                BusquedaFormulario.this.filtro.setProvincia(provincia);
                if (!provincia.equals(FiltrosProvider.TODAS_LAS_PROVINCIAS)
                        && !provincia.equals(FiltrosProvider.CAPITAL)) {
                    spCiudad.setVisibility(View.VISIBLE);
                    ArrayAdapter<String> adapterZona = new ArrayAdapter<>(BusquedaFormulario.this.getActivity(),
                            android.R.layout.simple_spinner_item, filtrosProvider.getCiudades(provincia));
                    adapterZona.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCiudad.setAdapter(adapterZona);
                    spCiudad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            BusquedaFormulario.this.filtro.setCiudad(parent.getItemAtPosition(position).toString());
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            BusquedaFormulario.this.filtro.setCiudad(FiltrosProvider.TODAS_LAS_CIUDADES);
                        }
                    });
                    spCiudad.setSelection(adapterZona.getPosition(filtro.getCiudad()));
                } else {
                    spCiudad.setVisibility(View.GONE);
                    BusquedaFormulario.this.filtro.setCiudad(FiltrosProvider.TODAS_LAS_CIUDADES);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                BusquedaFormulario.this.filtro.setProvincia(FiltrosProvider.TODAS_LAS_PROVINCIAS);
                spCiudad.setVisibility(View.GONE);
            }
        });
        spProvincia.setSelection(adapterProvincia.getPosition(filtro.getProvincia()));
        TextView txtDesde = (TextView) rootView.findViewById(R.id.txt_fecha_desde);
        txtDesde.setOnClickListener(new DatePickerListener(txtDesde));
        if (filtro.getFechaDesde() != null)
            txtDesde.setText(sf.format(filtro.getFechaDesde()));
        rootView.findViewById(R.id.img_fecha_desde).setOnClickListener(new DatePickerListener(txtDesde));
        rootView.findViewById(R.id.img_close_desde).setOnClickListener(new Cleaner(txtDesde, this.filtro));

        TextView txtHasta = (TextView) rootView.findViewById(R.id.txt_fecha_hasta);
        txtHasta.setOnClickListener(new DatePickerListener(txtHasta));
        if (filtro.getFechaHasta() != null)
            txtHasta.setText(sf.format(filtro.getFechaHasta()));
        rootView.findViewById(R.id.img_fecha_hasta).setOnClickListener(new DatePickerListener(txtHasta));
        rootView.findViewById(R.id.img_close_hasta).setOnClickListener(new Cleaner(txtHasta, this.filtro));

        return rootView;
    }


    @Override
    public void onClick(View v) {

        if (v != null && v.getId() == R.id.btn_buscar) {
            Intent i = new Intent(this.getActivity(), CarrerasActivity.class);
            Bundle b = new Bundle();
            b.putSerializable(Filtro.FILTRO_ID, this.filtro);
            i.putExtras(b);
            this.getActivity().startActivity(i);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        this.filtro.setNombreCarrera(s.toString());
    }


    private class GeneroSpinnerItemSelectedListener implements AdapterView.OnItemSelectedListener {
        private Filtro filtro;

        GeneroSpinnerItemSelectedListener(Filtro filtro) {
            this.filtro = filtro;
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            this.filtro.setModalidad((Modalidad) parent.getItemAtPosition(position));

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            this.filtro.setModalidad(Modalidad.TODOS);
        }
    }


    private class DistanciaSeekBarChangeListener implements RangeBar.OnRangeBarChangeListener {
        Filtro filtro;
        TextView left;
        TextView right;

        private DistanciaSeekBarChangeListener(Filtro filtro, TextView left, TextView right) {
            this.filtro = filtro;
            this.left = left;
            this.right = right;
        }

        @Override
        public void onIndexChangeListener(RangeBar rangeBar, int i, int i1) {
            if (i >= FiltrosProvider.MIN_DISTANCIA && i <= FiltrosProvider.MAX_DISTANCIA) {
                this.left.setText(String.valueOf(i * 10) + " Km");
                filtro.setMinDistancia(i * 10);
            } else {
                this.left.setText(String.valueOf(0) + " Km");
                filtro.setMinDistancia(0);
            }
            if (i1 >= FiltrosProvider.MIN_DISTANCIA && i1 <= FiltrosProvider.MAX_DISTANCIA) {
                this.right.setText(String.valueOf(i1 * 10) + " Km");
                filtro.setMaxDistancia(i1 * 10);
            } else {
                this.right.setText(String.valueOf(0) + " Km");
                filtro.setMaxDistancia(0);
            }

            filtro.setMaxDistancia(i1 * 10);

        }
    }


    private class DatePickerListener implements View.OnClickListener {
        private TextView fechaToUpdate;

        DatePickerListener(TextView fecha) {
            this.fechaToUpdate = fecha;
        }

        @Override
        public void onClick(View v) {
            DatePicker newFragment = new DatePicker();
            if (v.getId() == R.id.txt_fecha_desde || v.getId() == R.id.img_fecha_desde) {

                newFragment.setMaxDate(BusquedaFormulario.this.filtro.getFechaHasta());
                newFragment.setListener(new FechaDesdeListener(BusquedaFormulario.this.filtro, fechaToUpdate));
            } else {
                newFragment.setMinDate(BusquedaFormulario.this.filtro.getFechaDesde());
                newFragment.setListener(new FechaHastaListener(BusquedaFormulario.this.filtro, fechaToUpdate));

            }
            SimpleDateFormat sf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            try {
                newFragment.setInitialDate(sf.parse(fechaToUpdate.getText().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            newFragment.show(BusquedaFormulario.this.getActivity().getSupportFragmentManager(), "datePicker");

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


