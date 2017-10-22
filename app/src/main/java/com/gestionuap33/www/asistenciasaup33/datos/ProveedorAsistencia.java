package com.gestionuap33.www.asistenciasaup33.datos;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Content Provider personalizado para las actividades
 */
public class ProveedorAsistencia extends ContentProvider {
    /**
     * Nombre de la base de datos
     */
    private static final String DATABASE_NAME = "uap33.db";
    /**
     * Versión actual de la base de datos
     */
    private static final int DATABASE_VERSION = 1;
    /**
     * Instancia del administrador de BD
     */
    private DatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        // Inicializando gestor BD
        databaseHelper = new DatabaseHelper(
                getContext(),
                DATABASE_NAME,
                null,
                DATABASE_VERSION
        );
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        int match = ContratoAsistencia.uriMatcher.match(uri);
        switch (match) {
            // Consultando todos los registros
            case ContratoAsistencia.ALUMNOS:
                return query(ContratoAsistencia.CONTENT_URI_ALUMNO, ContratoAsistencia.ALUMNO, projection, selection, selectionArgs, sortOrder);
            case ContratoAsistencia.ALUMNOS_VIEW:
                return query(ContratoAsistencia.CONTENT_URI_ALUMNO_VIEW, ContratoAsistencia.ALUMNO_VIEW, projection, selection, selectionArgs, sortOrder);
            case ContratoAsistencia.HORARIOS:
                return query(ContratoAsistencia.CONTENT_URI_HORARIO, ContratoAsistencia.HORARIO, projection, selection, selectionArgs, sortOrder);
            case ContratoAsistencia.LISTAS:
                return query(ContratoAsistencia.CONTENT_URI_LISTA, ContratoAsistencia.LISTA, projection, selection, selectionArgs, sortOrder);
            case ContratoAsistencia.LISTA_ALUMNOS:
                return query(ContratoAsistencia.CONTENT_URI_LISTA_ALUMNO, ContratoAsistencia.LISTA_ALUMNO, projection, selection, selectionArgs, sortOrder);
            case ContratoAsistencia.REGISTRO_ASISTENCIAS:
                return query(ContratoAsistencia.CONTENT_URI_RESGISTRO_ASISTENCIA, ContratoAsistencia.REGISTRO_ASISTENCIA, projection, selection, selectionArgs, sortOrder);
            case ContratoAsistencia.PROFESORRES:
                return query(ContratoAsistencia.CONTENT_URI_PROFESOR, ContratoAsistencia.PROFESOR, projection, selection, selectionArgs, sortOrder);

            // Consultando registros por el _id
            case ContratoAsistencia.ALUMNOS_VIEW_ID:
                return idQuery(uri, ContratoAsistencia.CONTENT_URI_ALUMNO_VIEW, ContratoAsistencia.ALUMNO_VIEW, projection, selectionArgs, sortOrder);
            case ContratoAsistencia.ALUMNOS_ID:
                return idQuery(uri, ContratoAsistencia.CONTENT_URI_ALUMNO, ContratoAsistencia.ALUMNO, projection, selectionArgs, sortOrder);
            case ContratoAsistencia.HORARIOS_ID:
                return idQuery(uri, ContratoAsistencia.CONTENT_URI_HORARIO, ContratoAsistencia.HORARIO, projection, selectionArgs, sortOrder);
            case ContratoAsistencia.LISTAS_ID:
                return idQuery(uri, ContratoAsistencia.CONTENT_URI_LISTA, ContratoAsistencia.LISTA, projection, selectionArgs, sortOrder);
            case ContratoAsistencia.LSTA_ALUMNOS_ID:
                return idQuery(uri, ContratoAsistencia.CONTENT_URI_LISTA_ALUMNO, ContratoAsistencia.LISTA_ALUMNO, projection, selectionArgs, sortOrder);
            case ContratoAsistencia.REGISTRO_ASISTENCIAS_ID:
                return idQuery(uri, ContratoAsistencia.CONTENT_URI_RESGISTRO_ASISTENCIA, ContratoAsistencia.REGISTRO_ASISTENCIA, projection, selectionArgs, sortOrder);
            case ContratoAsistencia.PROFESORES_ID:
                return idQuery(uri, ContratoAsistencia.CONTENT_URI_PROFESOR, ContratoAsistencia.PROFESOR, projection, selectionArgs, sortOrder);

            default:
                throw new IllegalArgumentException("URI no soportada: " + uri);
        }
    }

    public Cursor query(Uri contenUri, String tabla, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor c;
        c = db.query(tabla, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(
                getContext().getContentResolver(), contenUri
        );
        return c;
    }

    public Cursor queryView(Uri contenUri, String tabla, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor c;
        c = db.query(tabla, projection, selection, selectionArgs, null, null, sortOrder);
        c.setNotificationUri(
                getContext().getContentResolver(), contenUri
        );
        return c;
    }


    public Cursor idQuery(Uri uri, Uri contenUri, String tabla, String[] projection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor c;
        long videoID = ContentUris.parseId(uri);
        c = db.query(tabla, projection,
                ContratoAsistencia.ColumnasProfesor._ID + " = " + videoID, selectionArgs, null, null, sortOrder
        );
        c.setNotificationUri(
                getContext().getContentResolver(), contenUri
        );
        return c;
    }

    @Override
    public String getType(Uri uri) {
        switch (ContratoAsistencia.uriMatcher.match(uri)) {
            case ContratoAsistencia.ALUMNOS:
                return ContratoAsistencia.MULTIPLE_MIME_ALUMNO;
            case ContratoAsistencia.HORARIOS:
                return ContratoAsistencia.MULTIPLE_MIME_HORARIO;
            case ContratoAsistencia.LISTAS:
                return ContratoAsistencia.MULTIPLE_MIME_LISTA;
            case ContratoAsistencia.LISTA_ALUMNOS:
                return ContratoAsistencia.MULTIPLE_MIME_LISTA_ALUMNO;
            case ContratoAsistencia.REGISTRO_ASISTENCIAS:
                return ContratoAsistencia.MULTIPLE_MIME_REGISTRO_ASISTENCIA;
            case ContratoAsistencia.PROFESORRES:
                return ContratoAsistencia.MULTIPLE_MIME_PROFESOR;
            case ContratoAsistencia.ALUMNOS_VIEW:
                return ContratoAsistencia.MULTIPLE_MIME_ALUMNO_VIEW;
            //mime por id
            case ContratoAsistencia.ALUMNOS_ID:
                return ContratoAsistencia.SIMPLE_MIME_ALUMNO;
            case ContratoAsistencia.ALUMNOS_VIEW_ID:
                return ContratoAsistencia.SIMPLE_MIME_ALUMNO_VIEW;
            case ContratoAsistencia.HORARIOS_ID:
                return ContratoAsistencia.SIMPLE_MIME_HORARIO;
            case ContratoAsistencia.LISTAS_ID:
                return ContratoAsistencia.SIMPLE_MIME_LISTA;
            case ContratoAsistencia.LSTA_ALUMNOS_ID:
                return ContratoAsistencia.SIMPLE_MIME_LISTA_ALUMNO;
            case ContratoAsistencia.REGISTRO_ASISTENCIAS_ID:
                return ContratoAsistencia.SIMPLE_MIME_REGISTRO_ASISTENCIA;
            case ContratoAsistencia.PROFESORES_ID:
                return ContratoAsistencia.SIMPLE_MIME_PROFESOR;
            default:
                throw new IllegalArgumentException("Tipo de tabla desconocida: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // Validar la uri
        switch (ContratoAsistencia.uriMatcher.match(uri)) {
            case ContratoAsistencia.ALUMNOS:
                return insert(uri, ContratoAsistencia.ALUMNO, ContratoAsistencia.CONTENT_URI_ALUMNO, values);
            case ContratoAsistencia.LISTAS:
                return insert(uri, ContratoAsistencia.LISTA, ContratoAsistencia.CONTENT_URI_LISTA, values);
            case ContratoAsistencia.LISTA_ALUMNOS:
                return insert(uri, ContratoAsistencia.LISTA_ALUMNO, ContratoAsistencia.CONTENT_URI_LISTA_ALUMNO, values);
            case ContratoAsistencia.REGISTRO_ASISTENCIAS:
                return insert(uri, ContratoAsistencia.REGISTRO_ASISTENCIA, ContratoAsistencia.CONTENT_URI_RESGISTRO_ASISTENCIA, values);
            case ContratoAsistencia.HORARIOS:
                return insert(uri, ContratoAsistencia.HORARIO, ContratoAsistencia.CONTENT_URI_HORARIO, values);
            case ContratoAsistencia.PROFESORRES:
                return insert(uri, ContratoAsistencia.PROFESOR, ContratoAsistencia.CONTENT_URI_PROFESOR, values);
            default:
                throw new SQLException("Falla al insertar fila en : " + uri);
        }
    }

    private Uri insert(Uri uri, String tabla, Uri contentUri, ContentValues values) {
        ContentValues contentValues;
        if (values != null) {
            contentValues = new ContentValues(values);
        } else {
            contentValues = new ContentValues();
        }
        // Si es necesario, verifica los valores
        // Inserci�n de nueva fila
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long rowId = db.insert(tabla, null, contentValues);
        if (rowId > 0) {
            Uri uri_actividad = ContentUris.withAppendedId(contentUri, rowId);
            getContext().getContentResolver().notifyChange(uri_actividad, null);
            return uri_actividad;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int match = ContratoAsistencia.uriMatcher.match(uri);
        switch (match) {
            case ContratoAsistencia.ALUMNOS:
                return delete(ContratoAsistencia.ALUMNO, selection, selectionArgs);
            case ContratoAsistencia.HORARIOS:
                return delete(ContratoAsistencia.HORARIO, selection, selectionArgs);
            case ContratoAsistencia.LISTAS:
                return delete(ContratoAsistencia.LISTA, selection, selectionArgs);
            case ContratoAsistencia.LISTA_ALUMNOS:
                return delete(ContratoAsistencia.LISTA_ALUMNO, selection, selectionArgs);
            case ContratoAsistencia.REGISTRO_ASISTENCIAS:
                return delete(ContratoAsistencia.REGISTRO_ASISTENCIA, selection, selectionArgs);
            case ContratoAsistencia.PROFESORRES:
                return delete(ContratoAsistencia.PROFESOR, selection, selectionArgs);
            //eliminar por id
            case ContratoAsistencia.ALUMNOS_ID:
                return idDelete(ContratoAsistencia.ALUMNO, uri, selection, selectionArgs);
            case ContratoAsistencia.HORARIOS_ID:
                return idDelete(ContratoAsistencia.HORARIO, uri, selection, selectionArgs);
            case ContratoAsistencia.LISTAS_ID:
                return idDelete(ContratoAsistencia.LISTA, uri, selection, selectionArgs);
            case ContratoAsistencia.LSTA_ALUMNOS_ID:
                return idDelete(ContratoAsistencia.LISTA_ALUMNO, uri, selection, selectionArgs);
            case ContratoAsistencia.REGISTRO_ASISTENCIAS_ID:
                return idDelete(ContratoAsistencia.REGISTRO_ASISTENCIA, uri, selection, selectionArgs);
            case ContratoAsistencia.PROFESORES_ID:
                return idDelete(ContratoAsistencia.PROFESOR, uri, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Elemento desconocido: " +
                        uri);
        }
    }

    public int delete(String tabla, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected = db.delete(tabla, selection, selectionArgs);
        return affected;
    }

    public int idDelete(String tabla, Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        long videoId = ContentUris.parseId(uri);
        int affected = db.delete(tabla, ContratoAsistencia.ColumnasProfesor._ID + "=" + videoId
                + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
        // Notificar cambio asociado a la uri
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        switch (ContratoAsistencia.uriMatcher.match(uri)) {
            case ContratoAsistencia.ALUMNOS:
                return update(ContratoAsistencia.ALUMNO, uri, values, selection, selectionArgs);
            case ContratoAsistencia.HORARIOS:
                return update(ContratoAsistencia.HORARIO, uri, values, selection, selectionArgs);
            case ContratoAsistencia.LISTAS:
                return update(ContratoAsistencia.LISTA, uri, values, selection, selectionArgs);
            case ContratoAsistencia.LISTA_ALUMNOS:
                return update(ContratoAsistencia.LISTA_ALUMNO, uri, values, selection, selectionArgs);
            case ContratoAsistencia.REGISTRO_ASISTENCIAS:
                return update(ContratoAsistencia.REGISTRO_ASISTENCIA, uri, values, selection, selectionArgs);
            case ContratoAsistencia.PROFESORRES:
                return update(ContratoAsistencia.PROFESOR, uri, values, selection, selectionArgs);
            //eliminar por id
            case ContratoAsistencia.ALUMNOS_ID:
                return idUpdate(ContratoAsistencia.ALUMNO, uri, values, selection, selectionArgs);
            case ContratoAsistencia.HORARIOS_ID:
                return idUpdate(ContratoAsistencia.HORARIO, uri, values, selection, selectionArgs);
            case ContratoAsistencia.LISTAS_ID:
                return idUpdate(ContratoAsistencia.LISTA, uri, values, selection, selectionArgs);
            case ContratoAsistencia.LSTA_ALUMNOS_ID:
                return idUpdate(ContratoAsistencia.LISTA_ALUMNO, uri, values, selection, selectionArgs);
            case ContratoAsistencia.REGISTRO_ASISTENCIAS_ID:
                return idUpdate(ContratoAsistencia.REGISTRO_ASISTENCIA, uri, values, selection, selectionArgs);
            case ContratoAsistencia.PROFESORES_ID:
                return idUpdate(ContratoAsistencia.PROFESOR, uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("URI desconocida: " + uri);
        }
    }

    public int update(String tabla, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected = db.update(tabla, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }

    public int idUpdate(String tabla, Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int affected;
        String videoId = uri.getPathSegments().get(1);
        affected = db.update(tabla, values, ContratoAsistencia.ColumnasProfesor._ID + "=" + videoId
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : ""),
                selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return affected;
    }
}

