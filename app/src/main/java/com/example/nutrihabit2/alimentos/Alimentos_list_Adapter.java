package com.example.nutrihabit2.alimentos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrihabit2.R;

import java.util.ArrayList;

public class Alimentos_list_Adapter extends RecyclerView.Adapter<Alimentos_list_Adapter.AlimentosViewHolder>{

    private ArrayList<Alimento> mAlimentos;

    public Alimentos_list_Adapter(ArrayList<Alimento> alimentos) {
        mAlimentos = alimentos;
    }

    @NonNull
    @Override
    public AlimentosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();

        int layoutIdAlimentosItem = R.layout.alimentos_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        boolean fastAttachToParent = false;
        View itemView = inflater.inflate(layoutIdAlimentosItem, parent, fastAttachToParent);

        AlimentosViewHolder viewHolder = new AlimentosViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AlimentosViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if (mAlimentos == null) {
            return 0;
        } else {
            return mAlimentos.size();
        }
    }


    public class AlimentosViewHolder extends RecyclerView.ViewHolder {

        TextView mNombreAlimentoView;

        public AlimentosViewHolder(@NonNull View itemView) {
            super(itemView);

            mNombreAlimentoView = itemView.findViewById(R.id.tvNombreAlimento);
        }

        void bind(int listaIndex) {
             mNombreAlimentoView.setText(String.valueOf(mAlimentos.get(listaIndex).getNombre()));
        }
    }
}
