package com.gestionuap33.www.asistenciasaup33.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Clase envoltura para el gestor de Bases de datos
 */
class DatabaseHelper extends SQLiteOpenHelper {


    public DatabaseHelper(Context context,
                          String name,
                          SQLiteDatabase.CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }

    public void onCreate(SQLiteDatabase database) {
        //Crear tabla materia
        String cmd="CREATE TABLE "+ContratoAsistencia.LISTA+" (" +
                ContratoAsistencia.ColumnasLista._ID + " INTEGER, " +
                ContratoAsistencia.ColumnasLista.GRUPO + " TEXT," +
                ContratoAsistencia.ColumnasLista.PERIODO + " TEXT," +
                ContratoAsistencia.ColumnasLista.SEMESTRE + " TEXT," +
                ContratoAsistencia.ColumnasLista.TURNO + " TEXT," +
                ContratoAsistencia.ColumnasHorario.ENTRADA + " TEXT," +
                ContratoAsistencia.ColumnasHorario.SALIDA + " TEXT," +
                ContratoAsistencia.ColumnasHorario.DIA + " TEXT," +
                ContratoAsistencia.ColumnasLista.UNIDAD_NOMBRE + " TEXT," +
                ContratoAsistencia.ColumnasRegistroAsistencia.UNIDAD_ID + " TEXT)";
        database.execSQL(cmd);

        //Crear tabla ALUMNO
         cmd = "CREATE TABLE " + ContratoAsistencia.ALUMNO + " (" +
                ContratoAsistencia.ColumnasAlumno._ID + " INTEGER, " +
                ContratoAsistencia.ColumnasAlumno.NOMBRE + " TEXT," +
                ContratoAsistencia.ColumnasAlumno.CURSO + " TEXT," +
                ContratoAsistencia.ColumnasAlumno.ID_LISTA + " INTEGER)";
        database.execSQL(cmd);

        //Crear tabla ALUMNO
        cmd = "CREATE TABLE " + ContratoAsistencia.LISTA_ALUMNO + " (" +
                ContratoAsistencia.ColumnasListaAlumno._ID + " INTEGER, " +
                ContratoAsistencia.ColumnasListaAlumno.ID_LISTA + " INTEGER, " +
                ContratoAsistencia.ColumnasListaAlumno.ALUMNO_ID + " INTEGER)";
        database.execSQL(cmd);
//Crear tabla PROFESOR
        cmd = "CREATE TABLE " + ContratoAsistencia.PROFESOR + " (" +
                ContratoAsistencia.ColumnasProfesor._ID + " INTEGER, " +
                ContratoAsistencia.ColumnasProfesor.NOMBRE + " TEXT, " +
                ContratoAsistencia.ColumnasProfesor.A_PATERNO + " TEXT, " +
                ContratoAsistencia.ColumnasProfesor.A_MATERNO + " TEXT, " +
                ContratoAsistencia.ColumnasProfesor.CLAVE + " INTEGER)";
        database.execSQL(cmd);

        cmd = "CREATE TABLE " + ContratoAsistencia.REGISTRO_ASISTENCIA + " (" +
                ContratoAsistencia.ColumnasRegistroAsistencia._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ContratoAsistencia.ColumnasRegistroAsistencia.ID_LISTA + " INTEGER," +
                ContratoAsistencia.ColumnasRegistroAsistencia.ID_ALUMNO + " INTEGER," +
                ContratoAsistencia.ColumnasRegistroAsistencia.FECHA + " TEXT," +
                ContratoAsistencia.ColumnasRegistroAsistencia.HORA + " TEXT," +
                ContratoAsistencia.ColumnasRegistroAsistencia.DETALLE + " TEXT," +
                ContratoAsistencia.ColumnasRegistroAsistencia.UNIDAD_ID + " TEXT," +
                ContratoAsistencia.ColumnasRegistroAsistencia.ID_TRABAJADOR + " TEXT," +
                ContratoAsistencia.ColumnasRegistroAsistencia.SYNC_DONE + " TEXT," +
                ContratoAsistencia.ColumnasRegistroAsistencia.ESTADO + " TEXT)";
        database.execSQL(cmd);

        cmd = "CREATE TABLE " + ContratoAsistencia.HORARIO + " (" +
                ContratoAsistencia.ColumnasHorario._ID + " INTEGER, " +
                ContratoAsistencia.ColumnasHorario.ENTRADA + " TEXT," +
                ContratoAsistencia.ColumnasHorario.SALIDA + " TEXT," +
                ContratoAsistencia.ColumnasHorario.DIA + " TEXT," +
                ContratoAsistencia.ColumnasHorario.ID_UNIDAD + " TEXT," +
                ContratoAsistencia.ColumnasHorario.ID_LISTA + " INTEGER," +
                ContratoAsistencia.ColumnasHorario.ID_PROFESOR + " INTEGER)";

        database.execSQL(cmd);

        cmd = "CREATE VIEW " + ContratoAsistencia.ALUMNO_VIEW + " AS " +
                "SELECT RA."+
                ContratoAsistencia.ColumnasRegistroAsistencia._ID+", "+
                ContratoAsistencia.ColumnasRegistroAsistencia.ID_ALUMNO+", "+
                ContratoAsistencia.ColumnasRegistroAsistencia.DETALLE+", "+
                ContratoAsistencia.ColumnasRegistroAsistencia.UNIDAD_ID+", "+
                ContratoAsistencia.ColumnasRegistroAsistencia.FECHA+", "+
                ContratoAsistencia.ColumnasRegistroAsistencia.HORA+", "+
                ContratoAsistencia.ColumnasAlumno.NOMBRE+", "+
                ContratoAsistencia.ColumnasAlumno.CURSO+
                " FROM "
                +ContratoAsistencia.ALUMNO+" A"
                +", "+ContratoAsistencia.REGISTRO_ASISTENCIA +" RA WHERE A."
                +ContratoAsistencia.ColumnasAlumno._ID
                +" = RA."+ContratoAsistencia.ColumnasRegistroAsistencia.ID_ALUMNO ;

        database.execSQL(cmd);

        Log.i("helper", cmd);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizaciones
    }

    /**
     * Crear tabla en la base de datos
     *
     * @param database Instancia de la base de datos
     */
    private void createTable(SQLiteDatabase database) {

    }

    /**
     * Carga datos de ejemplo en la tabla
     * @param database Instancia de la base de datos
     */

}
