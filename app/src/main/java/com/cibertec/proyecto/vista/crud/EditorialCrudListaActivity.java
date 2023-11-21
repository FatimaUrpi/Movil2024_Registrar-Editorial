package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.AutorAdapter;
import com.cibertec.proyecto.adapter.EditorialAdapter;
import com.cibertec.proyecto.entity.Autor;
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.service.ServiceAutor;
import com.cibertec.proyecto.service.ServiceEditorial;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditorialCrudListaActivity extends NewAppCompatActivity {


    Button btnListar;
    Button btnRegistrar;
    ListView lstEditorial;
    ArrayList<Editorial> data = new ArrayList<>();
    EditorialAdapter adaptador;

    ServiceEditorial serviceEditorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_crud_lista);

        btnListar=findViewById(R.id.idCrudEditorialLista);
        btnRegistrar=findViewById(R.id.idCrudEditorialRegistra);
        lstEditorial=findViewById(R.id.idCrudEditorialListView);

        adaptador = new EditorialAdapter (this, R.layout.activity_editorial_crud_item,data);
        lstEditorial.setAdapter(adaptador);

        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(
                        EditorialCrudListaActivity.this,
                        EditorialCrudFormularioActivity.class);
                intent.putExtra("var_tipo","Registrar");
                startActivity(intent);

            }
        });

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {lista();}
        });



        lstEditorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Editorial objEditorialSeleccionado = data.get(i);

                Intent intent = new Intent(
                        EditorialCrudListaActivity.this,
                        EditorialCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "Actualizar");
                intent.putExtra("var_seleccionado", objEditorialSeleccionado);
                startActivity(intent);
            }
        });
        lista();
    }


        public void lista(){

            Call<List<Editorial>> call = serviceEditorial.listaEditorial();

            call.enqueue(new Callback<List<Editorial>>() {
                @Override
                public void onResponse(Call<List<Editorial>> call, Response<List<Editorial>> response) {
                    if (response.isSuccessful()){
                        List<Editorial> lstSalida = response.body();

                        data.clear();
                        data.addAll(lstSalida);
                        adaptador.notifyDataSetChanged();
                    }
                }
                @Override
                public void onFailure(Call<List<Editorial>> call, Throwable t) {

                }
            });


        }

    }