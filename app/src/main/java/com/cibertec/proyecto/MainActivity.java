package com.cibertec.proyecto;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.cibertec.proyecto.adapter.ProveedorAdapter;
import com.cibertec.proyecto.entity.Proveedor;
import com.cibertec.proyecto.util.NewAppCompatActivity;

import java.util.ArrayList;


public class MainActivity extends NewAppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }


}