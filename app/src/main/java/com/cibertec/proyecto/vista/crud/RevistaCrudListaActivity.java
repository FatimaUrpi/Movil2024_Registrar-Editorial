package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.RevistaAdapter;
import com.cibertec.proyecto.entity.Revista;
import com.cibertec.proyecto.service.ServiceRevista;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RevistaCrudListaActivity extends NewAppCompatActivity {

    Button btnLista;
    Button btnRegistra;

    ListView lstLista;
    ArrayList<Revista> data = new ArrayList<>();
    RevistaAdapter adaptador;

    ServiceRevista serviceRevista;

   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_crud_lista);

        btnLista = findViewById(R.id.idCrudRevistaLista);
        btnRegistra = findViewById(R.id.idCrudRevistaRegistra);
        lstLista = findViewById(R.id.idCrudRevistaListView);

        adaptador = new RevistaAdapter(this, R.layout.activity_revista_crud_item, data);
        lstLista.setAdapter(adaptador);

        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);

        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        RevistaCrudListaActivity.this,
                        RevistaCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "Registrar");
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


                Revista objDataSeleccionada = data.get(i);

                Intent intent = new Intent(
                        RevistaCrudListaActivity.this, RevistaCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "Actualizar");
                intent.putExtra("var_seleccionado", objDataSeleccionada);
                startActivity(intent);

            }
        });

        lista();


    }

    public void lista(){
        Call<List<Revista>> call = serviceRevista.listaRevista();
        call.enqueue(new Callback<List<Revista>>() {
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                if (response.isSuccessful()){
                    List<Revista> lstSalida = response.body();
                    data.clear();;
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {}
        });
    }




}