package com.gestionuap33.www.asistenciasaup33.control;

/**
 * Created by Isai on 20/06/2017.
 */

public class RegistroAsistencia {
    public int ListaId;
    public String AlumnoNumeroControl;
    public String RegistroAsistenciaFecha;
    public String RegistroAsistenciaHora;
    public String RegistroAsistenciaDetalle;
    public String UnidadDeAprendizajeId;
    public String ProfesorNumeroTrabajado;

    public RegistroAsistencia(int listaId, String alumnoNumeroControl, String registroAsistenciaFecha, String registroAsistenciaHora, String registroAsistenciaDetalle, String unidadDeAprendizajeId, String profesorNumeroTrabajado) {

        ListaId = listaId;
        AlumnoNumeroControl = alumnoNumeroControl;
        RegistroAsistenciaFecha = registroAsistenciaFecha;
        RegistroAsistenciaHora = registroAsistenciaHora;
        RegistroAsistenciaDetalle = registroAsistenciaDetalle;
        UnidadDeAprendizajeId = unidadDeAprendizajeId;
        ProfesorNumeroTrabajado = profesorNumeroTrabajado;
    }

    public int getListaId() {
        return ListaId;
    }

    public void setListaId(int listaId) {
        ListaId = listaId;
    }

    public String getAlumnoNumeroControl() {
        return AlumnoNumeroControl;
    }

    public void setAlumnoNumeroControl(String alumnoNumeroControl) {
        AlumnoNumeroControl = alumnoNumeroControl;
    }

    public String getRegistroAsistenciaFecha() {
        return RegistroAsistenciaFecha;
    }

    public void setRegistroAsistenciaFecha(String registroAsistenciaFecha) {
        RegistroAsistenciaFecha = registroAsistenciaFecha;
    }

    public String getRegistroAsistenciaHora() {
        return RegistroAsistenciaHora;
    }

    public void setRegistroAsistenciaHora(String registroAsistenciaHora) {
        RegistroAsistenciaHora = registroAsistenciaHora;
    }

    public String getRegistroAsistenciaDetalle() {
        return RegistroAsistenciaDetalle;
    }

    public void setRegistroAsistenciaDetalle(String registroAsistenciaDetalle) {
        RegistroAsistenciaDetalle = registroAsistenciaDetalle;
    }

    public String getUnidadDeAprendizajeId() {
        return UnidadDeAprendizajeId;
    }

    public void setUnidadDeAprendizajeId(String unidadDeAprendizajeId) {
        UnidadDeAprendizajeId = unidadDeAprendizajeId;
    }

    public String getProfesorNumeroTrabajado() {
        return ProfesorNumeroTrabajado;
    }

    public void setProfesorNumeroTrabajado(String profesorNumeroTrabajado) {
        ProfesorNumeroTrabajado = profesorNumeroTrabajado;
    }
}
