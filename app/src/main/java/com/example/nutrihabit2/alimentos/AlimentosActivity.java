package com.example.nutrihabit2.alimentos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.nutrihabit2.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AlimentosActivity extends AppCompatActivity {

    private RecyclerView mRvAlimentosList;
    private ArrayList<Alimento> auxAlimentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alimentos_list);

        // Extraer el recycler de la lista de alimentos
        mRvAlimentosList = findViewById(R.id.rvAlimentos);

        // Solo decoración
        mRvAlimentosList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvAlimentosList.setLayoutManager(linearLayoutManager);

        // Creación lista temporal de alimentos
        Alimento a1 = new Alimento("Arroz");
        Alimento a2 = new Alimento("Lentejas");
        Alimento a3 = new Alimento("Papa");
        Alimento a4 = new Alimento("Huevos");

        auxAlimentos = new ArrayList<Alimento>();
        auxAlimentos.add(a1);
        auxAlimentos.add(a2);
        auxAlimentos.add(a3);
        auxAlimentos.add(a4);

        // Instanciar el adaptador
        Alimentos_list_Adapter mAdapter = new Alimentos_list_Adapter(auxAlimentos);

        // Setear el adaptador al recycler de alimentos
        mRvAlimentosList.setAdapter(mAdapter);

    }
}