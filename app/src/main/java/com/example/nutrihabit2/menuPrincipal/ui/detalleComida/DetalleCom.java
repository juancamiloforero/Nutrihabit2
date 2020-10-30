package com.example.nutrihabit2.menuPrincipal.ui.detalleComida;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.menuPrincipal.ui.home.HomeViewModel;

public class DetalleCom extends AppCompatActivity {

    TextView grasas, carbohidratos, proteinas;
    ImageView imagen;
    private int objetivo=2;//1 subir, 2 bajar, 3 mantener

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_com);

        Intent intent = getIntent();
        this.objetivo = intent.getIntExtra("objetivo",1);
        String tipo = intent.getStringExtra("tipoCom");

        /*calorias = (TextView) findViewById(R.id.txt_d_calorias);
        carbohidratos = (TextView) findViewById(R.id.txt_d_carbo);
        proteinas = (TextView) findViewById(R.id.txt_d_proteinas);
        imagen = (ImageView) findViewById(R.id.img_detalle_com);
        Intent intent = getIntent();
        String cal = intent.getStringExtra("calorias");

        calorias.setText(cal);
        carbohidratos.setText("30 %");
        proteinas.setText("40%");
        imagen.setImageResource(R.drawable.descarga);*/
        this.datosVista(intent,tipo);
    }

    public void datosVista(Intent intent, String tipo){
        grasas = (TextView) findViewById(R.id.txt_d_calorias);
        carbohidratos = (TextView) findViewById(R.id.txt_d_carbo);
        proteinas = (TextView) findViewById(R.id.txt_d_proteinas);
        imagen = (ImageView) findViewById(R.id.img_detalle_com);

        switch (tipo){
            case "desayuno":
                imagen.setImageResource(R.drawable.descarga);
                break;
            case "almuerzo":
                imagen.setImageResource(R.drawable.almuerzo);
                break;
            case "cena":
                imagen.setImageResource(R.drawable.cena);
                break;
            case "bocadillos":
                imagen.setImageResource(R.drawable.bocadillo);
                break;
        }

        switch (this.objetivo){
            case 1:
                grasas.setText("35 %");
                carbohidratos.setText("25 %");
                proteinas.setText("40%");
                break;
            case 2:
                grasas.setText("15 %");
                carbohidratos.setText("60 %");
                proteinas.setText("25 %");
                break;
            case 3:
                grasas.setText("25 %");
                carbohidratos.setText("50 %");
                proteinas.setText("25 %");
                break;
        }
    }

}