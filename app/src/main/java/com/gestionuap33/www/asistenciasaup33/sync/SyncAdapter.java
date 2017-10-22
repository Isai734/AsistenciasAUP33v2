package com.gestionuap33.www.asistenciasaup33.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.gestionuap33.www.asistenciasaup33.R;
import com.gestionuap33.www.asistenciasaup33.control.Alumno;
import com.gestionuap33.www.asistenciasaup33.control.Lista;
import com.gestionuap33.www.asistenciasaup33.control.RegistroAsistencia;
import com.gestionuap33.www.asistenciasaup33.control.Resources;
import com.gestionuap33.www.asistenciasaup33.datos.ContratoAsistencia;
import com.gestionuap33.www.asistenciasaup33.datos.ResponseApi;
import com.gestionuap33.www.asistenciasaup33.datos.RetrofitService;
import com.gestionuap33.www.asistenciasaup33.prefs.SessionPreferences;
import com.gestionuap33.www.asistenciasaup33.utils.Constantes;
import com.gestionuap33.www.asistenciasaup33.utils.Utilidades;
import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Maneja la transferencia de datos entre el servidor y el cliente
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static final String TAG = SyncAdapter.class.getSimpleName();

    ContentResolver resolver;
    private Gson gson = new Gson();

    /**
     * Proyección para las consultas
     */


    // Indices para las columnas indicadas en la proyección
    public static final String ESTADO_OK = "OK";
    public static final String ESTADO_SYNC = "NO";
    public static final int COLUMNA_ID_REMOTA = 1;
    public static final int COLUMNA_MONTO = 2;
    public static final int COLUMNA_ETIQUETA = 3;
    public static final int COLUMNA_FECHA = 4;
    public static final int COLUMNA_DESCRIPCION = 5;
    public Retrofit mRestAdapter;
    private RetrofitService mRetrofitService;
    private Context mContext;


    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        resolver = context.getContentResolver();
        mRestAdapter = new Retrofit.Builder().baseUrl(RetrofitService.BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        //Crear conexion a la API
        mRetrofitService = mRestAdapter.create(RetrofitService.class);
    }

    /**
     * Constructor para mantener compatibilidad en versiones inferiores a 3.0
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        resolver = context.getContentResolver();
    }

    public static void inicializarSyncAdapter(Context context) {
        obtenerCuentaASincronizar(context);
    }

    @Override
    public void onPerformSync(Account account,
                              Bundle extras,
                              String authority,
                              ContentProviderClient provider,
                              final SyncResult syncResult) {

        Log.i(TAG, "onPerformSync()...");

        realizarSincronizacionRemota();

    }
    /*private void iniciarActualizacion() {
        Uri uri = ContratoAsistencia.CONTENT_URI_RESGISTRO_ASISTENCIA;
        String selection = ContractParaGastos.Columnas.PENDIENTE_INSERCION + "=? AND "
                + ContractParaGastos.Columnas.ESTADO + "=?";
        String[] selectionArgs = new String[]{"1", ContractParaGastos.ESTADO_OK + ""};

        ContentValues v = new ContentValues();
        v.put(ContractParaGastos.Columnas.ESTADO, ContractParaGastos.ESTADO_SYNC);

        int results = resolver.update(uri, v, selection, selectionArgs);
        Log.i(TAG, "Registros puestos en cola de inserción:" + results);
    }*/


    private void realizarSincronizacionRemota() {
        Log.i(TAG, "Actualizando el servidor...");
        Resources resources = cursorToResource(obtenerRegistrosSucios());
        if (!resources.getRegistroasistencia().isEmpty()) {
            attempSync(resources);
        } else {
            Log.i(TAG, "No se requiere sincronización");
        }
    }

    public void attempSync(Resources resources) {
        Call<ResponseApi> sync = mRetrofitService.sync(resources);

        sync.enqueue(new Callback<ResponseApi>() {
            @Override
            public void onResponse(Call<ResponseApi> call, Response<ResponseApi> response) {
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
                        Log.d(TAG, responseApi.getMensaje());
                    } else {
                        try {
                            // Reportar causas de error no relacionado con la API
                            Log.d(TAG, response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    Toast.makeText(mContext, error, Toast.LENGTH_LONG).show();
                    //showLoginError(error);
                    return;
                }
                ResponseApi responseApi = response.body();
                Toast.makeText(mContext, responseApi.getMensaje(), Toast.LENGTH_LONG).show();
                finalizarActualizacion();
                SessionPreferences.get(mContext).setSyncDone(true);
            }

            @Override
            public void onFailure(Call<ResponseApi> call, Throwable t) {
                Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
    }

    public Resources cursorToResource(Cursor c) {
        ArrayList<RegistroAsistencia> registroAsistencias = new ArrayList<>();
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                registroAsistencias.add(new RegistroAsistencia(
                        c.getInt(c.getColumnIndex(ContratoAsistencia.ColumnasRegistroAsistencia.ID_LISTA)),
                        c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasRegistroAsistencia.ID_ALUMNO)),
                        c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasRegistroAsistencia.FECHA)),
                        c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasRegistroAsistencia.HORA)),
                        c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasRegistroAsistencia.DETALLE)),
                        c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasRegistroAsistencia.UNIDAD_ID)),
                        c.getString(c.getColumnIndex(ContratoAsistencia.ColumnasRegistroAsistencia.ID_TRABAJADOR))
                ));
            }

        }
        c.close();
        return new Resources(new ArrayList<Alumno>(), new ArrayList<Lista>(), registroAsistencias);
    }

    /**
     * Obtiene el registro que se acaba de marcar como "pendiente por sincronizar" y
     * con "estado de sincronización"
     *
     * @return Cursor con el registro.
     */
    private Cursor obtenerRegistrosSucios() {
        Uri uri = ContratoAsistencia.CONTENT_URI_RESGISTRO_ASISTENCIA;
        String selection = ContratoAsistencia.ColumnasRegistroAsistencia.SYNC_DONE + "= 'N'";
        return resolver.query(uri, null, selection, null, null);
    }


    private void finalizarActualizacion() {
        Uri uri = ContratoAsistencia.CONTENT_URI_RESGISTRO_ASISTENCIA;
        String selection = ContratoAsistencia.ColumnasRegistroAsistencia.SYNC_DONE + "= 'N'";
        ContentValues v = new ContentValues();
        v.put(ContratoAsistencia.ColumnasRegistroAsistencia.SYNC_DONE, "Y");
        resolver.update(uri, v, selection, null);
    }


    /**
     * Inicia manualmente la sincronización
     *
     * @param context Contexto para crear la petición de sincronización
     */
    public static void sincronizarAhora(Context context) {
        Log.i(TAG, "Realizando petición de sincronización manual.");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(obtenerCuentaASincronizar(context),
                context.getString(R.string.autoridad_provider), bundle);
    }

    /**
     * Crea u obtiene una cuenta existente
     *
     * @param context Contexto para acceder al administrador de cuentas
     * @return cuenta auxiliar.
     */
    public static Account obtenerCuentaASincronizar(Context context) {
        // Obtener instancia del administrador de cuentas
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        // Crear cuenta por defecto
        Account newAccount = new Account(
                context.getString(R.string.app_name), Constantes.ACCOUNT_TYPE);
        // Comprobar existencia de la cuenta
        if (null == accountManager.getPassword(newAccount)) {

            // Añadir la cuenta al account manager sin password y sin datos de usuario
            if (!accountManager.addAccountExplicitly(newAccount, "", null))
                return null;
        }
        Log.i(TAG, "Cuenta de usuario obtenida.");
        return newAccount;
    }

}