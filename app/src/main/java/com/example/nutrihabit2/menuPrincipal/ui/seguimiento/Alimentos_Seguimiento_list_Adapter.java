package com.example.nutrihabit2.menuPrincipal.ui.seguimiento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.modelos.Alimento;
import com.example.nutrihabit2.modelos.ConsumoAlimento;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Alimentos_Seguimiento_list_Adapter extends RecyclerView.Adapter<Alimentos_Seguimiento_list_Adapter.AlimentosViewHolder> {

    private ArrayList<ConsumoAlimento> mAlimentos;

    public Alimentos_Seguimiento_list_Adapter(ArrayList<ConsumoAlimento> alimentos) {
        this.mAlimentos = alimentos;
    }

    @NonNull
    @Override
    public Alimentos_Seguimiento_list_Adapter.AlimentosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();

        int layoutIdAlimentosItem = R.layout.alimentos_seguimiento_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        boolean fastAttachToParent = false;
        View itemView = inflater.inflate(layoutIdAlimentosItem, parent, fastAttachToParent);

        Alimentos_Seguimiento_list_Adapter.AlimentosViewHolder viewHolder = new Alimentos_Seguimiento_list_Adapter.AlimentosViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Alimentos_Seguimiento_list_Adapter.AlimentosViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mAlimentos != null ?  mAlimentos.size() :  0;
    }

    public class AlimentosViewHolder extends RecyclerView.ViewHolder {

        TextView mNombreAlimentoView;
        TextView mCantidadConsumidaView;
        TextView mUnidadMedidaView;

        public AlimentosViewHolder(@NonNull View itemView) {
            super(itemView);

            mNombreAlimentoView = itemView.findViewById(R.id.tvNombreAlimento);
            mCantidadConsumidaView = itemView.findViewById(R.id.tvCantidadConsumida);
            mUnidadMedidaView = itemView.findViewById(R.id.tvUnidadMedida);
        }

        void bind(int listaIndex) {
            mNombreAlimentoView.setText(String.valueOf(mAlimentos.get(listaIndex).getAlimento().getNombre()));

            DecimalFormat df = new DecimalFormat("0.#");
            mCantidadConsumidaView.setText(df.format(mAlimentos.get(listaIndex).getCantidadConsumida()));

            mUnidadMedidaView.setText(String.valueOf(mAlimentos.get(listaIndex).getAlimento().getUnidadMedida()));
        }
    }
}
