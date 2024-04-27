package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.entity.Libro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceEditorial {


    @GET("servicio/editorial/porNombre/{nombre}")
    public Call<List<Editorial>> listaPorNombre(@Path("nombre")String nombre);



}
