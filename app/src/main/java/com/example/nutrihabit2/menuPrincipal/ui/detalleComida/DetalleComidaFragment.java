package com.example.nutrihabit2.menuPrincipal.ui.detalleComida;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.nutrihabit2.R;

public class DetalleComidaFragment extends Fragment {

    private DetalleComidaViewModel detalleComidaViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        detalleComidaViewModel =
                new ViewModelProvider(this).get(DetalleComidaViewModel.class);
        View root = inflater.inflate(R.layout.fragment_detalle_comida, container, false);
        final TextView calorias = root.findViewById(R.id.txt_d_calorias);
        final TextView carbohidratos = root.findViewById(R.id.txt_d_carbo);
        final TextView proteinas = root.findViewById(R.id.txt_d_proteinas);
        final ImageView imagen = root.findViewById(R.id.img_detalle_com);
        detalleComidaViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                calorias.setText("30 %");
                carbohidratos.setText("30 %");
                proteinas.setText("40");
                imagen.setImageResource(R.drawable.descarga);
            }
        });
        return root;
    }
}