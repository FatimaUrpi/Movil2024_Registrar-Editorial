package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.entity.Revista;
import com.cibertec.proyecto.service.ServiceModalidad;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.service.ServiceRevista;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RevistaCrudFormularioActivity extends NewAppCompatActivity {

    Button btnRegresar, btnProcesar;
    TextView txtTitulo, txtNombre, txtFrecuencia, txtFecCrea, txtFecReg;

    Spinner spnEstado;
    ArrayAdapter<String> adaptadorEstado;
    ArrayList<String> estados = new ArrayList<String>();

    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();

    Spinner spnModalidad;
    ArrayAdapter<String> adaptadorModalidad;
    ArrayList<String> modalidades = new ArrayList<String>();


    ServicePais paisService;
    ServiceRevista revistaService;
    ServiceModalidad modalidadService;

    String tipo;
    Revista objRevistaSeleccionada;

    ServiceRevista serviceRevista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_crud_formulario);

        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);

        btnRegresar = findViewById(R.id.btnCrudRevistaRegresar);
        btnProcesar = findViewById(R.id.btnCrudRevistaRegistrar);

        txtTitulo = findViewById(R.id.txtCrudTitulo);
        txtNombre = findViewById(R.id.txtCrudNombre);
        txtFrecuencia = findViewById(R.id.txtCrudFrecuencia);
        txtFecCrea = findViewById(R.id.txtCrudFecCreacion);
        txtFecReg = findViewById(R.id.txtCrudFecRegistro);

        adaptadorEstado = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, estados);
        spnEstado = findViewById(R.id.spnCrudRevistaEstado);
        spnEstado.setAdapter(adaptadorEstado);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnCrudRevistaPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorModalidad = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, modalidades);
        spnModalidad = findViewById(R.id.spnCrudRevistaModalidad);
        spnModalidad.setAdapter(adaptadorModalidad);

        paisService = ConnectionRest.getConnection().create(ServicePais.class);
        revistaService = ConnectionRest.getConnection().create(ServiceRevista.class);
        modalidadService = ConnectionRest.getConnection().create(ServiceModalidad.class);

        Bundle extras = getIntent().getExtras();
        tipo = (String)extras.get("var_tipo");
        txtTitulo.setText( txtTitulo.getText() + " - " + tipo);

        if (tipo.equals("Actualizar")){
            objRevistaSeleccionada = (Revista) extras.get("var_seleccionado");
            txtNombre.setText(objRevistaSeleccionada.getNombre());
            txtFrecuencia.setText(objRevistaSeleccionada.getFrecuencia());
            txtFecCrea.setText(objRevistaSeleccionada.getFechaCreacion());
            txtFecReg.setText(objRevistaSeleccionada.getFechaRegistro());

            btnProcesar.setText("Actualizar");
        }else{
            btnProcesar.setText("Registrar");
        }

        cargaPais();
        cargaModalidad();
        cargaEstado();

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        RevistaCrudFormularioActivity.this,
                        RevistaCrudListaActivity.class);
                startActivity(intent);

            }
        });

        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pais = spnPais.getSelectedItem().toString().split(":")[0].trim().toString();
                String modalidad = spnModalidad.getSelectedItem().toString().split(":")[0].trim().toString();
                String estado = spnEstado.getSelectedItem().toString().split(":")[0].trim().toString();

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(pais));

                Modalidad objModalidad = new Modalidad();
                objModalidad.setIdModalidad(Integer.parseInt(modalidad));

                Revista objRevista = new Revista();
                objRevista.setNombre(txtNombre.getText().toString());
                objRevista.setFrecuencia(txtFrecuencia.getText().toString());
                objRevista.setFechaCreacion(txtFecCrea.getText().toString());
                objRevista.setFechaRegistro(txtFecReg.getText().toString());
                objRevista.setEstado(Integer.parseInt(estado));
                objRevista.setModalidad(objModalidad);
                objRevista.setPais(objPais);




                if (tipo.equals("Actualizar")){
                    objRevista.setIdRevista(objRevistaSeleccionada.getIdRevista());
                    actualiza(objRevista);
                }else{
                    objRevista.setIdRevista(0);
                    registra(objRevista);
                }

            }
        });

    }

    public void registra(Revista objRevista){


        Call<Revista> call = serviceRevista.registra(objRevista);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()){
                    Revista objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdRevista());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Revista> call, Throwable t) {}
        });
    }
    public void actualiza(Revista objRevista){

        Call<Revista> call = serviceRevista.actualiza(objRevista);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()){
                    Revista objSalida = response.body();
                    mensajeAlert(" Actualización exitosa  >>> ID >> " + objSalida.getIdRevista());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Revista> call, Throwable t) {}
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
                    paises.add(" [ Seleccione Pais ] ");
                    int idSeleccionado = -1;
                    for(Pais objPais: lstSalida){
                        paises.add(objPais.getIdPais()  + " : " + objPais.getNombre());
                        if (tipo.equals("Actualizar")){
                            if (objPais.getIdPais() == objRevistaSeleccionada.getPais().getIdPais()){
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


    public void cargaModalidad (){
        Call<List<Modalidad>> call = modalidadService.listaTodos();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()){
                    List<Modalidad> lstSalida = response.body();
                    modalidades.clear();
                    modalidades.add(" [ Seleccione Modalidad ] ");
                    int idSeleccionado = -1;
                    for(Modalidad objModalidad: lstSalida){
                        modalidades.add(objModalidad.getIdModalidad()  + " : " + objModalidad.getDescripcion());
                        if (tipo.equals("Actualizar")){
                            if (objModalidad.getIdModalidad() == objRevistaSeleccionada.getModalidad().getIdModalidad()){
                                idSeleccionado = lstSalida.indexOf(objModalidad);
                            }
                        }
                    }
                    adaptadorModalidad.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnModalidad.setSelection(idSeleccionado + 1);
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {}
        });
    }

    public void cargaEstado(){
        estados.clear();
        estados.add(" [ Seleccione Estado ] ");
        estados.add(" 0 : Inactivo ");
        estados.add(" 1 : Activo ");
        adaptadorEstado.notifyDataSetChanged();
        if (tipo.equals("Actualizar")){
            int estado = objRevistaSeleccionada.getEstado();
            if (estado == 0){//Inactivo
                spnEstado.setSelection(1);
            }
            if (estado == 1){//Activo
                spnEstado.setSelection(2);
            }
        }
    }


}