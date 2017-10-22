package com.gestionuap33.www.asistenciasaup33.control;

import com.google.gson.annotations.SerializedName;

/**
 * Esta clase representa la peticion GET del login, el parametro {@link SerializedName} indica el
 * nombre con que se mapeara el Ojeto JSON permitiendo poner cualquier nombre de variable.
 */
public class Login {

    @SerializedName("persona_clave")
    private int personaId;
    private String usuario;
    private String contrasenia;

    public Login(int personaId, String usuario, String contrasenia) {
        this.personaId = personaId;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
    }

    public int getPersonaId() {
        return personaId;
    }

    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
}
