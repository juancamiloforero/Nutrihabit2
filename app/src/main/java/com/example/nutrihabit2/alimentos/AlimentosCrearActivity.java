package com.example.nutrihabit2.alimentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
        setTitle(R.string.crear_alimento);

        // Back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    // Back button handler
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}