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
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.entity.Grado;
import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.entity.Sede;
import com.cibertec.proyecto.service.SedeService;
import com.cibertec.proyecto.service.ServiceCategoria;
import com.cibertec.proyecto.service.ServiceEditorial;
import com.cibertec.proyecto.service.ServiceModalidad;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.service.ServiceSala;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class EditorialCrudFormularioActivity extends NewAppCompatActivity {

    /*Button*/
    Button btnRegresar, btnProcesar;
    TextView txtTitulo, txtRazonSocial, txtDireccion, txtRuc, txtFCreacion,txtFechaRegistro;

    /*Spinner estado*/
    Spinner spnEstado;
    ArrayAdapter<String> adaptadorEstado;
    ArrayList<String> estados = new ArrayList<String>();

    /*Spinner pais*/
    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();

    /*Spinner categoria*/
    Spinner spnCategoria;
    ArrayAdapter<String> adaptadorCategoria;
    ArrayList<String> categorias = new ArrayList<String>();

    /*Spinners*/
    ServiceEditorial estadoService;
    ServicePais paisService;
    ServiceCategoria categoriaService;

    String tipo;

    Editorial objEditorialSeleccionada;
    ServiceEditorial serviceEditorial;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_formulario);
        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);

        /*Btn*/
        btnRegresar = findViewById(R.id.btnCrudEditorialRegresar);
        btnProcesar = findViewById(R.id.btnCrudEditorialRegistrar);

        txtTitulo = findViewById(R.id.txtCrudTitulo);
        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtDireccion = findViewById(R.id.txtCrudDireccion);
        txtRuc = findViewById(R.id.txtRuc);
        txtFCreacion = findViewById(R.id.txtFechaCreacion);
        txtFechaRegistro = findViewById(R.id.txtFechaRegistro);
        adaptadorEstado = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, estados);
        spnEstado = findViewById(R.id.spinnerEstado);
        spnEstado.setAdapter(adaptadorEstado);

        adaptadorPais = new ArrayAdapter<String>(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spinnerPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorCategoria = new ArrayAdapter<String>(this,androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, categorias);
        spnCategoria = findViewById(R.id.spinnerCategoria);
        spnCategoria.setAdapter(adaptadorCategoria);

        paisService = ConnectionRest.getConnection().create(ServicePais.class);
        categoriaService = ConnectionRest.getConnection().create(ServiceCategoria.class);

        Bundle extras = getIntent().getExtras();
        tipo = (String) extras.get("var_tipo");
        txtTitulo.setText(txtTitulo.getText() + " - " + tipo);

        if (tipo.equals("Actualizar")) {
            objEditorialSeleccionada = (Editorial) extras.get("var_seleccionado");
            txtRazonSocial.setText(objEditorialSeleccionada.getRazonSocial());
            txtDireccion.setText(String.valueOf(objEditorialSeleccionada.getDireccion()));
            txtRuc.setText(String.valueOf(objEditorialSeleccionada.getRuc()));
            txtFCreacion.setText(objEditorialSeleccionada.getFechaCreacion());
            txtFechaRegistro.setText(objEditorialSeleccionada.getFechaRegistro());
            btnProcesar.setText("Actualizar");
        } else {
            btnProcesar.setText("Registrar");
        }

        cargaEstado();
        cargaPais();
        cargaCategoria();

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regresar();

            }
        });
        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String estado = spnEstado.getSelectedItem().toString().split(":")[0].trim().toString();
                String pais = spnPais.getSelectedItem().toString().split(":")[0].trim().toString();
                String categoria = spnCategoria.getSelectedItem().toString().split(":")[0].trim().toString();

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(pais));

                Categoria objCaregoria = new Categoria();
                objCaregoria.setIdCategoria(Integer.parseInt(categoria));

                Editorial objEditorial = new Editorial();
                objEditorial.setRazonSocial(txtRazonSocial.getText().toString());
                objEditorial.setDireccion(txtDireccion.getText().toString());
                objEditorial.setRuc(txtRuc.getText().toString());
                objEditorial.setFechaCreacion(txtFCreacion.getText().toString());
                objEditorial.setFechaRegistro(txtFechaRegistro.getText().toString());
                objEditorial.setPais(objPais);
                objEditorial.setCategoria(objCaregoria);
                objEditorial.setEstado(Integer.parseInt(estado));
                objEditorial.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());

                if (tipo.equals("Actualizar")) {
                    objEditorial.setIdEditorial(objEditorialSeleccionada.getIdEditorial());
                    actualizar(objEditorial);
                } else {
                    objEditorial.setIdEditorial(0);
                    registrar(objEditorial);
                }
            }
        });
    }
    public  void regresar(){
        Intent intent = new Intent(
                EditorialCrudFormularioActivity.this,
                EditorialCrudListaActivity.class);
        startActivity(intent);
    }
    public void registrar(Editorial objEditorial){

        Call<Editorial> call = serviceEditorial.insertaEditorial(objEditorial);
        call.enqueue(new Callback<Editorial>() {

            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if (response.isSuccessful()){
                    Editorial objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdEditorial());
                    regresar();
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {}
        });
    }
    public void actualizar(Editorial objEditorial){

        Call<Editorial> call = serviceEditorial.actualizar(objEditorial);
        call.enqueue(new Callback<Editorial>() {
            @Override
            public void onResponse(Call<Editorial> call, Response<Editorial> response) {
                if (response.isSuccessful()){
                    Editorial objSalida = response.body();
                    mensajeAlert(" Actualización exitosa !!!  >>> ID >> " + objSalida.getIdEditorial());
                    regresar();
                }else{
                    mensajeAlert(response.toString());
                }
            }

            @Override
            public void onFailure(Call<Editorial> call, Throwable t) {}
        });
    }
    public void cargaPais(){
        Call<List<Pais>> call = paisService.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lst =  response.body();
                    paises.clear();
                    paises.add(" [ Seleccione el Pais ] ");
                    int idSeleccionado = -1;
                    for(Pais obj: lst){
                        paises.add(obj.getIdPais() +" : "+ obj.getNombre());
                        if (tipo.equals("Actualizar")){
                            if (obj.getIdPais()==objEditorialSeleccionada.getPais().getIdPais()){
                                idSeleccionado= lst.indexOf(obj);
                            }
                        }
                    }
                    adaptadorPais.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnPais.setSelection(idSeleccionado+1);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
            }
        });
    }
    public void cargaCategoria(){
        Call<List<Categoria>> call=categoriaService.listaTodos();
        call.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (response.isSuccessful()){
                    List<Categoria> lista=response.body();
                    categorias.clear();
                    categorias.add(" [ Seleccione la categoria ] ");
                    int idSeleccionado = -1;
                    for(Categoria obj:lista) {
                        categorias.add(obj.getIdCategoria() + " : " + obj.getDescripcion());
                        if (tipo.equals("Actualizar")) {
                            if (obj.getIdCategoria() == objEditorialSeleccionada.getCategoria().getIdCategoria()) {
                                idSeleccionado = lista.indexOf(obj);
                            }
                        }
                    }
                    adaptadorCategoria.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnCategoria.setSelection(idSeleccionado+1);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {
            }
        });

    }

    public void cargaEstado(){
        estados.clear();
        estados.add(" [ Seleccione el Estado ] ");
        estados.add(" 0 : Inactivo ");
        estados.add(" 1 : Activo ");
        adaptadorEstado.notifyDataSetChanged();
        if (tipo.equals("Actualizar")){
            int estado = objEditorialSeleccionada.getEstado();
            if (estado == 0){//Inactivo
                spnEstado.setSelection(1);
            }
            if (estado == 1){//Activo
                spnEstado.setSelection(2);
            }
        }
    }


}