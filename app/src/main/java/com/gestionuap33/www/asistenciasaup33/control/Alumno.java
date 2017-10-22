package com.gestionuap33.www.asistenciasaup33.control;

/**
 * Created by Isai on 15/06/2017.
 */

public class Alumno {
    public String AlumnoMatricula;
    public String AlumnoNombre;
    public String AlumnoCurs;
    public int ListaId;

    public Alumno(String alumnoMatricula, String alumnoNombre, String alumnoCurs, int listaId) {
        AlumnoMatricula = alumnoMatricula;
        AlumnoNombre = alumnoNombre;
        AlumnoCurs = alumnoCurs;
        ListaId = listaId;
    }

    public String getAlumnoMatricula() {
        return AlumnoMatricula;
    }

    public void setAlumnoMatricula(String alumnoMatricula) {
        AlumnoMatricula = alumnoMatricula;
    }

    public String getAlumnoNombre() {
        return AlumnoNombre;
    }

    public void setAlumnoNombre(String alumnoNombre) {
        AlumnoNombre = alumnoNombre;
    }

    public String getAlumnoCurs() {
        return AlumnoCurs;
    }

    public void setAlumnoCurs(String alumnoCurs) {
        AlumnoCurs = alumnoCurs;
    }

    public int getListaId() {
        return ListaId;
    }

    public void setListaId(int listaId) {
        ListaId = listaId;
    }
}
