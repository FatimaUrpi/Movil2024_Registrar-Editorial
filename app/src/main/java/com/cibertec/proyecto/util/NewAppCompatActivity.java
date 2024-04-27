package com.cibertec.proyecto.util;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.vista.consulta.AlumnoConsultaActivity;
import com.cibertec.proyecto.vista.consulta.AutorConsultaActivity;
import com.cibertec.proyecto.vista.consulta.EditorialConsultaActivity;
import com.cibertec.proyecto.vista.consulta.LibroConsultaActivity;
import com.cibertec.proyecto.vista.consulta.ProveedorConsultaActivity;
import com.cibertec.proyecto.vista.consulta.RevistaConsultaActivity;
import com.cibertec.proyecto.vista.consulta.SalaConsultaActivity;
import com.cibertec.proyecto.vista.consulta.UsuarioConsultaActivity;

public class NewAppCompatActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

       

        //Consulta
        if (id == R.id.idMenuConsultaAlumno){
            Intent intent = new Intent(this, AlumnoConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaAutor){
            Intent intent = new Intent(this, AutorConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaEditorial){
            Intent intent = new Intent(this, EditorialConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaLibro){
            Intent intent = new Intent(this, LibroConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaProveedor){
            Intent intent = new Intent(this, ProveedorConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaRevista){
            Intent intent = new Intent(this, RevistaConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaSala){
            Intent intent = new Intent(this, SalaConsultaActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.idMenuConsultaUsuario){
            Intent intent = new Intent(this, UsuarioConsultaActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void mensajeAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    public void mensajeToastLong(String msg){
        Toast toast1 =  Toast.makeText(this,msg, Toast.LENGTH_LONG);
        toast1.show();
    }
    public void mensajeToastShort(String msg){
        Toast toast1 =  Toast.makeText(this,msg, Toast.LENGTH_SHORT);
        toast1.show();
    }



}
