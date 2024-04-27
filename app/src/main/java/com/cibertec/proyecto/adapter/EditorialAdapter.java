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
import com.cibertec.proyecto.entity.Editorial;
import com.cibertec.proyecto.entity.Libro;

import java.util.List;

public class EditorialAdapter extends ArrayAdapter<Editorial> {

    private Context context;
    private List<Editorial> lista;

    public EditorialAdapter(@NonNull  Context context, int resource, @NonNull List<Editorial> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable  View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_editorial_consulta_item, parent, false);

        Editorial obj = lista.get(position);

        TextView txtId = row.findViewById(R.id.itemIdEditorial);
        txtId.setText("ID :"+String.valueOf(obj.getIdEditorial()));

        TextView txtRazonSocial = row.findViewById(R.id.itemRazonSocialEditorial);
        txtRazonSocial.setText("Razón Social :"+obj.getRazonSocial());

        TextView txtDireccion = row.findViewById(R.id.itemDireccionEditorial);
        txtDireccion.setText("Direccion :"+obj.getDireccion());

        TextView txtRuc = row.findViewById(R.id.itemRucEditorial);
        txtRuc.setText("RUC :"+obj.getRuc());

        TextView txtFechaCreacion = row.findViewById(R.id.itemFechaCreacionEditorial);
        txtFechaCreacion.setText("Fecha de creación :"+obj.getFechaCreacion());

        TextView txtFechaRegistro = row.findViewById(R.id.itemFechaRegistroEditorial);
        txtFechaRegistro.setText("Fecha de registro :"+obj.getFechaRegistro());

        TextView txtEstado = row.findViewById(com.cibertec.proyecto.R.id.itemEstadoEditorial);
        txtEstado.setText("Estado :"+obj.getEstado());


        TextView txtPais = row.findViewById(R.id.itemPaisEditorial);
        txtPais.setText("País :"+obj.getPais().getNombre());

        TextView txtCategoria = row.findViewById(R.id.itemCategoriaEditorial);
        txtCategoria.setText("Categoria :"+obj.getCategoria().getDescripcion());


        return row;
    }



}





