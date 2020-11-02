package com.example.nutrihabit2.menuPrincipal.ui.consumo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.modelos.Alimento;
import com.example.nutrihabit2.modelos.ConsumoAlimento;
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

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConsumoListaFragment extends Fragment {

    private Spinner spinnerTiposAlimentos;
    private Button mButtonGuardarConsumo;

    private Alimentos_Consumo_list_Adapter mRvAlimentosConsumoListAdapter;
    private RecyclerView mRvAlimentosList;
    private DocumentSnapshot mLastQueriedAlimentos;

    private ArrayList<ConsumoAlimento> mConsumoAlimentos = new ArrayList<>();

    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";

    public ConsumoListaFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // TODO: Rename and change types and number of parameters
    public static ConsumoListaFragment newInstance() {
        return new ConsumoListaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_consumo_lista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mButtonGuardarConsumo = view.findViewById(R.id.btGuardarConsumo);

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

        // Instanciar el adaptador
        mRvAlimentosConsumoListAdapter = new Alimentos_Consumo_list_Adapter(mConsumoAlimentos);
        mRvAlimentosList.setAdapter(mRvAlimentosConsumoListAdapter);

        // Spinner
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getContext(),
                R.array.arrTipoAlimentos, R.layout.alimentos_tipos_item);

        spinnerTiposAlimentos.setAdapter(adapterSpinner);

        // Listener guardar
        mButtonGuardarConsumo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ConsumoAlimento> listaConsumos = mRvAlimentosConsumoListAdapter.getmConsumosAlimento();
                for (int i = 0; i < listaConsumos.size(); i++) {
                    if (listaConsumos.get(i).getCantidadConsumida() == 0) {
                        listaConsumos.remove(i);
                    }
                }
                crearConsumo(listaConsumos);
            }
        });
    }

    private void getAlimentos() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        CollectionReference alimentosCollectionRef = db.collection("users")
                .document(getUserId()).collection("alimentos");

        Query alimentosQuery = null;
        if (mLastQueriedAlimentos != null) {
            alimentosQuery = alimentosCollectionRef.startAfter(mLastQueriedAlimentos);
        } else {
            alimentosQuery = alimentosCollectionRef;
        }

        alimentosQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documento : task.getResult()) {
                        Alimento alimento = documento.toObject(Alimento.class);
                        alimento.setId(documento.getId());
                        mConsumoAlimentos.add(new ConsumoAlimento(alimento, 0));
                    }

                    if (task.getResult().size() != 0) {
                        mLastQueriedAlimentos = task.getResult().getDocuments()
                                .get(task.getResult().size() - 1);
                    }

                    mRvAlimentosConsumoListAdapter.notifyDataSetChanged();
                } else {
                    Log.e("Error", "Error al recuperar los alimentos");
                }
            }
        });
    }

    // Retorna el id del usuario guardado en local
    private String getUserId() {
        SharedPreferences sharedPref = this.getContext().getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        return sharedPref.getString(this.keyUserId, null);
    }

    private void crearConsumo(final ArrayList<ConsumoAlimento> consumos) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (getUserId() != null) {
            final DocumentReference consumosDiaDocument = db.collection("users").document(getUserId())
                    .collection("consumos_dia").document();

            Map<String, Object> objConsumo = new HashMap<>();
            objConsumo.put("timestamp", new Timestamp(new Date()));

            consumosDiaDocument.set(objConsumo).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        // Subdocumento de cantidades con id de alimentos consumidos
                        for (int i = 0; i < consumos.size(); i++) {
                            DocumentReference consumoDocument = consumosDiaDocument.collection("consumo").document();
                            Map<String, Object> objCantidades = new HashMap<>();
                            objCantidades.put("cantidad", consumos.get(i).getCantidadConsumida());
                            objCantidades.put("alimento", consumos.get(i).getAlimento().getId());
                            consumoDocument.set(objCantidades);
                        }

                        Toast.makeText(getContext(), "Consumo Registrado Satisfactoriamente!", Toast.LENGTH_SHORT)
                                .show();

                    } else {
                        Log.e("Error", "Error al guardar los consumos los alimentos");
                    }
                }
            });
        }
    }
}