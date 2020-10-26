package com.example.nutrihabit2.alimentos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nutrihabit2.MainActivity;
import com.example.nutrihabit2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AlimentosCrearActivity extends AppCompatActivity implements View.OnClickListener{

    private Spinner mTiposAlimentos, mMedidaPorcion;
    private String mPrefs = "USER_INFORMATION";
    private String keyUserId = "userId";

    private EditText mNombre, mDescripcion, mPorcion, mCalorias, mGrasas, mCarbohidratos, mProteinas;
    private Button mAgregar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimentos_crear);
        setTitle(R.string.crear_alimento);

        // Back button
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        mAgregar = findViewById(R.id.btAgregarAlimento);

        mAgregar.setOnClickListener(this);

        // Llenar y setear spinners
        ArrayAdapter<CharSequence> aSpinnerTipoAlimentos = ArrayAdapter.createFromResource(this,
                R.array.arrTipoAlimentos, R.layout.alimentos_tipos_item);

        mTiposAlimentos.setAdapter(aSpinnerTipoAlimentos);

        ArrayAdapter<CharSequence> aSpinnerMedidaPorcion = ArrayAdapter.createFromResource(this,
                R.array.arrMedidasPorcion, R.layout.alimentos_tipos_item);
        mMedidaPorcion.setAdapter(aSpinnerMedidaPorcion);

    }

    // Back button handler
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    // Click agregar alimento
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
        this.crearAlimento(mAlimento);
    }

    private void crearAlimento(Alimento mAlimento) {
        if (getUserId() != null) {
            DocumentReference alimentoDocument = db.collection("users").document(getUserId())
                    .collection("alimentos").document();
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