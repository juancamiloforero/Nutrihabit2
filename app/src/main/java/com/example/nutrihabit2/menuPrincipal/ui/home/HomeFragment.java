package com.example.nutrihabit2.menuPrincipal.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.nutrihabit2.menuPrincipal.ui.alimentos.FragmentListaAlimentos;
import com.example.nutrihabit2.menuPrincipal.ui.consumo.ConsumoCrearActivity;
import com.example.nutrihabit2.menuPrincipal.ui.detalleComida.DetalleCom;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private  View root;
    private int objetivo=1;//1 subir, 2 bajar, 3 mantener
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        this.obtenerObjetivo(homeViewModel,root);

        ImageButton desayuno = root.findViewById(R.id.ibtn_desayuno);
        ImageButton almuerzo = root.findViewById(R.id.ibtn_almuerzo);
        ImageButton cena = root.findViewById(R.id.ibtn_cena);
        ImageButton bocadillos = root.findViewById(R.id.ibtn_bocadillos);
        desayuno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //getParentFragmentManager();
                Intent intent = new Intent(getActivity(),
                        DetalleCom.class);
                intent.putExtra("objetivo", objetivo);
                intent.putExtra("tipoCom", "desayuno");
                startActivity(intent);
            }
        });
        almuerzo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //getParentFragmentManager();
                Intent intent = new Intent(getActivity(),
                        DetalleCom.class);
                intent.putExtra("objetivo", objetivo);
                intent.putExtra("tipoCom", "almuerzo");
                startActivity(intent);
            }
        });
        cena.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //getParentFragmentManager();
                Intent intent = new Intent(getActivity(),
                        DetalleCom.class);
                intent.putExtra("objetivo", objetivo);
                intent.putExtra("tipoCom", "cena");
                startActivity(intent);
            }
        });
        bocadillos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //getParentFragmentManager();
                Intent intent = new Intent(getActivity(),
                        DetalleCom.class);
                intent.putExtra("objetivo", objetivo);
                intent.putExtra("tipoCom", "bocadillos");
                startActivity(intent);
            }
        });

        Button consumo = root.findViewById(R.id.btn_consumo);
        consumo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //getParentFragmentManager();
                Intent intent = new Intent(getActivity(), ConsumoCrearActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    private String getUserId() {
        SharedPreferences sharedPref = getActivity().getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        return sharedPref.getString(this.keyUserId, null);
    }

    private void obtenerObjetivo(HomeViewModel home, final View root) {
        if (this.getUserId() != null) {

            db.collection("users").document(getUserId()).addSnapshotListener(
                    new EventListener<DocumentSnapshot>() {
                        @Override
                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                            //System.out.println(documentSnapshot.getData());
                            long ax= (long) documentSnapshot.get("proposito");
                            objetivo= (int) ax;
                            datosPorcentajeVista(homeViewModel,root,"desy");
                            datosPorcentajeVista(homeViewModel,root,"almuerzo");
                            datosPorcentajeVista(homeViewModel,root,"cena");
                            datosPorcentajeVista(homeViewModel,root,"boca");
                        }
                    }
            );

        }
    }

    public void datosPorcentajeVista(HomeViewModel home,View root,String tipo){
        TextView grasas=null;
        TextView carbo=null;
        TextView proteinas=null;
        switch (tipo){
            case "desy":
                grasas = root.findViewById(R.id.txt_grasas_desy);
                carbo = root.findViewById(R.id.txt_carbo_desy);
                proteinas = root.findViewById(R.id.txt_proteinas_desy);
                break;
            case "almuerzo":
                grasas = root.findViewById(R.id.txt_grasas_almuerzo);
                carbo = root.findViewById(R.id.txt_carbo_almuerzo);
                proteinas = root.findViewById(R.id.txt_proteinas_almuerzo);
                break;
            case "cena":
                grasas = root.findViewById(R.id.txt_grasas_cena);
                carbo = root.findViewById(R.id.txt_carbo_cena);
                proteinas = root.findViewById(R.id.txt_proteinas_cena);
                break;
            case "boca":
                grasas = root.findViewById(R.id.txt_grasas_boca);
                carbo = root.findViewById(R.id.txt_carbo_boca);
                proteinas = root.findViewById(R.id.txt_proteinas_boca);
                break;
        }

        switch (this.objetivo){
            case 1:
                final TextView finalGrasas = grasas;
                final TextView finalCarbo = carbo;
                final TextView finalProteinas = proteinas;
                home.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        finalGrasas.setText("35 %");
                        finalCarbo.setText("25 %");
                        finalProteinas.setText("40 %");
                    }
                });
                break;
            case 2:
                final TextView finalGrasas1 = grasas;
                final TextView finalCarbo1 = carbo;
                final TextView finalProteinas1 = proteinas;
                home.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        finalGrasas1.setText("15 %");
                        finalCarbo1.setText("60 %");
                        finalProteinas1.setText("25 %");
                    }
                });
                break;
            case 3:
                final TextView finalGrasas2 = grasas;
                final TextView finalCarbo2 = carbo;
                final TextView finalProteinas2 = proteinas;
                home.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        finalGrasas2.setText("25 %");
                        finalCarbo2.setText("50 %");
                        finalProteinas2.setText("25 %");
                    }
                });
                break;
        }
    }
}