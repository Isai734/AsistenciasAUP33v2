package com.gestionuap33.www.asistenciasaup33.control;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Navi on 16/05/2017.
 */

public class Profesor {

    public String ProfesorNumeroTrabajador;
    public String ProfesorNombre;
    public String ProfesorApellidoPaterno;
    public String ProfesorApellidoMaterno;
    public String clave;

    public Profesor(String profesorNumeroTrabajador, String profesorNombre, String profesorApellidoPaterno, String profesorApellidoMaterno, String clave) {
        ProfesorNumeroTrabajador = profesorNumeroTrabajador;
        ProfesorNombre = profesorNombre;
        ProfesorApellidoPaterno = profesorApellidoPaterno;
        ProfesorApellidoMaterno = profesorApellidoMaterno;
        this.clave = clave;
    }

    public String getProfesorNumeroTrabajador() {
        return ProfesorNumeroTrabajador;
    }

    public void setProfesorNumeroTrabajador(String profesorNumeroTrabajador) {
        ProfesorNumeroTrabajador = profesorNumeroTrabajador;
    }

    public String getProfesorNombre() {
        return ProfesorNombre;
    }

    public void setProfesorNombre(String profesorNombre) {
        ProfesorNombre = profesorNombre;
    }

    public String getProfesorApellidoPaterno() {
        return ProfesorApellidoPaterno;
    }

    public void setProfesorApellidoPaterno(String profesorApellidoPaterno) {
        ProfesorApellidoPaterno = profesorApellidoPaterno;
    }

    public String getProfesorApellidoMaterno() {
        return ProfesorApellidoMaterno;
    }

    public void setProfesorApellidoMaterno(String profesorApellidoMaterno) {
        ProfesorApellidoMaterno = profesorApellidoMaterno;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
