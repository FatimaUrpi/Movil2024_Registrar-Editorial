package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.entity.Sala;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceAutor {

    @GET("autor")
    public abstract Call<List<Autor>> listaAutor();


    @GET("autor/porNombre/{nombre}")
    public abstract Call<List<Autor>> listaXNombre(@Path("nombre")String nombre);


    @POST("autor")
    public abstract Call<Autor> insertaAutor(@Body Autor objAutor);

    @PUT("autor")
    public abstract Call<Autor> actualiza(@Body Autor objAutor);





}
