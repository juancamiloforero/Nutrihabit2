package com.example.nutrihabit2.infoBasica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.menuPrincipal.menuPrincipal;

public class ImcActivity extends AppCompatActivity {

    float estatura, peso;
    int edad;
    TextView tvTuIMC, tvAltura, tvPeso, tvClasificacion, tvEdad;
    ImageView imgIMC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imc);
        setTitle(R.string.indice_masa_corporal);

        // Back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.tvTuIMC = (TextView) findViewById(R.id.tvTuIMC);
        this.tvAltura = (TextView) findViewById(R.id.tvAltura);
        this.tvPeso = (TextView) findViewById(R.id.tvPeso);
        this.tvEdad = (TextView) findViewById(R.id.tvEdad);
        this.tvClasificacion = (TextView) findViewById(R.id.tvClasificacion);
        this.imgIMC = (ImageView) findViewById(R.id.imgIMC);

        Intent intent = getIntent();
        this.estatura = intent.getFloatExtra("estatura",0);
        this.peso = intent.getFloatExtra("peso",0);
        this.edad = intent.getIntExtra("edad",0);
        String strIMC = this.calcularIMC(this.estatura,this.peso);

        this.tvTuIMC.setText(getString(R.string.tu_imc_es) + strIMC);
        this.tvAltura.setText((int) estatura + " cm");
        this.tvPeso.setText((int) peso + " kg");
        this.tvEdad.setText(Integer.toString(edad));


    }

    private String calcularIMC(float estatura, float peso) {
        estatura = estatura / 100;
        float imc = peso / (estatura * estatura);

        this.tvClasificacion.setText( this.clasificarIMC(imc));

        return String.format("%.1f", imc);
    }

    private int clasificarIMC(float parIMC) {

        int clasificacion = 0;
        if (parIMC < 18.5) {
            //clasificacion = "Peso inferior al normal";
            clasificacion = R.string.peso_inferior_al_normal;
            this.imgIMC.setBackgroundResource(R.drawable.imc_azul);
            this.tvClasificacion.setTextColor(Color.rgb(37, 172, 227));
        } else  if (parIMC >= 18.5 && parIMC <25) {
            //clasificacion = "Peso Normal";
            clasificacion = R.string.peso_normal;
            this.imgIMC.setBackgroundResource(R.drawable.imc_verde);
            this.tvClasificacion.setTextColor(Color.rgb(107, 127, 56));
        } else if (parIMC >= 25 && parIMC <30) {
            //clasificacion = "Peso superior al normal";
            clasificacion = R.string.peso_superior_al_normal;
            this.imgIMC.setBackgroundResource(R.drawable.imc_amarillo);
            this.tvClasificacion.setTextColor(Color.rgb(238, 169, 30));
        } else if (parIMC > 30) {
            //clasificacion = "Obesidad";
            clasificacion = R.string.obesidad;
            this.imgIMC.setBackgroundResource(R.drawable.imc_naranja);
            this.tvClasificacion.setTextColor(Color.rgb(231, 117, 44));
        }
        return  clasificacion;
    }

    // Back button handler
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btSiguiente:
                Intent intentMenu = new Intent(this, menuPrincipal.class);
                startActivity(intentMenu);
        }
    }
}