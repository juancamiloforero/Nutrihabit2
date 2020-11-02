package com.example.nutrihabit2.menuPrincipal.ui.seguimiento;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.menuPrincipal.ui.consumo.Alimentos_Consumo_list_Adapter;
import com.example.nutrihabit2.menuPrincipal.ui.consumo.ConsumoListaFragment;
import com.example.nutrihabit2.modelos.Alimento;
import com.example.nutrihabit2.modelos.ConsumoAlimento;
import com.example.nutrihabit2.modelos.ConsumoDia;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SeguimientoMainFragment extends Fragment {

    private RecyclerView mRvSeguimientoList;

    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";

    private ArrayList<ConsumoDia> mConsumoDias;
    private DocumentSnapshot mLastQueriedConsumos;

    public SeguimientoMainFragment() { }

    // TODO: Rename and change types and number of parameters
    public static ConsumoListaFragment newInstance() {
        return new ConsumoListaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.seguimiento_main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar recycler lista
        mRvSeguimientoList = view.findViewById(R.id.rvSeguimientoDiario);
        mRvSeguimientoList.setHasFixedSize(true);

        // Lista
        // Solo decoración
        mRvSeguimientoList.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        // Layout manager
        // LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mRvSeguimientoList.setLayoutManager(layoutManager);

        ArrayList<ConsumoDia> consumos = getConsumos();

        mRvSeguimientoList.setAdapter(new Seguimiento_List_Adapter(consumos));
    }

    private ArrayList<ConsumoDia> getConsumos() {
        Alimento alimento1 = new Alimento("Papa", "Papa parda", "Granos", "g", 100, 10, 10, 10, 10);
        Alimento alimento2 = new Alimento("Arroz", "Arroz blanco", "Granos", "g", 100, 10, 10, 10, 10);
        Alimento alimento3 = new Alimento("Frijoles", "Frijoles negros", "Granos", "g", 100, 10, 10, 10, 10);
        Alimento alimento4 = new Alimento("Carne de res", "Carne de res lomo", "Carnes", "g", 100, 10, 10, 10, 10);
        Alimento alimento5 = new Alimento("Limonada", "Limonada de limon", "Liquido", "ml", 100, 10, 10, 10, 10);
        Alimento alimento6 = new Alimento("Lentejas", "Lentejas", "Granos", "g", 100, 10, 10, 10, 10);

        ArrayList<Alimento> auxAlimentos = new ArrayList<>();
        auxAlimentos.add(alimento1);
        auxAlimentos.add(alimento2);
        auxAlimentos.add(alimento3);
        auxAlimentos.add(alimento4);
        auxAlimentos.add(alimento5);
        auxAlimentos.add(alimento6);

        ArrayList<ConsumoAlimento> auxConsumoAlimento1 = new ArrayList<>();
        ArrayList<ConsumoAlimento> auxConsumoAlimento2 = new ArrayList<>();

        auxConsumoAlimento1.add(new ConsumoAlimento(auxAlimentos.get(1), 100));
        auxConsumoAlimento1.add(new ConsumoAlimento(auxAlimentos.get(3), 70));
        auxConsumoAlimento1.add(new ConsumoAlimento(auxAlimentos.get(4), 80));

        auxConsumoAlimento2.add(new ConsumoAlimento(auxAlimentos.get(2), 100));
        auxConsumoAlimento2.add(new ConsumoAlimento(auxAlimentos.get(0), 500));
        auxConsumoAlimento2.add(new ConsumoAlimento(auxAlimentos.get(5), 90));
        auxConsumoAlimento2.add(new ConsumoAlimento(auxAlimentos.get(3), 200));

        ArrayList<ConsumoDia> auxConsumosDias = new ArrayList<>();

        auxConsumosDias.add(new ConsumoDia(auxConsumoAlimento1, new Date()));
        auxConsumosDias.add(new ConsumoDia(auxConsumoAlimento2, new Date()));

        return auxConsumosDias;

        /*FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference alimentosCollectionRef = db.collection("users")
                .document(getUserId()).collection("consumos_dia");

        Query consumosQuery = null;
        if (mLastQueriedConsumos != null) {
            consumosQuery = alimentosCollectionRef.startAfter(mLastQueriedConsumos);
        } else {
            consumosQuery = alimentosCollectionRef;
        }

        consumosQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documento : task.getResult()) {
                        ConsumoDia consumo = documento.toObject(ConsumoDia.class);
                        mConsumoDias.add(consumo);
                    }

                    if (task.getResult().size() != 0) {
                        mLastQueriedConsumos = task.getResult().getDocuments()
                                .get(task.getResult().size() - 1);
                    }

                    mRvSeguimientoList.getAdapter().notifyDataSetChanged();
                } else {
                    Log.e("Error", "Error al recuperar los alimentos");
                }
            }
        });*/

    }

    // Retorna el id del usuario guardado en local
    private String getUserId() {
        SharedPreferences sharedPref = getContext().getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        return sharedPref.getString(this.keyUserId, null);
    }
}