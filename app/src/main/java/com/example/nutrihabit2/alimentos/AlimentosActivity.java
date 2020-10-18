package com.example.nutrihabit2.alimentos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.nutrihabit2.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AlimentosActivity extends AppCompatActivity implements Alimentos_list_Adapter.OnAlimentosListener{

    private static final String TAG_ALIMENTOS_LISTA_FRAGMENT="TAG_ALIMENTOS_LISTA_FRAGMENT";
    private static final String TAG_ALIMENTOS_CREAR_FRAGMENT="TAG_ALIMENTOS_CREAR_FRAGMENT";

    private Spinner spinnerTiposAlimentos;
    private RecyclerView mRvAlimentosList;
    private ArrayList<Alimento> auxAlimentos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentos);
        setTitle(R.string.alimento_menu);

        // Inicializar recycler lista
        mRvAlimentosList = findViewById(R.id.rvAlimentos);

        // Inicializar spinner
        spinnerTiposAlimentos = findViewById(R.id.spTipoAlimento);

        // Lista
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
        Alimentos_list_Adapter mAdapter = new Alimentos_list_Adapter(auxAlimentos, this);

        // Setear el adaptador al recycler de alimentos
        mRvAlimentosList.setAdapter(mAdapter);


        // Spinner
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(this,
                R.array.arrTipoAlimentos, R.layout.alimentos_tipos_item);

        spinnerTiposAlimentos.setAdapter(adapterSpinner);

    }

    @Override
    public void onEditAlimentoClick(int position) {
        Log.d("TAG1", "onEditClicked");
        Intent intent = new Intent(this, AlimentosCrearActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDeleteAlimentoClick(int position) {
        Log.d("TAG2", "onDeleteClicked");
    }

}