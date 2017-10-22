package com.gestionuap33.www.asistenciasaup33.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.gestionuap33.www.asistenciasaup33.control.Profesor;

/**
 * Preferencias de Login== Las preferncias se guardan para que no se consulte el servicio web cada vez que se inicie sesion
 * por seguridad se sugiere que cada vez que se consume un servicio se verifique el usuario que consume el web service este activo
 */
public class SessionPreferences {

    public static final String PREFS_NUM_TRABAJADOR = "PREFS_NUM_TRABAJADOR";
    public static final String PREF_CLAVE = "PREF_CLAVE";
    public static final String PREF_CLIENTE_CLV = "PREF_CLIENTE_CLV";
    public static final String SYNC_DONE = "com.gestion.uap.sync_done";


    private final SharedPreferences mPrefs;

    private boolean mIsLoggedIn = false;

    private static SessionPreferences INSTANCE;

    public static SessionPreferences get(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SessionPreferences(context);
        }
        return INSTANCE;
    }

    private SessionPreferences(Context context) {
        mPrefs = context.getApplicationContext()
                .getSharedPreferences(PREFS_NUM_TRABAJADOR, Context.MODE_PRIVATE);

        mIsLoggedIn = !TextUtils.isEmpty(mPrefs.getString(PREFS_NUM_TRABAJADOR, null));
    }

    public String getClaveCliente() {
        return mPrefs.getString(PREFS_NUM_TRABAJADOR, "0");
    }

    public boolean isSyncDone() {
        return mPrefs.getBoolean(SYNC_DONE, false);
    }

    public boolean isLoggedIn() {
        return mIsLoggedIn;
    }

    public void saveLogin(Profesor profesor) {
        if (profesor != null) {
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(PREFS_NUM_TRABAJADOR, profesor.getProfesorNumeroTrabajador());
            editor.putString(PREF_CLAVE, profesor.getClave());
            editor.apply();
            mIsLoggedIn = true;
        }
    }

    public void setSyncDone(boolean done) {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putBoolean(SYNC_DONE, done);
        editor.apply();
    }

    public void logOut() {
        mIsLoggedIn = false;
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.putString(PREFS_NUM_TRABAJADOR, null);
        editor.putString(PREF_CLAVE, null);
        editor.apply();
    }
}
