package com.example.nutrihabit2.alimentos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.nutrihabit2.R;

public class AlimentosCrearActivity extends AppCompatActivity {

    private Spinner spinnerTiposAlimentos;
    private Spinner spinnerMedidaPorcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentos_crear);

        // Inicializar spinner
        spinnerTiposAlimentos = findViewById(R.id.spTipoAlimento);
        spinnerMedidaPorcion = findViewById(R.id.spMedidaPorcion);

        // Llenar y setear spinners
        ArrayAdapter<CharSequence> aSpinnerTipoAlimentos = ArrayAdapter.createFromResource(this,
                R.array.arrTipoAlimentos, R.layout.alimentos_tipos_item);

        spinnerTiposAlimentos.setAdapter(aSpinnerTipoAlimentos);

        ArrayAdapter<CharSequence> aSpinnerMedidaPorcion = ArrayAdapter.createFromResource(this,
                R.array.arrMedidasPorcion, R.layout.alimentos_tipos_item);
        spinnerMedidaPorcion.setAdapter(aSpinnerMedidaPorcion);

    }


}