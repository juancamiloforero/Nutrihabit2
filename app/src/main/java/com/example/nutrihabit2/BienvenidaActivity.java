package com.example.nutrihabit2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nutrihabit2.alimentos.AlimentosCrearActivity;
import com.example.nutrihabit2.infoBasica.DatosBasicosActivity;

public class BienvenidaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case (R.id.btnGanarMasa) :
                Toast.makeText(this,"MASA MUSCULAR",Toast.LENGTH_SHORT).show();
                this.irADatosBasicos(1);
                break;
            case (R.id.btnPerderPeso) :
                Toast.makeText(this,"PERDER PESO",Toast.LENGTH_SHORT).show();
                this.irADatosBasicos(2);
                break;
            case (R.id.btnMantenerEstado) :
                Toast.makeText(this,"MANTENER ESTADO",Toast.LENGTH_SHORT).show();
                this.irADatosBasicos(3);
                break;
        }
    }

    public void irADatosBasicos(int parSeleccion) {
        Log.d("TAG3", "NextClicked");
        Intent intent = new Intent(this, DatosBasicosActivity.class);
        intent.putExtra("seleccion",parSeleccion);
        startActivity(intent);
    }
}