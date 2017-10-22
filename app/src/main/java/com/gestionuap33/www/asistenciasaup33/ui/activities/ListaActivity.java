package com.gestionuap33.www.asistenciasaup33.ui.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gestionuap33.www.asistenciasaup33.R;
import com.gestionuap33.www.asistenciasaup33.control.Lista;
import com.gestionuap33.www.asistenciasaup33.datos.ContratoAsistencia;
import com.gestionuap33.www.asistenciasaup33.ui.fragments.ListaAsistenciasFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ListaActivity extends AppCompatActivity implements ListaAsistenciasFragment.OnListTagListener {

    Date fecha = null;
    private static final String TAG=ListaActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putString(ListaAsistenciasFragment.DIA, getIntent().getStringExtra(ContratoAsistencia.ColumnasHorario.DIA));
            fecha = new Date(getIntent().getStringExtra(ContratoAsistencia.ColumnasRegistroAsistencia.FECHA));
            ListaAsistenciasFragment fragment = new ListaAsistenciasFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    public void OnListTagListener(Lista lista) {
        Log.i("ListaActivity", "Id_Lista : " + lista.getListaId());

        //Calendar c = Calendar.getInstance();//yyyy-MM-dd HH:mm:ss.SSSZ
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fecha = formatoFecha.format(this.fecha == null ? "" : this.fecha);
        String selection = ContratoAsistencia.ColumnasRegistroAsistencia.FECHA + " = '" + fecha + "' AND " + ContratoAsistencia.ColumnasRegistroAsistencia.UNIDAD_ID + " = '" + lista.getUnidadDeAprendizajeId() + "'";

        Cursor c = getBaseContext().getContentResolver().query(
                ContratoAsistencia.CONTENT_URI_RESGISTRO_ASISTENCIA,
                null, selection, null, null);
        Log.i(TAG, "Selection in ListActivity : "+selection);
        if (!c.moveToFirst()){
            startActivity(new Intent(this, ListAlumnoActivity.class)
                    .putExtra(ContratoAsistencia.ColumnasAlumno.ID_LISTA, lista.getListaId())
                    .putExtra(ContratoAsistencia.ColumnasRegistroAsistencia.FECHA, this.fecha.toString())
                    .putExtra(ContratoAsistencia.ColumnasRegistroAsistencia.UNIDAD_ID, lista.getUnidadDeAprendizajeId())
            );
            return;
        }
        lanzarAlerta(lista,selection,this.fecha);
        c.close();
    }

    public void lanzarAlerta(final Lista lista,final String selection,final Date fecha) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Aviso!");
        dialog.setMessage("Ya existe un pase de lista en esta fecha para esta unidad de aprendizaje\nDesea Actualizarla?");
        dialog.setCancelable(true);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(getApplication(), EditActivity.class)
                        .putExtra("selection", selection)
                        .putExtra(ContratoAsistencia.ColumnasAlumno.ID_LISTA, lista.getListaId())
                        .putExtra(ContratoAsistencia.ColumnasRegistroAsistencia.FECHA, fecha.toString())
                        .putExtra(ContratoAsistencia.ColumnasRegistroAsistencia.UNIDAD_ID, lista.getUnidadDeAprendizajeId())
                );
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}
