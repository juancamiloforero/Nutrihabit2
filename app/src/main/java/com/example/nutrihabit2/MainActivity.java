package com.example.nutrihabit2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.nutrihabit2.alimentos.AlimentosActivity;
import com.example.nutrihabit2.consumoDiario.ConsumoRegistroActivity;
import com.example.nutrihabit2.seguimiento.SeguimientoListaActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Lanzar Alimentos
        //Intent intent = new Intent(this, AlimentosActivity.class);
        //startActivity(intent);

        // Lanzar registrar consumo
        //Intent intent2 = new Intent(this, ConsumoRegistroActivity.class);
        //startActivity(intent2);

        // Lanzar listar consumo
        //Intent intent3 = new Intent(this, SeguimientoListaActivity.class);
        //startActivity(intent3);
    }
}