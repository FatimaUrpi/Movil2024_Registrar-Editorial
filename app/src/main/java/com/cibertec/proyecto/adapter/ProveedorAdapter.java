package com.cibertec.proyecto.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviderGetKt;

import com.cibertec.proyecto.R;
import com.cibertec.proyecto.entity.Proveedor;

import java.util.List;

public class ProveedorAdapter extends ArrayAdapter<Proveedor>  {

    private Context context;
    private List<Proveedor> lista;

    public ProveedorAdapter(@NonNull Context context, int resource, @NonNull List<Proveedor> lista) {
        super(context, resource, lista);
        this.context = context;
        this.lista = lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_proveedor_crud_item,parent,false);

        Proveedor objProveedor = lista.get(position);

        TextView txtIdProveedor = row.findViewById(R.id.txtIdProveedor);
        txtIdProveedor.setText("ID: " + String.valueOf(objProveedor.getIdProveedor()));

        TextView txtRuc = row.findViewById(R.id.txtRuc);
        txtRuc.setText("Ruc: " + String.valueOf(objProveedor.getRuc()));

        TextView txtRazSoc = row.findViewById(R.id.txtRazonSocial);
        txtRazSoc.setText("RazonSocial: " + String.valueOf(objProveedor.getRazonsocial()));

        TextView txtDireccion = row.findViewById(R.id.txtDireccion);
        txtDireccion.setText("Direccion: " + String.valueOf(objProveedor.getDireccion()));

        TextView txtTelefono = row.findViewById(R.id.txtTelefono);
        txtTelefono.setText("Telefono: " + String.valueOf(objProveedor.getTelefono()));

        TextView txtCelular = row.findViewById(R.id.txtCelular);
        txtCelular.setText("Celular: " + String.valueOf(objProveedor.getCelular()));

        TextView txtContacto = row.findViewById(R.id.txtContacto);
        txtContacto.setText("Contacto: " + String.valueOf(objProveedor.getContacto()));

        TextView txtEstado = row.findViewById(R.id.txtEstado);
        txtEstado.setText("Estado: " + String.valueOf(objProveedor.getEstado()));

        TextView txtPais = row.findViewById(R.id.txtPais);
        txtPais.setText("Pais: " + String.valueOf(objProveedor.getPais().getNombre()));

        TextView txtTipoProveedor = row.findViewById(R.id.txtTipoProveedor);
        txtTipoProveedor.setText("Tipo Proveedor: " + String.valueOf(objProveedor.getTipoProveedor().getDescripcion()));



        return row;
    }
}