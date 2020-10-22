package com.example.nutrihabit2.consumoDiario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.alimentos.Alimento;
import com.example.nutrihabit2.alimentos.FragmentListaAlimentos;
import com.example.nutrihabit2.seguimiento.SeguimientoListaActivity;

import java.util.ArrayList;

public class ConsumoRegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonGuardarConsumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumo_registro);
        setTitle("Consumo");

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentListaAlimentos frag = FragmentListaAlimentos.newInstance(1);
        ft.replace(R.id.listaAlimentosContainer, frag);
        ft.commitNow();

        mButtonGuardarConsumo = findViewById(R.id.btGuardarConsumo);

        mButtonGuardarConsumo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, SeguimientoListaActivity.class);
        startActivity(intent);
    }
}