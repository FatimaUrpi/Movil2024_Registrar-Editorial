package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Revista;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceRevista {

    @GET("revista/porNombre/{nombre}")
    public Call<List<Revista>> listaRevista(@Path("nombre") String nombre);

    @GET("revista")
    public abstract Call<List<Revista>> listaRevista();

    @POST("revista")
    public abstract Call<Revista> registra(@Body Revista objRevista);

    @PUT("revista")
    public abstract Call<Revista> actualiza(@Body Revista objRevista);
}
