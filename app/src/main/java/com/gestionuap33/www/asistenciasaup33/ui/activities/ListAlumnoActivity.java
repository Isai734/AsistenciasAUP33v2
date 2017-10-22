package com.gestionuap33.www.asistenciasaup33.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gestionuap33.www.asistenciasaup33.R;
import com.gestionuap33.www.asistenciasaup33.control.Alumno;
import com.gestionuap33.www.asistenciasaup33.datos.ContratoAsistencia;
import com.gestionuap33.www.asistenciasaup33.ui.fragments.AlumnoFragment;

public class ListAlumnoActivity extends AppCompatActivity implements AlumnoFragment.OnListAlumnoListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_alumno);

        if (savedInstanceState == null) {
            Bundle bundle=new Bundle();
            Log.i("ListAlumnoActivity",getIntent().getIntExtra(ContratoAsistencia.ColumnasAlumno.ID_LISTA,0)+"");
            bundle.putInt(AlumnoFragment.ARG_ID_LISTA,getIntent().getIntExtra(ContratoAsistencia.ColumnasAlumno.ID_LISTA,0));
            bundle.putString(ContratoAsistencia.ColumnasRegistroAsistencia.FECHA,getIntent().getStringExtra(ContratoAsistencia.ColumnasRegistroAsistencia.FECHA));
            bundle.putString(ContratoAsistencia.ColumnasRegistroAsistencia.UNIDAD_ID,getIntent().getStringExtra(ContratoAsistencia.ColumnasRegistroAsistencia.UNIDAD_ID));

            AlumnoFragment fragment = new AlumnoFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(android.R.id.content, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }
    }

    @Override
    public void OnListAlumnoListener(Alumno alumno) {

    }
}
