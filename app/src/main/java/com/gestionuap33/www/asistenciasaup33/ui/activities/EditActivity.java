package com.gestionuap33.www.asistenciasaup33.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gestionuap33.www.asistenciasaup33.R;
import com.gestionuap33.www.asistenciasaup33.control.Alumno;
import com.gestionuap33.www.asistenciasaup33.datos.ContratoAsistencia;
import com.gestionuap33.www.asistenciasaup33.ui.fragments.AlumnoEditFragment;
import com.gestionuap33.www.asistenciasaup33.ui.fragments.AlumnoFragment;
import com.gestionuap33.www.asistenciasaup33.ui.fragments.ListaAsistenciasFragment;

import java.util.Date;

public class EditActivity extends AppCompatActivity implements AlumnoFragment.OnListAlumnoListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putString("selection",getIntent().getStringExtra("selection"));
            AlumnoEditFragment fragment = new AlumnoEditFragment();
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
