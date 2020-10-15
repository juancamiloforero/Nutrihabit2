package com.example.nutrihabit2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class bienvenidaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
    }

    public void onClick(View view) {
        switch(view.getId()) {
            case (R.id.btnGanarMasa) :
                Toast.makeText(this,"MASA MUSCULAR",Toast.LENGTH_SHORT).show();
                break;
            case (R.id.btnPerderPeso) :
                Toast.makeText(this,"PERDER PESO",Toast.LENGTH_SHORT).show();
                break;
            case (R.id.btnMantenerEstado) :
                Toast.makeText(this,"MANTENER ESTADO",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}