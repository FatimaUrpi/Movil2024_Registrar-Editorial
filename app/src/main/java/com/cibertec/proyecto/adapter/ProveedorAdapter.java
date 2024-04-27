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
import com.cibertec.proyecto.entity.Proveedor;

import java.util.List;

public class ProveedorAdapter extends ArrayAdapter<Proveedor> {
    private Context context;
    private List<Proveedor> lista;

    public ProveedorAdapter(@NonNull Context context, int resource,@NonNull List<Proveedor> lista){
        super(context,resource,lista);
        this.context=context;
        this.lista=lista;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.activity_proveedor_consulta_item, parent, false);

        Proveedor obj = lista.get(position);

        TextView txtId = row.findViewById(R.id.itemIdProveedor);
        txtId.setText("ID :"+String.valueOf(obj.getIdProveedor()));

        TextView txtRazonSocial = row.findViewById(R.id.itemRazonsocial);
        txtRazonSocial.setText("Razón Social :"+obj.getRazonsocial()); //string


        TextView txtRuc = row.findViewById(R.id.itemRuc);
        txtRuc.setText("Ruc :"+obj.getRuc());

        TextView txtDireccion = row.findViewById(R.id.itemDireccion);
        txtDireccion.setText("Dirección :"+obj.getDireccion());

        TextView txtTelefono = row.findViewById(R.id.itemTelefono);
        txtTelefono.setText("Teléfono :"+obj.getTelefono());

        TextView txtCelular = row.findViewById(R.id.itemCelular);
        txtCelular.setText("Celular :"+obj.getCelular());

        TextView txtContacto = row.findViewById(R.id.itemContacto);
        txtContacto.setText("Contacto :"+obj.getContacto());
//cbos
        TextView txtPais = row.findViewById(R.id.itemPaisProveedor);
        txtPais.setText("País :"+obj.getPais().getNombre());

        TextView txtTipoProveedor = row.findViewById(R.id.itemTipoProveedor);
        txtTipoProveedor.setText("Tipo Proveedor :"+obj.getTipoProveedor().getDescripcion());







        return row;}
}
