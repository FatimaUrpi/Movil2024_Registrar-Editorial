package com.cibertec.proyecto.vista.registra;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

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

public class RevistaRegistraActivity extends NewAppCompatActivity {
/*
    //Modalidad
    Spinner spnModalidad;
    ArrayAdapter<String> adaptadorModalidad;
    ArrayList<String> modalidad = new ArrayList<String>();

    //PaIS
    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> pais = new ArrayList<String>();

    //Servicio
    ServiceRevista serviceRevista;
    ServiceModalidad serviceModalidad;
    ServicePais servicePais;

    //Componentes del formulario
    Button btnRegistrar;
    EditText txtNombre, txtFrecuencia, txtFecCreacion, txtFecReg;
*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_revista_registra);
/*
        //conecta a los servicios Rest
        serviceModalidad = ConnectionRest.getConnection().create(ServiceModalidad.class);
        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);

        //Relacionar los variables con la variables de la GUI
        txtNombre = findViewById(R.id.idTxtNombre);
        txtFrecuencia = findViewById(R.id.idTxtFrecuencia);
        txtFecCreacion = findViewById(R.id.idTxtFecCreacion);
        txtFecReg = findViewById(R.id.idtxtFechaReg);
        btnRegistrar = findViewById(R.id.idBtnRegistrar);

        adaptadorModalidad = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, modalidad);
        spnModalidad = findViewById(R.id.idSpnModalidad);
        spnModalidad.setAdapter(adaptadorModalidad);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, pais);
        spnPais = findViewById(R.id.idSpnPais);
        spnPais.setAdapter(adaptadorPais);

        cargaModalidad();;
        cargaPais();

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nom = txtNombre.getText().toString();
                String frec = txtFrecuencia.getText().toString();
                String fecc = txtFecCreacion.getText().toString();
                String fecr = txtFecReg.getText().toString();
                String idModalidad= spnModalidad.getSelectedItem().toString().split(":")[0];
                String idPais= spnPais.getSelectedItem().toString().split(":")[0];

                Modalidad objModalidad = new Modalidad();
                objModalidad.setIdModalidad(Integer.parseInt(idModalidad));

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(idPais));


                Revista objRevista = new Revista();
                objRevista.setNombre(nom);
                objRevista.setFrecuencia(frec);
                objRevista.setFechaCreacion(fecc);
                objRevista.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());
                objRevista.setFechaRegistro(fecr);
                objRevista.setEstado(1);
                objRevista.setModalidad(objModalidad);
                objRevista.setPais(objPais);

                insertaRevista(objRevista);

            }
        });
    }*/
/*
    public void mensajeToast(String mensaje){
        Toast toast1 =  Toast.makeText(getApplicationContext(),mensaje, Toast.LENGTH_LONG);
        toast1.show();
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
*/
/*
    public void cargaModalidad(){
        Call<List<Modalidad>> call = serviceModalidad.listaTodos();
        call.enqueue(new Callback<List<Modalidad>>() {
            @Override
            public void onResponse(Call<List<Modalidad>> call, Response<List<Modalidad>> response) {
                if (response.isSuccessful()){
                    List<Modalidad> lst =  response.body();
                    for(Modalidad obj: lst){
                        modalidad.add(obj.getIdModalidad() +":"+ obj.getDescripcion());
                    }
                    adaptadorModalidad.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }
            @Override
            public void onFailure(Call<List<Modalidad>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }*/
/*
    public void cargaPais(){
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lst =  response.body();
                    for(Pais obj: lst){
                        pais.add(obj.getIdPais() +":"+ obj.getNombre());
                    }
                    adaptadorPais.notifyDataSetChanged();
                }else{
                    mensajeToast("Error al acceder al Servicio Rest >>> ");
                }
            }
            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }
*//*
    public  void insertaRevista(Revista objRevista){
        //Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //String json = gson.toJson(objEditorial);
        //mensajeAlert(json);*/
/*
        Call<Revista> call = serviceRevista.registra(objRevista);
        call.enqueue(new Callback<Revista>() {
            @Override
            public void onResponse(Call<Revista> call, Response<Revista> response) {
                if (response.isSuccessful()){
                    Revista objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdRevista()
                            + " >>> Razón Social >>> " +  objSalida.getNombre());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Revista> call, Throwable t) {
                mensajeToast("Error al acceder al Servicio Rest >>> " + t.getMessage());
            }
        });
    }*/

    }
}