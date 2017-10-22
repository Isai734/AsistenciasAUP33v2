package com.gestionuap33.www.asistenciasaup33;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.gestionuap33.www.asistenciasaup33.datos.ContratoAsistencia;
import com.gestionuap33.www.asistenciasaup33.prefs.SessionPreferences;
import com.gestionuap33.www.asistenciasaup33.sync.SyncAdapter;
import com.gestionuap33.www.asistenciasaup33.sync.SyncRemote;
import com.gestionuap33.www.asistenciasaup33.ui.activities.ListaActivity;
import com.gestionuap33.www.asistenciasaup33.ui.activities.LoginActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    Locale espanol = new Locale("es", "ES");
    private ContentResolver mResolver;
    private SyncRemote syncRemote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        syncRemote = new SyncRemote(this);
        if (!SessionPreferences.get(this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            Log.i(TAG, "Dentro de Pref...");
            finish();
        }

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CalendarView simpleCalendarView = (CalendarView) findViewById(R.id.calendario); // get the reference of CalendarView
        // perform setOnDateChangeListener event on CalendarView
        simpleCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                //Obtner dia
                String input = dayOfMonth + "/" + (month + 1) + "/" + year;
                SimpleDateFormat parser = new SimpleDateFormat("dd/MM/yyyy", espanol);
                Date date = null;
                try {
                    date = parser.parse(input);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat formatter = new SimpleDateFormat("EEEE");
                String dia = formatter.format(date).equals("miércoles") ? "miercoles" : formatter.format(date);

                //Obetener lista
                String selection = ContratoAsistencia.ColumnasHorario.DIA + " = '" + dia + "'";
                Log.i(TAG, "Dia : " + dia + " Fecha : " + date + " input : " + input);
                Cursor c = getBaseContext().getContentResolver().query(
                        ContratoAsistencia.CONTENT_URI_LISTA,
                        null, selection, null, null
                );

                if (!c.moveToFirst()) {
                    Snackbar.make(findViewById(R.id.fab), "No tiene grupos asignados a este dia", Snackbar.LENGTH_LONG).show();
                    return;
                }

                startActivity(
                        new Intent(getApplication(), ListaActivity.class)
                                .putExtra(ContratoAsistencia.ColumnasHorario.DIA, dia)
                                .putExtra(ContratoAsistencia.ColumnasRegistroAsistencia.FECHA, date.toString())
                );
                c.close();
            }
        });



        TableObserver observer = new TableObserver(null);
        mResolver = getContentResolver();
        mResolver.registerContentObserver(ContratoAsistencia.CONTENT_URI_RESGISTRO_ASISTENCIA, true, observer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh_data) {
            if (SessionPreferences.get(this).isSyncDone()) {

            } else {
                syncRemote.attempSync();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    public class TableObserver extends ContentObserver {
        /**
         * Crea un content observer
         */
        public TableObserver(Handler handler) {
            super(handler);
        }

        /*
        * Define el metodo que es llamado cuando los datos en el content provider cambian.
        * Este metodo es solo para que haya compatibilidad con plataformas mas viejas.
        */
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        /*
        * Define el metodo que es llamado cuando los datos en el content provider cambian.
        */
        @Override
        public void onChange(boolean selfChange, Uri changeUri) {

// Corre la sincronizacion
            if(SessionPreferences.get(getApplication()).isSyncDone()){
                Log.i("MainActivity", "Se inicia la sincronizacion para : " + changeUri);
                ContentResolver.requestSync(SyncAdapter.obtenerCuentaASincronizar(getApplication()), getApplication().getString(R.string.autoridad_provider), null);
            }else{
                Log.i("MainActivity", "Aun no hay datos : " + changeUri);
            }
        }
    }

}
