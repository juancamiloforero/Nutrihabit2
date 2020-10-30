package com.example.nutrihabit2.menuPrincipal.ui.alimentos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.modelos.Alimento;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AlimentosModificarFragment extends Fragment {

    private AlimentosModificarViewModel alimentosModificarViewModel;
    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";

    private Alimento mAlimento;

    private Spinner mTiposAlimentos, mMedidaPorcion;
    private EditText mNombre, mDescripcion, mPorcion, mCalorias, mGrasas, mCarbohidratos, mProteinas;
    private Button mModificar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAlimento = (Alimento) getArguments().getSerializable("alimento");
    }

    public static AlimentosModificarFragment newInstance() { return new AlimentosModificarFragment(); }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.alimentos_crear_fragment, container, false);

        // Cambio de orientación no pierde datos...
        alimentosModificarViewModel = new ViewModelProvider(this).get(AlimentosModificarViewModel.class);
        alimentosModificarViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                // ToDo: Ver que puede cambiar dentro de este fragment!
            }
        });
        return view;
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
        mModificar = view.findViewById(R.id.btAgregarAlimento);

        // Llenar y setear spinners
        ArrayAdapter<CharSequence> aSpinnerTipoAlimentos = ArrayAdapter.createFromResource(getContext(),
                R.array.arrTipoAlimentos, R.layout.alimentos_tipos_item);

        mTiposAlimentos.setAdapter(aSpinnerTipoAlimentos);

        ArrayAdapter<CharSequence> aSpinnerMedidaPorcion = ArrayAdapter.createFromResource(getContext(),
                R.array.arrMedidasPorcion, R.layout.alimentos_tipos_item);
        mMedidaPorcion.setAdapter(aSpinnerMedidaPorcion);

        llenarFormulario();
    }

    private void llenarFormulario() {
        // Set Spinner Tipo Alimentos
        ArrayAdapter<CharSequence> aSpinnerTipoAlimentos = ArrayAdapter.createFromResource(getContext(),
                R.array.arrTipoAlimentos, R.layout.alimentos_tipos_item);
        mTiposAlimentos.setAdapter(aSpinnerTipoAlimentos);
        int spPositionTipoAlimentos = aSpinnerTipoAlimentos.getPosition(mAlimento.getTipoAlimento());
        mTiposAlimentos.setSelection(spPositionTipoAlimentos);

        // Set Spinner Unidad Medida
        ArrayAdapter<CharSequence> aSpinnerMedidaPorcion = ArrayAdapter.createFromResource(getContext(),
                R.array.arrMedidasPorcion, R.layout.alimentos_tipos_item);
        mMedidaPorcion.setAdapter(aSpinnerMedidaPorcion);
        int spPositionMedidaPorcion = aSpinnerMedidaPorcion.getPosition(mAlimento.getUnidadMedida());
        mMedidaPorcion.setSelection(spPositionMedidaPorcion);

        // Set Atributos
        mNombre.setText(mAlimento.getNombre());
        mDescripcion.setText(mAlimento.getDescripcion());
        mPorcion.setText(String.valueOf(mAlimento.getPorcion()));
        mCalorias.setText(String.valueOf(mAlimento.getCalorias()));
        mGrasas.setText(String.valueOf(mAlimento.getGrasas()));
        mCarbohidratos.setText(String.valueOf(mAlimento.getCarbohidratos()));
        mProteinas.setText(String.valueOf(mAlimento.getProteinas()));

        // Set boton
        mModificar.setText(R.string.guardar);
        mModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlimento.setNombre(mNombre.getText().toString());
                mAlimento.setDescripcion(mDescripcion.getText().toString());
                mAlimento.setTipoAlimento(mTiposAlimentos.getSelectedItem().toString());
                mAlimento.setPorcion(Double.parseDouble(mPorcion.getText().toString()));
                mAlimento.setUnidadMedida(mMedidaPorcion.getSelectedItem().toString());
                mAlimento.setCalorias(Double.parseDouble(mCalorias.getText().toString()));
                mAlimento.setGrasas(Double.parseDouble(mGrasas.getText().toString()));
                mAlimento.setCarbohidratos(Double.parseDouble(mCarbohidratos.getText().toString()));
                mAlimento.setProteinas(Double.parseDouble(mProteinas.getText().toString()));

                modificarAlimento(v);
            }
        });

    }

    private void modificarAlimento(View view) {
        if (getUserId() != null) {
            DocumentReference alimentoDocument = db.collection("users").document(getUserId())
                    .collection("alimentos").document(mAlimento.getId());
            alimentoDocument.set(mAlimento);

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