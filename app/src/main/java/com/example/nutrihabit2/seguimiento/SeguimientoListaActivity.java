package com.example.nutrihabit2.seguimiento;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.consumoDiario.ConsumoDia;

import java.util.ArrayList;

public class SeguimientoListaActivity extends AppCompatActivity {

    private RecyclerView mRvSeguimientoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seguimiento_lista);
        setTitle("Seguimiento");

        // Inicializar recycler lista
        mRvSeguimientoList = findViewById(R.id.rvSeguimientoDiario);
        mRvSeguimientoList.setHasFixedSize(true);

        // Lista
        // Solo decoración
        mRvSeguimientoList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        // Layout manager
        // LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRvSeguimientoList.setLayoutManager(layoutManager);
        /*
        // Creación lista temporal de alimentos
        Alimento a1 = new Alimento("Arroz");
        Alimento a2 = new Alimento("Lentejas");
        Alimento a3 = new Alimento("Papa");
        Alimento a4 = new Alimento("Huevos");

        ArrayList<Alimento> auxAlimentos = new ArrayList<Alimento>();
        auxAlimentos.add(a1);
        auxAlimentos.add(a2);
        auxAlimentos.add(a3);
        auxAlimentos.add(a4);

        ArrayList<ConsumoAlimento> auxConsumosAlimentos1 = new ArrayList<ConsumoAlimento>();
        ArrayList<ConsumoAlimento> auxConsumosAlimentos2 = new ArrayList<ConsumoAlimento>();
        */
        ArrayList<ConsumoDia> auxConsumosDias = new ArrayList<ConsumoDia>();

        /*
        auxConsumosAlimentos1.add(new ConsumoAlimento(auxAlimentos.get(0), 100));
        auxConsumosAlimentos1.add(new ConsumoAlimento(auxAlimentos.get(2), 70));
        auxConsumosAlimentos1.add(new ConsumoAlimento(auxAlimentos.get(3), 80));

        auxConsumosDias.add(new ConsumoDia(auxConsumosAlimentos1, "18/10/2020"));

        auxConsumosAlimentos2.add(new ConsumoAlimento(auxAlimentos.get(1), 40));
        auxConsumosAlimentos2.add(new ConsumoAlimento(auxAlimentos.get(3), 50));

        auxConsumosDias.add(new ConsumoDia(auxConsumosAlimentos2, "17/10/2020"));

        auxConsumosDias.add(new ConsumoDia(auxConsumosAlimentos2, "17/10/2020"));
        */
        mRvSeguimientoList.setAdapter(new Seguimiento_List_Adapter(auxConsumosDias));

    }
}