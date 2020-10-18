package com.example.nutrihabit2.alimentos;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nutrihabit2.R;

import java.util.ArrayList;

public class Alimentos_list_Adapter extends RecyclerView.Adapter<Alimentos_list_Adapter.AlimentosViewHolder>{

    private ArrayList<Alimento> mAlimentos;
    private OnAlimentosListener mOnAlimentosListener;

    public Alimentos_list_Adapter(ArrayList<Alimento> alimentos, OnAlimentosListener onAlimentosListener) {
        mAlimentos = alimentos;
        this.mOnAlimentosListener = onAlimentosListener;
    }

    @NonNull
    @Override
    public AlimentosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context mContext = parent.getContext();

        int layoutIdAlimentosItem = R.layout.alimentos_list_item;
        LayoutInflater inflater = LayoutInflater.from(mContext);

        boolean fastAttachToParent = false;
        View itemView = inflater.inflate(layoutIdAlimentosItem, parent, fastAttachToParent);

        AlimentosViewHolder viewHolder = new AlimentosViewHolder(itemView, mOnAlimentosListener);

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

    public class AlimentosViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView mNombreAlimentoView;
        ImageButton mEliminarBoton;
        ImageButton mEditarBoton;
        OnAlimentosListener onAlimentosListener;

        public AlimentosViewHolder(@NonNull View itemView, final OnAlimentosListener onAlimentosListener) {
            super(itemView);

            mNombreAlimentoView = itemView.findViewById(R.id.tvNombreAlimento);
            mEliminarBoton = itemView.findViewById(R.id.btEliminarAlimento);
            mEditarBoton = itemView.findViewById(R.id.btEditarAlimento);

            this.onAlimentosListener = onAlimentosListener;
            itemView.setOnClickListener(this);

            mEliminarBoton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onAlimentosListener.onDeleteAlimentoClick(getAdapterPosition());
                }
            });

            mEditarBoton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    onAlimentosListener.onEditAlimentoClick(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View v) {
            // ToDo: On click para el alimento, ver sus detalles
        }

        void bind(int listaIndex) {
             mNombreAlimentoView.setText(String.valueOf(mAlimentos.get(listaIndex).getNombre()));
        }


    }

    public interface OnAlimentosListener {
        // Interfaz para implementar y escuchar los botones de eliminar, editar y ver alimentos
        void onEditAlimentoClick(int position);
        void onDeleteAlimentoClick(int position);
    }
}
