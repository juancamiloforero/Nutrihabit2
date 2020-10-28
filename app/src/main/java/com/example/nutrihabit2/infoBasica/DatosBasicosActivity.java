package com.example.nutrihabit2.infoBasica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nutrihabit2.R;


public class DatosBasicosActivity extends AppCompatActivity {

    EditText etEstatura;
    EditText etPeso;
    EditText etEdad;
    Spinner spGenero;
    Spinner spActividad;

    int objetivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_basicos);
        getSupportActionBar().setTitle(R.string.datos_basicos);

        // Back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.etEstatura = (EditText) findViewById(R.id.etEstatura);
        this.etPeso = (EditText) findViewById(R.id.etPeso);
        this.etEdad = (EditText) findViewById(R.id.etEdad);
        this.spGenero = (Spinner) findViewById(R.id.spGenero);
        this.spActividad = (Spinner) findViewById(R.id.spActividadFisica);

        Intent intent = getIntent();
        this.objetivo = intent.getIntExtra("seleccion", 0);

    }



    public void irAIMC(float estatura, float peso, int edad) {
        Intent intent = new Intent(this, ImcActivity.class);
        intent.putExtra("estatura",estatura);
        intent.putExtra("peso",peso);
        intent.putExtra("edad",edad);

        startActivity(intent);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.btSiguiente):

                //Captura de datos
                float estatura = Float.parseFloat(this.etEstatura.getText().toString());
                float peso = Float.parseFloat(this.etPeso.getText().toString());
                int edad = Integer.parseInt(this.etEdad.getText().toString());
                this.spGenero.getSelectedItem().toString();
                this.spActividad.getSelectedItem().toString();

                this.irAIMC(estatura,peso,edad);

                //Toast.makeText(this, "Genero Seleccionado: "+ this.spGenero.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // Back button handler
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}