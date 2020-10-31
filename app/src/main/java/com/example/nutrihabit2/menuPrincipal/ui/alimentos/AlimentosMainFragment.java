package com.example.nutrihabit2.menuPrincipal.ui.alimentos;

import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nutrihabit2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AlimentosMainFragment extends Fragment {

    public static AlimentosMainFragment newInstance() {
        return new AlimentosMainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.alimentos_main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Botón Agregar Alimento
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabCrearAlimento);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.alimentosCrearFragment);
            }
        });

        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        FragmentListaAlimentos frag = FragmentListaAlimentos.newInstance(0);
        ft.replace(R.id.listaAlimentosContainer, frag);
        ft.commit();
    }
}