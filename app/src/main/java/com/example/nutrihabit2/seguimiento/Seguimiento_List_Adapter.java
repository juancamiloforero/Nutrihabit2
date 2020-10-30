package com.example.nutrihabit2.seguimiento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.modelos.Alimento;
import com.example.nutrihabit2.consumoDiario.ConsumoDia;

import java.util.ArrayList;

public class Seguimiento_List_Adapter extends RecyclerView.Adapter<Seguimiento_List_Adapter.SeguimientoDiarioViewHolder> {

    private ArrayList<ConsumoDia> mConsumos;

    public Seguimiento_List_Adapter(ArrayList<ConsumoDia> consumos) {
        this.mConsumos = consumos;
    }

    @NonNull
    @Override
    public Seguimiento_List_Adapter.SeguimientoDiarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();

        int layoutIdAlimentosItem = R.layout.seguimiento_diario_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        boolean fastAttachToParent = false;
        View itemView = inflater.inflate(layoutIdAlimentosItem, parent, fastAttachToParent);

        Seguimiento_List_Adapter.SeguimientoDiarioViewHolder viewHolder = new Seguimiento_List_Adapter.SeguimientoDiarioViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Seguimiento_List_Adapter.SeguimientoDiarioViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {

        if (mConsumos == null) {
            return 0;
        } else {
            return mConsumos.size();
        }
    }

    public class SeguimientoDiarioViewHolder extends RecyclerView.ViewHolder {
        RecyclerView mNombresAlimentosView;
        TextView mFechaSeguimientoView;
        public SeguimientoDiarioViewHolder(@NonNull View itemView) {
            super(itemView);
            mNombresAlimentosView = itemView.findViewById(R.id.rvListaAlimentos);
            mFechaSeguimientoView = itemView.findViewById(R.id.tvFechaSeguimiento);
        }

        void bind(int listaIndex) {
            // Llenar los alimentos
            ArrayList<Alimento> alimentos = new ArrayList<Alimento>();
            String fecha = "Fecha: ";
            fecha = fecha + mConsumos.get(listaIndex).getDia();

            for(int i = 0; i < mConsumos.get(listaIndex).getConsumosDiarios().size(); i++) {
                alimentos.add(mConsumos.get(listaIndex).getConsumosDiarios().get(i).getAlimento());
            }

            // Recycler View
            mNombresAlimentosView.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.VERTICAL, false);
            mNombresAlimentosView.setLayoutManager(layoutManager);

            mNombresAlimentosView.setAdapter(new Alimentos_Seguimiento_list_Adapter(alimentos));

            // Fecha
            mFechaSeguimientoView.setText(fecha);
        }
    }
}
