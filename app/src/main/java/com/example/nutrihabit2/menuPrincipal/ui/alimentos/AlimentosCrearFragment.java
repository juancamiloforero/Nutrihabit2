package com.example.nutrihabit2.menuPrincipal.ui.alimentos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.modelos.Alimento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;

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

        this.obtenerCampos(view);

        mAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (camposEstanCorrectos()){
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
                } else {

                    Toast.makeText(getContext(), "Faltan información por llenar", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });

        // Llenar y setear spinners
        ArrayAdapter<CharSequence> aSpinnerTipoAlimentos = ArrayAdapter.createFromResource(getContext(),
                R.array.arrTipoAlimentos, R.layout.alimentos_tipos_item);

        mTiposAlimentos.setAdapter(aSpinnerTipoAlimentos);

        ArrayAdapter<CharSequence> aSpinnerMedidaPorcion = ArrayAdapter.createFromResource(getContext(),
                R.array.arrMedidasPorcion, R.layout.alimentos_tipos_item);
        mMedidaPorcion.setAdapter(aSpinnerMedidaPorcion);

        this.iniciarVerificacionesCampos();
    }

    private void crearAlimento(Alimento mAlimento, View view) {
        if (getUserId() != null) {
            DocumentReference alimentoDocument = db.collection("users").document(getUserId())
                    .collection("alimentos").document();
            mAlimento.setId(alimentoDocument.getId());
            alimentoDocument.set(mAlimento);

            Toast.makeText(getContext(), "Alimento Creado Satisfactoriamente!", Toast.LENGTH_SHORT)
                    .show();
            // Redirección a la vista principal
            Navigation.findNavController(view).navigate(R.id.nav_alimentos);
        }
    }

    // Retorna el id del usuario guardado en local
    private String getUserId() {
        SharedPreferences sharedPref = getContext().getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        return sharedPref.getString(this.keyUserId, null);
    }

    private void obtenerCampos(View view) {
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
    }

    private boolean camposEstanCorrectos() {
        boolean resultado = true;

        ArrayList<Integer> arrayLayouts = new ArrayList<>(Arrays.asList(
                R.id.etNombreLayout, R.id.etDescripcionLayout, R.id.etPorcionLayout, R.id.etCaloriasLayout, R.id.etGrasasLayout,
                R.id.etCarbohidratosLayout, R.id.etProteinasLayout));

        for(int i = 0; i < arrayLayouts.size(); i++) {
            TextInputLayout floatingLabel = (TextInputLayout) getView().findViewById(arrayLayouts.get(i));
            if (floatingLabel.getError() != null) {
                resultado = false;
            } else if(floatingLabel.getEditText().getText().toString().equals("")) {
                floatingLabel.setError(getString(R.string.errorLongitudNumeroCero));
                floatingLabel.setErrorEnabled(true);
                resultado = false;
            }
        }
        return resultado;
    }

    private void iniciarVerificacionesCampos() {
        verifyNombreLabelError();
        verifyDescripcionLabelError();
        verifyPorcionLabelError();
        verifyCaloriasLabelError();
        verifyGrasasLabelError();
        verifyCarbohidratosLabelError();
        verifyProteinasLabelError();
    }

    private void verifyNombreLabelError() {
        final TextInputLayout nombreAlimentoLayout = (TextInputLayout) getView().findViewById(R.id.etNombreLayout);
        nombreAlimentoLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    nombreAlimentoLayout.setError(getString(R.string.errorLongitudCero));
                    nombreAlimentoLayout.setErrorEnabled(true);
                } else if (s.length() > 255) {
                    nombreAlimentoLayout.setError(getString(R.string.errorLongitudMaxima));
                    nombreAlimentoLayout.setErrorEnabled(true);
                } else if (s.length() >= 1 && s.length() < 3) {
                    nombreAlimentoLayout.setError(getString(R.string.errorLongitudMinima));
                    nombreAlimentoLayout.setErrorEnabled(true);
                } else {
                    nombreAlimentoLayout.setError(null);
                    nombreAlimentoLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void verifyDescripcionLabelError() {
        final TextInputLayout descripcionAlimentoLayout = (TextInputLayout) getView().findViewById(R.id.etDescripcionLayout);
        descripcionAlimentoLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    descripcionAlimentoLayout.setError(getString(R.string.errorLongitudCero));
                    descripcionAlimentoLayout.setErrorEnabled(true);
                } else if (s.length() > 255) {
                    descripcionAlimentoLayout.setError(getString(R.string.errorLongitudMaxima));
                    descripcionAlimentoLayout.setErrorEnabled(true);
                } else if (s.length() >= 1 && s.length() < 3) {
                    descripcionAlimentoLayout.setError(getString(R.string.errorLongitudMinima));
                    descripcionAlimentoLayout.setErrorEnabled(true);
                } else {
                    descripcionAlimentoLayout.setError(null);
                    descripcionAlimentoLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void verifyPorcionLabelError() {
        final TextInputLayout porcionLayout = (TextInputLayout) getView().findViewById(R.id.etPorcionLayout);
        porcionLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    porcionLayout.setError(getString(R.string.errorLongitudNumeroCero));
                    porcionLayout.setErrorEnabled(true);
                } else {
                    porcionLayout.setError(null);
                    porcionLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void verifyCaloriasLabelError() {
        final TextInputLayout caloriasLayout = (TextInputLayout) getView().findViewById(R.id.etCaloriasLayout);
        caloriasLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    caloriasLayout.setError(getString(R.string.errorLongitudNumeroCero));
                    caloriasLayout.setErrorEnabled(true);
                } else {
                    caloriasLayout.setError(null);
                    caloriasLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void verifyGrasasLabelError() {
        final TextInputLayout grasasLayout = (TextInputLayout) getView().findViewById(R.id.etGrasasLayout);
        grasasLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    grasasLayout.setError(getString(R.string.errorLongitudNumeroCero));
                    grasasLayout.setErrorEnabled(true);
                } else {
                    grasasLayout.setError(null);
                    grasasLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void verifyCarbohidratosLabelError() {
        final TextInputLayout carbohidratosLayout = (TextInputLayout) getView().findViewById(R.id.etCarbohidratosLayout);
        carbohidratosLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    carbohidratosLayout.setError(getString(R.string.errorLongitudNumeroCero));
                    carbohidratosLayout.setErrorEnabled(true);
                } else {
                    carbohidratosLayout.setError(null);
                    carbohidratosLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    private void verifyProteinasLabelError() {
        final TextInputLayout proteinasLayout = (TextInputLayout) getView().findViewById(R.id.etProteinasLayout);
        proteinasLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    proteinasLayout.setError(getString(R.string.errorLongitudNumeroCero));
                    proteinasLayout.setErrorEnabled(true);
                } else {
                    proteinasLayout.setError(null);
                    proteinasLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
}