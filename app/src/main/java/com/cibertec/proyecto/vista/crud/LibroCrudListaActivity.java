package com.cibertec.proyecto.vista.crud;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.LibroAdapter;
import com.cibertec.proyecto.entity.Libro;
import com.cibertec.proyecto.service.ServiceLibro;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LibroCrudListaActivity extends NewAppCompatActivity {

    Button btnLista;
    Button btnRegistra;

    ListView lstLista;
    ArrayList<Libro> data = new ArrayList<>();
    LibroAdapter adapatador;

    ServiceLibro serviceLibro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_crud_lista);

        btnLista = findViewById(R.id.idCrudLibroLista);
        btnRegistra = findViewById(R.id.idCrudLibroRegistra);
        lstLista = findViewById(R.id.idCrudLibroListView);

        adapatador = new LibroAdapter(this, R.layout.activity_libro_crud_item, data);
        lstLista.setAdapter(adapatador);

        serviceLibro = ConnectionRest.getConnection().create(ServiceLibro.class);

        btnRegistra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        LibroCrudListaActivity.this,
                        LibroCrudFormularioActivity.class);
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

                Libro objDataSeleccionada =  data.get(i);

                Intent intent = new Intent(
                        LibroCrudListaActivity.this,
                        LibroCrudFormularioActivity.class);
                intent.putExtra("var_tipo", "Actualizar");
                intent.putExtra("var_seleccionado", objDataSeleccionada);
                startActivity(intent);
            }
        });

        lista();
    }

    public void lista(){
        Call<List<Libro>> call = serviceLibro.listaLibro();
        call.enqueue(new Callback<List<Libro>>() {
            @Override
            public void onResponse(Call<List<Libro>> call, Response<List<Libro>> response) {
                if (response.isSuccessful()){
                    List<Libro> lstSalida = response.body();
                    data.clear();;
                    data.addAll(lstSalida);
                    adapatador.notifyDataSetChanged();
                }
            }
            @Override
            public void onFailure(Call<List<Libro>> call, Throwable t) {}
        });
    }




}