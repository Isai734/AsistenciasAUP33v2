package com.gestionuap33.www.asistenciasaup33.datos;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.ResponseBody;

/*
    En este clase hacemos la gestion del los errores del servidor
 */
public class ResponseApi {
    private int estado;
    private String mensaje;

    public ResponseApi(int msg, String body) {
        this.estado = msg;
        this.mensaje = body;
    }

    public int getEstado() {
        return estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        return "(" + getEstado() + "): " + getMensaje();
    }

    public static ResponseApi fromResponseBody(ResponseBody responseBody) {
        Gson gson = new Gson();
        try {
            return gson.fromJson(responseBody.string(), ResponseApi.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}