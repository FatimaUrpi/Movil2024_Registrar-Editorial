package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.entity.Sala;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceEditorial {

    @GET("editorial/porNumero/{numero}")
    public abstract Call<List<Editorial>> listaEditorial(@Path("numero") String numero);

    @GET("editorial")
    public abstract Call<List<Editorial>> listaEditorial();
    @GET("editorial/porNombre/{nombre}")
    public abstract Call<List<Editorial>> listaXEditorial(@Path("nombre")String nombre);

    @POST("editorial")
    public abstract Call<Editorial> insertaEditorial(@Body Editorial objEditorial);

    @PUT("editorial")
    public abstract Call<Editorial> actualizar(@Body Editorial objEditorial);

}
