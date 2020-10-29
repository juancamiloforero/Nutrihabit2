package com.example.nutrihabit2.menuPrincipal.ui.home;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import  com.example.nutrihabit2.R;
import com.example.nutrihabit2.alimentos.FragmentListaAlimentos;
import com.example.nutrihabit2.infoBasica.ImcActivity;
import com.example.nutrihabit2.menuPrincipal.ui.detalleComida.DetalleComidaFragment;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.txt_calorias_desy);
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText("30 %");
            }
        });

        ImageButton desayuno = root.findViewById(R.id.ibtn_desayuno);
        ImageButton almuerzo = root.findViewById(R.id.ibtn_almuerzo);
        ImageButton cena = root.findViewById(R.id.ibtn_cena);
        ImageButton bocadillos = root.findViewById(R.id.ibtn_bocadillos);
        desayuno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //getParentFragmentManager();

                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.drawer_layout, new DetalleComidaFragment());
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return root;
    }

    /*@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton desayuno = view.findViewById(R.id.ibtn_desayuno);
        ImageButton almuerzo = view.findViewById(R.id.ibtn_almuerzo);
        ImageButton cena = view.findViewById(R.id.ibtn_cena);
        ImageButton bocadillos = view.findViewById(R.id.ibtn_bocadillos);
        desayuno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //getParentFragmentManager();
                Intent intent = new Intent(container, ImcActivity.class);
                DetalleComidaFragment detalle = new DetalleComidaFragment();
                FragmentManager fragmentManager = getActivity().getFragmentManager();
                FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                transaction.replace(R.id.container, detalle);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
    }*/
}