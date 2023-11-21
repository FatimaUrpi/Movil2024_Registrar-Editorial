package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.ProveedorAdapter;
import com.cibertec.proyecto.entity.Proveedor;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.service.ServicePais;
import com.cibertec.proyecto.service.ServiceProveedor;
import com.cibertec.proyecto.service.ServiceTipoProveedor;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;
import com.cibertec.proyecto.vista.consulta.ProveedorConsultaActivity;
import com.cibertec.proyecto.vista.registra.ProveedorRegistraActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProveedorCrudListaActivity extends NewAppCompatActivity {

    Button btnLista;
    Button btnRegistra;
    ListView lstLista;

    ArrayList<Proveedor> data = new ArrayList<>();
    ProveedorAdapter adaptador;

    ServiceProveedor serviceProveedor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proveedor_crud_lista);

        btnLista = findViewById(R.id.idCrudProveedorLista);
        btnRegistra = findViewById(R.id.idCrudProveedorRegistra);
        lstLista = findViewById(R.id.idCrudProveedorListView);

        adaptador = new ProveedorAdapter(this, R.layout.activity_proveedor_crud_item, data);

        lstLista.setAdapter(adaptador);

        serviceProveedor = ConnectionRest.getConnection().create(ServiceProveedor.class);


        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        ProveedorCrudListaActivity.this,
                                     ProveedorCrudFormularioActivity.class);
                intent.putExtra("var_tipo","Registrar");
                startActivity(intent);
            }
        });


        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lista();
            }
        });


        lstLista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //mensajeAlert(">>>>" + i );

                Proveedor objDataSeleccionada =  data.get(i);

                Intent intent = new Intent(
                        ProveedorCrudListaActivity.this,
                        ProveedorCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "Actualizar");
                intent.putExtra("var_Seleccionada", objDataSeleccionada);
                startActivity(intent);
            }
        });

        lista();
    }


    public void lista(){
        Call<List<Proveedor>> call = serviceProveedor.listaProveedor();
        call.enqueue(new Callback<List<Proveedor>>() {
            @Override
            public void onResponse(Call<List<Proveedor>> call, Response<List<Proveedor>> response) {
                if (response.isSuccessful()){
                    List<Proveedor> lstSalida = response.body();
                    data.clear();;
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Proveedor>> call, Throwable t) {}
        });
    }






}