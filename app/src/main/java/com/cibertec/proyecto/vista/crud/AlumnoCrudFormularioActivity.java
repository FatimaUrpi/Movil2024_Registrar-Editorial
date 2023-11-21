package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Alumno;
import com.cibertec.proyecto.entity.Modalidad;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.service.ServiceAlumno;
import com.cibertec.proyecto.service.ServiceModalidad;
import com.cibertec.proyecto.service.ServicePais;

import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlumnoCrudFormularioActivity extends NewAppCompatActivity {

    Button btnRegresar, btnProcesar;

    TextView txtTitulo, txtNombre, txtApellido, txtTelefono, txtDni, txtCorreo, txtDireccion,txtNacimiento;

    Spinner spnEstado;
    ArrayAdapter<String> adaptadorEstado;
    ArrayList<String> estados = new ArrayList<>();


    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> paises = new ArrayList<String>();


    Spinner spnModalidad;
    ArrayAdapter<String> adaptadorModalidad;
    ArrayList<String> modalidades = new ArrayList<String>();


    ServiceAlumno alumnoService;
    ServicePais paisService;
    ServiceModalidad modalidadService;


    String tipo;
    Alumno objAlumnoSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alumno_crud_formulario);


        alumnoService = ConnectionRest.getConnection().create(ServiceAlumno.class);


        btnRegresar = findViewById(R.id.btnCrudAlumnoRegresar);
        btnProcesar = findViewById(R.id.btnCrudAlumnoRegistrar);

        txtTitulo = findViewById(R.id.txtCrudTitulo);
        txtNombre= findViewById(R.id.txtCrudNombre);
        txtApellido= findViewById(R.id.txtCrudApellido);
        txtTelefono = findViewById(R.id.txtCrudTelefono);
        txtCorreo = findViewById(R.id.txtCrudCorreo);
        txtDni = findViewById(R.id.txtCrudDni);
        txtDireccion = findViewById(R.id.txtCrudDireccion);
        txtNacimiento =findViewById(R.id.txtCrudNacimiento);

        adaptadorEstado = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, estados);
        spnEstado = findViewById(R.id.spnCrudAlumnoEstado);
        spnEstado.setAdapter(adaptadorEstado);


        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, paises);
        spnPais = findViewById(R.id.spnCrudAlumnoPais);
        spnPais.setAdapter(adaptadorPais);


        adaptadorModalidad = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, modalidades);
        spnModalidad = findViewById(R.id.spnCrudAlumnoModalidad);
        spnModalidad.setAdapter(adaptadorModalidad);


        alumnoService = ConnectionRest.getConnection().create(ServiceAlumno.class);
        paisService = ConnectionRest.getConnection().create(ServicePais.class);
        modalidadService =ConnectionRest.getConnection().create(ServiceModalidad.class);


        Bundle extras = getIntent().getExtras();
        tipo = (String)extras.get("var_tipo");


        if(tipo.equals("Actualizar")) {

            objAlumnoSeleccionada = (Alumno) extras.get("var_seleccionado");
            txtNombre.setText(objAlumnoSeleccionada.getNombres());
            txtApellido.setText(objAlumnoSeleccionada.getApellidos());
            txtTelefono.setText(objAlumnoSeleccionada.getTelefono());
            txtDni.setText(objAlumnoSeleccionada.getDni());
            txtCorreo.setText(objAlumnoSeleccionada.getCorreo());
            txtDireccion.setText(objAlumnoSeleccionada.getDireccion());
            txtNacimiento.setText(objAlumnoSeleccionada.getFechaNacimiento());
            btnProcesar.setText("Actualizar");
        }else {
            btnProcesar.setText("Resgistrar");
        }

        cargaEstado();
        cargaPais();
        cargaModalidad ();



        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        AlumnoCrudFormularioActivity.this,
                        AlumnoCrudListaActivity.class);
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

                Alumno objAlumno = new Alumno();
                objAlumno.setNombres(txtNombre.getText().toString());
                objAlumno.setApellidos(txtApellido.getText().toString());
                objAlumno.setTelefono(txtTelefono.getText().toString());
                objAlumno.setDni(txtDni.getText().toString());
                objAlumno.setCorreo(txtCorreo.getText().toString());
                objAlumno.setDireccion(txtDireccion.getText().toString());
                objAlumno.setFechaNacimiento(txtNacimiento.getText().toString());
                objAlumno.setPais(objPais);
                objAlumno.setModalidad(objModalidad);
                objAlumno.setEstado(Integer.parseInt(estado));
                objAlumno.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());


                if (tipo.equals("Actualizar")){
                    objAlumno.setIdAlumno(objAlumnoSeleccionada.getIdAlumno());
                    actualiza(objAlumno);
                }else{
                    objAlumno.setIdAlumno(0);
                    registra(objAlumno);
                }
            }
        });
    }


    public void registra(Alumno objAlumno){


        Call<Alumno> call = alumnoService.registra(objAlumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()){
                    Alumno objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdAlumno());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {}
        });
    }


    public void actualiza(Alumno objAlumno){

        Call<Alumno> call = alumnoService.actualiza(objAlumno);
        call.enqueue(new Callback<Alumno>() {
            @Override
            public void onResponse(Call<Alumno> call, Response<Alumno> response) {
                if (response.isSuccessful()){
                    Alumno objSalida = response.body();
                    mensajeAlert(" Actualización exitosa  >>> ID >> " + objSalida.getIdAlumno());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Alumno> call, Throwable t) {}
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
                            if (objModalidad.getIdModalidad() == objAlumnoSeleccionada.getModalidad().getIdModalidad()){
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
                            if (objPais.getIdPais() == objAlumnoSeleccionada.getPais().getIdPais()){
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
            int estado = objAlumnoSeleccionada.getEstado();
            if (estado == 0){//Inactivo
                spnEstado.setSelection(1);
            }
            if (estado == 1){//Activo
                spnEstado.setSelection(2);
            }
        }
    }


}