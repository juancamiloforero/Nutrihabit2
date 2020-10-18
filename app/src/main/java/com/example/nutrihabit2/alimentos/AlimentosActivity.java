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

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        FragmentListaAlimentos frag = FragmentListaAlimentos.newInstance(0);
        ft.replace(R.id.listaAlimentosContainer, frag);
        ft.commitNow();
    }

}