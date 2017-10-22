package com.gestionuap33.www.asistenciasaup33.datos;

import com.gestionuap33.www.asistenciasaup33.control.Profesor;
import com.gestionuap33.www.asistenciasaup33.control.Resources;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * NOta que la peticion que hacemos es del tipo GET es decir que no se enviara nada en el Body de esta, pero que si nos regresara
 * un JSON con los datos del usuario que mandaremos como parametro en el metodo y que este se situara al final de la URL.
 * Si quisieramos enviar datos en el body de la peticion y asi mismo recibir una respuesta del tipo JSON tendriamos que usar
 * el metodo POST y en lugar de utilizar la palabra @Path usariamos @Body.
 */

public interface RetrofitService {
    //Sustituir por URL de server
    public static final String BASE_URL = "http://192.168.43.203/gestionuap/ws/";

    @GET("profesor/{clave}")
    Call<Profesor> login(@Path("clave") String clave);

    @GET("resources/{clave}")
    Call<Resources> resources(@Path("clave") String clave);

    @POST("resources")
    Call<ResponseApi> sync(@Body Resources resources);
}
