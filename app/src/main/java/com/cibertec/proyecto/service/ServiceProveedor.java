package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Proveedor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ServiceProveedor {
    @GET("servicio/proveedor/porRazonSocial/{razSoc}")
    public Call<List<Proveedor>> listaPorRazSoc(@Path("razSoc")String razSoc);

    //paso 1: creacion del service

}
