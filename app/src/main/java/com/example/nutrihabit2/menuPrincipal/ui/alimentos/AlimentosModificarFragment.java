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
import android.widget.Toast;

import com.example.nutrihabit2.R;
import com.example.nutrihabit2.modelos.Alimento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class AlimentosModificarFragment extends Fragment {

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
        return inflater.inflate(R.layout.alimentos_crear_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar campos
        this.obtenerCampos(view);

        // Llenar y setear spinners
        ArrayAdapter<CharSequence> aSpinnerTipoAlimentos = ArrayAdapter.createFromResource(getContext(),
                R.array.arrTipoAlimentos, R.layout.alimentos_tipos_item);

        mTiposAlimentos.setAdapter(aSpinnerTipoAlimentos);

        ArrayAdapter<CharSequence> aSpinnerMedidaPorcion = ArrayAdapter.createFromResource(getContext(),
                R.array.arrMedidasPorcion, R.layout.alimentos_tipos_item);
        mMedidaPorcion.setAdapter(aSpinnerMedidaPorcion);

        this.llenarFormulario();
        this.iniciarVerificacionesCampos();

        // Set boton
        mModificar.setText(R.string.guardar);
        mModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (camposEstanCorrectos()){
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
                } else {
                    Toast.makeText(getContext(), "Faltan información por llenar", Toast.LENGTH_SHORT)
                            .show();
                }
            }
        });
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
        mModificar = view.findViewById(R.id.btAgregarAlimento);
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

        DecimalFormat df = new DecimalFormat("0.##");
        mPorcion.setText(df.format(mAlimento.getPorcion()));
        mCalorias.setText(df.format(mAlimento.getCalorias()));
        mGrasas.setText(df.format(mAlimento.getGrasas()));
        mCarbohidratos.setText(df.format(mAlimento.getCarbohidratos()));
        mProteinas.setText(df.format(mAlimento.getProteinas()));

    }

    private void modificarAlimento(View view) {
        if (getUserId() != null) {
            DocumentReference alimentoDocument = db.collection("users").document(getUserId())
                    .collection("alimentos").document(mAlimento.getId());
            alimentoDocument.set(mAlimento);

            Toast.makeText(getContext(), "Alimento Editado Satisfactoriamente!", Toast.LENGTH_SHORT)
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