package com.example.nutrihabit2.consumoDiario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Spinner;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.alimentos.Alimento;
import com.example.nutrihabit2.alimentos.FragmentListaAlimentos;

import java.util.ArrayList;

public class ConsumoRegistroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumo_registro);
        setTitle("Consumo");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentListaAlimentos frag = FragmentListaAlimentos.newInstance(1);
        ft.replace(R.id.listaAlimentosContainer, frag);
        ft.commitNow();
    }
}