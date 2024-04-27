package com.cibertec.proyecto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Alumno;
import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno>  {

    private Context context;
    private List<Alumno> lista;

    public AlumnoAdapter(@NonNull Context context, int resource, @NonNull List<Alumno> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_alumno_consulta_item, parent, false);

        Alumno obj = lista.get(position);

        TextView txtId = row.findViewById(R.id.itemIdAlumno);
        txtId.setText("ID :"+String.valueOf(obj.getIdAlumno()));

        TextView txtNombres = row.findViewById(R.id.itemNombresAlumno);
        txtNombres.setText("Nombres :"+obj.getNombres());

        TextView txtApellidos = row.findViewById(R.id.itemApellidosAlumno);
        txtApellidos.setText("Apellidos :"+obj.getApellidos());

        TextView txtTelefono = row.findViewById(R.id.itemTelefonoAlumno);
        txtTelefono.setText("Telefono :"+obj.getNombres());

        TextView txtDni = row.findViewById(R.id.itemDniAlumno);
        txtDni.setText("Dni  :"+obj.getDni());

        TextView txtCorreo = row.findViewById(R.id.itemCorreoAlumno);
        txtCorreo.setText("Correo  :"+obj.getCorreo());

        TextView txtFechaNacimiento = row.findViewById(R.id.itemFechaNacimientoAlumno);
        txtFechaNacimiento.setText("Fecha de Nacimiento :"+obj.getFechaNacimiento());

        TextView txtPais = row.findViewById(R.id.itemPaisAlumno);
        txtPais.setText("País :"+obj.getPais().getNombre());

        TextView txtModalidad = row.findViewById(R.id.itemModalidadAlumno);
        if (obj.getModalidad() != null) {
            txtModalidad.setText("Modalidad :" + obj.getModalidad().getDescripcion());
        } else {
            txtModalidad.setText("Modalidad : Sin especificar");
        }
        return row;
    }
}
