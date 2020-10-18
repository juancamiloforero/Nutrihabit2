package com.example.nutrihabit2.alimentos;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.consumoDiario.Alimentos_Consumo_list_Adapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListaAlimentos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListaAlimentos extends Fragment implements Alimentos_list_Adapter.OnAlimentosListener{

    private Spinner spinnerTiposAlimentos;
    private RecyclerView mRvAlimentosList;
    private ArrayList<Alimento> auxAlimentos;

    private int activityParent;

    public FragmentListaAlimentos() { }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param parent Parameter 1.
     * @return A new instance of fragment FragmentListaAlimentos.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListaAlimentos newInstance(int parent) {
        FragmentListaAlimentos fragment = new FragmentListaAlimentos();
        Bundle args = new Bundle();
        args.putInt("activityParent", parent);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            activityParent = getArguments().getInt("activityParent", 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_alimentos, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar recycler lista
        mRvAlimentosList = view.findViewById(R.id.rvAlimentos);

        // Inicializar spinner
        spinnerTiposAlimentos = view.findViewById(R.id.spTipoAlimento);

        // Lista
        // Solo decoración
        mRvAlimentosList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // Layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRvAlimentosList.setLayoutManager(linearLayoutManager);

        // Creación lista temporal de alimentos
        Alimento a1 = new Alimento("Arroz");
        Alimento a2 = new Alimento("Lentejas");
        Alimento a3 = new Alimento("Papa");
        Alimento a4 = new Alimento("Huevos");

        auxAlimentos = new ArrayList<Alimento>();
        auxAlimentos.add(a1);
        auxAlimentos.add(a2);
        auxAlimentos.add(a3);
        auxAlimentos.add(a4);

        // Instanciar el adaptador
        switch (activityParent) {
            case 1:
                mRvAlimentosList.setAdapter(new Alimentos_Consumo_list_Adapter(auxAlimentos));
                break;
            case 0: default:
                mRvAlimentosList.setAdapter(new Alimentos_list_Adapter(auxAlimentos, this));
                break;
        }

        // Spinner
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getContext(),
                R.array.arrTipoAlimentos, R.layout.alimentos_tipos_item);

        spinnerTiposAlimentos.setAdapter(adapterSpinner);
    }


    @Override
    public void onEditAlimentoClick(int position) {
        Log.d("TAG1", "onEditClicked");
        Intent intent = new Intent(getContext(), AlimentosCrearActivity.class);
        startActivity(intent);
    }

    @Override
    public void onDeleteAlimentoClick(int position) {
        Log.d("TAG2", "onDeleteClicked");
    }
}