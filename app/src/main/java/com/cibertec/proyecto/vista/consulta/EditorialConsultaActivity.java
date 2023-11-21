package com.cibertec.proyecto.vista.consulta;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.adapter.EditorialAdapter;
import com.cibertec.proyecto.adapter.SalaAdapter;
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.entity.Revista;
import com.cibertec.proyecto.entity.Sala;
import com.cibertec.proyecto.service.ServiceEditorial;
import com.cibertec.proyecto.service.ServiceSala;
import com.cibertec.proyecto.util.ConnectionRest;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Body;

public class EditorialConsultaActivity extends NewAppCompatActivity {

    Button btnListar;
    ListView lstEditorial;
    ArrayList<Editorial> data = new ArrayList<>();
    EditorialAdapter adapter;
    ServiceEditorial serviceEditorial;
    EditText txtFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editorial_consulta);

        btnListar = findViewById(R.id.idBtnEditorialConsulta);
        txtFiltro = findViewById(R.id.idTxtEditorialConsulta);
        lstEditorial = findViewById(R.id.idLstEditorialConsulta);
        adapter = new EditorialAdapter(this, R.layout.activity_editorial_consulta_item, data);
        lstEditorial.setAdapter(adapter);

        serviceEditorial = ConnectionRest.getConnection().create(ServiceEditorial.class);

        btnListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filtro = txtFiltro.getText().toString();
                consulta(filtro);
            }
        });

    }

    public void consulta(String filtro) {

            Call<List<Editorial>> call = serviceEditorial.listaEditorial(filtro);
            call.enqueue(new Callback<List<Editorial>>() {
                @Override
                public void onResponse(Call<List<Editorial>> call, Response<List<Editorial>> response) {
                    if (response.isSuccessful()) {
                        List<Editorial> lstSalida = response.body();
                        data.clear();
                        data.addAll(lstSalida);
                        adapter.notifyDataSetChanged();
                    }

                }

                @Override
                public void onFailure(Call<List<Editorial>> call, Throwable t) {
                    mensajeAlert("ERROR -> Error en la respuesta" + t.getMessage());
                }

            });
        }
    }


