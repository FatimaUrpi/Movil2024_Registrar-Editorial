package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Pais;
import com.cibertec.proyecto.entity.Proveedor;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.entity.Sede;
import com.cibertec.proyecto.entity.TipoProveedor;
import com.cibertec.proyecto.service.SedeService;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.service.ServiceProveedor;
import com.cibertec.proyecto.service.ServiceSala;
import com.cibertec.proyecto.service.ServiceTipoProveedor;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.FunctionUtil;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorCrudFormularioActivity extends NewAppCompatActivity {

    Button btnRegresar,btnProcesar;
    TextView txtRuc,txtRazonSocial,txtTelefono,txtDireccion,txtIDPro,txtCelular,txtContacto,txtEstado,txTitulo;

    Spinner spnPais;
    ArrayAdapter<String> adaptadorPais;
    ArrayList<String> Pais = new ArrayList<String>();

    Spinner spnTipoProveedor;
    ArrayAdapter<String> adaptadorTipoProveedor;
    ArrayList<String> TipoProveedor = new ArrayList<String>();

    ServicePais servicePais;
    ServiceTipoProveedor  serviceTipoProveedor;
    ServiceProveedor serviceProveedor;

    String tipo;
    Proveedor objProveedorSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_crud_formulario);

        serviceProveedor = ConnectionRest.getConnection().create(ServiceProveedor.class);

        btnRegresar = findViewById(R.id.btnProveedorRegresar);
        btnProcesar = findViewById(R.id.btnProveedorRegistrar);

        txTitulo = findViewById(R.id.txtTituloProveedor);
        txtIDPro = findViewById(R.id.txtIdProveedor);
        txtRuc = findViewById(R.id.txtRuc);
        txtRazonSocial = findViewById(R.id.txtRazonSocial);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDireccion = findViewById(R.id.txtDireccion);
        txtCelular = findViewById(R.id.txtCelular);
        txtContacto = findViewById(R.id.txtContacto);
        txtEstado = findViewById(R.id.txtEstado);

        adaptadorPais = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,Pais);
        spnPais = findViewById(R.id.spnPais);
        spnPais.setAdapter(adaptadorPais);

        adaptadorTipoProveedor = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,TipoProveedor);
        spnTipoProveedor = findViewById(R.id.spnTipoProveedor);
        spnTipoProveedor.setAdapter(adaptadorTipoProveedor);

        servicePais = ConnectionRest.getConnection().create(ServicePais.class);
        serviceTipoProveedor = ConnectionRest.getConnection().create(ServiceTipoProveedor.class);

        Bundle extras = getIntent().getExtras();
        tipo = (String)extras.get("var_tipo");
        txTitulo.setText(txTitulo.getText() + "-" + tipo);


        if (tipo.equals("Actualizar"))
        {
            objProveedorSeleccionada = (Proveedor) extras.get("var_Seleccionada");
            txtIDPro.setText(String.valueOf(objProveedorSeleccionada.getIdProveedor()));
            txtRuc.setText(objProveedorSeleccionada.getRuc());
            txtRazonSocial.setText(objProveedorSeleccionada.getRazonsocial());
            txtDireccion.setText(objProveedorSeleccionada.getDireccion());
            txtTelefono.setText(objProveedorSeleccionada.getTelefono());
            txtCelular.setText(objProveedorSeleccionada.getCelular());
            txtContacto.setText(objProveedorSeleccionada.getContacto());
            txtEstado.setText(String.valueOf(objProveedorSeleccionada.getEstado()));

            btnProcesar.setText("Actualizar");
        }else {
            btnProcesar.setText("Registrar");
        }

        cargaPais();
        cargaTipoProveedor();


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        ProveedorCrudFormularioActivity.this,
                        ProveedorCrudListaActivity.class);
                startActivity(intent);

            }
        });

        btnProcesar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pais = spnPais.getSelectedItem().toString().split(":")[0].trim().toString();
                String tipoProveedor = spnTipoProveedor.getSelectedItem().toString().split(":")[0].trim().toString();

                Pais objPais = new Pais();
                objPais.setIdPais(Integer.parseInt(pais));

                TipoProveedor objTipoProveedor = new TipoProveedor();
                objTipoProveedor.setIdTipoProveedor(Integer.parseInt(tipoProveedor));

                Proveedor objProveedor = new Proveedor();
                objProveedor.setIdProveedor(Integer.parseInt(txtIDPro.getText().toString()));
                objProveedor.setRuc(txtRuc.getText().toString());
                objProveedor.setRazonsocial(txtRazonSocial.getText().toString());
                objProveedor.setDireccion(txtDireccion.getText().toString());
                objProveedor.setTelefono(txtTelefono.getText().toString());
                objProveedor.setCelular(txtCelular.getText().toString());
                objProveedor.setContacto(txtContacto.getText().toString());
                objProveedor.setEstado(Integer.parseInt(txtEstado.getText().toString()));
                objProveedor.setPais(objPais);
                objProveedor.setTipoProveedor(objTipoProveedor);
                objProveedor.setFechaRegistro(FunctionUtil.getFechaActualStringDateTime());

                if (tipo.equals("Actualizar")){
                    objProveedor.setIdProveedor(objProveedorSeleccionada.getIdProveedor());
                    actualiza(objProveedor);
                }else {
                    objProveedor.setIdProveedor(0);
                    registra(objProveedor);
                }
            }
        });

    }

       public void registra(Proveedor objProveedor){

        Call<Proveedor> call = serviceProveedor.registra(objProveedor);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()){
                    Proveedor objSalida = response.body();
                    mensajeAlert(" Registro exitoso  >>> ID >> " + objSalida.getIdProveedor());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {}
        });
    }

    public void actualiza(Proveedor objProveedor){

        Call<Proveedor> call = serviceProveedor.actualiza(objProveedor);
        call.enqueue(new Callback<Proveedor>() {
            @Override
            public void onResponse(Call<Proveedor> call, Response<Proveedor> response) {
                if (response.isSuccessful()){
                    Proveedor objSalida = response.body();
                    mensajeAlert(" Actualización exitosa  >>> ID >> " + objSalida.getIdProveedor());
                }else{
                    mensajeAlert(response.toString());
                }
            }
            @Override
            public void onFailure(Call<Proveedor> call, Throwable t) {}
        });
    }

    public void cargaPais (){
        Call<List<Pais>> call = servicePais.listaTodos();
        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                if (response.isSuccessful()){
                    List<Pais> lstSalida = response.body();
                    Pais.clear();
                    Pais.add(" [ Seleccione Pais ] ");
                    int idSeleccionado = -1;
                    for(Pais objPais: lstSalida){
                        Pais.add(objPais.getIdPais()  + " : " + objPais.getNombre());
                        if (tipo.equals("Actualizar")){
                            if (objPais.getIdPais() == objProveedorSeleccionada.getPais().getIdPais()){
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

    public void cargaTipoProveedor (){
        Call<List<TipoProveedor>> call = serviceTipoProveedor.listaTipoProveedor();
        call.enqueue(new Callback<List<TipoProveedor>>() {
            @Override
            public void onResponse(Call<List<TipoProveedor>> call, Response<List<TipoProveedor>> response) {
                if (response.isSuccessful()){
                    List<TipoProveedor> lstSalida = response.body();
                    TipoProveedor.clear();
                    TipoProveedor.add(" [ Seleccione Tipo Proveedor ] ");
                    int idSeleccionado = -1;
                    for(TipoProveedor objTipoProveedor: lstSalida){
                        TipoProveedor.add(objTipoProveedor.getIdTipoProveedor()  + " : " + objTipoProveedor.getDescripcion());
                        if (tipo.equals("Actualizar")){
                            if (objTipoProveedor.getIdTipoProveedor() == objProveedorSeleccionada.getTipoProveedor().getIdTipoProveedor()){
                                idSeleccionado = lstSalida.indexOf(objTipoProveedor);
                            }
                        }
                    }
                    adaptadorTipoProveedor.notifyDataSetChanged();
                    if (tipo.equals("Actualizar")){
                        spnTipoProveedor.setSelection(idSeleccionado + 1);
                    }

                }
            }
            @Override
            public void onFailure(Call<List<TipoProveedor>> call, Throwable t) {}
        });
    }

}