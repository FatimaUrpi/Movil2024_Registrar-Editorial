package com.cibertec.proyecto.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RevistaConsultaActivity extends NewAppCompatActivity {

    Button btnListar;
    ListView lstRevista;
    ArrayList<Revista> data = new ArrayList<>();
    RevistaAdapter adaptador;

    ServiceRevista serviceRevista;
    EditText txtFiltro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revista_consulta);

        btnListar = findViewById(R.id.btnListaRevistaConsulta);
        txtFiltro = findViewById(R.id.txtRegEdiNombre);
        lstRevista = findViewById(R.id.lstRevista);
        adaptador = new RevistaAdapter(this, R.layout.activity_revista_crud_item, data);
        lstRevista.setAdapter(adaptador);

        serviceRevista = ConnectionRest.getConnection().create(ServiceRevista.class);


        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                consulta();
            }
        });


    }

    void consulta(){
        String filtro = txtFiltro.getText().toString().trim();

        Call<List<Revista>> call = serviceRevista.listaRevista(filtro);
        call.enqueue(new Callback<List<Revista>>() {
            @Override
            public void onResponse(Call<List<Revista>> call, Response<List<Revista>> response) {
                if (response.isSuccessful()){
                    List<Revista> lstSalida =   response.body();
                    data.clear();
                    data.addAll(lstSalida);
                    adaptador.notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<List<Revista>> call, Throwable t) {
                mensajeAlert("Mensaje => "  + t.getMessage());
            }
        });


    }


}