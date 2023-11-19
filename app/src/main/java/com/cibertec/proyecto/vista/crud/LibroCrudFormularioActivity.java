package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Categoria;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.entity.Libro;
import com.cibertec.proyecto.service.ServiceCategoriaLibro;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.service.ServiceLibro;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.cibertec.proyecto.util.ValidacionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LibroCrudFormularioActivity extends NewAppCompatActivity {

    Button btnRegresar, btnProcesar;
    TextView  txtLibro, txtTitulo, txtAnio, txtSerie;

    Spinner spnEstado;
    ArrayAdapter<String> adaptadorEstado;
    ArrayList<String> estados = new ArrayList<String>();

    Spinner spnCategoria;
    ArrayAdapter<String> adaptadorCategoria;
    ArrayList<String> categorias = new ArrayList<String>();

    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();


    ServiceCategoriaLibro categoriaService;
    ServiceLibro libroService;
    ServicePais paisService;

    String tipo;
    Libro objLibroSeleccionada;

    ServiceLibro serviceLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_crud_formulario);

        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);

        btnRegresar = findViewById(R.id.btnCrudLibroRegresar);
        btnProcesar = findViewById(R.id.btnCrudLibroRegistrar);

        txtLibro = findViewById(R.id.txtCrudLibro);
        txtTitulo = findViewById(R.id.txtCrudTitulo);
        txtAnio = findViewById(R.id.txtCrudAnio);
        txtSerie = findViewById(R.id.txtCrudSerie);


        adaptadorEstado = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, estados);
        spnEstado = findViewById(R.id.spnCrudLibroEstado);
        spnEstado.setAdapter(adaptadorEstado);

        adaptadorCategoria = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categorias);
        spnCategoria = findViewById(R.id.spnCrudLibroCategoria);
        spnCategoria.setAdapter(adaptadorCategoria);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnCrudLibroPais);
        spnPais.setAdapter(adaptadorPais);

        categoriaService = ConnectionRest.getConnection().create(ServiceCategoriaLibro.class);
        libroService = ConnectionRest.getConnection().create(ServiceLibro.class);
        paisService = ConnectionRest.getConnection().create(ServicePais.class);

        Bundle extras = getIntent().getExtras();
        tipo = (String)extras.get("var_tipo");
        txtLibro.setText( txtLibro.getText() + " - " + tipo);

        if (tipo.equals("Actualizar")){
            objLibroSeleccionada = (Libro) extras.get("var_seleccionado");
            txtTitulo.setText(objLibroSeleccionada.getTitulo());
            txtAnio.setText(String.valueOf(objLibroSeleccionada.getAnio()));
            txtSerie.setText(String.valueOf(objLibroSeleccionada.getSerie()));
            btnProcesar.setText("Actualizar");
        }else{
            btnProcesar.setText("Registrar");
        }

        cargaCategoria();
        cargaPais();
        cargaEstado();

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        LibroCrudFormularioActivity.this,
                        LibroCrudListaActivity.class);
                startActivity(intent);

            }
        });

        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String categoria = spnCategoria.getSelectedItem().toString().split(":")[0].trim().toString();
                String pais = spnPais.getSelectedItem().toString().split(":")[0].trim().toString();
                String estado = spnEstado.getSelectedItem().toString().split(":")[0].trim().toString();


                Categoria objCategoria = new Categoria();
                objCategoria.setIdCategoria(Integer.parseInt(categoria));

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(pais));

                Libro objLibro = new Libro();
                objLibro.setTitulo(txtTitulo.getText().toString());
                objLibro.setAnio(Integer.parseInt(txtAnio.getText().toString()));
                objLibro.setSerie(txtSerie.getText().toString());
                objLibro.setCategoria(objCategoria);
                objLibro.setPais(objPais);
                objLibro.setEstado(Integer.parseInt(estado));
                objLibro.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());


                if (tipo.equals("Actualizar")){
                    objLibro.setIdLibro(objLibroSeleccionada.getIdLibro());
                    actualiza(objLibro);
                }else{
                    objLibro.setIdLibro(0);
                    registra(objLibro);
                }
            }
        });

    }

    public void registra(Libro objLibro){
       /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objSala);
        mensajeAlert(json);*/

        Call<Libro> call = serviceLibro.insertaLibro(objLibro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if (response.isSuccessful()){
                    Libro objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdLibro());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Libro> call, Throwable t) {}
        });
    }
    public void actualiza(Libro objLibro){
         /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(objSala);
        mensajeAlert(json);*/
        Call<Libro> call = serviceLibro.actualiza(objLibro);
        call.enqueue(new Callback<Libro>() {
            @Override
            public void onResponse(Call<Libro> call, Response<Libro> response) {
                if (response.isSuccessful()){
                    Libro objSalida = response.body();
                    mensajeAlert(" Actualización exitosa  >>> ID >> " + objSalida.getIdLibro());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Libro> call, Throwable t) {}
        });
    }

    public void cargaCategoria(){
        Call<List<Categoria>> call = categoriaService.listaTodos();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()){
                    List<Categoria> lstSalida = response.body();
                    categorias.clear();
                    categorias.add(" [ Seleccione Categoria ] ");
                    int idSeleccionado = -1;
                    for(Categoria objCategoria: lstSalida){
                        categorias.add(objCategoria.getIdCategoria()  + " : " + objCategoria.getDescripcion());
                        if (tipo.equals("Actualizar")){
                            if (objCategoria.getIdCategoria() == objLibroSeleccionada.getCategoria().getIdCategoria()){
                                idSeleccionado = lstSalida.indexOf(objCategoria);
                            }
                        }
                    }
                    adaptadorCategoria.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnCategoria.setSelection(idSeleccionado + 1);
                    }

                }
            }
            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {}
        });
    }

    public void cargaPais (){
        Call<List<Pais>> call = paisService.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lstSalida = response.body();
                    paises.clear();
                    paises.add(" [ Seleccione Paìs ] ");
                    int idSeleccionado = -1;
                    for(Pais objPais: lstSalida){
                        paises.add(objPais.getIdPais()  + " : " + objPais.getNombre());
                        if (tipo.equals("Actualizar")){
                            if (objPais.getIdPais() == objLibroSeleccionada.getPais().getIdPais()){
                                idSeleccionado = lstSalida.indexOf(objPais);
                            }
                        }
                    }
                    adaptadorPais.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnPais.setSelection(idSeleccionado + 1);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {}
        });
    }

    public void cargaEstado(){
        estados.clear();
        estados.add(" [ Seleccione Estado ] ");
        estados.add(" 0 : Inactivo ");
        estados.add(" 1 : Activo ");
        adaptadorEstado.notifyDataSetChanged();
        if (tipo.equals("Actualizar")){
            int estado = objLibroSeleccionada.getEstado();
            if (estado == 0){//Inactivo
                spnEstado.setSelection(1);
            }
            if (estado == 1){//Activo
                spnEstado.setSelection(2);
            }
        }
    }
}