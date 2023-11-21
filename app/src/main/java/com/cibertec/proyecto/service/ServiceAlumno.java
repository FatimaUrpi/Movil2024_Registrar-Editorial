package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Alumno;
import com.cibertec.proyecto.entity.Libro;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ServiceAlumno {

    @GET("alumno/porNombre/{nombre}")
    public Call<List<Alumno>> listaalumno(@Path("nombre") String alumno);




    @POST("alumno")
    public abstract Call<Alumno> insertaAlumno (@Body Alumno objAlumno);

    @GET("alumno")
    public abstract Call<List<Alumno>> listaAlumno();

    @PUT("alumno")
    public abstract Call<Alumno> actualiza(@Body Alumno objAlumno);

    @POST("alumno")
    public abstract Call<Alumno> registra(@Body Alumno objAlumno);
}