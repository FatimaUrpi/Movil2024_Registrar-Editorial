package com.cibertec.proyecto.entity;

import java.io.Serializable;

public class Categoria implements Serializable {

    private int idCategoria;
    private String  descripcion;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
