package com.example.nutrihabit2.menuPrincipal.ui.alimentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.modelos.Alimento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AlimentosCrearFragment extends Fragment{

    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";

    private Spinner mTiposAlimentos, mMedidaPorcion;
    private EditText mNombre, mDescripcion, mPorcion, mCalorias, mGrasas, mCarbohidratos, mProteinas;
    private Button mAgregar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static AlimentosCrearFragment newInstance() {
        return new AlimentosCrearFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.alimentos_crear_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar campos
        mTiposAlimentos = view.findViewById(R.id.spTipoAlimento);
        mMedidaPorcion = view.findViewById(R.id.spMedidaPorcion);
        mNombre = view.findViewById(R.id.etNombre);
        mDescripcion = view.findViewById(R.id.etDescripcion);
        mPorcion = view.findViewById(R.id.etPorcion);
        mCalorias = view.findViewById(R.id.etCalorias);
        mGrasas = view.findViewById(R.id.etGrasas);
        mCarbohidratos = view.findViewById(R.id.etCarbohidratos);
        mProteinas = view.findViewById(R.id.etProteinas);
        mAgregar = view.findViewById(R.id.btAgregarAlimento);

        mAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = mNombre.getText().toString();
                String descripcion = mDescripcion.getText().toString();
                String tipoAlimento = mTiposAlimentos.getSelectedItem().toString();
                double porcion = Double.parseDouble(mPorcion.getText().toString());
                String unidadMedida = mMedidaPorcion.getSelectedItem().toString();
                double calorias = Double.parseDouble(mCalorias.getText().toString());
                double grasas = Double.parseDouble(mGrasas.getText().toString());
                double carbo = Double.parseDouble(mCarbohidratos.getText().toString());
                double proteinas = Double.parseDouble(mProteinas.getText().toString());

                Alimento mAlimento = new Alimento(nombre, descripcion, tipoAlimento, unidadMedida, porcion, calorias, grasas, carbo, proteinas);
                crearAlimento(mAlimento, v);
            }
        });

        // Llenar y setear spinners
        ArrayAdapter<CharSequence> aSpinnerTipoAlimentos = ArrayAdapter.createFromResource(getContext(),
                R.array.arrTipoAlimentos, R.layout.alimentos_tipos_item);

        mTiposAlimentos.setAdapter(aSpinnerTipoAlimentos);

        ArrayAdapter<CharSequence> aSpinnerMedidaPorcion = ArrayAdapter.createFromResource(getContext(),
                R.array.arrMedidasPorcion, R.layout.alimentos_tipos_item);
        mMedidaPorcion.setAdapter(aSpinnerMedidaPorcion);

    }

    private void crearAlimento(Alimento mAlimento, View view) {
        if (getUserId() != null) {
            DocumentReference alimentoDocument = db.collection("users").document(getUserId())
                    .collection("alimentos").document();
            mAlimento.setId(alimentoDocument.getId());
            alimentoDocument.set(mAlimento).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Alimento Creado Satisfactoriamente!", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        Log.e("Error", "Error al crear el alimento");
                    }
                }
            });

            // Redirección a la vista principal
            Navigation.findNavController(view).navigate(R.id.nav_alimentos);
        }
    }

    // Retorna el id del usuario guardado en local
    private String getUserId() {
        SharedPreferences sharedPref = getContext().getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        return sharedPref.getString(this.keyUserId, null);
    }
}