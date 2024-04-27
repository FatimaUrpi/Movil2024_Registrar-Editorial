package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Alumno;
import com.cibertec.proyecto.entity.Libro;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceAlumno {

    @GET("servicio/alumno/porNombre/{nombre}")
    public Call<List<Alumno>> listaPorNombre(@Path("nombre")String nombre);
}
