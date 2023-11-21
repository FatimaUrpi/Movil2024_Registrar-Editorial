package com.cibertec.proyecto.service;

import com.cibertec.proyecto.entity.Proveedor;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceProveedor {
    @GET("util/registraProveedor")
    public abstract Call<Proveedor> RegistraProveedor(Proveedor objProveedor);

    @GET("proveedor/porRazonSocial/{razSoc}")
    public Call<List<Proveedor>> ListaProveedor (@Path("razSoc") String razSoc);

    @GET("proveedor")
    public abstract Call<List<Proveedor>> listaProveedor();

    @POST("proveedor")
    public abstract Call<Proveedor> registra(@Body Proveedor objProveedor);

    @PUT("proveedor")
    public abstract Call<Proveedor> actualiza(@Body Proveedor objProveedor);

}
