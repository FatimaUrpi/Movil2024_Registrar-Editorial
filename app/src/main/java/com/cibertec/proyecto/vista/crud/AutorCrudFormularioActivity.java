package com.cibertec.proyecto.vista.crud;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.entity.Grado;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.service.ServiceAutor;
import com.cibertec.proyecto.service.ServiceGrado;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutorCrudFormularioActivity extends NewAppCompatActivity {
    Button btnRegresar, btnProcesar;
    TextView txtTitulo, txtNombres, txtxApellidos, txtCorreo, txtFNacimiento, txtTelefono;
    Spinner spnEstado;
    ArrayAdapter<String> adaptadorEstado;
    ArrayList<String> estados = new ArrayList<String>();
    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();
    Spinner spnGrado;
    ArrayAdapter<String> adaptadorGrado;
    ArrayList<String> grados = new ArrayList<String>();

    ServicePais paisService;
    ServiceAutor autorService;
    ServiceGrado gradoService;

    String tipo;

    Autor objAutorSeleccionado;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_crud_formulario);


        autorService= ConnectionRest.getConnection().create(ServiceAutor.class);
        gradoService=ConnectionRest.getConnection().create(ServiceGrado.class);
        paisService = ConnectionRest.getConnection().create(ServicePais.class);

        btnRegresar = findViewById(R.id.btnRegresarAutorCrud);
        btnProcesar = findViewById(R.id.btnRegistrarAutorCrud);

        txtTitulo=findViewById(R.id.txtCrudTitulo);
        txtNombres=findViewById(R.id.txtNombresCrud);
        txtxApellidos=findViewById(R.id.txtApellidosCrud);
        txtCorreo=findViewById(R.id.txtCorreoCrud);
        txtTelefono=findViewById(R.id.txtTelefonoCrud);
        txtFNacimiento=findViewById(R.id.txtFechaNacimientoCrud);

        adaptadorEstado= new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,estados);
        spnEstado = findViewById(R.id.spinnerAutorEstadoCrud);
        spnEstado.setAdapter(adaptadorEstado);


        adaptadorPais=new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,paises);
        spnPais=findViewById(R.id.spinnerAutorPaisCrud);
        spnPais.setAdapter(adaptadorPais);

        adaptadorGrado= new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,grados);
        spnGrado=findViewById(R.id.spinnerAutorGradoCrud);
        spnGrado.setAdapter(adaptadorGrado);

        Bundle extras = getIntent().getExtras();
        tipo = (String)extras.get("var_tipo");
        txtTitulo.setText( txtTitulo.getText() + " - " + tipo);

        if (tipo.equals("Actualizar")){
            objAutorSeleccionado = (Autor) extras.get("var_seleccionado");
            txtNombres.setText(objAutorSeleccionado.getNombres());
            txtxApellidos.setText(objAutorSeleccionado.getApellidos());
            txtCorreo.setText(objAutorSeleccionado.getCorreo());
            txtFNacimiento.setText(objAutorSeleccionado.getFechaNacimiento());
            txtTelefono.setText(objAutorSeleccionado.getTelefono());

            btnProcesar.setText("Actualizar");
        }else{
            btnProcesar.setText("Registrar");
        }
        cargaEstado();
        cargaPais();
        cargaGrado();


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        AutorCrudFormularioActivity.this,
                        AutorCrudListaActivity.class);
                startActivity(intent);

            }
        });

        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String estado = spnEstado.getSelectedItem().toString().split(":")[0].trim().toString();
                String pais = spnPais.getSelectedItem().toString().split(":")[0].trim().toString();
                String grado = spnGrado.getSelectedItem().toString().split(":")[0].trim().toString();

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(pais));

                Grado objGrado = new Grado();
                objGrado.setIdGrado(Integer.parseInt(grado));

                Autor objAutor = new Autor();
                objAutor.setNombres(txtNombres.getText().toString());
                objAutor.setApellidos(txtxApellidos.getText().toString());
                objAutor.setCorreo(txtCorreo.getText().toString());
                objAutor.setFechaNacimiento(txtFNacimiento.getText().toString());
                objAutor.setTelefono(txtTelefono.getText().toString());

                objAutor.setEstado(Integer.parseInt(estado));
                objAutor.setPais(objPais);
                objAutor.setGrado(objGrado);

                objAutor.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());

                if (tipo.equals("Actualizar")) {
                    objAutor.setIdAutor(objAutorSeleccionado.getIdAutor());
                    actualizar(objAutor);
                } else {
                    objAutor.setIdAutor(0);
                    registrar(objAutor);
                }


            }
        });









    }
    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    public  void regresar(){

                Intent intent = new Intent(
                        AutorCrudFormularioActivity.this,
                        AutorCrudListaActivity.class);
                startActivity(intent);

    }

    private void registrar(Autor objAutor) {
        Call<Autor> call=autorService.insertaAutor(objAutor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()){
                    Autor objSalida = response.body();
                    mensajeToast(" Registro exitoso  >>> ID >> " + objSalida.getIdAutor());
                    regresar();
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Autor> call, Throwable t) {

            }
        });

    }

    private void actualizar(Autor objAutor) {
        Call<Autor> call=autorService.actualiza(objAutor);
        call.enqueue(new Callback<Autor>() {
            @Override
            public void onResponse(Call<Autor> call, Response<Autor> response) {
                if (response.isSuccessful()){
                    Autor objSalida = response.body();
                    mensajeToast(" Actualizacion exitosa  >>> ID >> " + objSalida.getIdAutor());
                    regresar();
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Autor> call, Throwable t) {
                mensajeAlert("Error al acceder al Servicio Rest "+t.getMessage());
            }
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
                    paises.add(" [ Seleccione Pais ] ");
                    int idSeleccionado = -1;
                    for(Pais obj: lst){
                        paises.add(obj.getIdPais() +" : "+ obj.getNombre());
                        if (tipo.equals("Actualizar")){
                            if (obj.getIdPais()==objAutorSeleccionado.getPais().getIdPais()){
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
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }

    public void cargaGrado(){
        Call<List<Grado>> call=gradoService.Todos();
        call.enqueue(new Callback<List<Grado>>() {
            @Override
            public void onResponse(Call<List<Grado>> call, Response<List<Grado>> response) {
                if (response.isSuccessful()){
                    List<Grado> lista=response.body();
                    grados.clear();
                    grados.add(" [ Seleccione Grado ] ");
                    int idSeleccionado = -1;
                    for(Grado obj:lista) {
                        grados.add(obj.getIdGrado() + " : " + obj.getDescripcion());
                        if (tipo.equals("Actualizar")) {
                            if (obj.getIdGrado() == objAutorSeleccionado.getGrado().getIdGrado()) {
                                idSeleccionado = lista.indexOf(obj);
                            }
                        }
                    }
                    adaptadorGrado.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnGrado.setSelection(idSeleccionado+1);
                    }
                }else {
                    mensajeToast("Error al acceder al servicio Rest");
                }
            }

            @Override
            public void onFailure(Call<List<Grado>> call, Throwable t) {
                mensajeToast("Error al acceder al servicio rest "+ t.getMessage());
            }
        });

    }



    public void cargaEstado(){
        estados.clear();
        estados.add(" [ Seleccione Estado ] ");
        estados.add(" 0 : Inactivo ");
        estados.add(" 1 : Activo ");
        adaptadorEstado.notifyDataSetChanged();
        if (tipo.equals("Actualizar")){
            int estado = objAutorSeleccionado.getEstado();
            if (estado == 0){//Inactivo
                spnEstado.setSelection(1);
            }
            if (estado == 1){//Activo
                spnEstado.setSelection(2);
            }
        }
    }


}