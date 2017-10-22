package com.gestionuap33.www.asistenciasaup33.datos;

import android.content.UriMatcher;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Contract Class entre el provider y las aplicaciones
 */
public class ContratoAsistencia {
    /**
     * Autoridad del Content Provider
     */
    public final static String AUTORIDAD = "com.gestionuap33.www.asistenciasaup33";
    /**
     * Representaci�n de las tablas a consultar
     */

    public static final String LISTA = "lista";
    public static final String ALUMNO = "alumno";
    public static final String ALUMNO_VIEW = "alumno_view";
    public static final String LISTA_ALUMNO = "listaalumno";
    public static final String REGISTRO_ASISTENCIA = "registro_asistencia";
    public final static String HORARIO = "horariodetalle";
    public final static String PROFESOR = "profesor";
    /**
     * Tipo MIME que retorna la consulta de una sola fila
     */

    public final static String SIMPLE_MIME_LISTA =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + LISTA;
    public final static String SIMPLE_MIME_ALUMNO =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + ALUMNO;
    public final static String SIMPLE_MIME_ALUMNO_VIEW =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + ALUMNO_VIEW;
    public final static String SIMPLE_MIME_LISTA_ALUMNO =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + LISTA_ALUMNO;
    public final static String SIMPLE_MIME_HORARIO =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + HORARIO;
    public final static String SIMPLE_MIME_PROFESOR =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + PROFESOR;
    public final static String SIMPLE_MIME_REGISTRO_ASISTENCIA =
            "vnd.android.cursor.item/vnd." + AUTORIDAD + REGISTRO_ASISTENCIA;
    /**
     * Tipo MIME que retorna la consulta de {@link }
     */
    public final static String MULTIPLE_MIME_HORARIO =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + HORARIO;
    public final static String MULTIPLE_MIME_LISTA =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + LISTA;

    public final static String MULTIPLE_MIME_ALUMNO =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + ALUMNO;

    public final static String MULTIPLE_MIME_ALUMNO_VIEW =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + ALUMNO_VIEW;

    public final static String MULTIPLE_MIME_LISTA_ALUMNO =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + LISTA_ALUMNO;

    public final static String MULTIPLE_MIME_REGISTRO_ASISTENCIA =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + REGISTRO_ASISTENCIA;
    public final static String MULTIPLE_MIME_PROFESOR =
            "vnd.android.cursor.dir/vnd." + AUTORIDAD + PROFESOR;
    /**
     * URI de contenido principal
     */
    public final static Uri CONTENT_URI_HORARIO =
            Uri.parse("content://" + AUTORIDAD + "/" + HORARIO);
    public final static Uri CONTENT_URI_LISTA =
            Uri.parse("content://" + AUTORIDAD + "/" + LISTA);

    public final static Uri CONTENT_URI_ALUMNO =
            Uri.parse("content://" + AUTORIDAD + "/" + ALUMNO);

    public final static Uri CONTENT_URI_ALUMNO_VIEW =
            Uri.parse("content://" + AUTORIDAD + "/" + ALUMNO_VIEW);

    public final static Uri CONTENT_URI_LISTA_ALUMNO =
            Uri.parse("content://" + AUTORIDAD + "/" + LISTA_ALUMNO);
    public final static Uri CONTENT_URI_RESGISTRO_ASISTENCIA =
            Uri.parse("content://" + AUTORIDAD + "/" + REGISTRO_ASISTENCIA);
    public final static Uri CONTENT_URI_PROFESOR =
            Uri.parse("content://" + AUTORIDAD + "/" + PROFESOR);
    /**
     * Comparador de URIs de contenido
     */
    public static final UriMatcher uriMatcher;
    /**
     * C�digo para URIs de multiples registros
     */
    public static final int HORARIOS = 1;
    public static final int LISTAS = 2;
    public static final int ALUMNOS = 3;
    public static final int ALUMNOS_VIEW = 13;
    public static final int LISTA_ALUMNOS = 4;
    public static final int PROFESORRES = 5;
    public static final int REGISTRO_ASISTENCIAS = 11;
    /**
     * C�digo para URIS de un solo registro
     */
    public static final int HORARIOS_ID = 6;
    public static final int LISTAS_ID = 7;
    public static final int ALUMNOS_ID = 8;
    public static final int ALUMNOS_VIEW_ID = 14;
    public static final int LSTA_ALUMNOS_ID = 9;
    public static final int PROFESORES_ID = 10;
    public static final int REGISTRO_ASISTENCIAS_ID = 12;

    // AsignaciÓn de URIs
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTORIDAD, HORARIO, HORARIOS);
        uriMatcher.addURI(AUTORIDAD, HORARIO + "/#", HORARIOS_ID);

        uriMatcher.addURI(AUTORIDAD, LISTA, LISTAS);
        uriMatcher.addURI(AUTORIDAD, LISTA + "/#", LISTAS_ID);

        uriMatcher.addURI(AUTORIDAD, ALUMNO, ALUMNOS);
        uriMatcher.addURI(AUTORIDAD, ALUMNO + "/#", ALUMNOS_ID);

        uriMatcher.addURI(AUTORIDAD, ALUMNO_VIEW, ALUMNOS_VIEW);
        uriMatcher.addURI(AUTORIDAD, ALUMNO_VIEW + "/#", ALUMNOS_VIEW_ID);

        uriMatcher.addURI(AUTORIDAD, LISTA_ALUMNO, LISTA_ALUMNOS);
        uriMatcher.addURI(AUTORIDAD, LISTA_ALUMNO + "/#", LSTA_ALUMNOS_ID);

        uriMatcher.addURI(AUTORIDAD, REGISTRO_ASISTENCIA, REGISTRO_ASISTENCIAS);
        uriMatcher.addURI(AUTORIDAD, REGISTRO_ASISTENCIA + "/#", REGISTRO_ASISTENCIAS_ID);

        uriMatcher.addURI(AUTORIDAD, PROFESOR, PROFESORRES);
        uriMatcher.addURI(AUTORIDAD, PROFESOR + "/#", PROFESORES_ID);
    }

    /**
     * Estructura de la tabla HORARIO
     */
    public static class ColumnasHorario implements BaseColumns {
        private ColumnasHorario() {
        }

        public final static String ENTRADA = "entrada";
        public final static String SALIDA = "salida";
        public final static String DIA = "dia";
        public final static String ID_UNIDAD = "id_unidad";
        public final static String ID_PROFESOR = "id_profesor";
        public final static String ID_LISTA = "id_lista";
    }

    /**
     * Estructura de la tabla LISTA
     */
    public static class ColumnasLista implements BaseColumns {
        private ColumnasLista() {
        }

        public final static String GRUPO = "grupo";
        public final static String PERIODO = "perido";
        public final static String SEMESTRE = "semestre";
        public final static String TURNO = "turno";
        public final static String NO_ALUMNOS = "no_alumnos";
        public final static String UNIDAD_NOMBRE = "UnidadDeAprendizajeNombre";

    }

    /**
     * Estructura de la tabla ALUMNO
     */
    public static class ColumnasAlumno implements BaseColumns {
        private ColumnasAlumno() {
        }
        public final static String NOMBRE = "nombre";
        public final static String CURSO = "curso";
        public final static String ID_LISTA = "id_lista";
    }

    /**
     * Estructura de la tabla LISTA_ALUMNO
     */
    public static class ColumnasListaAlumno implements BaseColumns {
        private ColumnasListaAlumno() {
        }

        public final static String ID_LISTA = "id_pase_lista";
        public final static String ALUMNO_ID = "id_miembro";
    }

    /**
     * Estructura de la tabla REGISTRO_ASISTENCIA
     */
    public static class ColumnasRegistroAsistencia implements BaseColumns {
        private ColumnasRegistroAsistencia() {
        }

        public final static String ID_LISTA = "id_lista";
        public final static String ID_ALUMNO = "id_alumno";
        public final static String FECHA = "fecha";
        public final static String HORA = "hora";
        public final static String DETALLE = "detalle";
        public final static String UNIDAD_ID = "unidad_aprendizaje";
        public final static String ID_TRABAJADOR = "profesornumerotrabajador";
        public final static String SYNC_DONE = "is_sync";
        public final static String ESTADO = "estado";

    }

    /**
     * Estructura de la tabla PROFESOR
     */
    public static class ColumnasProfesor implements BaseColumns {
        private ColumnasProfesor() {
        }

        public final static String NOMBRE = "id_lista";
        public final static String A_PATERNO = "id_alumno";
        public final static String A_MATERNO = "fecha";
        public final static String CLAVE = "hora";
    }


}
