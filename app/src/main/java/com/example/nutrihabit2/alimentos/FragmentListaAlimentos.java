package com.example.nutrihabit2.alimentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.nutrihabit2.seguimiento.Seguimiento_List_Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentListaAlimentos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListaAlimentos extends Fragment implements Alimentos_list_Adapter.OnAlimentosListener {

    private Spinner spinnerTiposAlimentos;
    private Alimentos_list_Adapter mRvAlimentosListAdapter;
    private Alimentos_Consumo_list_Adapter mRvAlimentosConsumoListAdapter;
    private RecyclerView mRvAlimentosList;
    private ArrayList<Alimento> mAlimentos;
    private DocumentSnapshot mLastQueriedAlimentos;

    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    private int activityParent;

    public FragmentListaAlimentos() {
    }

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
        mAlimentos = new ArrayList<Alimento>();
        getAlimentos();
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

        // Instanciar el adaptador
        switch (activityParent) {
            case 1:
                mRvAlimentosConsumoListAdapter = new Alimentos_Consumo_list_Adapter(mAlimentos);
                mRvAlimentosList.setAdapter(mRvAlimentosConsumoListAdapter);
                break;
            case 0:
            default:
                mRvAlimentosListAdapter = new Alimentos_list_Adapter(mAlimentos, this);
                mRvAlimentosList.setAdapter(mRvAlimentosListAdapter);
                break;
        }

        // Spinner
        ArrayAdapter<CharSequence> adapterSpinner = ArrayAdapter.createFromResource(getContext(),
                R.array.arrTipoAlimentos, R.layout.alimentos_tipos_item);

        spinnerTiposAlimentos.setAdapter(adapterSpinner);
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
                        mAlimentos.add(alimento);
                    }

                    if (task.getResult().size() != 0) {
                        mLastQueriedAlimentos = task.getResult().getDocuments()
                                .get(task.getResult().size() - 1);
                    }

                    mRvAlimentosList.getAdapter().notifyDataSetChanged();
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


    @Override
    public void onEditAlimentoClick(int position) {
        Intent intent = new Intent(getContext(), AlimentosModificarActivity.class);
        intent.putExtra("alimento", mAlimentos.get(position));
        startActivity(intent);
    }

    @Override
    public void onDeleteAlimentoClick(int position) {
        openDialog(position);
    }

    private void openDialog(final int position) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.advertencia_eliminar)
                .setMessage(R.string.confirmar_eliminar)

                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        borrarAlimento(position);
                    }
                })

                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void borrarAlimento(final int position) {
        if (getUserId() != null) {
            DocumentReference alimentoDocument = db.collection("users").document(getUserId())
                    .collection("alimentos").document(mAlimentos.get(position).getId());
            alimentoDocument.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        mRvAlimentosListAdapter.removeAlimento(position);
                    } else {
                        Log.e("Error", "Error al recuperar los alimentos");
                    }
                }
            });
        }
    }
}