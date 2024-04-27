package com.cibertec.proyecto.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.ProveedorAdapter;
import com.cibertec.proyecto.entity.Libro;
import com.cibertec.proyecto.entity.Proveedor;
import com.cibertec.proyecto.service.ServiceProveedor;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorConsultaActivity extends NewAppCompatActivity {

    EditText txtRazonSocial;
    Button btnConsultar;

    ListView lstConsultaProveedor;
    ArrayList<Proveedor> data = new ArrayList<Proveedor>();

    ProveedorAdapter adaptador;
    ServiceProveedor servicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_proveedor_consulta);

        txtRazonSocial = findViewById(R.id.txtRegRazonSocial);

        lstConsultaProveedor=findViewById(R.id.lstConsultaProveedores);
        adaptador = new ProveedorAdapter(this, R.layout.activity_proveedor_consulta_item, data);
        lstConsultaProveedor.setAdapter(adaptador);

        servicio=ConnectionRest.getConnection().create(ServiceProveedor.class);
        btnConsultar = findViewById(R.id.btnLista);

        btnConsultar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String filtro = txtRazonSocial.getText().toString();
                consulta(filtro);
            }
        });




    }

    private void consulta(String filtro) {
        Call<List<Proveedor>> call = servicio.listaPorRazSoc(filtro);
        call.enqueue(new Callback<List<Proveedor>>() {
            @Override
            public void onResponse(Call<List<Proveedor>> call, Response<List<Proveedor>> response) {

                if(response.isSuccessful()){
                    List<Proveedor> lstSalida = response.body();
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Proveedor>> call, Throwable t) {

                mensajeAlert("ERROR -> Error en la respuesta" + t.getMessage());
            }
        });
    }

    public  void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }
}