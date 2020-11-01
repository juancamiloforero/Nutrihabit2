package com.example.nutrihabit2.menuPrincipal.ui.consumo;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.modelos.Alimento;
import com.example.nutrihabit2.modelos.ConsumoAlimento;

import java.util.ArrayList;

public class Alimentos_Consumo_list_Adapter extends RecyclerView.Adapter<Alimentos_Consumo_list_Adapter.AlimentosViewHolder> {

    private ArrayList<ConsumoAlimento> mConsumosAlimento;

    public Alimentos_Consumo_list_Adapter(ArrayList<ConsumoAlimento> consumoAlimentos) {
        this.mConsumosAlimento = consumoAlimentos;
    }
    @NonNull
    @Override
    public AlimentosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();

        int layoutIdAlimentosItem = R.layout.alimentos_consumo_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View itemView = inflater.inflate(layoutIdAlimentosItem, parent, false);

        Alimentos_Consumo_list_Adapter.AlimentosViewHolder viewHolder = new Alimentos_Consumo_list_Adapter.AlimentosViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlimentosViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mConsumosAlimento != null ?  mConsumosAlimento.size() :  0;
    }

    public ArrayList<ConsumoAlimento> getmConsumosAlimento() {
        return this.mConsumosAlimento;
    }

    public class AlimentosViewHolder extends RecyclerView.ViewHolder{

        TextView mNombreAlimentoView;
        EditText mCantidadAlimentoView;

        public AlimentosViewHolder(@NonNull View itemView) {
            super(itemView);

            mNombreAlimentoView = itemView.findViewById(R.id.tvNombreAlimento);
            mCantidadAlimentoView = itemView.findViewById(R.id.etCantidadConsumo);
        }

        void bind(final int listaIndex) {
            mCantidadAlimentoView.setText(String.valueOf(mConsumosAlimento.get(listaIndex).getCantidadConsumida()));
            mNombreAlimentoView.setText(String.valueOf(mConsumosAlimento.get(listaIndex).getAlimento().getNombre()));

            mCantidadAlimentoView.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().trim().equals("")) {
                        mConsumosAlimento.get(listaIndex).setCantidadConsumida(0);
                    } else {
                        mConsumosAlimento.get(listaIndex).setCantidadConsumida(Double.parseDouble(s.toString()));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) { }
            });
        }
    }
}
