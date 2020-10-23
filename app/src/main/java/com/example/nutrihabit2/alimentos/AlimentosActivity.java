package com.example.nutrihabit2.alimentos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.nutrihabit2.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class AlimentosActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentos);
        setTitle(R.string.alimento_menu);

        // Back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentListaAlimentos frag = FragmentListaAlimentos.newInstance(0);
        ft.replace(R.id.listaAlimentosContainer, frag);
        ft.commitNow();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.alimentos_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.txCrearAlimento:
                lanzarCrearAlimento();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Back button handler
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void lanzarCrearAlimento() {
        Intent intent = new Intent(this, AlimentosCrearActivity.class);
        startActivity(intent);
    }

}