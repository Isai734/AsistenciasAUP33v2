package com.gestionuap33.www.asistenciasaup33.sync;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.provider.Contacts;
import android.util.Log;
import android.widget.Toast;

import com.gestionuap33.www.asistenciasaup33.control.Alumno;
import com.gestionuap33.www.asistenciasaup33.control.Lista;
import com.gestionuap33.www.asistenciasaup33.control.RegistroAsistencia;
import com.gestionuap33.www.asistenciasaup33.control.Resources;
import com.gestionuap33.www.asistenciasaup33.datos.ContratoAsistencia;
import com.gestionuap33.www.asistenciasaup33.datos.ResponseApi;
import com.gestionuap33.www.asistenciasaup33.datos.RetrofitService;
import com.gestionuap33.www.asistenciasaup33.prefs.SessionPreferences;
import com.gestionuap33.www.asistenciasaup33.ui.activities.LoginActivity;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Isai on 20/06/2017.
 */

public class SyncRemote {

    private Context mContext;
    private static final String TAG = SyncRemote.class.getSimpleName();
    public Retrofit mRestAdapter;
    private RetrofitService mRetrofitService;
    private ProgressDialog progressBar;


    public SyncRemote(Context mContext) {
        this.mContext = mContext;
        //Crear conexion al servicio REST
        mRestAdapter = new Retrofit.Builder().baseUrl(RetrofitService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        //Crear conexion a la API
        mRetrofitService = mRestAdapter.create(RetrofitService.class);
    }


    public void attempSync() {
        showProgressBar("Descargando Catalogos...");
        Call<Resources> getResources = mRetrofitService.resources(SessionPreferences.get(mContext).getClaveCliente() + "");
        Log.i(TAG,"Clave profesor"+SessionPreferences.get(mContext).getClaveCliente());
        getResources.enqueue(new Callback<Resources>() {
            @Override
            public void onResponse(Call<Resources> call, Response<Resources> response) {
                // Mostrar progreso

                Log.i(TAG, "Nmun : ");
                // Procesar errore+us
                if (!response.isSuccessful()) {

                    String error = "Ha ocurrido un error. Contacte al administrador";
                    if (response.errorBody()
                            .contentType()
                            .subtype()
                            .equals("json")) {
                        ResponseApi responseApi = ResponseApi.fromResponseBody(response.errorBody());
                        error = responseApi.getMensaje();
                        Log.d("LoginActivity", responseApi.getMensaje());
                    } else {
                        try {
                            // Reportar causas de error no relacionado con la API
                            Log.d("LoginActivity", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
                    progressBar.dismiss();
                    //showLoginError(error);
                    return;
                }
                //Procesar Respuesta
                if (!response.body().getAlumno().isEmpty()) {
                    //Log.i(TAG,response.body().getProfesorNombre());
                    fillAlumnos(response.body().getAlumno());
                }
                if (!response.body().getLista().isEmpty()) {
                    //("Clave no valida");
                    fillListas(response.body().getLista());

                }
                if (!response.body().getRegistroasistencia().isEmpty()) {
                    // Guardar Login en preferencias
                    // Ir a la interfaz principal.
                    //showAppointmentsScreen();
                    fillRegistroAsistencias(response.body().getRegistroasistencia());
                }
                Toast.makeText(mContext, "Descarga de catalogos con exito", Toast.LENGTH_LONG).show();
                SessionPreferences.get(mContext).setSyncDone(true);
                progressBar.dismiss();
            }

            @Override
            public void onFailure(Call<Resources> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    public void fillAlumnos(List<Alumno> alumnoList) {
        ContentValues values = new ContentValues();
        for (Alumno alumno : alumnoList) {
            Log.i(TAG,"Id Alumno: "+alumno.getAlumnoMatricula());
            values.put(ContratoAsistencia.ColumnasAlumno._ID, alumno.getAlumnoMatricula());
            values.put(ContratoAsistencia.ColumnasAlumno.NOMBRE, alumno.getAlumnoNombre());
            values.put(ContratoAsistencia.ColumnasAlumno.CURSO, alumno.getAlumnoCurs());
            values.put(ContratoAsistencia.ColumnasAlumno.ID_LISTA, alumno.getListaId());
            mContext.getContentResolver().insert(ContratoAsistencia.CONTENT_URI_ALUMNO, values);
        }
    }

    public void fillListas(List<Lista> listaList) {
        ContentValues values = new ContentValues();
        for (Lista lista : listaList) {
            Log.i(TAG,"Id Lista: "+lista.getListaId());
            values.put(ContratoAsistencia.ColumnasLista._ID, lista.getListaId());
            values.put(ContratoAsistencia.ColumnasLista.GRUPO, lista.getListaGrupo());
            values.put(ContratoAsistencia.ColumnasLista.PERIODO, lista.getListaPeriodoLectivo());
            values.put(ContratoAsistencia.ColumnasLista.SEMESTRE, lista.getListaSemestre());
            values.put(ContratoAsistencia.ColumnasLista.TURNO, lista.getListaTurno());
            values.put(ContratoAsistencia.ColumnasHorario.DIA, lista.getHorarioDetalleDia());
            values.put(ContratoAsistencia.ColumnasHorario.ENTRADA, lista.getHorarioDetalleEntrada());
            values.put(ContratoAsistencia.ColumnasHorario.SALIDA, lista.getHorarioDetalleSalida());
            values.put(ContratoAsistencia.ColumnasRegistroAsistencia.UNIDAD_ID, lista.getUnidadDeAprendizajeId());
            values.put(ContratoAsistencia.ColumnasLista.UNIDAD_NOMBRE, lista.getUnidadDeAprendizajeNombre());
            mContext.getContentResolver().insert(ContratoAsistencia.CONTENT_URI_LISTA, values);
        }
    }

    public void fillRegistroAsistencias(List<RegistroAsistencia> asistenciaList) {
        ContentValues values = new ContentValues();
        for (RegistroAsistencia registroAsistencia : asistenciaList) {
            Log.i(TAG,"Id Registro: "+registroAsistencia.getRegistroAsistenciaFecha());
            values.put(ContratoAsistencia.ColumnasRegistroAsistencia.ID_ALUMNO, registroAsistencia.getAlumnoNumeroControl());
            values.put(ContratoAsistencia.ColumnasRegistroAsistencia.FECHA, registroAsistencia.getRegistroAsistenciaFecha());
            values.put(ContratoAsistencia.ColumnasRegistroAsistencia.HORA, registroAsistencia.getRegistroAsistenciaHora());
            values.put(ContratoAsistencia.ColumnasRegistroAsistencia.DETALLE, registroAsistencia.getRegistroAsistenciaDetalle());
            values.put(ContratoAsistencia.ColumnasRegistroAsistencia.UNIDAD_ID, registroAsistencia.getUnidadDeAprendizajeId());
            values.put(ContratoAsistencia.ColumnasRegistroAsistencia.ID_TRABAJADOR, registroAsistencia.getProfesorNumeroTrabajado());
            values.put(ContratoAsistencia.ColumnasRegistroAsistencia.SYNC_DONE,"Y");
            mContext.getContentResolver().insert(ContratoAsistencia.CONTENT_URI_RESGISTRO_ASISTENCIA, values);
        }
    }

    public void showProgressBar(String message) {
        progressBar = new ProgressDialog(mContext);
        progressBar.setCancelable(false);
        progressBar.setMessage(message);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setProgress(0);
        progressBar.setMax(100);
        progressBar.show();
    }


}