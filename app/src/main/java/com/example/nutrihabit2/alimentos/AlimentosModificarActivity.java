package com.example.nutrihabit2.alimentos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nutrihabit2.R;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AlimentosModificarActivity extends AppCompatActivity implements View.OnClickListener {

    private Spinner mTiposAlimentos, mMedidaPorcion;
    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";

    private Alimento mAlimento;

    private EditText mNombre, mDescripcion, mPorcion, mCalorias, mGrasas, mCarbohidratos, mProteinas;
    private Button mModificar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentos_crear);
        setTitle(R.string.editar_title);

        // Back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mAlimento = (Alimento) intent.getSerializableExtra("alimento");

        // Inicializar campos
        mTiposAlimentos = findViewById(R.id.spTipoAlimento);
        mMedidaPorcion = findViewById(R.id.spMedidaPorcion);
        mNombre = findViewById(R.id.etNombre);
        mDescripcion = findViewById(R.id.etDescripcion);
        mPorcion = findViewById(R.id.etPorcion);
        mCalorias = findViewById(R.id.etCalorias);
        mGrasas = findViewById(R.id.etGrasas);
        mCarbohidratos = findViewById(R.id.etCarbohidratos);
        mProteinas = findViewById(R.id.etProteinas);
        mModificar = findViewById(R.id.btAgregarAlimento);

        llenarFormulario();
    }

    private void llenarFormulario() {
        // Set Spinner Tipo Alimentos
        ArrayAdapter<CharSequence> aSpinnerTipoAlimentos = ArrayAdapter.createFromResource(this,
                R.array.arrTipoAlimentos, R.layout.alimentos_tipos_item);
        mTiposAlimentos.setAdapter(aSpinnerTipoAlimentos);
        int spPositionTipoAlimentos = aSpinnerTipoAlimentos.getPosition(mAlimento.getTipoAlimento());
        mTiposAlimentos.setSelection(spPositionTipoAlimentos);

        // Set Spinner Unidad Medida
        ArrayAdapter<CharSequence> aSpinnerMedidaPorcion = ArrayAdapter.createFromResource(this,
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
        mModificar.setOnClickListener(this);

    }

    // Back button handler
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

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

        this.modificarAlimento();
    }

    private void modificarAlimento() {
        if (getUserId() != null) {
            DocumentReference alimentoDocument = db.collection("users").document(getUserId())
                    .collection("alimentos").document(mAlimento.getId());
            alimentoDocument.set(mAlimento);

            // Redirección a la vista principal
            Intent intent = new Intent(getApplicationContext(), AlimentosActivity.class);
            startActivity(intent);
        }
    }

    // Retorna el id del usuario guardado en local
    private String getUserId() {
        SharedPreferences sharedPref = getSharedPreferences(this.mPrefs, Context.MODE_PRIVATE);
        return sharedPref.getString(this.keyUserId, null);
    }
}