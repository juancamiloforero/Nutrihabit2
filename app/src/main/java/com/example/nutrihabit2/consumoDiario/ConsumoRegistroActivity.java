package com.example.nutrihabit2.consumoDiario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.menuPrincipal.ui.alimentos.FragmentListaAlimentos;
import com.example.nutrihabit2.seguimiento.SeguimientoListaActivity;

public class ConsumoRegistroActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mButtonGuardarConsumo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumo_registro);
        setTitle("Consumo");
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
}