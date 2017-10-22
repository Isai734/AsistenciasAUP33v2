package com.gestionuap33.www.asistenciasaup33.control;

import java.util.ArrayList;

/**
 * Created by Isai on 20/06/2017.
 */

public class Resources {

    public ArrayList<Alumno> alumno = new ArrayList<>();
    public ArrayList<Lista> lista = new ArrayList<>();
    public ArrayList<RegistroAsistencia> registroasistencia = new ArrayList<>();

    public Resources(ArrayList<Alumno> alumno, ArrayList<Lista> lista, ArrayList<RegistroAsistencia> registroasistencia) {
        this.alumno = alumno;
        this.lista = lista;
        this.registroasistencia = registroasistencia;
    }

    public ArrayList<Alumno> getAlumno() {
        return alumno;
    }

    public void setAlumno(ArrayList<Alumno> alumno) {
        this.alumno = alumno;
    }

    public ArrayList<Lista> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Lista> lista) {
        this.lista = lista;
    }

    public ArrayList<RegistroAsistencia> getRegistroasistencia() {
        return registroasistencia;
    }

    public void setRegistroasistencia(ArrayList<RegistroAsistencia> registroasistencia) {
        this.registroasistencia = registroasistencia;
    }
}
