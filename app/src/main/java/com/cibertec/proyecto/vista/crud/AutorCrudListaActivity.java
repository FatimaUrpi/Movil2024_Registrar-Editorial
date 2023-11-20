package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.AutorAdapter;
import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.service.ServiceAutor;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AutorCrudListaActivity extends NewAppCompatActivity {

    Button btnListar;
    Button btnRegistrar;
    ListView lstAutor;
    ArrayList<Autor> data = new ArrayList<>();
    AutorAdapter adaptador;

    ServiceAutor serviceAutor;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autor_crud_lista);

        btnListar=findViewById(R.id.btnListarCrudAutor);
        btnRegistrar=findViewById(R.id.btnRegistrarCrudAutor);
        lstAutor=findViewById(R.id.listCrudAutor);

        adaptador = new AutorAdapter(this, R.layout.activity_autor_crud_item,data);
        lstAutor.setAdapter(adaptador);

        serviceAutor = ConnectionRest.getConnection().create(ServiceAutor.class);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(
                        AutorCrudListaActivity.this,
                        AutorCrudFormularioActivity.class);
                intent.putExtra("var_tipo","Registrar");
                startActivity(intent);

            }
        });


        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {listar();}
        });

        lstAutor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //mensajeAlert(">>>>" + i );

                Autor objAutorSeleccionado =  data.get(i);

                Intent intent = new Intent(
                        AutorCrudListaActivity.this,
                        AutorCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "Actualizar");
                intent.putExtra("var_seleccionado", objAutorSeleccionado);
                startActivity(intent);
            }
        });
      listar();

    }

    private void listar() {

        Call<List<Autor>> call = serviceAutor.listaAutor();

        call.enqueue(new Callback<List<Autor>>() {
            @Override
            public void onResponse(Call<List<Autor>> call, Response<List<Autor>> response) {
                if (response.isSuccessful()){
                    List<Autor> lstSalida = response.body();

                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Autor>> call, Throwable t) {

            }
        });


    }


}